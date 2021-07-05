import React, { useState, useEffect } from 'react';
import '../../App.css';
import '../../css/projects.css';
import SideNavBar from '../../components/SideNavBar.js'
import Topbar from '../../components/Topbar.js'
import Backlog from '../../components/Backlog.js'
import { Link, useHistory, useLocation } from 'react-router-dom'
import IssuePopUp from '../../components/IssuePopUp.js'
import TaskInfoPopUp from '../../components/TaskInfoPopUp.js'
import StoriesInEpicsPopUp from '../../components/StoriesInEpicsPopUp.js'
import { OverlayTrigger, Popover} from 'react-bootstrap'
import TasksInStoryOfSprintPopUp from '../../components/TasksInStoryOfSprintPopUp.js'
import deleteicon from '../../images/delete.png'



function ProjectNoPage() {

    const history = useHistory();
    const loggedUser = JSON.parse(localStorage.getItem('loggedUser'));
    const location = useLocation();
    
    let projectId = null//location.state.projectId;
    let projectName = 100//location.state.projectName;
    if (location.state){
        projectId = location.state.projectId;
        projectName = location.state.projectName;
    }
    else {
        history.push("/projects");
    }
    //const projectId = 100//location.state.projectId;
    //const projectName = 100//location.state.projectName;

    useEffect(() => {
        document.body.style.background = "#fff";

        if (!loggedUser){
            history.push("/login");
        }
        else{
            // Fetch Epics
            fetch('http://localhost:8080/projects/'+ projectId +'/epics', {
                method: 'get', 
                headers: { Authorization: 'Bearer ' + loggedUser.accessToken }
            })
                .then(res => res.json())
                .then((data) => {
                    console.log("Epics:");
                    console.log(data);
                    if (data._embedded){
                        setEpicList(data._embedded.epicList)
                    }
                })
            // Fetch Active Sprints
            fetch('http://localhost:8080/projects/' + projectId + '/sprints/active', {
                method: 'get', 
                headers: { Authorization: 'Bearer ' + loggedUser.accessToken }
            })
                .then(res => res.json())
                .then((data) => {
                    console.log("ActiveSprints:");
                    console.log(data);
                    if (data._embedded){
                        setActiveSprints(data._embedded.sprintList)
                        changeSprShown(data._embedded.sprintList)
                    }
                    return data
                })
            // Fetch Stories of Active
            fetch('http://localhost:8080/projects/' + projectId + '/sprints/active/storiesInList', {
                method: 'get', 
                headers: { Authorization: 'Bearer ' + loggedUser.accessToken }
            })
                .then(res => res.json())
                .then((data) => {
                    console.log("These are the active stories:")
                    console.log(data);
                    setActiveStories(data)
                    changeStorShown(data)
                })
            // Fetch Inactive Sprints
            fetch('http://localhost:8080/projects/' + projectId + '/sprints/archived', {
                method: 'get', 
                headers: { Authorization: 'Bearer ' + loggedUser.accessToken }
            })
                .then(res => res.json())
                .then((data) => {
                    console.log("ArchivedSprints:");
                    console.log(data);
                    if (data._embedded){
                        setArchivedSprints(data._embedded.sprintList)
                    }
                })
            // Fetch Stories of Archived
            fetch('http://localhost:8080/projects/' + projectId + '/sprints/archived/storiesInList', {
                method: 'get', 
                headers: { Authorization: 'Bearer ' + loggedUser.accessToken }
            })
                .then(res => res.json())
                .then((data) => {
                    console.log("These are the stories of archived sprints:")
                    console.log(data);
                    setArchivedStories(data)
                })
        }
        console.log("Project ID: " + projectId);
    }, []);

    const [modalIssueShow, setModalIssueShow] = useState(false);
    const [modalTaskInfoShow, setModalTaskInfoShow] = useState(false);
    const [modalSinE, setModalSinE] = useState(false);
    const [modalTinSofS, setModalTinSofS] = useState(false);
    const [epicTBDel, setEpicTBDel] = useState({id: 0});

    const [backlog, changeBacklog] = useState("nav-link active");
    const [spr, changeSpr] = useState("nav-link");
    const [epics, changeEpics] = useState("nav-link");
    const [pastSpr, changePastSpr] = useState("nav-link");
    const [pressedTab, changeTab] = useState("backlog");
    const [vertOrHoz, changeVerHoz] = useState("row pt-3 overflow-auto horizontal-scrollable");

    function clickBacklog() {
        changeBacklog("nav-link active");
        changeSpr("nav-link");
        changeEpics("nav-link");
        changePastSpr("nav-link");
        changeTab("backlog");
        changeVerHoz("row pt-3 vertical-scrollable overflow-auto");
    }

    function clickSpr() {
        changeBacklog("nav-link");
        changeSpr("nav-link active");
        changeEpics("nav-link");
        changePastSpr("nav-link");
        changeSprShown(activeSprints);
        changeStorShown(activeStories)
        changeTab("sprints");
        changeVerHoz("row pt-3 overflow-auto horizontal-scrollable");
    }

    function clickEpics() {
        changeBacklog("nav-link");
        changeSpr("nav-link");
        changeEpics("nav-link active");
        changePastSpr("nav-link");
        changeTab("epics");
        changeVerHoz("row pt-3 vertical-scrollable overflow-auto");
    }

    function clickPastSpr() {
        changeBacklog("nav-link");
        changeSpr("nav-link");
        changeEpics("nav-link");
        changePastSpr("nav-link active");
        changeSprShown(archivedSprints);
        changeStorShown(archivedStories)
        changeTab("sprints");
        changeVerHoz("row pt-3 overflow-auto horizontal-scrollable");
    }

    const [focusStory, setFocusStory] = useState({id: 0, title: ""})

    //const sprNames = ['Sprint 1','Sprint 2','Sprint 3','Sprint 4','Sprint 5','Sprint 2','Sprint 3','Sprint 4','Sprint 5','Sprint 2','Sprint 3','Sprint 4','Sprint 5'];
    //let pastSprNames = ['Old Sprint 1','Old Sprint 2','Old Sprint 3','Old Sprint 4','Old Sprint 5'];
    const [epicList, setEpicList] = useState([])
    const [activeSprints, setActiveSprints] = useState([])
    const [archivedSprints, setArchivedSprints] = useState([])
    const [activeStories, setActiveStories] = useState([[],[],[]])
    const [archivedStories, setArchivedStories] = useState([[],[],[]])
    //let epicNames = ['Epic 1','Epic 2','Epic 3','Epic 4','Epic 5','Epic 6','Epic 7','Epic 8'];
    
    const [sprintsShown, changeSprShown] = useState(activeSprints);
    const [storiesShown, changeStorShown] = useState(activeSprints);

    const [clickedTask, setClickedTask] = useState({id:'0',title:'_'});

    function showStoriesOfEpic(epic) {
        setEpicTBDel(epic)
        setModalSinE(true)
    }

    function getEpic(id){
        for (var i=0; i<epicList.length; i++){
            if (id === epicList[i].id){
                console.log("found epic:")
                console.log(epicList[i])
                return epicList[i]
            }
        }
        console.log('Epic was not found')
        return {id: 0, title: "__"}
    }

    function openStory(story){
        setFocusStory({id: 0, title: ""}) // PROBLEM
        setFocusStory(story)
        setModalTinSofS(true)
    }

    function delEpic(){
        fetch('http://localhost:8080/projects/'+ projectId +'/epics/' + epicTBDel.id + '/delete/'+loggedUser.id, {
                method: 'delete', 
                headers: { Authorization: 'Bearer ' + loggedUser.accessToken }
            })
            .then(res => res.json())
            .then((data) => {
                console.log(data);
                if (data.message === "OK"){
                    window.location.reload(false);
                }
            })
    }

    const epicPopover = (
        <Popover id="popover-basic">
            <Popover.Title as="h3">Είσαι Σίγουρος;</Popover.Title>
            <Popover.Content>
            <div className="container">
                <div className="row">
                    <small>Μαζί με το Epic θα διαγραφούν και τα Stories και Tasks που του ανήκουν.</small>
                </div>
                <div className="row d-flex pt-3">
                    <div className="col-1"/>
                    <div className="col-10">
                        <div className="btn btn-primary" onClick={()=> delEpic()}>Οκ, ας το κάνουμε</div>
                    </div>
                </div>
            </div>
            </Popover.Content>
        </Popover>
    );

    return (
        <div>
            <TasksInStoryOfSprintPopUp show={modalTinSofS} onHide={() => setModalTinSofS(false)} projId={projectId} epicId={focusStory.epic_id} focusStory={focusStory}/>
            <StoriesInEpicsPopUp show={modalSinE} onHide={() => setModalSinE(false)} projId={projectId} epic={epicTBDel}/>
            <IssuePopUp show={modalIssueShow} onHide={() => setModalIssueShow(false)} projId={projectId} epics={epicList} sprints={activeSprints} activeStories={activeStories}/>
            <TaskInfoPopUp show={modalTaskInfoShow} task={clickedTask} onHide={() => setModalTaskInfoShow(false)} projId={projectId}/>
			<Topbar/>
            <SideNavBar/>
            <div className="mainContent">
                <div className="projects-frame">
                    <nav aria-label="breadcrumb">
                        <ol className="breadcrumb">
                            <li className="breadcrumb-item">
                                <Link to='/projects'>
                                    <a href="#" className="breadcrumb-option">Projects</a>
                                </Link>
                            </li>
                            <li className="breadcrumb-item active" aria-current="page">{projectName}</li>
                        </ol>
                    </nav>
                    <div className="row justify-content-between">
                        <div className="col-6">
                            <h1 className="text">{projectName}</h1>
                        </div>
                        <div className="col-6">
                            <button type="button" className="btn btn-outline-secondary float-end" onClick={() => setModalIssueShow(true)}>Create Issue</button>
                        </div>
                    </div>
                    <div className="row pt-4" >
                        {/* ----------- Nav Tabs ------------ */}
                        <div className="col-8">
                            <ul className="nav nav-tabs"> 
                                <li className="nav-item"  onClick={clickBacklog}>
                                    <a className={backlog} aria-current="page">Backlog</a>
                                </li>
                                <li className="nav-item"  onClick={clickSpr}>
                                    <a className={spr}>Sprints</a>
                                </li>
                                <li className="nav-item" onClick={clickEpics}>
                                    <a className={epics}>Epics</a>
                                </li>
                                <li className="nav-item" onClick={clickPastSpr}>
                                    <a className={pastSpr}>Past Sprints</a>
                                </li>
                            </ul>
                        </div>
                        {/* ----------- Search Bar ------------ */}
                        <div className="col-4">
                            <div className="input-group mb-3">
                                <input type="text" className="form-control" placeholder="Search..." aria-label="Search..." aria-describedby="button-addon2"/>
                                <button className="btn btn-outline-secondary" type="button" id="button-addon2">Go</button>
                            </div>
                        </div>
                        <div className={vertOrHoz}>
                            {(pressedTab === "epics") && epicList.map(i => 
                                <div className="row pt-3">
                                    <div className="col-12 container">
                                        <div className="card">
                                            <div className="card-body">
                                                <div class="row">
                                                    <div className="col-11">
                                                        <h5 className="card-title">{i.title}</h5>
                                                        <p className="card-text">{i.description}</p>
                                                        <div className="btn btn-primary project-button" onClick={()=>showStoriesOfEpic(i)}>Εμφάνιση των Stories</div>
                                                    </div>
                                                    <div className="col-1 buttons-epic">
                                                        <OverlayTrigger trigger="click" placement="left" overlay={epicPopover}>
                                                            <img src={deleteicon} className="img-btn-proj delete-icon " onClick={()=>setEpicTBDel(i)}></img>
                                                        </OverlayTrigger>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            )}
                            {(pressedTab === "sprints") && sprintsShown.map(i => 
                                    {var index = sprintsShown.indexOf(i);
                                     var st =  activeStories[index];
                                     var nm = "";
                                     if (index === 0){
                                         nm = 'Τρέχων Sprint'
                                     }
                                     else if (index === 1){
                                         nm = 'Επόμενο Sprint'
                                     }
                                     else if (index === 2){
                                         nm = 'Παράεπομενο Sprint'
                                     }
                                     console.log("Kako")
                                     console.log(st)
                                    return (<div className=" full-col mt-2" style={{width: '33%',"flex-wrap": "nowrap"}}>
                                        <div className="card full-col">
                                            <div className="card-body sprint-card ">
                                                {(sprintsShown === activeSprints) && <h5 className="card-title">{nm}</h5>}
                                                {(sprintsShown != activeSprints) && <h5 className="card-title">{i.title}</h5>}
                                                <div class="list-group pt-3" style={{width: '100%',"flex-wrap": "nowrap"}}>
                                                    {st.content.map(k => <button type="button" class="list-group-item list-group-item-action" onClick={()=>openStory(k)}>{k.title}</button>)}
                                                </div>
                                            </div>
                                        </div> 
                                    </div>)}
                            )}
                            {(pressedTab === "backlog") && (<Backlog setModalTaskInfoShow={setModalTaskInfoShow} setSelectedTask={setClickedTask} selectedTask={clickedTask} activeStories={activeStories[0]} projectId={projectId} activeSprints={activeSprints}/>)}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}
 
export default ProjectNoPage;

import React, { useState, useEffect } from 'react';
import '../../App.css';
import '../../css/projects.css';
import SideNavBar from '../../components/SideNavBar.js'
import Topbar from '../../components/Topbar.js'
import Backlog from '../../components/Backlog.js'
import { Link, useHistory } from 'react-router-dom'
import IssuePopUp from '../../components/IssuePopUp.js'
import TaskInfoPopUp from '../../components/TaskInfoPopUp.js'



function ProjectNoPage() {

    const history = useHistory();
    const loggedUser = JSON.parse(localStorage.getItem('loggedUser'));

    useEffect(() => {
        if (!loggedUser){
            history.push("/login");
        }
    }, []);

    const [modalIssueShow, setModalIssueShow] = useState(false);
    const [modalTaskInfoShow, setModalTaskInfoShow] = useState(false);

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
        changeSprShown(sprNames);
        changeTab("backlog");
        changeVerHoz("row pt-3 vertical-scrollable overflow-auto");
    }

    function clickSpr() {
        changeBacklog("nav-link");
        changeSpr("nav-link active");
        changeEpics("nav-link");
        changePastSpr("nav-link");
        changeSprShown(sprNames);
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
        changeSprShown(pastSprNames);
        changeTab("sprints");
        changeVerHoz("row pt-3 overflow-auto horizontal-scrollable");
    }



    let sprNames = ['Sprint 1','Sprint 2','Sprint 3','Sprint 4','Sprint 5'];
    let pastSprNames = ['Old Sprint 1','Old Sprint 2','Old Sprint 3','Old Sprint 4','Old Sprint 5'];
    let epicNames = ['Epic 1','Epic 2','Epic 3','Epic 4','Epic 5','Epic 6','Epic 7','Epic 8'];
    
    const [sprintsShown, changeSprShown] = useState(sprNames);

    const [clickedTask, setClickedTask] = useState("");

    const searchButton = document.getElementById('search-button');
    const searchInput = document.getElementById('search-input');

    return (
        <div>
            <IssuePopUp show={modalIssueShow} onHide={() => setModalIssueShow(false)}/>
            <TaskInfoPopUp show={modalTaskInfoShow} taskName={clickedTask} onHide={() => setModalTaskInfoShow(false)}/>
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
                            <li className="breadcrumb-item active" aria-current="page">Proj Name</li>
                        </ol>
                    </nav>
                    <div className="row justify-content-between">
                        <div className="col-6">
                            <h1 className="text">Project Something</h1>
                        </div>
                        <div className="col-6">
                            <button type="button" className="btn btn-outline-secondary float-end" onClick={() => setModalIssueShow(true)}>Create Issue</button>
                        </div>
                    </div>
                    <div className="row pt-4">
                        {/* ----------- Nav Tabs ------------ */}
                        <div className="col-8">
                            <ul className="nav nav-tabs"> 
                                <li className="nav-item"  onClick={clickBacklog}>
                                    <a className={backlog} aria-current="page" href="#">Backlog</a>
                                </li>
                                <li className="nav-item"  onClick={clickSpr}>
                                    <a className={spr} href="#">Sprints</a>
                                </li>
                                <li className="nav-item" onClick={clickEpics}>
                                    <a className={epics} href="#">Epics</a>
                                </li>
                                <li className="nav-item" onClick={clickPastSpr}>
                                    <a className={pastSpr} href="#">Past Sprints</a>
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
                            {(pressedTab === "epics") && epicNames.map(i => 
                                <div className="row pt-3">
                                    <div className="col-12">
                                        <div className="card">
                                            <div className="card-body">
                                                <h5 className="card-title">{i}</h5>
                                                <p className="card-text">With supporting text below as a natural lead-in to additional content.</p>
                                                <a href="#" className="btn btn-primary project-button">Go somewhere</a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            )}
                            {(pressedTab === "sprints") && sprintsShown.map(i => 
                                    <div className="col-4 full-col">
                                        <div className="card full-col">
                                            <div className="card-body">
                                                <h5 className="card-title">{i}</h5>
                                                <p className="card-text">With supporting text below as a natural lead-in to additional content. With supporting text below as a natural lead-in to additional content.</p>
                                                <a href="#" className="btn btn-primary project-button">Go somewhere</a>
                                            </div>
                                        </div>
                                    </div>
                            )}
                            {(pressedTab === "backlog") && (<Backlog setModalTaskInfoShow={setModalTaskInfoShow} setSelectedTask={setClickedTask} selectedTask={clickedTask}/>)}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
    
        // searchButton.addEventListener('click', () => {
        // const inputValue = searchInput.value;
        // alert(inputValue);
        // });
}
 
export default ProjectNoPage;

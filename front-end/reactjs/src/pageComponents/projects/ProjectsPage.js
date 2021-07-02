import React, { Component, useState, useEffect } from 'react';
import '../../App.css';
import '../../css/projects.css';
import SideNavBar from '../../components/SideNavBar.js'
import Topbar from '../../components/Topbar.js'
import { Link, useHistory } from 'react-router-dom'
import NewProjectPopUp from '../../components/NewProjectPopUp.js'
import ProjectInfoPopUp from '../../components/ProjectInfoPopUp.js'
import ProjectAddDevPopUp from '../../components/ProjectAddDevPopUp.js'


function ProjectsPage() {

    const history = useHistory();
    const loggedUser = JSON.parse(localStorage.getItem('loggedUser'));

    const [isLoading, setLoading] = useState(true);
    const [rawProjects, setRawProjects] = useState([]);

    useEffect(() => {
        document.body.style.background = "#fff";

        // setLoading(false);
        if (!loggedUser){
            history.push("/login");
        }
        else{
            fetch('http://localhost:8080/users/'+ loggedUser.id +'/projects', {
            // fetch('http://localhost:8080/users/1/projects', {

                method: 'get', 
                headers: { Authorization: 'Bearer ' + loggedUser.accessToken }
            })
                .then(res => res.json())
                .then((data) => {
                    console.log(data);
                    setRawProjects(data);
                    setLoading(false);
                })
            
        }
    }, []);

    const [projectList,setProjectList] = useState([])
    const [projectListCurrent,setProjectListCurrent] = useState([])
    const [projectListArch,setProjectListArch] = useState([])

    const rpCurr = []
    const rpArch = []

    useEffect(() => {
        if (!isLoading){
            for (var i=0; i < rawProjects._embedded.projectList.length; i++){
                if (rawProjects._embedded.projectList[i].status){
                    rpArch.push(rawProjects._embedded.projectList[i])
                }
                else {
                    rpCurr.push(rawProjects._embedded.projectList[i])
                }
            }
            setProjectListCurrent(rpCurr)
            setProjectListArch(rpArch)
            setProjectList(rpCurr)
        }
    },[isLoading])

    const [all, changeCurrent] = useState("nav-link active");
    const [arch, changeArch] = useState("nav-link");

    function clickCurrent() {
        changeCurrent("nav-link active");
        changeArch("nav-link");
        setProjectList(projectListCurrent);
    }

    function clickArch() {
        changeCurrent("nav-link");
        changeArch("nav-link active")
        setProjectList(projectListArch);
    }



    const searchButton = document.getElementById('search-button');
    const searchInput = document.getElementById('search-input');

    const [searchVal, setSearch] = useState("");
    const [modalShow, setModalShow] = useState(false);
    const [modalInfoShow, setModalInfoShow] = useState(false); 
    const [modalAddShow, setModalAddShow] = useState(false); 
    const [infoId, setInfoId] = useState(0); // the id of the project, which will be passed to the info modal
    const [infoName, setInfoName] = useState("Project");
    const [infoArchStatus, setInfoArchStatus] = useState(0);

    function showInfo(id,name,status){
        setInfoId(id)
        setInfoName(name)
        setInfoArchStatus(status)
        setModalInfoShow(true)
    } 

    function showAdd(id,name){
        setInfoId(id)
        setInfoName(name)
        setModalAddShow(true)
    } 

    useEffect(() => console.log("this happened1"),[infoId])

    return (
        <div>
            <NewProjectPopUp show={modalShow} onHide={() => setModalShow(false)} />
            <ProjectInfoPopUp show={modalInfoShow} onHide={() => setModalInfoShow(false)} projectId={infoId} status={infoArchStatus} projName={infoName}/>
            <ProjectAddDevPopUp show={modalAddShow} onHide={() => setModalAddShow(false)} projectId={infoId} projName={infoName}/>
			<Topbar/>
            <SideNavBar/>
            <div className="mainContent">
                <div className="projects-frame">
                    <nav aria-label="breadcrumb">
                        <ol className="breadcrumb">
                            <li className="breadcrumb-item active" aria-current="page">Projects</li>
                        </ol>
                    </nav>
                    <div className="row justify-content-between">
                        <div className="col-6">
                            <h1 className="text projects-page-header">Projects</h1>
                        </div>
                        <div className="col-6">
                            <button type="button" className="btn btn-outline-secondary float-end" onClick={() => setModalShow(true)}>Δημιουργία Project</button>
                        </div>
                    </div>
                    <div className="row pt-4">
                        {/* ----------- Nav Tabs ------------ */}
                        <div className="col-8">
                            <ul className="nav nav-tabs"> 
                                <li className="nav-item"  onClick={clickCurrent}>
                                    <a className={all} aria-current="page" href="#">Σε εξέλιξη</a>
                                </li>
                                <li className="nav-item" onClick={clickArch}>
                                    <a className={arch} href="#">Αρχειοθετημένα</a>
                                </li>
                            </ul>
                        </div>
                        {/* ----------- Search Bar ------------ */}
                        <div className="col-4">
                            <div className="input-group mb-3 searchbar">
                                <input type="text" className="form-control" placeholder="Search..." aria-label="Search..." aria-describedby="button-addon2" value={searchVal} onChange={e => setSearch(e.target.value)}/>
                                <button className="btn btn-outline-secondary" type="button" id="button-addon2">Go</button>
                            </div>
                        </div>
                        <div className="row pt-3 vertical-scrollable overflow-auto">
                            {
                                projectList.map(i => i.title.toLowerCase().includes(searchVal.toLowerCase()) &&
                                (<div key={i.id + i.title} className="row pt-3">
                                    <div className="col-12">
                                        <div className="card">
                                            <div className="card-body proj-container">
                                                <div className="row">
                                                    <div className="col-12">
                                                        
                                                        <h5 className="card-title">{i.title}</h5>
                                                        <p className="card-text">{i.description}</p>
                                                        <button className="btn btn-primary btn-sm btn-dark proj-info-btn" onClick={() => showInfo(i.id,i.title,i.status)}>Πληροφορίες / Αρχειοθέτηση</button>
                                                        {!i.status && (<button className="btn btn-primary btn-sm btn btn-success proj-devadd-btn"  onClick={() => showAdd(i.id,i.title)}>Προσθήκη συνεργάτη</button>)}
                                                        <Link to={{pathname: '/projects/projectNo',
                                                                    state: {
                                                                        projectId: i.id,
                                                                        projectName: i.title
                                                                    }
                                                                }}>

                                                            <a href="#" className="btn btn-primary  btn-sm project-button proj-list-btn">Μετάβαση στο project</a>
                                                        </Link>
                                                    </div>
                                                    
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>)
                            )}
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
 
export default ProjectsPage;

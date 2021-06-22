import React, { Component, useState, useEffect } from 'react';
import '../../App.css';
import '../../css/projects.css';
import SideNavBar from '../../components/SideNavBar.js'
import Topbar from '../../components/Topbar.js'
import { Link, useHistory } from 'react-router-dom'
import NewProjectPopUp from '../../components/NewProjectPopUp.js'
import ProjectInfoPopUp from '../../components/ProjectInfoPopUp.js'


function ProjectsPage() {

    const history = useHistory();
    const loggedUser = JSON.parse(localStorage.getItem('loggedUser'));

    useEffect(() => {
        if (!loggedUser){
            history.push("/login");
        }
    }, []);

    const [all, changeAll] = useState("nav-link active");
    const [arch, changeArch] = useState("nav-link");

    function clickAll() {
        changeAll("nav-link active");
        changeArch("nav-link");
        changeProj(allNames);
    }

    function clickArch() {
        changeAll("nav-link");
        changeArch("nav-link active")
        changeProj(archNames);
    }

    const [allNames, setAllNames] = useState(['Atlas','Zeus','Prometheus','Apollo','Johnny','Porta']);
    let archNames = ['Epikalesths','Thomas','Margarita','Pokopikos','Johnny palios'];


    const [projNames, changeProj] = useState(allNames);

    const searchButton = document.getElementById('search-button');
    const searchInput = document.getElementById('search-input');

    const [searchVal, setSearch] = useState("");
    const [modalShow, setModalShow] = useState(false);
    const [modalInfoShow, setModalInfoShow] = useState(false);

    function addProj(name) {
        allNames.push(name)
    }

    return (
        <div>
            <NewProjectPopUp show={modalShow} onHide={() => setModalShow(false)} addProj={addProj}/>
            <ProjectInfoPopUp show={modalInfoShow} onHide={() => setModalInfoShow(false)}/>
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
                                <li className="nav-item"  onClick={clickAll}>
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
                            {projNames.map(i => i.toLowerCase().includes(searchVal.toLowerCase()) &&
                                (<div className="row pt-3">
                                    <div className="col-12">
                                        <div className="card">
                                            <div className="card-body container">
                                                <div className="row">
                                                    <div className="col-11">
                                                        <h5 className="card-title">Project {i}</h5>
                                                        <p className="card-text">With supporting text below as a natural lead-in to additional content.</p>
                                                        <Link to='/projects/projectNo'>
                                                            <a href="#" className="btn btn-primary project-button">Go somewhere</a>
                                                        </Link>
                                                    </div>
                                                    <div className="col-1">
                                                        <button onClick={() => setModalInfoShow(true)}>info</button>
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

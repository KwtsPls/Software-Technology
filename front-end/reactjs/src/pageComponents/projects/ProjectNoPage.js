import React, { Component, useState } from 'react';
import '../../App.css';
import '../../css/projects.css';
import SideNavBar from '../../components/SideNavBar.js'
import Topbar from '../../components/Topbar.js'
import { Link } from 'react-router-dom'


function ProjectNoPage() {

    const [spr, changeSpr] = useState("nav-link active");
    const [epics, changeEpics] = useState("nav-link");
    const [pastSpr, changePastSpr] = useState("nav-link");
    const [sprintShowing, changeTab] = useState(true);
    const [vertOrHoz, changeVerHoz] = useState("row pt-3 overflow-auto horizontal-scrollable");

    function clickSpr() {
        changeSpr("nav-link active");
        changeEpics("nav-link");
        changePastSpr("nav-link");
        changeSprShown(sprNames);
        changeTab(true);
        changeVerHoz("row pt-3 overflow-auto horizontal-scrollable");
    }

    function clickEpics() {
        changeSpr("nav-link");
        changeEpics("nav-link active");
        changePastSpr("nav-link");
        changeTab(false);
        changeVerHoz("row pt-3 vertical-scrollable overflow-auto");
    }

    function clickPastSpr() {
        changeSpr("nav-link");
        changeEpics("nav-link");
        changePastSpr("nav-link active");
        changeSprShown(pastSprNames);
        changeTab(true);
        changeVerHoz("row pt-3 overflow-auto horizontal-scrollable");
    }

    let sprNames = ['Sprint 1','Sprint 2','Sprint 3','Sprint 4','Sprint 5'];
    let pastSprNames = ['Old Sprint 1','Old Sprint 2','Old Sprint 3','Old Sprint 4','Old Sprint 5'];
    let epicNames = ['Epic 1','Epic 2','Epic 3','Epic 4','Epic 5','Epic 6','Epic 7','Epic 8'];
    
    const [sprintsShown, changeSprShown] = useState(sprNames);

    const searchButton = document.getElementById('search-button');
    const searchInput = document.getElementById('search-input');

    return (
        <div>
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
                            <button type="button" className="btn btn-outline-secondary float-end">Create Issue</button>
                        </div>
                    </div>
                    <div className="row pt-4">
                        {/* ----------- Nav Tabs ------------ */}
                        <div className="col-8">
                            <ul className="nav nav-tabs"> 
                                <li className="nav-item"  onClick={clickSpr}>
                                    <a className={spr} aria-current="page" href="#">Sprints</a>
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
                            {!sprintShowing && epicNames.map(i => 
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
                            {sprintShowing && sprintsShown.map(i => 
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

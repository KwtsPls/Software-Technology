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
                    <div class="row justify-content-between">
                        <div class="col-6">
                            <h1 className="text">Project Something</h1>
                        </div>
                        <div class="col-6">
                            <button type="button" class="btn btn-outline-secondary float-end">Create Issue</button>
                        </div>
                    </div>
                    <div class="row pt-4">
                        {/* ----------- Nav Tabs ------------ */}
                        <div class="col-8">
                            <ul class="nav nav-tabs"> 
                                <li class="nav-item"  onClick={clickSpr}>
                                    <a class={spr} aria-current="page" href="#">Sprints</a>
                                </li>
                                <li class="nav-item" onClick={clickEpics}>
                                    <a class={epics} href="#">Epics</a>
                                </li>
                                <li class="nav-item" onClick={clickPastSpr}>
                                    <a class={pastSpr} href="#">Past Sprints</a>
                                </li>
                            </ul>
                        </div>
                        {/* ----------- Search Bar ------------ */}
                        <div class="col-4">
                            <div class="input-group mb-3">
                                <input type="text" class="form-control" placeholder="Search..." aria-label="Search..." aria-describedby="button-addon2"/>
                                <button class="btn btn-outline-secondary" type="button" id="button-addon2">Go</button>
                            </div>
                        </div>
                        <div class={vertOrHoz}>
                            {!sprintShowing && epicNames.map(i => 
                                <div class="row pt-3">
                                    <div class="col-12">
                                        <div class="card">
                                            <div class="card-body">
                                                <h5 class="card-title">{i}</h5>
                                                <p class="card-text">With supporting text below as a natural lead-in to additional content.</p>
                                                <a href="#" class="btn btn-primary project-button">Go somewhere</a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            )}
                            {sprintShowing && sprintsShown.map(i => 
                                    <div class="col-4 full-col">
                                        <div class="card full-col">
                                            <div class="card-body">
                                                <h5 class="card-title">{i}</h5>
                                                <p class="card-text">With supporting text below as a natural lead-in to additional content. With supporting text below as a natural lead-in to additional content.</p>
                                                <a href="#" class="btn btn-primary project-button">Go somewhere</a>
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
    
        searchButton.addEventListener('click', () => {
        const inputValue = searchInput.value;
        alert(inputValue);
        });
}
 
export default ProjectNoPage;

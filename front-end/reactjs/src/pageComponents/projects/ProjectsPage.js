import React, { Component, useState } from 'react';
import '../../App.css';
import '../../css/projects.css';
import SideNavBar from '../../components/SideNavBar.js'
import Topbar from '../../components/Topbar.js'
import { Link } from 'react-router-dom'


function ProjectsPage() {

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

    let allNames = ['Atlas','Zeus','Prometheus','Apollo','Johnny','Porta'];
    let archNames = ['Epikalesths','Thomas','Margarita','Pokopikos','Johnny palios'];


    const [projNames, changeProj] = useState(allNames);

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
                            <li className="breadcrumb-item active" aria-current="page">Projects</li>
                        </ol>
                    </nav>
                    <div class="row justify-content-between">
                        <div class="col-6">
                            <h1 className="text">Projects</h1>
                        </div>
                        <div class="col-6">
                            <button type="button" class="btn btn-outline-secondary float-end">Δημιουργία Project</button>
                        </div>
                    </div>
                    <div class="row pt-4">
                        {/* ----------- Nav Tabs ------------ */}
                        <div class="col-8">
                            <ul class="nav nav-tabs"> 
                                <li class="nav-item"  onClick={clickAll}>
                                    <a class={all} aria-current="page" href="#">Σε εξέλιξη</a>
                                </li>
                                <li class="nav-item" onClick={clickArch}>
                                    <a class={arch} href="#">Αρχειοθετημένα</a>
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
                        <div class="row pt-3 vertical-scrollable overflow-auto">
                            {projNames.map(i => 
                                <div class="row pt-3">
                                    <div class="col-12">
                                        <div class="card">
                                            <div class="card-body">
                                                <h5 class="card-title">Project {i}</h5>
                                                <p class="card-text">With supporting text below as a natural lead-in to additional content.</p>
                                                <Link to='/projects/projectNo'>
                                                    <a href="#" class="btn btn-primary project-button">Go somewhere</a>
                                                </Link>
                                            </div>
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
 
export default ProjectsPage;

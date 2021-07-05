import React, { Component, useEffect, useState } from 'react';
import { Link, useHistory } from 'react-router-dom'
import '../css/Overviewscreen.css'
import sunphoto from '../images/sun2.png'






function OverviewScreen() {

    const history = useHistory();
    const loggedUser = JSON.parse(localStorage.getItem('loggedUser'));
    const [isLoading, setLoading] = useState(true);
    const [tilecounter, setTileCounter] = useState(0);
    const [rawProjects, setRawProjects] = useState([]);
    const [recentprojectList,setRecentProjectList] = useState([])


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

    const recProj = []

    useEffect(() => {
        if (!isLoading){
            if(rawProjects._embedded === undefined){
                
            }else{

                for (var i=0; recProj.length <3 && i < rawProjects._embedded.projectList.length; i++){
                    if (! rawProjects._embedded.projectList[i].status){
                        recProj.push(rawProjects._embedded.projectList[i])
                    }   
                }
            }
            
            setRecentProjectList(recProj)
        }
    },[isLoading])



    function get_recentProjectTile(name){
        
        // find a way to get only the first three projects
        if(setTileCounter>3){
            console.log(1);

        }

        return(
            <a className="col-md-2 offset-md-1 mt-4 project-tile text-center">
                {/* {name} */}
                <h5 className="mt-3 mb-3 project-tile-name">Project 1</h5>
            </a> 
        );

    }

    function emptyProjectsList(){
        if(!recentprojectList.length){
            return(
            <div className="row">
                <div className="col-md-9 mt-2 mb-5 offset-1 bg-success text-center noproject-text">
                    <p className="homepage-text"> Δεν έχετε ακόμα κάποιο τρέχων project</p>
                </div>
            </div>
            );
        }
    
    }

    return (
        <div className="homepage">

            <div className="row mt-5 d-flex justify-content-center">
                <div className="col-md-6 pt-3 pb-3  greetingstile">
                    <h1 className="homepage-text text-center greetings-text">Καλησπέρα, {loggedUser && loggedUser.username}</h1>

                    
                </div>
                <div className="col-md-2 pt-3 pb-3  text-center greetingstile tile-sun">
                    <img className = "sun-img" src={sunphoto} alt="avatar"/>
                </div>
                

                

                
            
            </div>
            
            <div className="recproj">
                <div className="row mt-5 d-flex justify-content-center">
                    <div className="col-md-8 mt-2 bg-trans ">
                        <h2 className="homepage-text curr-proj-title">Πρόσφατα projects &bull; </h2>

                    </div>
                </div>

                <div className="row mt-4 offset-md-1 tilesrow">
                    {
                        recentprojectList.map(i => 

                        (

                            <a className="col-md-2 offset-md-1 mt-4 mb-5 project-tile text-center">
                                <Link to={{pathname: '/projects/projectNo',
                                        state: {
                                            projectId: i.id,
                                            projectName: i.title
                                        }
                                    }}>
                                
                                    <h5 className="mt-3 mb-3 project-tile-name">{i.title}</h5>
                                </Link>
                            </a>    
                        )

                        

                    )}

                    {emptyProjectsList()}

                    
                    
                </div>

            </div>

            <div className="row mt-5 d-flex justify-content-center">
                <div className="col-md-8 mt-2 bg-trans ">
                    <h2 className="homepage-text curr-proj-title">Σύνοψη &bull; </h2>

                </div>
            </div>

                                    {/* bars */}
            <div class="row genbacklog-container d-flex justify-content-center">
                    <div class="col-md-9 mt-4 mb-4">
                        <div class="card mycard proj-progress-card">
                            <div class="card-block mycard-block">
                                <div class="row">
                                    <div class="col-xl-4 col-md-6">
                                        <h3 className="backlogtitle">Ολοκληρωμένα Projects</h3>
                                        <h5 class="m-b-30 f-w-700">1 από 4</h5>
                                        <div class="progress countprogress">
                                            <div class="progress-bar countprogress-bar bg-c-red" style={{width:'25%'}}></div>
                                        </div>
                                    </div>
                                    <div class="col-xl-4 col-md-6">
                                        <h3 className="backlogtitle">Ολοκληρωμένα Epics</h3>
                                        <h5 class="m-b-30 f-w-700">4 από 6</h5>
                                        <div class="progress countprogress">
                                            <div class="progress-bar countprogress-bar bg-c-blue" style={{width:'65%'}}></div>
                                        </div>
                                    </div>
                                    <div class="col-xl-4 col-md-6">
                                        <h3 className="backlogtitle">Ολοκληρωμένα Stories</h3>
                                        <h5 class="m-b-30 f-w-700">10 από 20</h5>
                                        <div class="progress countprogress">
                                            <div class="progress-bar countprogress-bar bg-c-green" style={{width:'85%'}}></div>
                                        </div>
                                    </div>
                                    {/* <div class="col-xl-3 col-md-6">
                                        <h3 className="backlogtitle">Ανοικτά Issues</h3>
                                        <h5 class="m-b-30 f-w-700"></h5>
                                        <div class="progress countprogress">
                                            <div class="progress-bar countprogress-bar bg-c-yellow" style={{width:'45%'}}></div>
                                        </div>
                                    </div> */}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div className="row genbacklog-container justify-content-center someprojects-container">

                    <div className="col-md-3 mt-5">
                        
                        
                            

                            <div class="col-md-8 ">
                            <h2 className="backlogtitle">Project 1</h2>
                            <h4 className="backlogtitle mt-4">Ολοκληρωμένα Epics</h4>
                                <div class="progress">
                                    <div class="progress-bar bg-warning" role="progressbar" style={{width: '25%'}} aria-valuenow="25" aria-valuemin="0" aria-valuemax="100">25%</div>
                                </div>
                            </div>


                        
                            <div class="col-md-8 mt-3">
                                <h4 className="backlogtitle">Ολοκληρωμένα Tasks</h4>

                                <div class="progress">
                                    <div class="progress-bar bg-danger" role="progressbar" style={{width: '25%'}} aria-valuenow="25" aria-valuemin="0" aria-valuemax="100">25%</div>
                                </div>
                            </div>

                        
                    </div>

                    <div className="col-md-3 mt-5">
                        
                        
                            

                            <div class="col-md-8">
                            <h2 className="backlogtitle">Project 1</h2>
                            <h4 className="backlogtitle mt-4">Ολοκληρωμένα Epics</h4>
                                <div class="progress">
                                    <div class="progress-bar bg-warning" role="progressbar" style={{width: '25%'}} aria-valuenow="25" aria-valuemin="0" aria-valuemax="100">25%</div>
                                </div>
                            </div>


                        
                            <div class="col-md-8 mt-3">
                                <h4 className="backlogtitle">Ολοκληρωμένα Tasks</h4>

                                <div class="progress">
                                    <div class="progress-bar bg-danger" role="progressbar" style={{width: '25%'}} aria-valuenow="25" aria-valuemin="0" aria-valuemax="100">25%</div>
                                </div>
                            </div>

                        
                    </div>

                    <div className="col-md-3 mt-5">
                        
                        
                            

                            <div class="col-md-8">
                            <h2 className="backlogtitle">Project 1</h2>
                            <h4 className="backlogtitle mt-4">Ολοκληρωμένα Epics</h4>
                                <div class="progress">
                                    <div class="progress-bar bg-warning" role="progressbar" style={{width: '25%'}} aria-valuenow="25" aria-valuemin="0" aria-valuemax="100">25%</div>
                                </div>
                            </div>


                        
                            <div class="col-md-8 mt-3">
                                <h4 className="backlogtitle">Ολοκληρωμένα Tasks</h4>

                                <div class="progress">
                                    <div class="progress-bar bg-danger" role="progressbar" style={{width: '25%'}} aria-valuenow="25" aria-valuemin="0" aria-valuemax="100">25%</div>
                                </div>
                            </div>

                        
                    </div>

                    <div className="col-12" style={{height: '200px' , background: 'white'}}> </div>


                </div>
        </div>

    );
}

 
export default OverviewScreen;
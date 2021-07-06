import { set } from 'date-fns';
import React, { Component, useEffect, useState } from 'react';
import { Link, useHistory } from 'react-router-dom'
import '../css/Overviewscreen.css'
import '../css/generalbacklog.css'

import sunphoto from '../images/sun2.png'






function OverviewScreen() {

    const history = useHistory();
    const loggedUser = JSON.parse(localStorage.getItem('loggedUser'));
    const [isLoading, setLoading] = useState(null);
    const [tilecounter, setTileCounter] = useState(0);
    const [rawProjects, setRawProjects] = useState(null);
    const [recentprojectList,setRecentProjectList] = useState([]);
    const [projectidList, setProjectidList] = useState([]);
    const [projectSprintInfo, setProjectSprintInfo] = useState(0)
    const [projectSprintDict, setProjectSprintDict] = useState({})

    const [lastSprintNumber, setLastSprintNumber] = useState(0)

    const [totalSprintNumber, setTotalSprintNumber] = useState(0)
    const [doneProjects, setDoneProjects] = useState(null);
    const [currProjects, setCurrProjects] = useState(null);
    const [allProjects, setAllProjects] = useState(null);
    
    const [donePerc, setDonePerc] = useState(null);
    
    const recProj = []
    const currProj = []
    const doneProj = []
    const allProj = []
    const idProj = []
    const sprint_proj_info = {}
    var totalProj = 0;
    var otin=0;
    var percentageStr = null

    useEffect(() => {
        document.body.style.background = "#fff";

        // setLoading(false);
        if (!loggedUser){
            history.push("/login");
        }
        else{
            let overall_dict = {};

            fetch('http://localhost:8080/users/'+ loggedUser.id +'/projects', {
            // fetch('http://localhost:8080/users/1/projects', {

                method: 'get', 
                headers: { Authorization: 'Bearer ' + loggedUser.accessToken }
            })
                .then(res => res.json())
                .then((data) => {
                    
                    
                    if(data._embedded){
                        console.log(data);
                        
                        setRawProjects(data._embedded.projectList);
                        console.log(data);

                        console.log(data._embedded.projectList);
                        
                        for (var i=0; i < data._embedded.projectList.length; i++){
                            if (! data._embedded.projectList[i].status){
                                
                                if(recProj.length < 3){
                                    recProj.push(data._embedded.projectList[i])
                                    idProj.push(data._embedded.projectList[i].id)

                                }
        
                                currProj.push(data._embedded.projectList[i])
                            
                            }else{
                            
                                doneProj.push(data._embedded.projectList[i])
                                
                            }
                            
                            allProj.push(data._embedded.projectList[i])
                        }
                        
                        
                        if(allProj.length != 0){

                            var percentage = (doneProj.length * 100) / allProj.length;
                            
                            console.log(idProj)
                            
                            
                            // setDonePerc(((doneProj.length * 100) / allProj.length) + '%')
                            percentageStr = percentage + '%'
                        }else{
                            percentageStr = '0%'
                            
                        }
                        
                        
                        
                        
                        
                    }
                    
                    setAllProjects(data._embedded.projectList.length)
                    setDoneProjects(doneProj.length)
                    setCurrProjects(currProj.length)
                    setProjectidList(idProj);
                    setRecentProjectList(recProj)
                    setDonePerc(percentageStr)

                })
                // .then((kappa) => {
                    

                //     console.log("Project id " + idProj[1])
                //     var dict = {};
                    
                //     for(var i=0 ; i < idProj.length ; i++){

                //         let count = 0;
                //         let i_counter = i;

                //         fetch('http://localhost:8080/projects/' + idProj[i] + '/sprints/active', {
                //         // fetch('http://localhost:8080/users/1/projects', {

                //             method: 'get', 
                //             headers: { Authorization: 'Bearer ' + loggedUser.accessToken }
                //         })
                //             .then(res => res.json())
                //             .then((data) => {
                //                 // console.log(idProj[i])
                                
                //                 if(data._embedded){

                //                     console.log(data)
                                    
        

                //                     count = data._embedded.sprintList.length;
                //                     console.log("exw toso re file " + count)
                //                 }  
                                

                                
                //                 setLastSprintNumber(count)
                //                 console.log("etsigoust " + idProj[i_counter] +"ontws" + count);
                //                 dict[idProj[i_counter]] = count;
                //                 console.log(dict)
                                
                                
                //             })
                //     }


                //     console.log("OURLAIZEW")
                //     console.log(dict)
                //     console.log("OURLAIZAAAAAAAAAAAAs")
                
                
                    
                //     var totalSprints = 0;
                //     // for (var key in projectSprintDict) {
                //     //     totalSprints += projectSprintDict[key];
                //     //     // your code here...
                //     // }
                //     console.log(dict)
                    
                //     setTotalSprintNumber(totalSprints)
                //     // setLoading(false);
                //     setProjectSprintDict(dict);



                    
                    
                // })
                

                
                



            
        }

    }, []);

   


    // useEffect(() => {
        
    //     if (!isLoading){
    //         if(rawProjects._embedded === undefined){
                
    //         }else{

    //             for (var i=0; i < rawProjects._embedded.projectList.length; i++){
    //                 console.log(rawProjects._embedded.projectList.length)
    //                 if (! rawProjects._embedded.projectList[i].status){
                        
    //                     if(recProj.length < 3){
    //                         recProj.push(rawProjects._embedded.projectList[i])
    //                     }

    //                     currProj.push(rawProjects._embedded.projectList[i])
                    
    //                 }else{
                    
    //                     doneProj.push(rawProjects._embedded.projectList[i])
                        
    //                 }
                    
    //                 allProj.push(rawProjects._embedded.projectList[i])
                    
    //             }
                
    //             setAllProjects(rawProjects._embedded.projectList.length)
    //             setDoneProjects(doneProj.length)
    //             setCurrProjects(currProj.length)

    //             console.log("tiptoa")

    //             console.log(allProjects)
    //             console.log(currProjects)
    //             console.log(doneProjects)
               
    //             var percentage = (doneProjects * 100) / allProjects;
    //             console.log(percentage)
    //             console.log(percentage + 'sdasdasd')
                
    //             console.log("tiptoa")
                
                
                
    //             setDonePerc(((doneProjects * 100) / allProjects) + '%')
                
                
    //             console.log("tiiii" + donePerc)

    //             console.log(doneProj)
    
                
    //         }
            
    //         setRecentProjectList(recProj)
    //     }
    // },[isLoading])


   

    function doneProjectsNumber(){
        return doneProj.length;
    }


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
    
    if(donePerc === null){
        return (

        <div className="text-center">
            <h4 className="homepage-text loading-text ">Loading posts...</h4>
        </div>
        
        );
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
            <div className="row genbacklog-container d-flex justify-content-center">
                    <div className="col-md-9 mt-4 mb-4">
                        <div className="card mycard proj-progress-card">
                            <div className="card-block mycard-block">
                                <div className="row">
                                    <div className="col-xl-12 col-md-6">
                                        <h3 className="backlogtitle">Ολοκληρωμένα Projects</h3>
                                        <h5 className="m-b-30 f-w-700">{doneProjects} από {allProjects}</h5>
                                        <div className="progress countprogress">
                                            <div className="progress-bar countprogress-bar bg-c-green" style={{width: donePerc }}></div>
                                        </div>
                                    </div>
                                    {/* <div className="col-xl-4 col-md-6">
                                        <h3 className="backlogtitle">Ολοκληρωμένα Epics</h3>
                                        <h5 className="m-b-30 f-w-700">4 από 6</h5>
                                        <div className="progress countprogress">
                                            <div className="progress-bar countprogress-bar bg-c-blue" style={{width:'65%'}}></div>
                                        </div>
                                    </div>
                                    <div className="col-xl-4 col-md-6">
                                        <h3 className="backlogtitle">Ολοκληρωμένα Stories</h3>
                                        <h5 className="m-b-30 f-w-700">10 από 20</h5>
                                        <div className="progress countprogress">
                                            <div className="progress-bar countprogress-bar bg-c-green" style={{width:'85%'}}></div>
                                        </div>
                                    </div> */}
                                    {/* <div className="col-xl-3 col-md-6">
                                        <h3 className="backlogtitle">Ανοικτά Issues</h3>
                                        <h5 className="m-b-30 f-w-700"></h5>
                                        <div className="progress countprogress">
                                            <div className="progress-bar countprogress-bar bg-c-yellow" style={{width:'45%'}}></div>
                                        </div>
                                    </div> */}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div className="row offset-1 genbacklogs-container justify-content-start someprojects-container">
                                    
                    {/* {
                        recentprojectList.map(i =>

                        (<div className="col-4 mt-5">
                            
                            
                                

                                <div className="col-md-8 ">
                                <h2 className="backlogtitle">{i.title}</h2>
                                <h4 className="backlogtitle mt-4">Sprints</h4>
                                    <div className="progress">
                                        {console.log("ti els " + i.id + "totso " + projectSprintDict)}
                                        {console.log(projectSprintDict)}
                                        <div className="progress-bar bg-warning" role="progressbar" style={{width: '25%'}} aria-valuenow="25" aria-valuemin="0" aria-valuemax="100">{projectSprintDict[i.id]}</div>
                                    </div>
                                </div>


                            
                                <div className="col-md-8 mt-3">
                                    <h4 className="backlogtitle">Ολοκληρωμένα Tasks</h4>

                                    <div className="progress">
                                        <div className="progress-bar bg-danger" role="progressbar" style={{width: '25%'}} aria-valuenow="25" aria-valuemin="0" aria-valuemax="100">25%</div>
                                    </div>
                                </div>

                            
                        </div>)
                    )} */}

                    {/* <div className="col-md-3 mt-5">
                        
                        
                            

                            <div className="col-md-8">
                            <h2 className="backlogtitle">Project 1</h2>
                            <h4 className="backlogtitle mt-4">Ολοκληρωμένα Epics</h4>
                                <div className="progress">
                                    <div className="progress-bar bg-warning" role="progressbar" style={{width: '25%'}} aria-valuenow="25" aria-valuemin="0" aria-valuemax="100">25%</div>
                                </div>
                            </div>


                        
                            <div className="col-md-8 mt-3">
                                <h4 className="backlogtitle">Ολοκληρωμένα Tasks</h4>

                                <div className="progress">
                                    <div className="progress-bar bg-danger" role="progressbar" style={{width: '25%'}} aria-valuenow="25" aria-valuemin="0" aria-valuemax="100">25%</div>
                                </div>
                            </div>

                        
                    </div>

                    <div className="col-md-3 mt-5">
                        
                        
                            

                            <div className="col-md-8">
                            <h2 className="backlogtitle">Project 1</h2>
                            <h4 className="backlogtitle mt-4">Ολοκληρωμένα Epics</h4>
                                <div className="progress">
                                    <div className="progress-bar bg-warning" role="progressbar" style={{width: '25%'}} aria-valuenow="25" aria-valuemin="0" aria-valuemax="100">25%</div>
                                </div>
                            </div>


                        
                            <div className="col-md-8 mt-3">
                                <h4 className="backlogtitle">Ολοκληρωμένα Tasks</h4>

                                <div className="progress">
                                    <div className="progress-bar bg-danger" role="progressbar" style={{width: '25%'}} aria-valuenow="25" aria-valuemin="0" aria-valuemax="100">25%</div>
                                </div>
                            </div>

                        
                    </div> */}

                    <div className="col-12" style={{height: '200px' , background: 'white'}}> </div>


                </div>
        </div>

    );
}

 
export default OverviewScreen;
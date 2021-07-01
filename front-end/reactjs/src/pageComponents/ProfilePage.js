import React, { Component, useEffect, useState } from 'react';
import '../App.css';
//import '../css/settings.css';
import SideNavBar from '../components/SideNavBar.js'
import Topbar from '../components/Topbar.js'
import { useHistory } from 'react-router-dom'
import '../css/profile.css'
import userphoto from '../images/userphoto.png'


function ProfilePage() {

    const history = useHistory();
    const loggedUser = JSON.parse(localStorage.getItem('loggedUser'));

    console.log(loggedUser);

    const [isLoading, setLoading] = useState(true);
    const [doneProjects, setdoneProjects] = useState(0);
    const [currentProjects, setcurrentProjects] = useState(0);
    const [user, setUser] = useState([])
    const [rawProjects, setRawProjects] = useState([]);


    function countdoneProjects(cond){
        //setdoneProjects
    
        
    }

    const [projectList,setProjectList] = useState([])
    
    let projArch_counter = 0;
    let projCurr_counter = 0;


    useEffect(() => {
        document.body.style.background = "#fff";

        if (!loggedUser){
            history.push("/login");
        }
        else{
            document.body.style.background = "#fff";

            fetch('http://localhost:8080/users/' + loggedUser.id, {
                method: 'get', 
                headers: { Authorization: 'Bearer ' + loggedUser.accessToken }
            })
                .then(res => res.json())
                .then((data) => {
                    setUser(data);
                    // setLoading(false);
                   
                })
                
                fetch('http://localhost:8080/users/1/projects', {
                    method: 'get', 
                    headers: { Authorization: 'Bearer ' + loggedUser.accessToken }
                })
                    .then(res => res.json())
                    .then((data) => {
                        console.log(data);
                        for (var i=0; i < (data)._embedded.projectList.length; i++){
                            if (data._embedded.projectList[i].status){  
                                setdoneProjects(doneProjects+1);
                            }
                            else {
                                setcurrentProjects(currentProjects+1);

                            }
                        }
                        setLoading(false);

                    }) 

        }

    }, []);



    

    

        return (
            <div>
				<Topbar/>
                <SideNavBar/>
                <div className="mainContent">

                    <div className="container profile-container">
                        <div className="row d-flex justify-content-center ">
                            <div className="col-md-10 mt-5 pt-5 pb-5 bg-success">
                                <div className="row z-depth-3">
                                    <div className="col-sm-4 bg-success rounded-left">

                                        <div className="card-block text-center">
                                            <img className = "profile-img" src={userphoto} alt="avatar"/>
                                        </div>
                                        
                                    </div>

                                    <div className="col-sm-8 bg-success rounded-right">
                                        {/* <h1 className="profile-text mt-3 text-center">Stavros Kostopoulos</h1> */}

                                        <h1 className="profile-text mt-3 text-center">{user["firstName"]} {user["lastName"]}</h1>
                                        
                                        <div className="row mt-4">
                                            <div className="col-sm-6">
                                                <h4 className="profile-text"> username </h4>
                                                {/* <p className="text-muted">skankhunt42</p> */}

                                                <p className="text-muted">{loggedUser && loggedUser.username}</p>
                                            </div>
                                            <div className="col-sm-6">
                                                <h4 className="profile-text"> e-mail </h4>
                                                {/* <p className="text-muted">stavroskost@outlook.com</p> */}

                                                <p className="text-muted">{loggedUser && loggedUser.email}</p>
                                            </div>

                                            
                                        </div>

                                        <div className="row mt-4">
                                            
                                            <div className="col-sm-6">
                                                <h4 className="profile-text"> Τοποθεσία </h4>
                                                {/* <p className="text-muted">stavroskost@outlook.com</p> */}

                                                <p className="text-muted">{user["location"]}</p>
                                            </div>

                                            
                                        </div>

                                        <div className="row mt-4">
                                            <div className="col-sm-6">
                                                <h4 className="profile-text"> Περιγραφή </h4>
                                                {/* <p className="text-muted">skankhunt42</p> */}

                                                <p className="text-muted">{user["bio"]}</p>
                                            </div>
                                            

                                            
                                        </div>

                                    </div>


                                </div>
                                
                            </div>    
                        </div>
                        <div className="row d-flex justify-content-center ">
                            <div className="col-md-10 pt-5 pb-4 bg-success">
                                <ul className="list-group" style={{width: '100%'}}>
                                    <li className="list-group-item d-flex justify-content-between align-items-center profile-list-item">
                                        Tρέχων projects
                                        {/* <span className="badge badge-primary badge-pill">5</span> */}

                                        <span className="badge badge-primary badge-pill">{currentProjects}</span>
                                    </li>
                                    <li className="list-group-item d-flex justify-content-between align-items-center profile-list-item">
                                        Ολοκληρωμένα/αρχειοθετημένα projects
                                        {/* <span className="badge badge-primary badge-pill">12</span> */}

                                        <span className="badge badge-primary badge-pill">{doneProjects}</span>
                                    </li>
                                    
                                </ul>
                            </div>

                        </div>
                    </div>
                

                </div>
            
                
                
            </div>
        );

    
    
}
 
export default ProfilePage;
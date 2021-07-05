import React, { Component, useEffect, useState } from 'react';
import { Link, useHistory } from 'react-router-dom'
import SideNavBar from '../components/SideNavBar.js'
import Topbar from '../components/Topbar.js'
import EventCalendar from './EventCalendar.js'

import '../css/notifications.css'




function OverviewScreen() {

    const history = useHistory();
    const loggedUser = JSON.parse(localStorage.getItem('loggedUser'));
    
    useEffect(() => {
        document.body.style.background = "#fff";
        if (!loggedUser){
            history.push("/login");
        }
        else {
            fetch('http://localhost:8080/developers/users/'+loggedUser.id+'/invitations', {
                    method: 'get', 
                    headers: { Authorization: 'Bearer ' + loggedUser.accessToken }
                })
                    .then(res => res.json())
                    .then((data) => {
                        console.log("Notifications:");
                        console.log(data);
                        if (data.invitationList){
                            setNotifs(data.invitationList)
                        }
                    })

        }

    }, []);

    function decline(projId) {
        fetch('http://localhost:8080/developers/users/'+loggedUser.id+'/projects/'+projId, {
                    method: 'delete', 
                    headers: { Authorization: 'Bearer ' + loggedUser.accessToken }
                })
                    .then(res => res.json())
                    .then((data) => {
                        console.log("Tried to delete:");
                        console.log(data); // expecting {message:"OK"}
                        if (data.message){
                            if (data.message === "OK"){
                                window.location.reload(false);
                            }
                        }
                    })
    }

    function accept(projId) {// /developers/users/{user_id}/projects/{project_id}
        fetch('http://localhost:8080/developers/users/'+loggedUser.id+'/projects/'+projId+'/accept', {
                    method: 'put', 
                    headers: { Authorization: 'Bearer ' + loggedUser.accessToken }
                })
                    .then(res => res.json())
                    .then((data) => {
                        console.log("Trying to accept:");
                        console.log(data); // expecting {message:"OK"}
                        if (data.message){
                            if (data.message === "OK"){
                                window.location.reload(false);
                            }
                        }
                    })
    }

    const [notifs, setNotifs] = useState([])
    
    return (
        <div>
            <Topbar/>
            <SideNavBar/>
            <div className="mainContent">
                <div className="row mt-5 pb-5 d-flex justify-content-center">
                    
                    <div className="col-md-9 mt-4">
                        <h3 className="notifications-title">Ειδοποιήσεις</h3>
                        <hr/>
                    </div>

                </div>


                {/* notifications list */}
                {(notifs.length != 0) && (<div className="row mt-2 d-flex justify-content-center">
                    {notifs.map(i=> <>
                        <div className="col-md-8  mt-3 notification-box">
                            <p className="notification-desc"> Ο χρήστης <b>{i.owner_username}</b> σας προσκάλεσε να συμμετέχετε στο <b>{i.title}</b></p>
                        </div>
                        <div className="col-6 col-md-1 mt-3 ">
                            <div class="btn-group notification-btns" role="group" aria-label="Basic mixed styles example">
                                <button class="btn btn-success btn-sm" onClick={()=>accept(i.project_id)}>Αποδοχή</button>
                                <button class="btn btn-danger btn-sm" onClick={()=>decline(i.project_id)}>Απόρριψη</button>
                            </div>
                        </div>
                    </>)} 
                </div>)}
                {(notifs.length === 0) && <div className= "justify-content-center text-center"><p className=" text-muted">Καμία Ειδοποίηση</p></div>}
                


                            
            </div>

        </div>

    );
}

 
export default OverviewScreen;

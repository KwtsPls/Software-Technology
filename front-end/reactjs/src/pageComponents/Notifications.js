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
        // if (!loggedUser){
        //     history.push("/login");
        // }

    }, []);

    
 
    
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

                
                <div className="row mt-2 d-flex justify-content-center">
                    
                    <div className="col-md-8  mt-3 notification-box">
                        <p className="notification-desc"> Ο χρήστης <b>kostasplas</b> σας προσκάλεσε να συμμετέχετε στο Project 1</p>
                    </div>
                    
                    <div className="col-6 col-md-1 mt-3 ">
                        <div class="btn-group notification-btns" role="group" aria-label="Basic mixed styles example">
                            <button type="button" class="btn btn-success btn-sm">Αποδοχή</button>
                            <button type="button" class="btn btn-danger btn-sm">Απόρριψη</button>
                        </div>
                    </div>

                    

                </div>
                


                            
            </div>

        </div>

    );
}

 
export default OverviewScreen;
import React, { Component, useEffect, useState } from 'react';
import { Link, useHistory } from 'react-router-dom'
import '../css/Overviewscreen.css'
import sunphoto from '../images/sun2.png'






function OverviewScreen() {

    const history = useHistory();
    const loggedUser = JSON.parse(localStorage.getItem('loggedUser'));
    
    useEffect(() => {
        if (!loggedUser){
            history.push("/login");
        }
        document.body.style.background = "#fff";

    }, []);

    
    return (
        <div>
            <Topbar/>
            <SideNavBar/>
            <div className="mainContent">

                
                
            </div>

        </div>

    );
}

 
export default OverviewScreen;
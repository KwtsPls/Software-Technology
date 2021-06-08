import React, { Component, useEffect } from 'react';
import SideNavBar from '../components/SideNavBar.js'
import OverviewScreen from '../pageComponents/OverviewScreen.js'
import Topbar from '../components/Topbar.js'
import { useHistory } from 'react-router-dom'

function HomePage() {
    const history = useHistory();
    const loggedUser = JSON.parse(localStorage.getItem('loggedUser'));

    useEffect(() => {
        if (!loggedUser){
            history.push("/login");
        }
    }, []);

    return (
        <div>
			<Topbar/>
            <SideNavBar/>
            <div className="mainContent">
			    <OverviewScreen/>
		    </div>
        </div>
    );
}
 
export default HomePage;
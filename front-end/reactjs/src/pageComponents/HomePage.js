import React, { Component } from 'react';
import SideNavBar from '../components/SideNavBar.js'
import OverviewScreen from '../pageComponents/OverviewScreen.js'
import Topbar from '../components/Topbar.js'

class HomePage extends Component {
    render() { 
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
}
 
export default HomePage;
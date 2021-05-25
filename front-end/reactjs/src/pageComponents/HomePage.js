import React, { Component } from 'react';
import SideNavBar from '../components/SideNavBar.js'
import OverviewScreen from '../components/OverviewScreen.js'

class HomePage extends Component {
    render() { 
        return (
            <div>
                <SideNavBar/>
                <div className="mainContent">
				    <OverviewScreen/>
			    </div>
            </div>
        );
    }
}
 
export default HomePage;
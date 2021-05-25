import React, { Component } from 'react';
import '../App.css';
import SideNavBar from '../components/SideNavBar.js'
import Topbar from '../components/Topbar.js'


class ProfilePage extends Component {
    render() { 
        return (
            <div>
				<Topbar/>

                <SideNavBar/>
                <h1 className="text-center">
                    ProfilePage
                </h1>
                <h1 className="text-center">
                    ProfilePage
                </h1>
                <h1 className="text-center">
                    ProfilePage
                </h1>
                
                
            </div>
        );
    }
}
 
export default ProfilePage;
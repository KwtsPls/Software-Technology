import React, { Component } from 'react';
import '../App.css';
//import '../css/settings.css';
import SideNavBar from '../components/SideNavBar.js'
import Topbar from '../components/Topbar.js'


class ProfilePage extends Component {
    render() { 
        return (
            <div>
				<Topbar/>
                <SideNavBar/>
                <div className="mainContent">

                    <div className="jete-breadcrump">
                        <nav aria-label="breadcrumb">
                            <ol className="breadcrumb">
                                <li className="breadcrumb-item"><a href="#" className="breadcrumb-option">Home</a></li>
                                <li className="breadcrumb-item"><a href="#" className="breadcrumb-option">Library</a></li>
                                <li className="breadcrumb-item active" aria-current="page">Data</li>
                            </ol>
                        </nav>
                    </div>


                </div>
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
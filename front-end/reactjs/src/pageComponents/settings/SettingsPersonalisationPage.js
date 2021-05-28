import React, { Component } from 'react';
import '../../App.css';
import SideNavBar from '../../components/SideNavBar.js'
import Topbar from '../../components/Topbar.js'
import { Link } from 'react-router-dom'



class SettingsPersonalisationPage extends Component {
    render() { 
        return (
            <div>
                <Topbar/>
                <SideNavBar/>
                <div className="mainContent">
                    <div className="jete-settingsMenu">
                        <div class="list-group">
                            <Link to='/settings/profile'>
                                <a href="#" class="list-group-item list-group-item-action">Profile</a>
                            </Link>
                            <a href="#" class="list-group-item list-group-item-action active">Personalisation</a>
                            <Link to='/settings/security'>
                                <a href="#" class="list-group-item list-group-item-action">Security</a>
                            </Link>
                            <Link to='/settings/billing'>
                                <a href="#" class="list-group-item list-group-item-action">Billing</a>
                            </Link>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}
 
export default SettingsPersonalisationPage;
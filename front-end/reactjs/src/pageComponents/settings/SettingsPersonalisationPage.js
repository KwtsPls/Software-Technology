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
                            <Link to='/settings/profile' class="list-group-item list-group-item-action">Profile</Link>
                            <a href="#" class="list-group-item list-group-item-action active">Personalisation</a>
                            <Link to='/settings/security' class="list-group-item list-group-item-action">Security</Link>
                            <Link to='/settings/billing' class="list-group-item list-group-item-action">Billing</Link>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}
 
export default SettingsPersonalisationPage;
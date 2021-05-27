import React, { Component } from 'react';
import '../../App.css';
import SideNavBar from '../../components/SideNavBar.js'
import { Link } from 'react-router-dom'
import Topbar from '../../components/Topbar.js'
import '../../css/settings.css';



class SettingsBillingPage extends Component {
    render() { 
        return (
            <div>
                <Topbar/>

                <SideNavBar/>

                <div className="mainContent">
                    
                    
                    

                    <div className="jete-settingsMenu">
                        <div class="list-group">
                            <Link to='/settings/profile'>
                            <a href="#" class="list-group-item list-group-item-action">
                                Profile
                            </a>
                            </Link>

					        <Link to='/settings/personalisation'>
                            <a href="#" class="list-group-item list-group-item-action">Personisation</a>
                            </Link>
					        <Link to='/settings/security'>
                            <a href="#" class="list-group-item list-group-item-action">Security</a>
                            </Link>
                            
                            <a href="#" class="list-group-item list-group-item-action  active">Billing</a>
                            
                        </div>
                    </div>

                    <div className="settingsContains">
                    <h1 className="settings-header">Account billing</h1>
                        <hr className="new4"/>
                    </div>
                
                    <h1 className="text-center">
                        SettingsBillingPage
                    </h1>
                    <h1 className="text-center">
                        SettingsBillingPage
                    </h1>
                    <h1 className="text-center">
                        SettingsBillingPage
                    </h1>
                </div>
                
            </div>
        );
    }
}
 
export default SettingsBillingPage;
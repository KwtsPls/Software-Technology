import React, { Component } from 'react';
import '../../App.css';
import { Link } from 'react-router-dom'

import SideNavBar from '../../components/SideNavBar.js'
import Topbar from '../../components/Topbar.js'
import '../../css/settings.css';


class SettingsProfilePage extends Component {
    

    render() { 
        return (
            <div>
                <Topbar/>
                <SideNavBar/>
                <div className="mainContent">
                    
                    <div className="jete-settingsMenu">
                        <div class="list-group">
                            <a href="#" class="list-group-item list-group-item-action active">Profile</a>
					        <Link to='/settings/personalisation' class="list-group-item list-group-item-action">Personalisation</Link>
					        <Link to='/settings/security' class="list-group-item list-group-item-action">Security</Link>
                            <Link to='/settings/billing' class="list-group-item list-group-item-action">Billing</Link>
                        </div>
                    </div>

                    <div className="settingsContains">
                        <h1 className="settings-header">Public profile</h1>
                        <hr className="new4"/>


                        <form className="settingsprofile-form">
                            <div className="form-group name">
                                <label for="settingsname">Name</label>
                                <input type="text" className="form-control" id="settingsname"  placeholder="Name from backend"/>
                            </div>
                            <div className="form-group surname">
                                <label for="settingssurname">Surname</label>
                                <input type="text" className="form-control" id="settingssurname" aria-describedby="namehelp" placeholder="Surname from backend"/>
                                <small id="surnamehelp" className="form-text text-muted">Your name and surname appears on your public profile</small>
                            </div>
                            

                            <div className="form-group pt-3 email">
                                <label className="sr-only" for="emailInput">Email</label>
                                <div className="input-group mb-2">
                                    <div className="input-group-prepend">
                                        <div className="input-group-text">@</div>
                                    </div>
                                    <input type="email" class="form-control" id="emailInput" placeholder="Email from backend" readOnly/>
                                </div>
                            </div>



                            <div className="form-group bio">
                                <label for="settingsbio">Bio</label>
                                {/* <input type="text" className="form-control" id="settingsbio" aria-describedby="biohelp" placeholder="Bio from backend"/> */}
                                <textarea className="form-control" id="settingsbio" placeholder="Bio from backend"></textarea>
                                <small id="biohelp" className="form-text text-muted">Tell us a bit about yourself</small>
                            </div>

                            <div className="form-group location">
                                <label for="locationname">Location</label>
                                <input type="text" className="form-control" id="locationname"  placeholder="Location from Backend"/>
                            </div>

                            <button type="button" class="btn btn-success settingsprofile-button">Update my profile</button>


                        </form>
                        <div className="blankspace"/>                            
                    </div>
                </div>
                
            </div>
        );
    }
}
 
export default SettingsProfilePage;
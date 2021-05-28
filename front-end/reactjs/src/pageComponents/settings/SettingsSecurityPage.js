import React, { Component } from 'react';
import '../../App.css';
import SideNavBar from '../../components/SideNavBar.js'
import { Link } from 'react-router-dom'
import Topbar from '../../components/Topbar.js'
import '../../css/settings.css';

class SettingsSecurityPage extends Component {

    constructor(props) {
        super(props);
        this.verify = React.createRef();
        this.verifymsg = React.createRef();
          
    }

    verifyemail=()=>{
        
        const node = this.verifymsg.current;
        node.style.display = "block";

        const node2 = this.verify.current;
        node2.style.display = "none";
        
    }

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
                            <Link to='/settings/personalisation'>
                                <a href="#" class="list-group-item list-group-item-action">Personalisation</a>
                            </Link>
                            <a href="#" class="list-group-item list-group-item-action active">Security</a>
                            <Link to='/settings/billing'>
                                <a href="#" class="list-group-item list-group-item-action">Billing</a>
                            </Link>
                        </div>
                    </div>
                    <div className="settingsContains">
                        <h1 className="settings-header">Account security</h1>
                        <hr className="new4"/>


                        <p className="settings-unit">
                            <b>Change password:</b>
                        </p>

                        <form className="settingsprofile-form change-password-form">
                            
                            <div className="form-group oldpassword">
                                <label for="settingsoldpassword">Old password:</label>
                                <input type="password" className="form-control" id="settingsoldpassword"  placeholder="Type your old password"/>
                            </div>
                            
                            <div className="form-group newpassword">
                                <label for="settingsnewpassword">New password:</label>
                                <input type="password" className="form-control" id="settingsnewpassword"  placeholder="Type your new password"/>
                            </div>
                            

                            <div className="form-group newpassword-reapeat">
                                <label for="settingsnewpassword-repeat">Confirm password:</label>
                                <input type="password" className="form-control" id="settingsnewpassword-repeat" aria-describedby="pwhelp" placeholder="Type new password again"/>
                                <small id="pwhelp" className="form-text text-muted"><br/>Password must contain at least one uppercase letter and one number.</small>

                            </div>

                            <button type="button" class="btn btn-primary passwordchange-button">Change password</button>

                        </form>

                        <hr className="changepasswordline"/>

                        {/* <p className="settings-unit">
                            <b>Email verification:</b>&emsp; &emsp; &emsp; Your email is already verified!
                        </p> */}

                        <p className="settings-unit">
                            <b>Email verification:</b>
                        </p>

                        <button ref={this.verify} onClick={this.verifyemail} type="button" class="btn btn-success verifyemail-button">Resend verification email</button>
                        
                        <p ref={this.verifymsg} className="settings-unit emailsent-verify">
                            Verification email sent! Check your inbox! 
                        </p>

                        <div className="blankspace"/>                            

                    </div>
                
                
                </div>
            </div>
        );
    }
}
 
export default SettingsSecurityPage;
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
                        <div className="list-group">
                            <Link to='/settings/profile' className="list-group-item list-group-item-action">Προφίλ</Link>
                            <Link to='/settings/personalisation' className="list-group-item list-group-item-action">Εξατομίκευση</Link>
                            <a href="#" className="list-group-item list-group-item-action active">Ασφάλεια</a>
                            <Link to='/settings/billing' className="list-group-item list-group-item-action">Χρεώσεις</Link>
                        </div>
                    </div>
                    <div className="settingsContains">
                        <h1 className="settings-header">Ασφάλεια λογαριασμού</h1>
                        <hr className="new4"/>


                        <p className="settings-unit">
                            <b>Αλλαγή κωδικού πρόσβασης:</b>
                        </p>

                        <form className="settingsprofile-form change-password-form">
                            
                            <div className="form-group oldpassword">
                                <label for="settingsoldpassword">Παλιός κωδικός:</label>
                                <input type="password" className="form-control" id="settingsoldpassword"  placeholder="Πληκτρολογίστε τον τωρινό κωδικό σας"/>
                            </div>
                            
                            <div className="form-group newpassword">
                                <label for="settingsnewpassword">Νέος κωδικός:</label>
                                <input type="password" className="form-control" id="settingsnewpassword"  placeholder="Πληκτρολογίστε τον νέο κωδικό σας"/>
                            </div>
                            

                            <div className="form-group newpassword-reapeat">
                                <label for="settingsnewpassword-repeat">Επιβεβαίωση νέου κωδικού:</label>
                                <input type="password" className="form-control" id="settingsnewpassword-repeat" aria-describedby="pwhelp" placeholder="Πληκτρολογίστε τον νέο κωδικό σας ξανά"/>
                                <small id="pwhelp" className="form-text text-muted"><br/>Ο κωδικός πρόσβασης πρέπει να περιέγχει τουλάχιστον ένα κεφαλαίο γράμμα και έναν αριθμό</small>

                            </div>

                            <button type="button" className="btn btn-primary passwordchange-button">Επιβεβαίωση</button>

                        </form>

                        <hr className="changepasswordline"/>

                        {/* <p className="settings-unit">
                            <b>Email verification:</b>&emsp; &emsp; &emsp; Το email σας είναι ήδη επαληθευμένο!
                        </p> */}

                        <p className="settings-unit">
                            <b>Επαλήθευση email:</b>
                        </p>

                        <button ref={this.verify} onClick={this.verifyemail} type="button" className="btn btn-success verifyemail-button">Αποστολή μηνυμήματος επαλήθευσης</button>
                        
                        <p ref={this.verifymsg} className="settings-unit emailsent-verify">
                            Το μήνυμα εστάλη!Δείτε το email σας! 
                        </p>

                        <div className="blankspace"/>                            

                    </div>
                
                
                </div>
            </div>
        );
    }
}
 
export default SettingsSecurityPage;
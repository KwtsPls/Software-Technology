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
                            <a href="#" class="list-group-item list-group-item-action active">Προφίλ</a>
					        <Link to='/settings/personalisation' class="list-group-item list-group-item-action">Εξατομίκευση</Link>
					        <Link to='/settings/security' class="list-group-item list-group-item-action">Ασφάλεια</Link>
                            <Link to='/settings/billing' class="list-group-item list-group-item-action">Χρεώσεις</Link>
                        </div>
                    </div>

                    <div className="settingsContains">
                        <h1 className="settings-header">Δημόσιο προφίλ</h1>
                        <hr className="new4"/>


                        <form className="settingsprofile-form">
                            <div className="form-group name">
                                <label for="settingsname">Όνομα</label>
                                <input type="text" className="form-control" id="settingsname"  placeholder="Name from backend"/>
                            </div>
                            <div className="form-group surname">
                                <label for="settingssurname">Επίθετο</label>
                                <input type="text" className="form-control" id="settingssurname" aria-describedby="namehelp" placeholder="Surname from backend"/>
                                <small id="surnamehelp" className="form-text text-muted">Το όνομα και το επίθετό σας εμφανίζονται στο δημόσιο προφίλ σας</small>
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
                                <label for="settingsbio">Περιγραφή</label>
                                {/* <input type="text" className="form-control" id="settingsbio" aria-describedby="biohelp" placeholder="Bio from backend"/> */}
                                <textarea className="form-control" id="settingsbio" placeholder="Bio from backend"></textarea>
                                <small id="biohelp" className="form-text text-muted">Πείτε μας κάτι για εσάς</small>
                            </div>

                            <div className="form-group location">
                                <label for="locationname">Τοποθεσία</label>
                                <input type="text" className="form-control" id="locationname"  placeholder="Location from Backend"/>
                            </div>

                            <button type="button" class="btn btn-success settingsprofile-button">Ενημέρωση στοιχείων</button>


                        </form>
                        <div className="blankspace"/>                            
                    </div>
                </div>
                
            </div>
        );
    }
}
 
export default SettingsProfilePage;
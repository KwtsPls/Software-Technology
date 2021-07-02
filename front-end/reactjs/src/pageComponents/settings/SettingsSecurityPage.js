import React, { useState, useEffect } from 'react';

import '../../App.css';
import SideNavBar from '../../components/SideNavBar.js'
import { Link, useHistory } from 'react-router-dom'
import Topbar from '../../components/Topbar.js'
import '../../css/settings.css';

function SettingsSecurityPage(){

    const history = useHistory();
    const loggedUser = JSON.parse(localStorage.getItem('loggedUser'));
    
    const [oldpass, setOldpass] = useState("");
    const [pass1, setPass1] = useState("");
    const [pass2, setPass2] = useState("");


    useEffect(() => {
        if (!loggedUser){
            history.push("/login");
        }
        document.body.style.background = "#fff";

    }, []);

    const [showEmptyOldPass, setShowEmptyOldPass] = useState(false)
    const [showEmptyPass1, setShowEmptyPass1] = useState(false)
    const [showEmptyPass2, setShowEmptyPass2] = useState(false)
    const [showPasswordsDontMatch, setShowPasswordsDontMatch] = useState(false)
    const [showBadPassInput, setShowBadPassInput] = useState(false)

    useEffect(() => {
        setShowPasswordsDontMatch(false);
    }, [pass1, pass2]);

    function passwordValid(str) {
        return /[a-z]/.test(str) && /[A-Z]/.test(str) && /\d/.test(str);
    }


    function sendPasswordChange() {
        let earlyExit = false

        if (!oldpass){
            console.log("oldpass empty");
            earlyExit = true;
            setShowEmptyOldPass(true);
        }else{
            setShowEmptyOldPass(false);
        }

        //Check pass1
        if (!pass1){//not empty
            console.log("pass1 is empty");
            earlyExit = true;
            setShowEmptyPass1(true);
        }
        else {
            if(!passwordValid(pass1)){//invalid chars
                earlyExit = true;
                setShowBadPassInput(true);
                setShowEmptyPass1(false);

            }else{
                setShowBadPassInput(false);
                setShowEmptyPass1(false);
            }
        }

        //check pass2
        if (!pass2){
            console.log("pass2 is empty");
            earlyExit = true;
            setShowEmptyPass2(true);
        }
        else {
            setShowEmptyPass2(false);
        }

        if (pass1 != pass2){
            console.log("passwords don't match");
            earlyExit = true;
            setShowPasswordsDontMatch(true);
        }

        

        if (earlyExit){
            return ;
        }

        const requestOptions = {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json',
                        'Authorization': 'Bearer ' + loggedUser.accessToken  
                    },
            body: JSON.stringify({  password: pass1
                                })
        };
        fetch('http://localhost:8080/users/' + loggedUser.id, requestOptions)
            .then(response => response.json())
            .then(data => {
                console.log(data); // JSON data parsed by `data.json()` call
              });

    }



    return (
        <div>
            <Topbar/>
            <SideNavBar/>
            <div className="mainContent">
                <div className="jete-settingsMenu settings-postition">
                    <div className="list-group">
                        <Link to='/settings/profile' className="list-group-item list-group-item-action">Προφίλ</Link>
                        <a href="#" className="list-group-item list-group-item-action active">Ασφάλεια</a>
                        <Link to='/settings/billing' className="list-group-item list-group-item-action">Χρεώσεις</Link>
                    </div>
                </div>
                <div className="settingsContains settings-postition">
                    <h1 className="settings-header">Ασφάλεια λογαριασμού</h1>
                    <hr className="new4"/>


                    <p className="settings-unit">
                        <b>Αλλαγή κωδικού πρόσβασης:</b>
                    </p>

                    <form className="settingsprofile-form change-password-form">
                            
                        <div className="form-group oldpassword">
                            <label for="settingsoldpassword">Παλιός κωδικός:</label>
                            <input type="password" className="form-control" value={oldpass} id="settingsoldpassword" onChange={e => setOldpass(e.target.value)} placeholder="Πληκτρολογίστε τον τωρινό κωδικό σας"/>
                            <div>
                                { (showEmptyOldPass) && <span className="badge bg-danger rounded-pill">Συμπληρώστε τον τωρινό σας κωδικό</span>}
                            </div>
                        </div>
                            
                        <div className="form-group newpassword">
                            <label for="settingsnewpassword">Νέος κωδικός:</label>
                            <input type="password" className="form-control"  value={pass1} id="settingsnewpassword"  onChange={e => setPass1(e.target.value)} placeholder="Πληκτρολογίστε τον νέο κωδικό σας"/>
                            <div>
                                { (showBadPassInput) && <span className="badge bg-danger rounded-pill">Ο κωδικός πρέπει να περιέγχει τουλάχιστον <br/>ένα κεφαλαίο γράμμα και έναν αριθμό</span>}
                            </div>
                            <div>
                                { (showEmptyPass1) && <span className="badge bg-danger rounded-pill">Ο κωδικός δεν μπορεί να είναι κενός</span>}
                            </div>
                        </div>
                            

                        <div className="form-group newpassword-reapeat">
                            <label for="settingsnewpassword-repeat">Επιβεβαίωση νέου κωδικού:</label>
                            <input type="password" className="form-control" value={pass2}  id="settingsnewpassword-repeat" onChange={e => setPass2(e.target.value)} aria-describedby="pwhelp" placeholder="Πληκτρολογίστε τον νέο κωδικό σας ξανά"/>
                            <small id="pwhelp" className="form-text text-muted"><br/>Ο κωδικός πρόσβασης πρέπει να περιέγχει τουλάχιστον ένα κεφαλαίο γράμμα και έναν αριθμό</small>
                            <div>
                                { (showEmptyPass2) && <span className="badge bg-danger rounded-pill">Ο κωδικός δεν μπορεί να είναι κενός</span>}
                            </div>
                            <div>
                                { (showPasswordsDontMatch) && <span className="badge bg-danger rounded-pill">Οι δυο κωδικοί δεν είναι ίδιοι</span>}
                            </div>
                        </div>

                        <button type="button" className="btn btn-primary passwordchange-button" onClick={sendPasswordChange}>Επιβεβαίωση</button>

                    </form>

                    <hr className="changepasswordline"/>

                    

                    
                    <p className="settings-unit">
                        <b>Επαλήθευση email:</b>&emsp; &emsp; &emsp; Το email σας είναι ήδη επαληθευμένο!
                    </p>
                    
                    <div className="blankspace"/>                            

                </div>
                
                
            </div>
        </div>
    );
    
}
 
export default SettingsSecurityPage;
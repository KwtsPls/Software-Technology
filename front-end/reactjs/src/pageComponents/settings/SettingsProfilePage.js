import React, { useEffect, useState } from 'react';
import '../../App.css';
import { Link, useHistory } from 'react-router-dom'
import SideNavBar from '../../components/SideNavBar.js'
import Topbar from '../../components/Topbar.js'
import '../../css/settings.css';


function SettingsProfilePage() {

    const history = useHistory();
    const loggedUser = JSON.parse(localStorage.getItem('loggedUser'));

    const [isLoading, setLoading] = useState(true);
    const [user, setUser] = useState([])

    // State for input fields
    const [fName, setFName] = useState("")
    const [lName, setLName] = useState("")
    const [username, setUsername] = useState("")
    const [bio, setBio] = useState("")
    const [loc, setLoc] = useState("")

    useEffect(() => {
        if (!loggedUser){
            history.push("/login");
        }
        else{
            document.body.style.background = "#fff";

            fetch('http://localhost:8080/users/' + loggedUser.id, {
                method: 'get', 
                headers: { Authorization: 'Bearer ' + loggedUser.accessToken }
            })
                .then(res => res.json())
                .then((data) => {
                    setUser(data);
                    setLoading(false);
                })
            
        }
    }, []);

    function placeHolder(load,attr){
        if (isLoading) { return "Loading " + load + "..."; }
        return user[attr];
    }

    function conditionalUpdate(stateItem,attr){
        if (stateItem === ""){
            return user[attr];
        }
        return stateItem;
    }

    function changeInfo() {
        // Simple PUT request with a JSON body using fetch
        const requestOptions = {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json',
                        'Authorization': 'Bearer ' + loggedUser.accessToken  
                    },
            body: JSON.stringify({  firstName: conditionalUpdate(fName,'firstName'),
                                    lastName: conditionalUpdate(lName,'lastName'),
                                    username: conditionalUpdate(username,'username'),
                                    location: conditionalUpdate(loc,'location'),
                                    bio: conditionalUpdate(bio,'bio')
                                })
        };
        fetch('http://localhost:8080/users/' + loggedUser.id, requestOptions)
            .then(response => response.json())
            .then(data => {
                console.log(data); // JSON data parsed by `data.json()` call
              });
            setTimeout(function(){window.location.reload();}, 100);
    }

    
    

    return (
        <div>
            <Topbar/>
            <SideNavBar/>
            <div className="mainContent">
                
                <div className="jete-settingsMenu settings-postition">
                    <div className="list-group">
                        <a href="#" className="list-group-item list-group-item-action active">????????????</a>
				        <Link to='/settings/security' className="list-group-item list-group-item-action">????????????????</Link>
                        <Link to='/settings/billing' className="list-group-item list-group-item-action">????????????????</Link>
                    </div>
                </div>
                
                <div className="settingsContains settings-postition">
                    <h1 className="settings-header">?????????????? ????????????</h1>
                    <hr className="new4"/>


                    <form className="settingsprofile-form">
                        <div className="form-group name">
                            <label for="settingsname">??????????</label>
                            <input type="text" className="form-control" id="settingsname"  placeholder={placeHolder("name","firstName")} value={fName} onChange={e => setFName(e.target.value)}/>
                        </div>
                        <div className="form-group surname">
                            <label for="settingssurname">??????????????</label>
                            <input type="text" className="form-control" id="settingssurname" aria-describedby="namehelp" placeholder={placeHolder("surname","lastName")} value={lName} onChange={e => setLName(e.target.value)}/>
                            <small id="surnamehelp" className="form-text text-muted">???? ?????????? ?????? ???? ?????????????? ?????? ???????????????????????? ?????? ?????????????? ???????????? ??????</small>
                        </div>
                        <div className="form-group usernamesettings">
                            <label for="settingsname">Username</label>
                            <input type="text" className="form-control" id="settingsusername" aria-describedby="usernamehelp" placeholder={placeHolder("username","username")} value={username} onChange={e => setUsername(e.target.value)}/>
                            <small id="usernamehelp" className="form-text text-muted">???? username ?????? ???????????? ???? ?????????????????????? ?????? ?????????????????????? 6 ???????????????????? ???????????????????? ?????? ????????????????</small>

                        </div>
                        
                        <div className="form-group pt-3 email">
                            <label className="sr-only" for="emailInput">Email</label>
                            <div className="input-group mb-2">
                                <div className="input-group-prepend">
                                    <div className="input-group-text">@</div>
                                </div>
                                <input type="email" className="form-control" id="emailInput" placeholder={placeHolder("email","email")} readOnly/>
                            </div>
                        </div>



                        <div className="form-group bio">
                            <label for="settingsbio">??????????????????</label>
                            {/* <input type="text" className="form-control" id="settingsbio" aria-describedby="biohelp" placeholder="Bio from backend"/> */}
                            <textarea className="form-control" id="settingsbio" placeholder={placeHolder("bio","bio")} value={bio} onChange={e => setBio(e.target.value)}/>
                            <small id="biohelp" className="form-text text-muted">?????????? ?????? ???????? ?????? ????????</small>
                        </div>

                        <div className="form-group location">
                            <label for="locationname">??????????????????</label>
                            <input type="text" className="form-control" id="locationname"  placeholder={placeHolder("location","location")} value={loc} onChange={e => setLoc(e.target.value)}/>
                        </div>

                        <button type="button" className="btn btn-success settingsprofile-button" onClick={changeInfo}>?????????????????? ??????????????????</button>


                    </form>
                    <div className="blankspace"/>                            
                </div>
            </div>
        </div>
    );
}
 
export default SettingsProfilePage;
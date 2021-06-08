import React, { Component, useState} from 'react';
import { Link, useHistory } from 'react-router-dom'
import logo from '../images/logo.png';
import partners from '../images/partners.png';
import {Modal} from "react-bootstrap"


import '../App.css'
import '../css/login.css'


function LoginPage() {
    const history = useHistory();

    const [user, setUser] = useState("");
    const [pass, setPass] = useState("");

    const [showModal, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    let dataReceived = []

    function sendLoginCredentials() {
        fetch('http://localhost:8080/signin', {
            method: 'post', 
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({  username: user,
                                    password: pass
        })
        })
        .then(response => response.json())
        .then(data => {
            console.log(data);
            if (data.accessToken) {
              localStorage.setItem("loggedUser", JSON.stringify(data));
            }
            console.log(data);
            dataReceived = data;
          }).then( () => {
                if (dataReceived){
                    //localStorage.setItem("loggedUser", true);
                    console.log("logged in");
                    history.push("/home");
                }
                else{
                    handleShow();
                }
            });
            // .then(response => response.json())
            // .then(data => {
            //     console.log("13v");
            //     console.log(data); // JSON data parsed by `data.json()` call
            //     dataReceived = data;
            // })
            // .then( () => {
            //     if (dataReceived){
            //         localStorage.setItem("loggedUser", true);
            //         console.log("logged in");
            //         history.push("/home");
            //     }
            //     else{
            //         handleShow();
            //     }
            // });
    } 

    return (
        <div className="loginbackground">
            <div className="login-screen">
                <div className="login-desc-box">
                    <img className = "jete_logo-icon-login" src={logo} alt="logo"></img>        
                    <p className="desc-header">Καλωσήρθατε</p>
                    <img className = "partners" src={partners} alt="partnerssketch"></img>        
                </div>
                <div className="login-form-box">
                    <div className="login-form">
                                    
                        <div className="col-auto">
                            <div>
                                <div className="top-buffer">
                                    <h1 className="text-center login-header">
                                        Σύνδεση
                                    </h1>
                                    <form className="login-form-boxes">
                                        <div className="form-group pt-3">
                                            <label for="exampleInputUsername1">Όνομα χρήστη</label>
                                            <input type="username" className="form-control" id="exampleInputUsername1" aria-describedby="usernameHelp" placeholder="Πληκτρολογίστε το όνομα χρήστη σας" value={user} onChange={e => setUser(e.target.value)}/>
                                        </div>
                                        <div className="form-group pt-3">
                                            <label for="exampleInputPassword1">Κωδικός πρόσβασης</label>
                                            <input type="password" className="form-control" id="exampleInputPassword1" placeholder="Πληκτρολογίστε τον κωδικό σας"  value={pass} onChange={e => setPass(e.target.value)}/>
                                        </div>
                                        <div className="form-check pt-3">
                                            <input type="checkbox" className="form-check-input mycheckbox" id="exampleCheck1"/>
                                            <label className="form-check-label" for="exampleCheck1">Remember me</label>
                                        </div>
                                        <div className="text-center pt-3">
                                            {/* <Link to='/home'> */}
                                                <button type="button" className="btn-lg btn-primary login-button" onClick={sendLoginCredentials}>Σύνδεση</button>
                                            {/* </Link> */}
                                        </div>
                                        <div className="text-center pt-3">
                                            <small id="emailHelp" className="form-text text-muted">Είναι η πρώτη σας φορά εδώ; </small>
                                            <Link to='/signUp'>
                                                <small id="signUpLink" className="form-text signup-link signUpStyle"> Εγγραφείτε</small>
                                            </Link>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <Modal show={showModal} centered>
                <Modal.Header>
                    <Modal.Title id="contained-modal-title-vcenter">
                        Σφάλμα
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    Λάθος όνομα χρήστη ή κωδικός
                </Modal.Body>
                <Modal.Footer>
                    <button type="button" class="btn btn-outline-danger" onClick={handleClose}>Κλείσιμο</button>
                </Modal.Footer>
            </Modal>
        </div>
    );
}
 
export default LoginPage;

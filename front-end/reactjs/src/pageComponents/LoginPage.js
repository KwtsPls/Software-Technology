import React, { useEffect, useState} from 'react';
import { Link, useHistory } from 'react-router-dom'
import logo from '../images/logo2.png';
import partners from '../images/partners.png';
import {Modal} from "react-bootstrap"

import '../App.css'
import '../css/login.css'


function LoginPage() {
    const history = useHistory();
    const loggedUser = JSON.parse(localStorage.getItem('loggedUser'));

    useEffect(() => {
        if (loggedUser){
            history.push("/home");
        }
        
        document.body.style.backgroundColor = "#0f0f0f";

        
    }, []);

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
                if (dataReceived.status != 401){
                    //localStorage.setItem("loggedUser", true);
                    console.log(dataReceived); // REMOVE AT END
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
        <div>
            <img className = "loginlogo" src={logo} alt="partnerssketch"></img>        

            <div className="container">

                <div className="row content">
                    <div className="col-md-6 mb-3 eikona">
                        <img className = "img-fluid partners" src={partners} alt="partnerssketch"></img>        
                        
                    </div>
                    <div className="col-md-6">
                        <h1 className="signin-text mb-3">Σύνδεση</h1>
                        <form className="loginform">
                            <div className="form-group loginformgroup">
                                <label for="exampleInputUsername1">Όνομα χρήστη</label>
                                <input type="username" className = "form-control loginform-control" id="exampleInputUsername1" aria-describedby="usernameHelp" placeholder="Πληκτρολογίστε το όνομα χρήστη σας" value={user} onChange={e => setUser(e.target.value)}/>
                            </div>
                            <div className="form-group loginformgroup">
                                <label for="exampleInputPassword1">Κωδικός πρόσβασης</label>
                                <input type="password" className = "form-control loginform-control" id="exampleInputPassword1" placeholder="Πληκτρολογίστε τον κωδικό σας"  value={pass} onChange={e => setPass(e.target.value)}/>
                            </div>
                            <div className="form-group loginformgroup form-check">
                                <input type="checkbox" className="form-check-input mycheckbox" id="exampleCheck1"/>
                                <label className="form-check-label" for="exampleCheck1">Remember me</label>
                            </div>
                            <button type="button" className="btn-lg btn-primary login-button" onClick={sendLoginCredentials}>Σύνδεση</button>
                            <div className="signupadvice">
                                <small id="emailHelp" className="form-text text-muted signupadvice-muted-text">Είναι η πρώτη σας φορά εδώ; </small>
                                <Link to='/signUp'>
                                    <small id="signUpLink" className="form-text signup-link signUpStyle"> Εγγραφείτε</small>
                                </Link>
                            </div>
                        </form>
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
        </div>
    );
}
 
export default LoginPage;

import React, { useState, useEffect } from 'react';
import { Link, useHistory } from 'react-router-dom';
import logo from '../images/logo.png';
import partners from '../images/notes.png';
import etairia from '../images/etairia.png';

import '../App.css';
import '../css/login.css'

function SignUpPage() {
    const history = useHistory();
    const loggedUser = JSON.parse(localStorage.getItem('loggedUser'));

    useEffect(() => {
        if (loggedUser){
            history.push("/home");
        }
    }, []);

    const [email, setEmail] = useState("");
    const [firstName, setFirst] = useState("");
    const [lastName, setLast] = useState("");
    const [username, setUsername] = useState("");
    const [pass1, setPass1] = useState("");
    const [pass2, setPass2] = useState("");

    function sendSignupCredentials() {
        if (!email || !firstName || !lastName || !username || !pass1 || !pass2){
            ;
        }
        else{
            fetch('http://localhost:8080/signup', {
                method: 'post', 
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({  username: username,
                                        firstname: firstName,
                                        lastname: lastName,
                                        password: pass1,
                                        matchingPassword: pass2,
                                        email: email
                })
            })
            .then(response => response.json())
            .then(data => console.log(data));
            // .then(response => response.json())
            // .then(data => {
            //     console.log(data);
            //     if (data.accessToken) {
            //       localStorage.setItem("loggedUser", JSON.stringify(data));
            //     }
            //     console.log(data);
            //     dataReceived = data;
            //   }).then( () => {
            //         if (dataReceived){
            //             //localStorage.setItem("loggedUser", true);
            //             console.log("logged in");
            //             history.push("/home");
            //         }
            //         else{
            //             handleShow();
            //         }
            //     });
        }
    }

    return (
        <div>
            <div className="signup-box">
                <div className="signuptopbar">
                    <img className = "jete_logo-icon-signup" src={logo} alt="logo"></img>        

                </div>
                <div className="signup-form-box">
                        
                    <div className="top-buffer login-form signup-content">
                        <h1 className="text-center login-header">
                            Εγγραφή
                        </h1>
                        <form className="signup-form-boxes">
                            <div className="form-group pt-3">
                                <label className="sr-only" for="emailInput">Email</label>
                                <div className="input-group mb-2">
                                    <div className="input-group-prepend">
                                        <div className="input-group-text">@</div>
                                    </div>
                                    <input type="email" className="form-control" id="emailInput" placeholder="Πληκτρολογίστε το email σας" value={email} onChange={e => setEmail(e.target.value)}/>
                                </div>
                            </div>
                            <div className="form-group pt-3">
                                <label for="shortnameInput">Όνομα</label>
                                <input type="name" className="form-control" id="shortnameInput" placeholder="Πληκτρολογίστε το όνομα σας" value={firstName} onChange={e => setFirst(e.target.value)}/>
                            </div>
                            <div className="form-group pt-3">
                                <label for="surnameInput">Επίθετο</label>
                                <input type="surname" className="form-control" id="surnameInput" placeholder="Πληκτρολογίστε το επίθετο σας" value={lastName} onChange={e => setLast(e.target.value)}/>
                            </div>
                            <div className="form-group pt-3">
                                <label for="usernameInput">Username</label>
                                <input type="username" className="form-control" id="usernameInput" aria-describedby="usernamehelp" placeholder="Πληκτρολογίστε το username σας" value={username} onChange={e => setUsername(e.target.value)}/>
                                <small id="usernamehelp" className="form-text text-muted">Το username σας πρέπει να αποτελείται από τουλάχιστον 6 λατινικούς χαρακτήρες και αριθμούς</small>

                            </div>
                            <div className="form-group pt-3">
                                <label for="exampleInputPassword1">Κωδικός πρόσβασης</label>
                                <input type="password" className="form-control" id="exampleInputPassword1" placeholder="Πληκτρολογίστε τον κωδικό σας" value={pass1} onChange={e => setPass1(e.target.value)}/>
                            </div>
                            <div className="form-group pt-3">
                                <label for="exampleInputPassword1">Επιβεβαίωση κωδικού</label>
                                <input type="password" className="form-control" id="pwhelp" placeholder="Πληκτρολογήστε ξανά τον κωδικό σας" value={pass2} onChange={e => setPass2(e.target.value)}/>
                                <small id="pwhelp" className="form-text text-muted">Ο κωδικός πρόσβασης πρέπει να περιέγχει τουλάχιστον ένα κεφαλαίο γράμμα και έναν αριθμό</small>

                            </div>
                                
                            <div className="text-center pt-3">
                                <Link to='/paymentPlan'>
                                    <button type="button" className="btn-lg btn-primary login-button" onClick={sendSignupCredentials}>Εγγραφή</button>
                                </Link>
                            </div>
                        </form>
                    </div>
                </div>
                    

            </div>
            <div className="signupimage">
                <img className = "partnerssignup notes" src={partners} alt="partnerssketch"></img>        
                <img className = "partnerssignup etairia" src={etairia} alt="partnerssketch"></img>        

            </div>
        </div>
    );
}
 
export default SignUpPage;
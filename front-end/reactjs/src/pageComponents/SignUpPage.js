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
        document.body.style.display = "flex";
        document.body.style.height = "100vh";
        document.body.style.justifyContent = "center";
        document.body.style.alignItems = "center";
        document.body.style.padding = "10px";
        
        return () => {
            // Anything in here is fired on component unmount.
            document.body.style.display = "";
            document.body.style.height = "";
            document.body.style.justifyContent = "";
            document.body.style.alignItems = "";
            document.body.style.padding = "";
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
            <img className = "signuplogo" src={logo} alt="partnerssketch"></img>        

            <div className="container container-signup">
                <h1 className="signin-text mb-3">Εγγραφή</h1>
                
                <form>
                    <div className="user-details">

                        <div className="input-box">
                            <span className = "details">Όνομα</span>
                            <input className="form-control"  type="text" value={firstName} onChange={e => setFirst(e.target.value)} placeholder="Πληκτρολογίστε το όνομα σας" />
                        </div>

                        <div className="input-box">
                            <span className = "details">Επίθετο</span>
                            <input className="form-control"  type="text" value={lastName} onChange={e => setLast(e.target.value)} placeholder="Πληκτρολογίστε το επίθετο σας" />
                        </div>

                        <div className="input-box">
                            <span className = "details">Username</span>
                            <input className="form-control"  type="text" value={username} onChange={e => setUsername(e.target.value)} placeholder="Πληκτρολογίστε το username σας" aria-describedby="usernamehelp" />
                            <small id="usernamehelp" className="form-text text-muted">Το username σας πρέπει να αποτελείται από τουλάχιστον 6 λατινικούς χαρακτήρες και αριθμούς</small>
                        </div>

                        <div className="input-box">
                            <span className = "details">Email</span>
                            <input className="form-control"  type="email" value={email} onChange={e => setEmail(e.target.value)} placeholder="Πληκτρολογίστε το email σας" />
                        </div>

                        <div className="input-box">
                            <span className = "details">Κωδικός πρόσβασης</span>
                            <input className="form-control"  type="password" placeholder="Πληκτρολογίστε τον κωδικό σας" />
                        </div>

                        <div className="input-box">
                            <span className = "details">Επιβεβαίωση κωδικού</span>
                            <input  className="form-control" type="password" placeholder="Πληκτρολογίστε ξανά τον κωδικό" />
                        </div>

                        <div className="text-center pt-3">
                            <Link to='/paymentPlan'>
                                <button type="button" className="btn-lg btn-primary login-button signup-button" onClick={sendSignupCredentials}>Εγγραφή</button>
                            </Link>
                        </div>

                    </div>
                </form>
            </div>

        </div>
    );
}
 
export default SignUpPage;
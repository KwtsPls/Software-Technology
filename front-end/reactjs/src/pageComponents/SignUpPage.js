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
        document.body.style.background = "linear-gradient(45deg, #935bb7 0%,#7c39a8 50%,#6c2f91 51%,#6f259f 100%) fixed";

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

    const [showEmptyEmail, setShowEmptyEmail] = useState(false)
    const [showEmptyFirstName, setShowEmptyFirstName] = useState(false)
    const [showEmptyLastName, setShowEmptyLastName] = useState(false)
    const [showEmptyUsername, setShowEmptyUsername] = useState(false)
    const [showEmptyPass1, setShowEmptyPass1] = useState(false)
    const [showEmptyPass2, setShowEmptyPass2] = useState(false)
    const [showPasswordsDontMatch, setShowPasswordsDontMatch] = useState(false)
    const [showAlreadyEmail, setShowAlreadyEmail] = useState(false)
    const [showAlreadyUsername, setShowAlreadyUsername] = useState(false)
    const [showBadPassInput, setShowBadPassInput] = useState(false)

    useEffect(() => {
        setShowPasswordsDontMatch(false);
    }, [pass1, pass2]);

    function passwordValid(str) {
        return /[a-z]/.test(str) && /[A-Z]/.test(str) && /\d/.test(str);
    }

    function sendSignupCredentials() {
        console.log(13);
        let earlyExit = false
        if (!email){
            console.log("email is empty");
            earlyExit = true;
            setShowEmptyEmail(true);
        }
        else {
            setShowEmptyEmail(false);
        }
        if (!firstName){
            console.log("firstName is empty");
            earlyExit = true;
            setShowEmptyFirstName(true);
        }
        else {
            setShowEmptyFirstName(false);
        }
        if (!lastName){
            console.log("lastName is empty");
            earlyExit = true;
            setShowEmptyLastName(true);
        }
        else {
            setShowEmptyLastName(false);
        }
        if (!username){
            console.log("username is empty");
            earlyExit = true;
            setShowEmptyUsername(true);
        }
        else {
            setShowEmptyUsername(false);
        }
        if (!pass1){
            console.log("pass1 is empty");
            earlyExit = true;
            setShowEmptyPass1(true);
        }
        else {
            if(!passwordValid(pass1)){
                earlyExit = true;
                setShowBadPassInput(true);
                setShowEmptyPass1(false);

            }else{
                setShowBadPassInput(false);
                setShowEmptyPass1(false);
            }
        }
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

        setShowAlreadyUsername(false)
        setShowAlreadyEmail(false)

        if (earlyExit){
            return ;
        }

        console.log(12);
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
        .then(response => {
            const contentType = response.headers.get("content-type");
            if (contentType && contentType.indexOf("application/json") !== -1) {
                return response.json().then(data => {
                console.log(data)
                if (data.message === "User registered successfully!"){
                    history.push("/paymentPlan");
                }
                });
            } else {
                return response.text().then(text => {
                console.log(text)
                if (text.includes("Username ") && text.includes(" already in use")){
                    setShowAlreadyUsername(true)
                }
                if (text.includes("Email ") && text.includes(" already in use")){
                    setShowAlreadyEmail(true)
                }
                });
            }
        });
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
                            <input className="form-control"  type="text" value={firstName} onChange={e => setFirst(e.target.value)} placeholder="Πληκτρολογίστε το όνομα σας" maxLength="15"/>
                            <div>
                                { (showEmptyFirstName) && <span className="badge bg-danger rounded-pill">Το Όνομα δεν μπορεί να είναι κενό</span>}
                            </div>
                        </div>

                        <div className="input-box">
                            <span className = "details">Επίθετο</span>
                            <input className="form-control"  type="text" value={lastName} onChange={e => setLast(e.target.value)} placeholder="Πληκτρολογίστε το επίθετο σας" maxLength="20"/>
                            <div>
                                { (showEmptyLastName) && <span className="badge bg-danger rounded-pill">Το Επίθετο δεν μπορεί να είναι κενό</span>}
                            </div>
                        </div>

                        <div className="input-box">
                            <span className = "details">Username</span>
                            <input className="form-control"  type="text" value={username} onChange={e => setUsername(e.target.value)} placeholder="Πληκτρολογίστε το username σας" aria-describedby="usernamehelp" maxLength="10"/>
                            <div>
                                { (showEmptyUsername) && <span className="badge bg-danger rounded-pill">Το username δεν μπορεί να είναι κενό</span>}
                            </div>
                            <div>
                                { (showAlreadyUsername) && <span className="badge bg-danger rounded-pill">Το username αυτό είναι ήδη σε χρήση</span>}
                            </div>
                            <small id="usernamehelp" className="form-text text-muted">Το username σας πρέπει να αποτελείται από τουλάχιστον 6 λατινικούς χαρακτήρες και αριθμούς</small>
                        </div>

                        <div className="input-box">
                            <span className = "details">Email</span>
                            <input className="form-control"  type="email" value={email} onChange={e => setEmail(e.target.value)} placeholder="Πληκτρολογίστε το email σας" />
                            <div>
                                { (showEmptyEmail) && <span className="badge bg-danger rounded-pill">Το email δεν μπορεί να είναι κενό</span>}
                            </div>
                            <div>
                                { (showAlreadyEmail) && <span className="badge bg-danger rounded-pill">Το email αυτό είναι ήδη σε χρήση</span>}
                            </div>
                        </div>

                        <div className="input-box">
                            <span className = "details">Κωδικός πρόσβασης</span>
                            <input className="form-control"  type="password" value={pass1} onChange={e => setPass1(e.target.value)} placeholder="Πληκτρολογίστε τον κωδικό σας" />
                            <div>
                                { (showBadPassInput) && <span className="badge bg-danger rounded-pill">Ο κωδικός πρέπει να περιέγχει τουλάχιστον <br/>ένα κεφαλαίο γράμμα και έναν αριθμό</span>}

                                { (showEmptyPass1) && <span className="badge bg-danger rounded-pill">Ο κωδικός δεν μπορεί να είναι κενός</span>}
                            </div>
                        </div>

                        <div className="input-box">
                            <span className = "details">Επιβεβαίωση κωδικού</span>
                            <input  className="form-control" type="password" value={pass2} onChange={e => setPass2(e.target.value)} placeholder="Πληκτρολογίστε ξανά τον κωδικό" />
                            <div>
                                { (showEmptyPass2) && <span className="badge bg-danger rounded-pill">Ο κωδικός δεν μπορεί να είναι κενός</span>}
                            </div>
                            <div>
                                { (showPasswordsDontMatch) && <span className="badge bg-danger rounded-pill">Οι δυο κωδικοί δεν είναι ίδιοι</span>}
                            </div>
                        </div>

                        <div className="text-center pt-3">
                            <button type="button" className="btn-lg btn-primary login-button signup-button mybtn" onClick={sendSignupCredentials}>Εγγραφή</button>
                        </div>

                    </div>
                </form>
            </div>

        </div>
    );
}

export default SignUpPage;

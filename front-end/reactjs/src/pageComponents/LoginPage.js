import React, { Component } from 'react';
import { Link } from 'react-router-dom'
import logo from '../images/logo.png';
import partners from '../images/partners.png';
import { Redirect } from "react-router-dom";


import '../App.css'
import '../css/login.css'


function LoginPage() {

    let user = 'Adam647'
    let pass = 'aosdid1209j'

    let dataReceived = []

    function sendLoginCredentials() {
        fetch('http://localhost:8080/users/login/u=' + user + '&p=' + pass)
        //fetch('http://localhost:8080/users/login/u=Adam647&p=aosdid1209j')
            .then(response => response.json())
            .then(data => {
                console.log(data); // JSON data parsed by `data.json()` call
                dataReceived = data;
            })
            .then( () => {
                if (dataReceived.id != null){
                    window.location.replace("http://localhost:3000/home")
                }
                else{
                    window.location.replace("http://youtube.com")
                }
            });
            //setTimeout(function(){;}, 100);
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
                                            <label for="exampleInputEmail1">Email</label>
                                            <input type="email" className="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="Πληκτρολογίστε το email σας"/>
                                        </div>
                                        <div className="form-group pt-3">
                                            <label for="exampleInputPassword1">Κωδικός πρόσβασης</label>
                                            <input type="password" className="form-control" id="exampleInputPassword1" placeholder="Πληκτρολογίστε τον κωδικό σας"/>
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
        </div>
    );
}
 
export default LoginPage;
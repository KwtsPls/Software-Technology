import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import logo from '../images/logo.png';
import partners from '../images/notes.png';
import etairia from '../images/etairia.png';


import '../App.css';
import '../css/login.css'

class SignUpPage extends Component {
    render() { 
        return (
            <div>
                <div className="signup-box">
                    <div className="signuptopbar">
                        <img className = "jete_logo-icon-signup" src={logo} alt="logo"></img>        

                    </div>
                    <div className="signup-form-box">
                        
                        <div className="top-buffer login-form signup-content">
                            <h1 className="text-center login-header">
                                Sign Up
                            </h1>
                            <form className="signup-form-boxes">
                                <div className="form-group pt-3">
                                    <label className="sr-only" for="emailInput">Email</label>
                                    <div className="input-group mb-2">
                                        <div className="input-group-prepend">
                                            <div className="input-group-text">@</div>
                                        </div>
                                        <input type="email" class="form-control" id="emailInput" placeholder="Enter email"/>
                                    </div>
                                </div>
                                <div className="form-group pt-3">
                                    <label for="shortnameInput">Name</label>
                                    <input type="name" className="form-control" id="shortnameInput" aria-describedby="emailHelp" placeholder="Your name"/>
                                </div>
                                <div className="form-group pt-3">
                                    <label for="surnameInput">Surname</label>
                                    <input type="surname" className="form-control" id="surnameInput" placeholder="Your surname"/>
                                </div>
                                <div className="form-group pt-3">
                                    <label for="usernameInput">Username</label>
                                    <input type="username" className="form-control" id="usernameInput" aria-describedby="emailHelp" placeholder="Username"/>
                                </div>
                                <div className="form-group pt-3">
                                    <label for="exampleInputPassword1">Password</label>
                                    <input type="password" className="form-control" id="exampleInputPassword1" placeholder="Password"/>
                                </div>
                                <div className="form-group pt-3">
                                    <label for="exampleInputPassword1">Confirm Password</label>
                                    <input type="password" className="form-control" id="pwhelp" placeholder="Confirm password"/>
                                    <small id="pwhelp" className="form-text text-muted">Password must contain at least one uppercase letter and one number.</small>

                                </div>
                                
                                <div className="text-center pt-3">
                                    <Link to='/paymentPlan'>
                                        <button type="submit" className="btn-lg btn-primary login-button">Sign Up</button>
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
}
 
export default SignUpPage;
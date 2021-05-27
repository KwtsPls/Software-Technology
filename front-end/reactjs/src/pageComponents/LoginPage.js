import React, { Component } from 'react';
import { Link } from 'react-router-dom'
import logo from '../images/logo.png';
import partners from '../images/partners.png';

import '../App.css'
import '../css/login.css'


class LoginPage extends Component {

    signUpStyle = {
        color: 'black'

    }

    render() { 
        return (
            <div className="login-screen">
                <div className="login-desc-box">
                    <img className = "jete_logo-icon-login" src={logo} alt="logo"></img>        
                    <p className="desc-header">Welcome to  our platform</p>
                    <img className = "partners" src={partners} alt="partnerssketch"></img>        

                </div>
                <div className="login-form-box">
                    <div className="login-form">
                                    
                        <div className="col-auto">
                            <div>
                                <div className="top-buffer">
                                    <h1 className="text-center login-header">
                                        Sign In
                                    </h1>
                                    <form className="login-form-boxes">
                                        <div className="form-group pt-3">
                                            <label for="exampleInputEmail1">Email</label>
                                            <input type="email" className="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="Enter email"/>
                                        </div>
                                        <div className="form-group pt-3">
                                            <label for="exampleInputPassword1">Password</label>
                                            <input type="password" className="form-control" id="exampleInputPassword1" placeholder="Password"/>
                                        </div>
                                        <div className="form-check pt-3">
                                            <input type="checkbox" class="form-check-input mycheckbox" id="exampleCheck1"/>
                                            <label className="form-check-label" for="exampleCheck1">Remember me</label>
                                        </div>
                                        <div className="text-center pt-3">
                                            <Link to='/home'>
                                                <button type="submit" className="btn-lg btn-primary login-button">Sign In</button>
                                            </Link>
                                        </div>
                                        <div className="text-center pt-3">
                                            <small id="emailHelp" className="form-text text-muted">Is this your first time here? </small>
                                            <Link to='/signUp'>
                                                <small id="signUpLink" className="form-text signup-link" style={this.signUpStyle}> Sign up</small>
                                            </Link>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            
            
        );
    }
}
 
export default LoginPage;
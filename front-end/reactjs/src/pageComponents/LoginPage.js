import React, { Component } from 'react';
import { Link } from 'react-router-dom'
import '../App.css'


class LoginPage extends Component {

    signUpStyle = {
        color: 'black'

    }

    render() { 
        return (
            <div className="login-form">
                <div class="col-auto">
                    <h1>
                        Welcome home
                    </h1>
                </div>
                <div class="col-auto">
                    <div>
                        <div class="top-buffer">
                            <h1 className="text-center">
                                Sign In
                            </h1>
                            <form>
                                <div class="form-group pt-3">
                                    <label for="exampleInputEmail1">Email</label>
                                    <input type="email" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="Enter email"/>
                                </div>
                                <div class="form-group pt-3">
                                    <label for="exampleInputPassword1">Password</label>
                                    <input type="password" class="form-control" id="exampleInputPassword1" placeholder="Password"/>
                                </div>
                                <div class="form-check pt-3">
                                    <input type="checkbox" class="form-check-input" id="exampleCheck1"/>
                                    <label class="form-check-label" for="exampleCheck1">Check me out</label>
                                </div>
                                <div className="text-center pt-3">
                                    <Link to='/home'>
                                        <button type="submit" class="btn-lg btn-primary">Sign In</button>
                                    </Link>
                                </div>
                                <div className="text-center pt-3">
                                    <small id="emailHelp" class="form-text text-muted">Is this your first time here? </small>
                                    <Link to='/signUp'>
                                        <small id="signUpLink" class="form-text" style={this.signUpStyle}> Sign up</small>
                                    </Link>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}
 
export default LoginPage;
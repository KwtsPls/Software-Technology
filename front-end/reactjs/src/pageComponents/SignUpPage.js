import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import '../App.css';


class SignUpPage extends Component {
    render() { 
        return (
            <div>
                
                <div class="top-buffer login-form">
                    <h1 class="text-center">
                        Sign Up
                    </h1>
                    <form>
                        <div class="form-group pt-3">
                            <label class="sr-only" for="inlineFormInputGroup">Username</label>
                            <div class="input-group mb-2">
                                <div class="input-group-prepend">
                                    <div class="input-group-text">@</div>
                                </div>
                                <input type="text" class="form-control" id="inlineFormInputGroup" placeholder="Username"/>
                            </div>
                        </div>
                        <div class="form-group pt-3">
                            <label for="exampleInputEmail1">Email</label>
                            <input type="email" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="Enter email"/>
                        </div>
                        <div class="form-group pt-3">
                            <label for="exampleInputPassword1">Password</label>
                            <input type="password" class="form-control" id="exampleInputPassword1" placeholder="Password"/>
                        </div>
                        <div class="form-group pt-3">
                            <label for="exampleInputPassword1">Confirm Password</label>
                            <input type="password" class="form-control" id="confirmExampleInputPassword1" placeholder="Confirm password"/>
                        </div>
                        <div class="form-check pt-3">
                            <input type="checkbox" class="form-check-input" id="exampleCheck1"/>
                            <label class="form-check-label" for="exampleCheck1">Check me out</label>
                        </div>
                        <div class="text-center pt-3">
                            <Link to='/paymentPlan'>
                                <button type="submit" class="btn-lg btn-primary">Sign Up</button>
                            </Link>
                        </div>
                    </form>
                </div>
            </div>
        );
    }
}
 
export default SignUpPage;
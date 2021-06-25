import React, { Component } from 'react';
import logo from '../images/logo2.png';
import helpicon from '../images/helpicon.png'

class TopBar extends Component {

    constructor(props) {
        super(props);
        this.loggedUser = JSON.parse(localStorage.getItem('loggedUser'));

    }

    logout = () => {
        localStorage.removeItem("loggedUser");
		console.log("logging out");
		setTimeout(function(){window.location.reload();}, 10);
    }

    render() { 
        return (
            <div>
                <div className="TopBar"></div>
			    <img className = "logo-icon" src={logo} alt="logo"></img>
			    
			    {/* <button className="btn  btn-outline-danger btn-sm logout-option">Αποσύνδεση</button> */}

                <a className="logout-option" onClick={this.logout}>Αποσύνδεση</a>
            </div>
        );
    }
}
 
export default TopBar;
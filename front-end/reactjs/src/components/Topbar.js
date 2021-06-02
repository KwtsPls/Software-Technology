import React, { Component } from 'react';
import logo from '../images/logo2.png';
import helpicon from '../images/helpicon.png'

class TopBar extends Component {
    render() { 
        return (
            <div>
                <div className="TopBar"></div>
			    <img className = "logo-icon" src={logo} alt="logo"></img>
			    {/* <div className="helpButton">
					<a href="#" className="help-button" ><img className = "help-icon" src={helpicon} alt="help"></img></a>
			    </div> */}
            </div>
        );
    }
}
 
export default TopBar;
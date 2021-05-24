import React, { Component } from 'react';
import logo from '../images/logo.png';
import helpicon from '../images/helpicon.png'

class TopBar extends Component {
    render() { 
        return (
            <div>
                <div class="TopBar"></div>
			    <img class = "logo-icon" src={logo} alt="logo"></img>
			    <div class="helpButton">
					<a href="#" class="help-button" ><img class = "help-icon" src={helpicon} alt="help"></img></a>
			    </div>
            </div>
        );
    }
}
 
export default TopBar;
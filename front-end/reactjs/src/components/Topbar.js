import React from 'react';
import logo from '../images/logo2.png';
import helpicon from '../images/helpicon.png'
import { useHistory } from 'react-router-dom'

function TopBar() {

    const history = useHistory();
    const loggedUser = JSON.parse(localStorage.getItem('loggedUser'));

    function logout () {
        localStorage.removeItem("loggedUser");
		console.log("logging out");
        history.push("/login")
    }

    return (
        <div>
            <div className="TopBar"></div>
            <img className = "logo-icon" src={logo} alt="logo"></img>
            
            {/* <button className="btn  btn-outline-danger btn-sm logout-option">Αποσύνδεση</button> */}

            <a className="logout-option" onClick={()=>logout()}>Αποσύνδεση</a>
        </div>
    )
}
 
export default TopBar;
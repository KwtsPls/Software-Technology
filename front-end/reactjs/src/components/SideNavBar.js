import React from 'react';
import '../App.css';
import backlogicon from '../images/backlogout.png'
import backloghovericon from '../images/backloghover.png'
import overviewicon from '../images/overviewout.png'
import overviewhovericon from '../images/overviewhover.png'
import projecticon from '../images/projectout.png'
import projecthovericon from '../images/projecthover.png'
import userphoto from '../images/userphoto.png'
import settingsicon from '../images/settingsout.png'
import settingshovericon from '../images/settingshover.png'
import { Link } from 'react-router-dom'
import { useState } from "react";

function fnover(link_id){ //Function to replace a div contains with another (used in navbar menu choices on hover)
	console.log("hover");
	document.getElementById(link_id.concat("Out")).style.display = "none"; // change display style attribute
	document.getElementById(link_id.concat("Hover")).style.display = "block";
}

function fnout(link_id){ //Function to replace a div contains with another (used in navbar menu choices on hover)
	document.getElementById(link_id.concat("Out")).style.display = "block"; // change display style attribute
	document.getElementById(link_id.concat("Hover")).style.display = "none";
}

function Dropdown(){
	return <div>
		<Link to='/settings/profile'>
			<div id = "firstOption1" onMouseEnter={() => fnover("Profile")} onMouseLeave={() => fnout("Profile")}>
				<a href="#" className="navChoiceSmall text-center" id="ProfileOut"><span>Προφίλ</span></a>
				<a href="#" className="navChoiceSmall text-center" id="ProfileHover"><span>Προφίλ</span></a>
			</div>
		</Link>
		<Link to='/settings/security'>
			<div id = "thirdOption1" onMouseEnter={() => fnover("Security")} onMouseLeave={() => fnout("Security")}>
				<a href="#" className="navChoiceSmall text-center" id="SecurityOut"><span>Ασφάλεια</span></a>
				<a href="#" className="navChoiceSmall text-center" id="SecurityHover"><span>Ασφάλεια</span></a>
			</div>
		</Link>
		<Link to='/settings/billing'>
			<div id = "fourthOption1" onMouseEnter={() => fnover("Billing")} onMouseLeave={() => fnout("Billing")}>
				<a href="#" className="navChoiceSmall text-center" id="BillingOut"><span>Χρεώσεις</span></a>
				<a href="#" className="navChoiceSmall text-center" id="BillingHover"><span>Χρεώσεις</span></a>
			</div>
		</Link>
		
	</div>;
}

function SideNavBar(){

	const loggedUser = JSON.parse(localStorage.getItem('loggedUser'));

	const [dropdown, setClick] = useState(false);

	const handleClick = () => setClick(!dropdown);

	// function logout() {
    //     localStorage.removeItem("loggedUser");
	// 	console.log("logging out");
	// 	setTimeout(function(){window.location.reload();}, 10);
    // }

    return (
        <div className="SideNavBar" >
			<Link to='/profile'>
				<div className="userDisplay">
					<a href="#" className="userNameNavBar" ><img className = "user-icon" src={userphoto} alt="avatar"></img><li id="userNameDisplay">{loggedUser && loggedUser.username}</li></a>
				</div>
            </Link>

			{/* <button onClick={logout}>logout</button> */}
			<div className="NavBar">

				<Link to='/home'>
					<div id = "firstOption" onMouseEnter={() => fnover("Overview")} onMouseLeave={() => fnout("Overview")}>
						<a href="#" className="navChoice" id="OverviewOut" ><img className = "menu-icon home-icon" src={overviewicon} alt="logo"></img><span>Σύνοψη</span></a>
						<a href="#" className="navChoice" id="OverviewHover"><img className = "menu-icon home-icon" src={overviewhovericon} alt="logo"></img><span>Σύνοψη</span></a>
					</div>
				</Link>
				<Link to='/notifications'>

				<div id = "secondOption" onMouseEnter={() => fnover("Backlog")} onMouseLeave={() => fnout("Backlog")}>
					<a href="#" className="navChoice" id="BacklogOut"><img className = "menu-icon backlog-icon" src={backlogicon} alt="logo"></img><span>Ειδοποιήσεις</span></a>
					<a href="#" className="navChoice" id="BacklogHover"><img className = "menu-icon backlog-icon" src={backloghovericon} alt="logo"></img><span>Ειδοποιήσεις</span></a>
				</div>
				</Link>

				<Link to='/projects'>
					<div id = "thirdOption" onMouseEnter={() => fnover("Projects")} onMouseLeave={() => fnout("Projects")}>
						<a href="#" className="navChoice" id="ProjectsOut"><img className = "menu-icon projects-icon" src={projecticon} alt="logo"></img><span>Projects</span></a>
						<a href="#" className="navChoice" id="ProjectsHover"><img className = "menu-icon projects-icon" src={projecthovericon} alt="logo"></img><span>Projects</span></a>
					</div>
				</Link>
				<div className="deadSpace">

				</div>
				<div id = "thirdOption" onClick={handleClick} onMouseEnter={() => fnover("Settings")} onMouseLeave={() => fnout("Settings")}>
					<a href="#" className="navChoice" id="SettingsOut"><img className = "menu-icon settings-icon" src={settingsicon} alt="logo"></img><span>Ρυθμίσεις</span></a>
					<a href="#" className="navChoice" id="SettingsHover"><img className = "menu-icon settings-icon" src={settingshovericon} alt="logo"></img><span>Ρυθμίσεις</span></a>
				</div>
				{dropdown && <Dropdown/>} 
			</div>
		</div>
    );
}
 
export default SideNavBar;
import React, { useEffect } from 'react';
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
import { Link, useHistory } from 'react-router-dom'
import { useState } from "react";



function SideNavBar(){

	const loggedUser = JSON.parse(localStorage.getItem('loggedUser'));

	const [dropdown, setClick] = useState(false);

	const history = useHistory();

	function fnover(link_id){ //Function to replace a div contains with another (used in navbar menu choices on hover)
		console.log("hover");
		document.getElementById(link_id.concat("Out")).style.display = "none"; // change display style attribute
		document.getElementById(link_id.concat("Hover")).style.display = "block";
	}

	function fnout(link_id){ //Function to replace a div contains with another (used in navbar menu choices on hover)
		document.getElementById(link_id.concat("Out")).style.display = "block"; // change display style attribute
		document.getElementById(link_id.concat("Hover")).style.display = "none";
	}

	function goToProfSet(){
		history.push("/settings/profile");
	}

	function goToProfSec(){
		history.push("/settings/security");
	}

	function goToProfBil(){
		history.push("/settings/billing");
	}



	// function logout() {
    //     localStorage.removeItem("loggedUser");
	// 	console.log("logging out");
	// 	setTimeout(function(){window.location.reload();}, 10);
    // }

	const [notifs, setNotifs] = useState(0)

	useEffect(()=>{// /developers/users/{user_id}/notifications
		if (loggedUser){
			fetch('http://localhost:8080/developers/users/' + loggedUser.id + '/notifications', {
                method: 'get', 
                headers: { Authorization: 'Bearer ' + loggedUser.accessToken }
            })
                .then(res => res.json())
                .then((data) => {
                    console.log("These is the number of notifs:")
                    console.log(data);
					if (data){
						setNotifs(data[0])
					}
                })
		}
	},[])

	

    return (
        <div className="SideNavBar" >
			<div className="userDisplay">
                <Link to='/profile' className="userNameNavBar" ><img className = "user-icon" src={userphoto} alt="avatar"></img><li id="userNameDisplay">{loggedUser && loggedUser.username}</li></Link>
			</div>

			{/* <button onClick={logout}>logout</button> */}
			<div className="NavBar">

				
				<div id = "firstOption" onMouseEnter={() => fnover("Overview")} onMouseLeave={() => fnout("Overview")}>
					<a href="#" className="navChoice" id="OverviewOut" ><img className = "menu-icon home-icon" src={overviewicon} alt="logo"></img><span>Σύνοψη</span></a>
					<Link to='/home' className="navChoice" id="OverviewHover"><img className = "menu-icon home-icon" src={overviewhovericon} alt="logo"></img><span>Σύνοψη</span></Link>
				</div>
				

				<div id = "secondOption" onMouseEnter={() => fnover("Backlog")} onMouseLeave={() => fnout("Backlog")}>
					<a href="#" className="navChoice" id="BacklogOut"><img className = "menu-icon backlog-icon" src={backlogicon} alt="logo"></img><span>Ειδοποιήσεις  </span>{(notifs != 0) && <span className="badge bg-danger rounded-pill">{notifs}</span>}</a>
					<Link to='/notifications' className="navChoice" id="BacklogHover"><img className = "menu-icon backlog-icon" src={backloghovericon} alt="logo"></img><span>Ειδοποιήσεις  </span>{(notifs != 0) && <span className="badge bg-danger rounded-pill">{notifs}</span>}</Link>
				</div>

				
				<div id = "thirdOption" onMouseEnter={() => fnover("Projects")} onMouseLeave={() => fnout("Projects")}>
					<a href="#" className="navChoice" id="ProjectsOut"><img className = "menu-icon projects-icon" src={projecticon} alt="logo"></img><span>Projects</span></a>
					<Link to='/projects' className="navChoice" id="ProjectsHover"><img className = "menu-icon projects-icon" src={projecthovericon} alt="logo"></img><span>Projects</span></Link>
				</div>
				
				<div className="deadSpace">

				</div>
				<div id = "thirdOption" onClick={() => setClick(!dropdown)} onMouseEnter={() => fnover("Settings")} onMouseLeave={() => fnout("Settings")}>
					<a href="#" className="navChoice" id="SettingsOut"><img className = "menu-icon settings-icon" src={settingsicon} alt="logo"></img><span>Ρυθμίσεις</span></a>
					<a href="#" className="navChoice" id="SettingsHover"><img className = "menu-icon settings-icon" src={settingshovericon} alt="logo"></img><span>Ρυθμίσεις</span></a>
				</div>
				{dropdown && <div>
					<div id = "firstOption1" onMouseEnter={() => fnover("Profile")} onMouseLeave={() => fnout("Profile")}>
						<a href="#" className="navChoiceSmall text-center" id="ProfileOut"><span>Προφίλ</span></a>
						<a href="#" className="navChoiceSmall text-center" id="ProfileHover"  onClick={()=>goToProfSet()}><span>Προφίλ</span></a>
					</div>
					<div id = "thirdOption1" onMouseEnter={() => fnover("Security")} onMouseLeave={() => fnout("Security")}>
						<a href="#" className="navChoiceSmall text-center" id="SecurityOut"><span>Ασφάλεια</span></a>
						<a href="#" className="navChoiceSmall text-center" id="SecurityHover" onClick={()=>goToProfSec()}><span>Ασφάλεια</span></a>
					</div>
					<div id = "fourthOption1" onMouseEnter={() => fnover("Billing")} onMouseLeave={() => fnout("Billing")}>
						<a href="#" className="navChoiceSmall text-center" id="BillingOut"><span>Χρεώσεις</span></a>
						<a href="#" className="navChoiceSmall text-center" id="BillingHover" onClick={()=>goToProfBil()}><span>Χρεώσεις</span></a>
					</div>
				</div>} 
			</div>
		</div>
    );
}
 
export default SideNavBar;
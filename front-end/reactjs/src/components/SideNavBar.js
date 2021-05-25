import React, { Component } from 'react';
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

class SideNavBar extends Component {

    fnover(link_id){ //Function to replace a div contains with another (used in navbar menu choices on hover)
		console.log("hover");
		document.getElementById(link_id.concat("Out")).style.display = "none"; // change display style attribute
		document.getElementById(link_id.concat("Hover")).style.display = "block";
	}
	
	fnout(link_id){ //Function to replace a div contains with another (used in navbar menu choices on hover)
		document.getElementById(link_id.concat("Out")).style.display = "block"; // change display style attribute
		document.getElementById(link_id.concat("Hover")).style.display = "none";
		
	}

    render() { 
        return (
            <div className="SideNavBar" >
				

				
				<Link to='/profile'>
					<div className="userDisplay">
						<a href="#" className="userNameNavBar" ><img className = "user-icon" src={userphoto} alt="avatar"></img><li id="userNameDisplay">Dimitris Beros</li></a>
					</div>
                </Link>




				<div className="NavBar">


					<div id = "firstOption" onMouseEnter={() => this.fnover("Overview")} onMouseLeave={() => this.fnout("Overview")}>
						<a href="#" className="navChoice" id="OverviewOut" ><img className = "menu-icon home-icon" src={overviewicon} alt="logo"></img><span>Overview</span></a>
						<a href="#" className="navChoice" id="OverviewHover"><img className = "menu-icon home-icon" src={overviewhovericon} alt="logo"></img><span>Overview</span></a>
					</div>


					<div id = "secondOption" onMouseEnter={() => this.fnover("Backlog")} onMouseLeave={() => this.fnout("Backlog")}>
						<a href="#" className="navChoice" id="BacklogOut"><img className = "menu-icon backlog-icon" src={backlogicon} alt="logo"></img><span>Backlog</span></a>
						<a href="#" className="navChoice" id="BacklogHover"><img className = "menu-icon backlog-icon" src={backloghovericon} alt="logo"></img><span>Backlog</span></a>
					</div>

					<Link to='/projects'>
						<div id = "thirdOption" onMouseEnter={() => this.fnover("Projects")} onMouseLeave={() => this.fnout("Projects")}>
							<a href="#" className="navChoice" id="ProjectsOut"><img className = "menu-icon projects-icon" src={projecticon} alt="logo"></img><span>Projects</span></a>
							<a href="#" className="navChoice" id="ProjectsHover"><img className = "menu-icon projects-icon" src={projecthovericon} alt="logo"></img><span>Projects</span></a>
						</div>
					</Link>


					<div id = "thirdOption" onMouseEnter={() => this.fnover("Settings")} onMouseLeave={() => this.fnout("Settings")}>
						<a href="#" className="navChoice" id="SettingsOut"><img className = "menu-icon settings-icon" src={settingsicon} alt="logo"></img><span>Settings</span></a>
						<a href="#" className="navChoice" id="SettingsHover"><img className = "menu-icon settings-icon" src={settingshovericon} alt="logo"></img><span>Settings</span></a>
					</div>



				</div>


			</div>
        );
    }
}
 
export default SideNavBar;
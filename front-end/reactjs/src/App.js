import './App.css';
import './Overviewscreen.css';

import backlogicon from './images/backlogout.png'
import backloghovericon from './images/backloghover.png'
import overviewicon from './images/overviewout.png'
import overviewhovericon from './images/overviewhover.png'
import projecticon from './images/projectout.png'
import projecthovericon from './images/projecthover.png'
import userphoto from './images/userphoto.png'
import settingsicon from './images/settingsout.png'
import settingshovericon from './images/settingshover.png'

import Topbar from './components/Topbar.js'
import OverviewScreen from './components/OverviewScreen.js'




function App() {

	
	function fnover(link_id){ //Function to replace a div contains with another (used in navbar menu choices on hover)
		console.log("hover");
		document.getElementById(link_id.concat("Out")).style.display = "none"; // change display style attribute
		document.getElementById(link_id.concat("Hover")).style.display = "block";
	}
	
	function fnout(link_id){ //Function to replace a div contains with another (used in navbar menu choices on hover)
		document.getElementById(link_id.concat("Out")).style.display = "block"; // change display style attribute
		document.getElementById(link_id.concat("Hover")).style.display = "none";
		
	}


	return (
		<div className="App">


			
			

			<div className="SideNavBar" >
				

				<div className="userDisplay">
					<a href="#" className="userNameNavBar" ><img className = "user-icon" src={userphoto} alt="avatar"></img><li id="userNameDisplay">Dimitris Beros</li></a>
				</div>




				<div className="NavBar">


					<div id = "firstOption" onMouseEnter={() => fnover("Overview")} onMouseLeave={() => fnout("Overview")}>
						<a href="#" className="navChoice" id="OverviewOut" ><img className = "menu-icon home-icon" src={overviewicon} alt="logo"></img><span>Overview</span></a>
						<a href="#" className="navChoice" id="OverviewHover"><img className = "menu-icon home-icon" src={overviewhovericon} alt="logo"></img><span>Overview</span></a>
					</div>


					<div id = "secondOption" onMouseEnter={() => fnover("Backlog")} onMouseLeave={() => fnout("Backlog")}>
						<a href="#" className="navChoice" id="BacklogOut"><img className = "menu-icon backlog-icon" src={backlogicon} alt="logo"></img><span>Backlog</span></a>
						<a href="#" className="navChoice" id="BacklogHover"><img className = "menu-icon backlog-icon" src={backloghovericon} alt="logo"></img><span>Backlog</span></a>
					</div>


					<div id = "thirdOption" onMouseEnter={() => fnover("Projects")} onMouseLeave={() => fnout("Projects")}>
						<a href="#" className="navChoice" id="ProjectsOut"><img className = "menu-icon projects-icon" src={projecticon} alt="logo"></img><span>Projects</span></a>
						<a href="#" className="navChoice" id="ProjectsHover"><img className = "menu-icon projects-icon" src={projecthovericon} alt="logo"></img><span>Projects</span></a>
					</div>


					<div id = "thirdOption" onMouseEnter={() => fnover("Settings")} onMouseLeave={() => fnout("Settings")}>
						<a href="#" className="navChoice" id="SettingsOut"><img className = "menu-icon settings-icon" src={settingsicon} alt="logo"></img><span>Settings</span></a>
						<a href="#" className="navChoice" id="SettingsHover"><img className = "menu-icon settings-icon" src={settingshovericon} alt="logo"></img><span>Settings</span></a>
					</div>



				</div>


			</div>

			<Topbar>
			</Topbar>
			

			<div className="mainContent">
				<OverviewScreen>
				</OverviewScreen>
			</div>


		</div>
	);
}

export default App;

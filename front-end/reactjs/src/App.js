import './App.css';
import backlogicon from './images/backlogout.png'
import backloghovericon from './images/backloghover.png'
import menuicon from './images/backlog2.png'
import overviewicon from './images/overviewout.png'
import overviewhovericon from './images/overviewhover.png'
import projecticon from './images/projectout.png'
import projecthovericon from './images/projecthover.png'
import userphoto from './images/userphoto.png'
import settingsicon from './images/settingsout.png'
import settingshovericon from './images/settingshover.png'

import Topbar from './components/Topbar.js'



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


			
			

			<div class="SideNavBar" >
				

				<div class="userDisplay">
					<a href="#" class="userNameNavBar" ><img class = "user-icon" src={userphoto} alt="avatar"></img><li id="userNameDisplay">Dimitris Beros</li></a>
				</div>




				<div class="NavBar">


					<div id = "firstOption" onMouseEnter={() => fnover("Overview")} onMouseLeave={() => fnout("Overview")}>
						<a href="#" class="navChoice" id="OverviewOut" ><img class = "menu-icon home-icon" src={overviewicon} alt="logo"></img><span>Overview</span></a>
						<a href="#" class="navChoice" id="OverviewHover"><img class = "menu-icon home-icon" src={overviewhovericon} alt="logo"></img><span>Overview</span></a>
					</div>


					<div id = "secondOption" onMouseEnter={() => fnover("Backlog")} onMouseLeave={() => fnout("Backlog")}>
						<a href="#" class="navChoice" id="BacklogOut"><img class = "menu-icon backlog-icon" src={backlogicon} alt="logo"></img><span>Backlog</span></a>
						<a href="#" class="navChoice" id="BacklogHover"><img class = "menu-icon backlog-icon" src={backloghovericon} alt="logo"></img><span>Backlog</span></a>
					</div>


					<div id = "thirdOption" onMouseEnter={() => fnover("Projects")} onMouseLeave={() => fnout("Projects")}>
						<a href="#" class="navChoice" id="ProjectsOut"><img class = "menu-icon projects-icon" src={projecticon} alt="logo"></img><span>Projects</span></a>
						<a href="#" class="navChoice" id="ProjectsHover"><img class = "menu-icon projects-icon" src={projecthovericon} alt="logo"></img><span>Projects</span></a>
					</div>


					<div id = "thirdOption" onMouseEnter={() => fnover("Settings")} onMouseLeave={() => fnout("Settings")}>
						<a href="#" class="navChoice" id="SettingsOut"><img class = "menu-icon settings-icon" src={settingsicon} alt="logo"></img><span>Settings</span></a>
						<a href="#" class="navChoice" id="SettingsHover"><img class = "menu-icon settings-icon" src={settingshovericon} alt="logo"></img><span>Settings</span></a>
					</div>



				</div>


			</div>

			<Topbar>
			</Topbar>
			

			<div class="mainContent"></div>


		</div>
	);
}

export default App;

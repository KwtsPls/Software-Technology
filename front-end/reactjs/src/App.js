import './App.css';
import './Overviewscreen.css';
import { BrowserRouter as Router, Switch, Route} from 'react-router-dom';

import Topbar from './components/Topbar.js'
import HomePage from './pageComponents/HomePage.js'
import LoginPage from './pageComponents/LoginPage.js'
import SignUpPage from './pageComponents/SignUpPage.js'
import PaymentPlanPage from './pageComponents/PaymentPlanPage.js'
import SettingsSecurityPage from './pageComponents/settings/SettingsSecurityPage.js'
import SettingsPersonalisationPage from './pageComponents/settings/SettingsPersonalisationPage.js'
import SettingsBillingPage from './pageComponents/settings/SettingsBillingPage.js'
import SettingsProfilePage from './pageComponents/settings/SettingsProfilePage.js'
import ProfilePage from './pageComponents/ProfilePage.js'
import ChatPage from './pageComponents/chat/ChatPage.js'
import AddContactPage from './pageComponents/chat/AddContactPage.js'
import ProjectCreatePage from './pageComponents/projects/ProjectCreatePage.js'
import ProjectNoPage from './pageComponents/projects/ProjectNoPage.js'
import ProjectsPage from './pageComponents/projects/ProjectsPage.js'
import ProjNewIssuePage from './pageComponents/projects/ProjNewIssuePage.js'


function App() {
	return (
		<Router>
			<div className="App">
				<Switch>
					<Route path="/" exact component={HomePage} />
					<Route path="/home" exact component={HomePage} />
					<Route path="/login" exact component={LoginPage} />
					<Route path="/signUp" exact component={SignUpPage} />
					<Route path="/paymentPlan" exact component={PaymentPlanPage} />
					<Route path="/settings/security" exact component={SettingsSecurityPage} />
					<Route path="/settings/personalisation" exact component={SettingsPersonalisationPage} />
					<Route path="/settings/billing" exact component={SettingsBillingPage} />
					<Route path="/settings/profile" exact component={SettingsProfilePage} />
					<Route path="/profile" exact component={ProfilePage} />
					<Route path="/chat" exact component={ChatPage} />
					<Route path="/chat/addContact" exact component={AddContactPage} />
					<Route path="/projects" exact component={ProjectsPage} />
					<Route path="/projectCreate" exact component={ProjectCreatePage} />
					<Route path="/projects/projectNo" exact component={ProjectsPage} />
					<Route path="/projNewIssue" exact component={ProjNewIssuePage} />
				</Switch>
			</div>
		</Router>
	);
}

export default App;

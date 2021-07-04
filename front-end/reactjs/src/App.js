import './App.css';
import './css/Overviewscreen.css';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import { BrowserRouter as Router, Switch, Route} from 'react-router-dom';

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
import ProjectNoPage from './pageComponents/projects/ProjectNoPage.js'
import ProjectsPage from './pageComponents/projects/ProjectsPage.js'
import GeneralBacklog from './pageComponents/GeneralBacklog.js'
import Notifications from './pageComponents/Notifications.js'


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
					<Route path="/notifications" exact component={Notifications} />

					<Route path="/chat" exact component={ChatPage} />
					<Route path="/chat/addContact" exact component={AddContactPage} />
					<Route path="/projects" exact component={ProjectsPage} />
					<Route path="/projects/projectNo" exact component={ProjectNoPage} />
				</Switch>
			</div>
		</Router>
	);
}

export default App;

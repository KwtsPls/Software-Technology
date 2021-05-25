import './App.css';
import './Overviewscreen.css';
import { BrowserRouter as Router, Switch, Route} from 'react-router-dom';

import Topbar from './components/Topbar.js'
import HomePage from './pageComponents/HomePage.js'
import LoginPage from './pageComponents/LoginPage.js'
import SignUpPage from './pageComponents/SignUpPage.js'




function App() {
	return (
		<Router>
			<div className="App">
				<Topbar/>
				<Switch>
					<Route path="/" exact component={HomePage} />
					<Route path="/home" exact component={HomePage} />
					<Route path="/login" exact component={LoginPage} />
					<Route path="/signUp" exact component={SignUpPage} />
				</Switch>
			</div>
		</Router>
	);
}

export default App;

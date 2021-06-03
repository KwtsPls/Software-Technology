import React, { Component } from 'react';
import '../../App.css';
import SideNavBar from '../../components/SideNavBar.js'
import Topbar from '../../components/Topbar.js'
import { Link } from 'react-router-dom'



class SettingsPersonalisationPage extends Component {
    render() { 
        return (
            <div>
                <Topbar/>
                <SideNavBar/>
                <div className="mainContent">
                    <div className="jete-settingsMenu">
                        <div className="list-group">
                            <Link to='/settings/profile' className="list-group-item list-group-item-action">Προφίλ</Link>
                            <a href="#" className="list-group-item list-group-item-action active">Εξατομίκευση</a>
                            <Link to='/settings/security' className="list-group-item list-group-item-action">Ασφάλεια</Link>
                            <Link to='/settings/billing' className="list-group-item list-group-item-action">Χρεώσεις</Link>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}
 
export default SettingsPersonalisationPage;
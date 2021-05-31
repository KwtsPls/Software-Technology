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
                        <div class="list-group">
                            <Link to='/settings/profile' class="list-group-item list-group-item-action">Προφίλ</Link>
                            <a href="#" class="list-group-item list-group-item-action active">Εξατομίκευση</a>
                            <Link to='/settings/security' class="list-group-item list-group-item-action">Ασφάλεια</Link>
                            <Link to='/settings/billing' class="list-group-item list-group-item-action">Χρεώσεις</Link>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}
 
export default SettingsPersonalisationPage;
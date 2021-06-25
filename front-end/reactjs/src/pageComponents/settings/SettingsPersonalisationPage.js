import React, { useEffect } from 'react';
import '../../App.css';
import SideNavBar from '../../components/SideNavBar.js'
import Topbar from '../../components/Topbar.js'
import { Link, useHistory } from 'react-router-dom'



function SettingsPersonalisationPage(){

    const history = useHistory();
    const loggedUser = JSON.parse(localStorage.getItem('loggedUser'));

    useEffect(() => {
        if (!loggedUser){
            history.push("/login");
        }
        document.body.style.background = "#fff";

    }, []);

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
 
export default SettingsPersonalisationPage;
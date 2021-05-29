import React, { Component } from 'react';
import '../../App.css';
import SideNavBar from '../../components/SideNavBar.js'
import { Link } from 'react-router-dom'
import Topbar from '../../components/Topbar.js'
import '../../css/settings.css';



class SettingsBillingPage extends Component {
    render() { 
        return (
            <div>
                <Topbar/>

                <SideNavBar/>

                <div className="mainContent">
                    
                    
                    

                    <div className="jete-settingsMenu">
                        <div class="list-group">
                            <Link to='/settings/profile' class="list-group-item list-group-item-action">Profile</Link>
                            <Link to='/settings/personalisation' class="list-group-item list-group-item-action">Personalisation</Link>
					        <Link to='/settings/security' class="list-group-item list-group-item-action">Security</Link>
                            <a href="#" class="list-group-item list-group-item-action  active">Billing</a>
                        </div>
                    </div>

                    <div className="settingsContains">
                        <h1 className="settings-header">Account billing</h1>
                        <hr className="new4"/>

                        <p className="settings-unit">
                            <b>Current account status:</b> &emsp; &nbsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &ensp;Jete Premium
                        </p>

                        <p className="expirationdate">Expires on: &emsp;04/04/21</p>
                        
                        <Link to='/paymentPlan'>
                        <button type="button" className="btn btn-outline-danger paymentplan-button-change">Change payment plan</button>
                        </Link>

                        <p className="settings-unit">
                            <b>Credit card:</b>&emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &ensp;7XXX-XXXX-XXXX-X420
                        </p>

                        <button type="button" class="btn btn-outline-warning creditcard-button-change">Change credit card</button>
                        

                    </div>
                    
                        
                        
                    </div>
                
            </div>
        );
    }
}
 
export default SettingsBillingPage;
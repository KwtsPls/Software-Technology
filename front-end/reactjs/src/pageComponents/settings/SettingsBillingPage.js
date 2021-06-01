import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import '../../App.css';
import '../../css/settings.css';
import SideNavBar from '../../components/SideNavBar.js'
import Topbar from '../../components/Topbar.js'
import BankPopUp from '../../components/BankPopUp.js'



function SettingsBillingPage() {

    const [modalShow, setModalShow] = useState(false);

    return (
        <div>
            <Topbar/>
            <SideNavBar/>
            <div className="mainContent">
                <div className="jete-settingsMenu">
                    <div class="list-group">
                        <Link to='/settings/profile' class="list-group-item list-group-item-action">Προφίλ</Link>
                        <Link to='/settings/personalisation' class="list-group-item list-group-item-action">Εξατομίκευση</Link>
					    <Link to='/settings/security' class="list-group-item list-group-item-action">Ασφάλεια</Link>
                        <a href="#" class="list-group-item list-group-item-action  active">Χρεώσεις</a>
                    </div>
                </div>
                <div className="settingsContains">
                    <h1 className="settings-header">Χρεώσεις λογαριασμού</h1>
                    <hr className="new4"/>

                    <p className="settings-unit">
                        <b>Τρέχον πρόγραμμα:</b> &emsp; &nbsp; &nbsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &ensp;Jete Premium
                    </p>

                    <p className="expirationdate">Λήξη συνδρομής: &emsp;04/04/21</p>
                        
                    <Link to='/paymentPlan'>
                        <button type="button" className="btn btn-outline-danger paymentplan-button-change">Αλλαγή προγράμματος</button>
                    </Link>

                    <p className="settings-unit">
                        <b>Πιστωτική κάρτα:</b>&emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &ensp;7XXX-XXXX-XXXX-X420
                    </p>
                    <button type="button" onClick={() => setModalShow(true)} class="btn btn-outline-warning creditcard-button-change">Αλλαγή πιστωτικής κάρτας</button>
                    <BankPopUp show={modalShow} onHide={() => setModalShow(false)}/>
                    </div>
                    
                        
                        
            </div>
                
        </div>
    );
}
 
export default SettingsBillingPage;
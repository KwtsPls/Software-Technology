import React, { Component, useEffect } from 'react';
import { Link, useHistory } from 'react-router-dom'

import '../App.css';
import '../css/payment.css';
import BankPopUp from '../components/BankPopUp.js'
import { useState } from "react";
import 'bootstrap/dist/css/bootstrap.css';






function PaymentPlanPage() {

    const [modalShow, setModalShow] = useState(false);
    const history = useHistory();
    const loggedUser = JSON.parse(localStorage.getItem('loggedUser'));

    useEffect(() => {
        if (loggedUser){
            document.body.style.background = "#fff";
            document.body.style.backgroundColor = "#fff";

            history.push("/home");
        }
        
        document.body.style.backgroundColor = "#0f0f0f";
        document.body.style.background = "linear-gradient(45deg, #935bb7 0%,#7c39a8 50%,#6c2f91 51%,#6f259f 100%) fixed";

        return () => { 
     
        }
        
    }, []);

    return (
        <div className="container">
            <div className="row text-center">
                <h1 className="paymenttitle">Επιλέξτε πρόγραμμα συνδρομής</h1>
            </div>
            <div className="row pay-content choiceboxes">

                <div className = "col-md-4 offset-md-1 paychoice-box text-center">
                    <h2 className="paymentchoicetitle">Jete Δωρεάν</h2>
                    <ul class="list-group list-group-flush mylist">
                        <li class="list-group-item">Προτέρημα #1</li>
                        <li class="list-group-item">Προτέρημα #2</li>
                        <li class="list-group-item">Μειονέκτημα #3</li>
                    </ul>
                    <Link to='/home'>
                        {/* edw POST KAI GET GIA TA LOGIN CREDENTIALS  */}
                        <button className= "btn-lg btn-primary login-button mybtn"> Επιλογή </button>
                    </Link>

                </div>

                
                <div className = "col-md-4 offset-md-2 paychoice-box text-center" >
                    <h2 className="paymentchoicetitle">Jete Premium</h2>
                    <ul class="list-group list-group-flush mylist">
                        <li class="list-group-item">Προτέρημα #1</li>
                        <li class="list-group-item">Προτέρημα #2</li>
                        <li class="list-group-item">Προτέρημα #3</li>
                        
                    </ul>
                    <button className= "btn-lg btn-primary login-button mybtn" onClick={() => setModalShow(true)}> Αγορά </button>
                </div>
                    
            </div>
            <BankPopUp show={modalShow} onHide={() => setModalShow(false)}/>

        </div>
        
    );
}
 
export default PaymentPlanPage;
import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import '../App.css';
import '../css/payment.css';
import BankPopUp from '../components/BankPopUp.js'
import { useState } from "react";
import 'bootstrap/dist/css/bootstrap.css';






function PaymentPlanPage() {

    const [modalShow, setModalShow] = useState(false);

    return (
        <div className="paypage">
            <div className="paymentplanpage">
                    
                <h1 className="paymentplan-header">
                    Choose your payment plan
                </h1>
                <div className="payChoiceBox firstpaybox">
                    <h2 className="paychoice-header">Free</h2>
                    <hr className="new5"></hr>

                </div>
                <div className="payChoiceBox secondpaybox">
                    <h2 className="paychoice-header">Premium</h2>
                    <hr className="new5"></hr>
                    <button onClick={() => setModalShow(true)}> Click me </button>
                    
                </div>
                <BankPopUp show={modalShow} onHide={() => setModalShow(false)}/>
                    
            </div>
        </div>
        
    );
}
 
export default PaymentPlanPage;
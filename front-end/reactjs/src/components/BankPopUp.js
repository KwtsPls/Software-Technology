import React, { Component } from 'react';
import '../App.css';
import {Modal, Button, Row, Col, Form} from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import PaymentIcon from 'react-payment-icons-inline';




function BankPopUp(props){

    const months = ['01','02','03','04','05','06','07','08','09','10','11','12']
    const years = [2021,2022,2023,2024,2025,2026]

    return (
        <div>
            <Modal
                {...props}
                size="md"
                aria-labelledby="contained-modal-title-vcenter"
                centered
                >
                <Modal.Header>
                    <Modal.Title id="contained-modal-title-vcenter">
                    Πληροφορίες πληρωμής
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <form class="row g-3">
                        <div class="col-12">
                            <label for="inputAddress" class="form-label">Όνομα κατόχου</label>
                            <input type="text" class="form-control" id="cardholderName" placeholder="John Doe"/>
                        </div>
                        <div class="col-12">
                            <label for="inputAddress2" class="form-label">Αριθμός πιστωτικής κάρτας</label>
                            <input type="text" class="form-control" id="creditCardNumber" placeholder="0000 0000 0000 0000" minlength="16" maxlength="16"/>
                        </div>
                        <div class="col-md-4">
                            <label for="inputExpMonth" class="form-label">Μήνας λήξης</label>
                            <select id="inputExpMonth" class="form-select">
                                <option selected>Επιλέξτε...</option>
                                {months.map(i => <option key={i}>{i}</option>)}
                            </select>
                        </div>
                        <div class="col-md-4">
                            <label for="inputExpYear" class="form-label">Έτος λήξης</label>
                            <select id="inputExpYear" class="form-select">
                                <option selected>Επιλέξτε...</option>
                                {years.map(i => <option key={i}>{i}</option>)}
                            </select>
                        </div>
                        <div class="col-md-4">
                            <label for="inputCvv" class="form-label">CVV</label>
                            <input type="password" class="form-control" id="inputCvv" placeholder="000" minlength="3" maxlength="3"/>
                        </div>
                        <PaymentIcon icon="visa" style={{ margin: 10, width: 70 }} />
                        <PaymentIcon icon="mastercard" style={{ margin: 10, width: 70 }} />
                        <PaymentIcon icon="maestro" style={{ margin: 10, width: 70 }} />
                        <PaymentIcon icon="paypal" style={{ margin: 10, width: 70 }} />
                        <div class="col-8">
                            {/* <Link to='/home'> */}
                                <button type="submit" class="btn btn-primary">Επιβεβαίωση</button>
                            {/* </Link> */}
                        </div>
                    </form>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="outline-danger" onClick={props.onHide}>Άκυρο</Button>
                </Modal.Footer>
            </Modal>
		</div>
    );
}
 
export default BankPopUp;
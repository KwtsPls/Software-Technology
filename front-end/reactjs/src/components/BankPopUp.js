import React from 'react';
import '../App.css';
import {Modal, Button} from 'react-bootstrap';
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
                    <form className="row g-3">
                        <div className="col-12">
                            <label for="inputAddress" className="form-label">Όνομα κατόχου</label>
                            <input type="text" className="form-control" id="cardholderName" placeholder="John Doe"/>
                        </div>
                        <div className="col-12">
                            <label for="inputAddress2" className="form-label">Αριθμός πιστωτικής κάρτας</label>
                            <input type="text" className="form-control" id="creditCardNumber" placeholder="0000 0000 0000 0000" minlength="16" maxlength="16"/>
                        </div>
                        <div className="col-md-4">
                            <label for="inputExpMonth" className="form-label">Μήνας λήξης</label>
                            <select id="inputExpMonth" className="form-select">
                                <option selected>Επιλέξτε...</option>
                                {months.map(i => <option key={i}>{i}</option>)}
                            </select>
                        </div>
                        <div className="col-md-4">
                            <label for="inputExpYear" className="form-label">Έτος λήξης</label>
                            <select id="inputExpYear" className="form-select">
                                <option selected>Επιλέξτε...</option>
                                {years.map(i => <option key={i}>{i}</option>)}
                            </select>
                        </div>
                        <div className="col-md-4">
                            <label for="inputCvv" className="form-label">CVV</label>
                            <input type="password" className="form-control" id="inputCvv" placeholder="000" minlength="3" maxlength="3"/>
                        </div>
                        <PaymentIcon icon="visa" style={{ margin: 10, width: 70 }} />
                        <PaymentIcon icon="mastercard" style={{ margin: 10, width: 70 }} />
                        <PaymentIcon icon="maestro" style={{ margin: 10, width: 70 }} />
                        <PaymentIcon icon="paypal" style={{ margin: 10, width: 70 }} />
                        <div className="col-8">
                            {/* <Link to='/home'> */}
                                <button type="submit" className="btn btn-primary mybtn">Επιβεβαίωση</button>
                            {/* </Link> */}
                        </div>
                    </form>
                </Modal.Body>
                <Modal.Footer>
                    <Button className="mybtn" variant="outline-danger" onClick={props.onHide}>Άκυρο</Button>
                </Modal.Footer>
            </Modal>
		</div>
    );
}
 
export default BankPopUp;
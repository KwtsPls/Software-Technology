import React, {useState} from 'react';
import '../App.css';
import {Modal, Button} from 'react-bootstrap';
import AssignDev from './AssignDev.js'




function TaskInfoPopUp(props){

    const devs = ["nick", "alex", "jojo"]

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
                    {props.taskName}
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <form className="row g-3">
                        <div className="col-12">
                            <label for="inputDescription" className="form-label">Περιγραφή Task: </label>
                            <div>
                                <small> Αυτό ειναι ένα τακσ που κάνει διαφορα πράγματα και τα λοιπά </small>
                            </div>
                        </div>
                        <div className="col-12">
                            <label className="form-label">Story που υπάγεται το task: </label>
                            <small> Story No. 45 </small>
                        </div>
                        <div className="col-12">
                            <label className="form-label">Epic που υπάγεται το story: </label>
                            <small> Epic No. 7 </small>
                        </div>
                        <div className="col-12">
                            <label for="assignDev" className="form-label">Developers: </label>
                            {devs.map(i => <div><span class="badge bg-primary rounded-pill cool-purple">{i}</span></div>
                            )}
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
 
export default TaskInfoPopUp;
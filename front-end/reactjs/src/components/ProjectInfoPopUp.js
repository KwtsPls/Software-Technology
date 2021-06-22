import React, {useState} from 'react';
import '../App.css';
import {Modal, Button} from 'react-bootstrap';
import AssignDev from './AssignDev.js'




function ProjectInfoPopUp(props){

    const devs = ['alex','mich34','nikos','alejiz90']
    const devsPending = ['aerjerr','mrjkfnrj','rnf','fifimiio']

    const [title, setTitle] = useState("")

    function archive() {
        // call to data base
    }

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
                    Πληροφορίες για _ProjName_
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <form className="row g-3">
                        <div className="col-12">
                            <label for="inputTittle" className="form-label">Product Owner:</label>
                            <div><span class="badge bg-primary rounded-pill cool-purple">prOwn1</span></div>
                        </div>
                        <div className="col-12">
                            <label for="assignDev" className="form-label">Developers: </label>
                            {devs.map(i => <div><span class="badge bg-primary rounded-pill cool-purple">{i}</span></div>
                            )}
                        </div>
                        <div className="col-12">
                            <label for="assignDev" className="form-label">Developers που δεν έχουν απαντήσει: </label>
                            {devsPending.map(i => <div><span class="badge bg-primary rounded-pill cool-purple">{i}</span></div>
                            )}
                        </div>
                    </form>
                    <div className="row g-3 pt-3">
                        <div className="col-12 text-center">
                            <button className="btn btn-outline-warning text-center" onClick={() => archive()}>Αρχειοθέτηση</button>
                        </div>
                    </div>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="outline-danger" onClick={props.onHide}>Άκυρο</Button>
                </Modal.Footer>
            </Modal>
		</div>
    );
}
 
export default ProjectInfoPopUp;
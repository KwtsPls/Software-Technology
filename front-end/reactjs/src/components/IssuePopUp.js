import React, {useState} from 'react';
import '../App.css';
import {Modal, Button} from 'react-bootstrap';
import AssignDev from './AssignDev.js'




function IssuePopUp(props){

    const issueTypes = ['Task','Story',"Epic"]
    const epics = ['epic1','epic2','epic3']
    const stories = ['epic1/story1','epic1/story2','epic1/story3','epic3/story1']
    const sprints = ['Τρέχων Sprint', 'Επόμενο Sprint', 'Παράεπομενο Sprint']

    const [selectedType, setIssTy] = useState('')

    const [devs, setDevs] = useState([])

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
                    Δημιουργία νέου Ιssue
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <form className="row g-3">
                        <div className="col-12">
                            <label for="inputTittle" className="form-label">Τίτλος Issue</label>
                            <input type="text" className="form-control" id="issueTittle" placeholder="Τίτλος"/>
                        </div>
                        <div className="col-md-6">
                            <label for="inputIssueType" className="form-label">Είδος Issue</label>
                            <select id="inputIssueType" className="form-select" value={selectedType} onChange={e => setIssTy(e.target.value)}>
                                <option selected>Επιλέξτε...</option>
                                {issueTypes.map(i => <option key={i}>{i}</option>)}
                            </select>
                        </div>
                        {(selectedType === 'Story') && (
                            <>
                            <div className="col-md-6"/>
                            <div className="col-md-6">
                                <label for="chooseEpic" className="form-label">Ανάθεση σε Epic</label>
                                <select id="chooseEpic" className="form-select">
                                    <option selected>Επιλέξτε...</option>
                                    {epics.map(i => <option key={i}>{i}</option>)}
                                </select>
                            </div>
                            <div className="col-md-6">
                                <label for="chooseSprint" className="form-label">Ανάθεση σε Sprint</label>
                                <select id="chooseSprint" className="form-select">
                                    <option selected>Επιλέξτε...</option>
                                    {sprints.map(i => <option key={i}>{i}</option>)}
                                </select>
                            </div>
                            </>
                        )}
                        {(selectedType === 'Task') && ( <>
                            <div className="col-md-6">
                                <label for="chooseStory" className="form-label">Ανάθεση σε Story</label>
                                <select id="chooseStory" className="form-select">
                                    <option selected>Επιλέξτε...</option>
                                    {stories.map(i => <option key={i}>{i}</option>)}
                                </select>
                            </div>
                            <div className="col-12">
                                <label for="assignDev" className="form-label">Assign Dev</label>
                                <AssignDev devs={devs} setDevs={setDevs} message=" added as dev "/>
                            </div>
                        </>)}
                    </form>
                    <div className="row g-3 pt-3">
                        <div className="col-8">
                            {/* <Link to='/home'> */}
                                <button type="submit" className="btn btn-primary">Επιβεβαίωση</button>
                            {/* </Link> */}
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
 
export default IssuePopUp;
import React, {useState} from 'react';
import '../App.css';
import {Modal, Button} from 'react-bootstrap';
import AssignDev from './AssignDev.js'
import TaskWindow from './TaskWindow.js'




function TaskInfoPopUp(props){

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
                    {props.task.title}
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    {/* <TaskWindow epic={props.epic} focusTask={focusTask} focusStory={focusStory} devs={devs} projId={props.projId}/> */}
                    <TaskWindow epic={{title:"oki"}} focusTask={props.task} focusStory={{title:"oki"}} devs={devs} projId={props.projId}/>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="outline-danger" onClick={props.onHide}>Άκυρο</Button>
                </Modal.Footer>
            </Modal>
		</div>
    );
}
 
export default TaskInfoPopUp;
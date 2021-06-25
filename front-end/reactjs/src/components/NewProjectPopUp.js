import React, {useState} from 'react';
import '../App.css';
import {Modal, Button} from 'react-bootstrap';
import AssignDev from './AssignDev.js'




function NewProjectPopUp(props){

    const loggedUser = JSON.parse(localStorage.getItem('loggedUser'));

    const [devs, setDevs] = useState([])

    const [title, setTitle] = useState("")
    const [description, setDescription] = useState("")

    const [showEmptyTitle, setShowEmptyTitle] = useState(false)
    const [showEmptyDescription, setShowEmptyDescription] = useState(false)

    function submit() {
        let earlyExit = false
        if (title === ""){
            earlyExit = true;
            setShowEmptyTitle(true);
        }
        else {
            setShowEmptyTitle(false)
        }
        if (description === ""){
            earlyExit = true;
            setShowEmptyDescription(true);
        }
        else {
            setShowEmptyDescription(false)
        }
        if (earlyExit){
            return ;
        }

        props.addProj(title)
        props.onHide()

        fetch('http://localhost:8080/projects/create/1', { // TO BE CHANGED
                method: 'post', 
                headers: { Authorization: 'Bearer ' + loggedUser.accessToken,
                            'Content-Type': 'application/json'  
                    },
                body: JSON.stringify({  date_finished: null,
                                        description: description,
                                        status: 0,
                                        title: title
                    })
            })
                .then(res => res.json())
                .then((data) => {
                    console.log(data)
                    setTitle("")
                    setDescription("")
                    return data;
                    })
                .then((data) => {
                    for (const dev of devs){
                        console.log("sending request for " + dev.username + " with id " + dev.id + " to project with id " + data.id);
                        fetch('http://localhost:8080/developers/', { 
                            method: 'post', 
                            headers: { Authorization: 'Bearer ' + loggedUser.accessToken,
                                'Content-Type': 'application/json'  },
                            body: JSON.stringify({  user_id: dev.id,
                                        project_id: data.id,
                                        role: 0,
                                        accepted: 0
                                    })
                        })
                            .then(res => res.json())
                            .then((data) => {
                                console.log(data);
                            })
                    }
                })
        
        
    }

    function clickExit(){
        props.onHide()
        setDevs([])
        setTitle("")
        setDescription("")
        setShowEmptyTitle(false)
        setShowEmptyDescription(false)
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
                    Δημιουργία νέου Project
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <form className="row g-3">
                        <div className="col-12">
                            <label for="inputTittle" className="form-label">Τίτλος Project <span style={{color: "#cc0000"}}>*</span></label>
                            <input type="text" className="form-control" id="projectTittle" placeholder="Τίτλος" value={title} onChange={e => setTitle(e.target.value)}/>
                            <div>
                                { (showEmptyTitle) && <span class="badge bg-danger rounded-pill">Ο τίτλος δεν μπορεί να είναι κενός</span>}
                            </div>
                        </div>
                        <div className="col-12">
                            <label for="inputDescription" className="form-label">Περιγραφή Project <span style={{color: "#cc0000"}}>*</span></label>
                            <textarea type="text" className="form-control" id="projectDescription" placeholder="Περιγραφή" value={description} onChange={e => setDescription(e.target.value)}/>
                            <div>
                                { (showEmptyDescription) && <span class="badge bg-danger rounded-pill">Η περιγραφή δεν μπορεί να είναι κενή</span>}
                            </div>
                        </div>
                        <div className="col-12">
                            <label for="assignDev" className="form-label">Πρόσκληση προς Developers</label>
                            <AssignDev devs={devs} setDevs={setDevs} message=" will be requested to join "/>
                        </div>
                    </form>
                    <div className="row g-3 pt-3">
                        <div className="col-8">
                            {/* <Link to='/home'> */}
                                <button className="btn btn-primary" onClick={() => submit()}>Επιβεβαίωση</button>
                            {/* </Link> */}
                        </div>
                    </div>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="outline-danger" onClick={clickExit}>Άκυρο</Button>
                </Modal.Footer>
            </Modal>
		</div>
    );
}
 
export default NewProjectPopUp;
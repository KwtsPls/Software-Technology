import React, {useState, useEffect} from 'react';
import '../App.css';
import {Modal, Button} from 'react-bootstrap';
import AssignDev from './AssignDev.js'




function ProjectAddDevPopUp(props){

    const loggedUser = JSON.parse(localStorage.getItem('loggedUser'));


    const [devs, setDevs] = useState([])

    useEffect(() => {
        // setDevs([])
        // if (props.show){
        //     console.log("el fetch")
        //     fetch('http://localhost:8080/projects/' + props.projectId + '/users' , {
        //         method: 'get', 
        //         headers: { Authorization: 'Bearer ' + loggedUser.accessToken }
        //     })
        //         .then(res => res.json())
        //         .then((data) => {
        //             console.log(data)
        //             if (data._embedded){
        //                 for (var i=0; i< data._embedded.userList.length; i++){
        //                     devs.push(data._embedded.userList[i].username)
        //                     console.log(data._embedded.userList[i].username)
        //                 }
        //                 setDevs(devs)
        //                 console.log(devs)
        //             }
        //             // setTitle("")
        //             // setDescription("")
        //             // return data;
        //             })
            
        //     fetch('http://localhost:8080/developers/projects/' + props.projectId, {
        //         method: 'get', 
        //         headers: { Authorization: 'Bearer ' + loggedUser.accessToken }
        //     })
        //         .then((data) => {
        //             console.log(data)
        //             // setTitle("")
        //             // setDescription("")
        //             // return data;
        //             })
        // }
        
    },[props.show])

    function submit() {
        props.onHide()

        for (const dev of devs){
            console.log("sending request for " + dev.username + " with id " + dev.id + " to project with id " + props.projectId);
            fetch('http://localhost:8080/developers/', { 
                method: 'post', 
                headers: { Authorization: 'Bearer ' + loggedUser.accessToken,
                            'Content-Type': 'application/json'  },
                            body: JSON.stringify({  user_id: dev.id,
                                        project_id: props.projectId,
                                        role: 0,
                                        accepted: 0
                                    })
                        })
                            .then(res => res.json())
                            .then((data) => {
                                console.log(data);
                            })
                    }
        
        
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
                        Προσθήκη Developer στο {props.projName}
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <div className="col-12">
                        <label for="assignDev" className="form-label">Πρόσκληση προς Developers</label>
                        <AssignDev devs={devs} setDevs={setDevs} message=" will be requested to join "/>
                    </div>
                    <div className="row g-3 pt-3">
                        <div className="col-8">
                            <button className="btn btn-primary" onClick={() => submit()}>Επιβεβαίωση</button>
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
 
export default ProjectAddDevPopUp;
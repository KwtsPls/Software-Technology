import React, {useState, useEffect} from 'react';
import '../App.css';
import {Modal, Button} from 'react-bootstrap';
import AssignDev from './AssignDev.js'




function ProjectInfoPopUp(props){

    const loggedUser = JSON.parse(localStorage.getItem('loggedUser'));

    //const devs = ['alex','mich34','nikos','alejiz90']
    const [owner,setOwner] = useState([])
    const [devs,setDevs] = useState([])
    const [devsPending,setDevsPending] = useState([])

    const [title, setTitle] = useState("")

    function archive() {
        fetch('http://localhost:8080/projects/' + props.projectId + '/archive/'+loggedUser.id , {
                method: 'put', 
                headers: { Authorization: 'Bearer ' + loggedUser.accessToken
                    }
            })
                .then(res => res.json())
                .then((data) => {
                    console.log(data) // expects {message:"OK"}
                    if (data.message){
                        if (data.message === "OK"){
                            window.location.reload(false);
                        }

                    }
                })
    }

    useEffect(() => {
        setOwner([])
        setDevs([])
        setDevsPending([])
        if (props.show){
            fetch('http://localhost:8080/developers/projects/' + props.projectId, {
                method: 'get', 
                headers: { Authorization: 'Bearer ' + loggedUser.accessToken }
            })
                .then(res => res.json())
                .then((data) => {
                    console.log(data)
                    for (var i=0; i< data.length; i++){
                        if(data[i][3]){
                            owner.push(data[i][1])
                        }
                        else {
                            if (data[i][2]){
                                devs.push(data[i][1])
                            }
                            else {
                                devsPending.push(data[i][1])
                            }

                        }
                    }
                    setOwner(owner)
                    setDevs(devs)
                    setDevsPending(devsPending)
                    // setTitle("")
                    // setDescription("")
                    // return data;
                    })
        }
        
    },[props.show])

    function retDevs(){
        return devs.map(i => <div><span class="badge bg-primary rounded-pill cool-purple">{i}</span></div>)
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
                        Πληροφορίες για το {props.projName}
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <form className="row g-3">
                        <div className="col-12">
                            <label for="inputTittle" className="form-label">Product Owner:</label>
                            <div><span class="badge bg-primary rounded-pill cool-purple">{owner[0]}</span></div>
                        </div>
                        <div className="col-12">
                            <label className="form-label">Developers: </label>
                            {devs.map(i => <div><span class="badge bg-primary rounded-pill cool-purple">{i}</span></div>)}
                        </div>
                        <div className="col-12">
                            <label className="form-label">Developers που δεν έχουν απαντήσει: </label>
                            {devsPending.map(i => <div><span class="badge bg-primary rounded-pill cool-purple">{i}</span></div>)}
                        </div>
                    </form>
                    { !props.status && (loggedUser.id === loggedUser.id) &&
                        (<div className="row g-3 pt-3">
                            <div className="col-12 text-center">
                                <button className="btn btn-outline-warning text-center" onClick={() => archive()}>Αρχειοθέτηση</button>
                            </div>
                        </div>)
                    }
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="outline-danger" onClick={props.onHide}>Άκυρο</Button>
                </Modal.Footer>
            </Modal>
		</div>
    );
}
 
export default ProjectInfoPopUp;
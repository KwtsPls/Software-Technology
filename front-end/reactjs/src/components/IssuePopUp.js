import React, { useState, useEffect } from 'react';
import '../App.css';
import {Modal, Button} from 'react-bootstrap'
import AssignDev from './AssignDev.js'
import { useHistory } from 'react-router-dom'
import RedAsterisc from './RedAsterisc.js'




function IssuePopUp(props){

    const history = useHistory();
    const loggedUser = JSON.parse(localStorage.getItem('loggedUser'));

    useEffect(() => {
        fetch('http://localhost:8080/projects/' + props.projId + '/sprints/active/stories', {
                method: 'get', 
                headers: { Authorization: 'Bearer ' + loggedUser.accessToken }
            })
                .then(res => res.json())
                .then((data) => {
                    console.log("Raw list of active stories:");
                    console.log(data);
                    if (data._embedded){
                        setRawStories(data._embedded.storyList)
                    }
                })
    },[props.projΙd])

    const [rawStories, setRawStories] = useState([])

    const issueTypes = ['Task','Story',"Epic"]
    const sprints = ['Τρέχων Sprint', 'Επόμενο Sprint', 'Παράεπομενο Sprint']

    const [title, setTitle] = useState("")
    const [description, setDescription] = useState("")
    const [selectedType, setIssTy] = useState('')
    const [selectedEpic, setSelectedEpic] = useState('')
    const [selectedSprint, setSelectedSprint] = useState('')
    const [selectedStory, setSelectedStory] = useState('')

    const [devs, setDevs] = useState([])

    function cancel() {
        props.onHide()
        setDevs([])
    }

    function getStory(){
        console.log('Looking for:')
        console.log(selectedStory)
        for (var i=0; i<rawStories.length; i++){
            if (selectedStory === rawStories[i].title){
                return rawStories[i]
            }
        }
        console.log('Story was not found')
        return -1
    }

    function getEpicId(){
        const epics = props.epics
        for (var i=0; i<epics.length; i++){
            if (selectedEpic === epics[i].title){
                return epics[i].id
            }
        }
        console.log('Epic was not found')
        return -1
    }

    function getSprintId(){
        const sprints = props.sprints
        if (selectedSprint === 'Τρέχων Sprint'){
            return sprints[0].id
        }
        if (selectedSprint === 'Επόμενο Sprint'){
            return sprints[1].id
        }
        if (selectedSprint === 'Παράεπομενο Sprint'){
            return sprints[2].id
        }
        console.log('Sprint was not found')
        return -1
    }

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

        props.onHide()

        let fetchAdr = ''
        let body = ''
        if (selectedType === 'Task'){
            const story = getStory()
            fetchAdr = 'http://localhost:8080/projects/' + props.projId + '/sprints&epics/' + story.sprint_id + '&' + story.epic_id + '/stories/'+story.id+'/tasks/create'
            body = JSON.stringify({ title: title,
                                    description: description,
                                    status: 0,
                                    project_id: props.projId,
                                    epic_id: story.epic_id,
                                    sprint_id: story.sprint_id,
                                    story_id: story.id})
        }
        else if (selectedType === 'Story'){
            const sprint = getSprintId()
            const epic = getEpicId()
            fetchAdr = 'http://localhost:8080/projects/' + props.projId + '/sprints&epics/' + sprint + '&' + epic + '/stories/create'
            body = JSON.stringify({ title: title,
                                    description: description,
                                    status: 0,
                                    project_id: props.projId,
                                    epic_id: epic,
                                    sprint_id: sprint})
        }
        else if (selectedType === 'Epic'){
            fetchAdr = 'http://localhost:8080/projects/epics/create'
            body = JSON.stringify({  title: title,
                                    description: description,
                                    status: 0,
                                    project_id: props.projId })
        }
        else{
            console.log("pick one dummy")
            return
        }
        fetch(fetchAdr, {
            method: 'post', 
            headers: { Authorization: 'Bearer ' + loggedUser.accessToken,
                        'Content-Type': 'application/json' },
            body: body
        })
            .then(res => res.json())
            .then((data) => {
                console.log(data)
                setTitle("")
                setDescription("")
                if (data.id){
                    window.location.reload(false);
                }
                else {
                    console.log("Something went wrong while creating issue")
                }
                return data;
            })
            // .then((data) => {
            //     var i=0;
            //     var numResponses = 0;
            //     for (; i < devs.length; i++){
            //         console.log("sending request for " + devs[i].username + " with id " + devs[i].id + " to task with id " + data.id);
            //         fetch('http://localhost:8080/developers/', { 
            //             method: 'post', 
            //             headers: { Authorization: 'Bearer ' + loggedUser.accessToken,
            //                 'Content-Type': 'application/json'  },
            //             body: JSON.stringify({  user_id: devs[i].id,
            //                         project_id: data.id,
            //                         role: 0,
            //                         accepted: 0
            //                     })
            //         })
            //             .then(res => res.json())
            //             .then((data) => {
            //                 console.log(numResponses)
            //                 console.log(data);
            //                 numResponses++
            //             })
            //             .then(() => {
            //                 if (numResponses === devs.length){
            //                     window.location.reload(false);
            //                 }
            //             })
            //     }
            //     if (i === 0){
            //         window.location.reload(false);
            //     }
            // })
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
                        Δημιουργία νέου Ιssue
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <form className="row g-3">
                        <div className="col-12">
                            <label for="inputTittle" className="form-label">Τίτλος Issue <RedAsterisc/></label>
                            <input type="text" className="form-control" id="issueTittle" placeholder="Τίτλος" value={title} onChange={e => setTitle(e.target.value)}/>
                            <div>
                                { (showEmptyTitle) && <span class="badge bg-danger rounded-pill">Ο τίτλος δεν μπορεί να είναι κενός</span>}
                            </div>
                        </div>
                        <div className="col-12">
                            <label for="inputDescription" className="form-label">Περιγραφή Issue <RedAsterisc/></label>
                            <textarea type="text" className="form-control" id="issueDescription" placeholder="Περιγραφή" value={description} onChange={e => setDescription(e.target.value)}/>
                            <div>
                                { (showEmptyDescription) && <span class="badge bg-danger rounded-pill">Η περιγραφή δεν μπορεί να είναι κενή</span>}
                            </div>
                        </div>
                        <div className="col-md-6">
                            <label for="inputIssueType" className="form-label">Είδος Issue <RedAsterisc/></label>
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
                                <select id="chooseEpic" className="form-select" value={selectedEpic} onChange={e => setSelectedEpic(e.target.value)}>
                                    <option selected>Επιλέξτε...</option>
                                    {props.epics.map(i => <option key={i.id}>{i.title}</option>)}
                                </select>
                            </div>
                            <div className="col-md-6">
                                <label for="chooseSprint" className="form-label">Ανάθεση σε Sprint</label>
                                <select id="chooseSprint" className="form-select" value={selectedSprint} onChange={e => setSelectedSprint(e.target.value)}>
                                    <option selected>Επιλέξτε...</option>
                                    {sprints.map(i => <option key={i}>{i}</option>)}
                                </select>
                            </div>
                            </>
                        )}
                        {(selectedType === 'Task') && ( <>
                            <div className="col-md-6">
                                <label for="chooseStory" className="form-label">Ανάθεση σε Story</label>
                                <select id="chooseStory" className="form-select" value={selectedStory} onChange={e => setSelectedStory(e.target.value)}>
                                    <option selected>Επιλέξτε...</option>
                                    {rawStories.map(i => <option key={i.id}>{i.title}</option>)}
                                </select>
                            </div>
                            <div className="col-12">
                                <label for="assignDev" className="form-label">Assign Dev</label>
                                <AssignDev devs={devs} setDevs={setDevs}  message=" will be assigned " checkForReAdd={false} projId={0}/>
                            </div>
                        </>)}
                    </form>
                    <div className="row g-3 pt-3">
                        <div className="col-8">
                            <button className="btn btn-primary" onClick={() => submit()}>Επιβεβαίωση</button>
                        </div>
                    </div>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="outline-danger" onClick={cancel}>Άκυρο</Button>
                </Modal.Footer>
            </Modal>
		</div>
    );
}
 
export default IssuePopUp;
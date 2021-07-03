import React, {useState, useEffect} from 'react';
import '../App.css';
import {Modal, Button} from 'react-bootstrap';
import { useHistory } from 'react-router-dom'
import AssignDev from './AssignDev.js'




function StoriesInEpicsPopUp(props){

    const history = useHistory();
    const loggedUser = JSON.parse(localStorage.getItem('loggedUser'));

    const [storiesList, setStoriesList] = useState([])
    const [tasksList, setTasksList] = useState([])

    const [view, setView] = useState("stories")
    const [focusStory, setFocusStory] = useState({id: 0, title: ""})
    const [focusTask, setFocusTask] = useState(-1)

    const [devs, setDevs] = useState([])

    useEffect(() => {
        if (props.show){
            fetch('http://localhost:8080/projects/'+props.projId+'/epics/'+props.epic.id+'/stories', {
                    method: 'get', 
                    headers: { Authorization: 'Bearer ' + loggedUser.accessToken }
                })
                    .then(res => res.json())
                    .then((data) => {
                        console.log("Stories in epic "+props.epic.title+":");
                        console.log(data);
                        if (data._embedded){
                            setStoriesList(data._embedded.storyList)
                        }
                        else{
                            setStoriesList([])
                        }
                    })
        }
        else {
            console.log("all good")
        }
    },[props.show])

    useEffect(() => {
        if (focusStory != {id: 0, title: ""}){
            fetch('http://localhost:8080/projects/'+props.projId+'/stories/'+focusStory.id+'/tasks', {
                    method: 'get', 
                    headers: { Authorization: 'Bearer ' + loggedUser.accessToken }
                })
                    .then(res => res.json())
                    .then((data) => {
                        console.log("Tasks in story "+focusStory.title+":");
                        console.log(data);
                        if (data._embedded){
                            setTasksList(data._embedded.taskList)
                        }
                        else{
                            setTasksList([])
                        }
                    })
        }
        else {
            console.log("all good")
        }
    },[focusStory])

    function openStory(story){
        setFocusStory({id: 0, title: ""}) // PROBLEM
        setFocusStory(story)
        setView('tasks')
        ;
    }

    function openTask(task){
        //setFocusTask(-1) // PROBLEM
        setFocusTask(task)
        setView('taskInfo')
        ;
    }

    function goBack(){
        setView('stories')
    }

    function goBack2(){
        setView('tasks')
    }

    function clickExit(){
        props.onHide()
        setView('stories')
    }

    function doneSatus(cond){
        if (cond){
            return (<span class="badge bg-success rounded-pill done-gradient">
                Done
            </span>);
        }
        return (<span class="badge bg-warning rounded-pill inprogress-gradient">In progress</span>);
    }

    function markAsDone() {
        fetch('http://localhost:8080/projects/'+props.projId+'/sprints&epics/'+focusStory.sprint_id+'&'+focusStory.epic_id+'/stories/'+focusStory.id+'/tasks/'+focusTask.id+'/archive', {
                    method: 'put', 
                    headers: { Authorization: 'Bearer ' + loggedUser.accessToken}
                })
                    .then(res => res.json())
                    .then((data) => {
                        console.log("Trying to mark as done");
                        console.log(data);
                    })
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
                    {(view === 'stories') && "Stories του Epic:"}
                    {(view === 'tasks') && "Tasks του Story:"}
                    {(view === 'taskInfo') && ("Task: " + focusTask.title)}
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <div class="list-group pt-3" style={{width: '100%',"flex-wrap": "nowrap"}}>
                        {(view === 'stories') && storiesList.map(i => <div class="list-group-item list-group-item-action" onClick={() => openStory(i)}>{i.title}</div>)}
                        {(view === 'tasks') && 
                            <div>
                                <div>
                                    <button class="btn btn-secondary" onClick={() => goBack()}>{"< Back"}</button>
                                </div>
                                <div className="pt-3">
                                    {tasksList.map(i => <div class="list-group-item list-group-item-action justify-content-between align-items-center" onClick={() => openTask(i)}>{i.title + "     "}{doneSatus(i.status)}</div>)}
                                </div>
                            </div> 
                        }
                        {(view === 'taskInfo') && 
                            <div>
                                <div>
                                    <button class="btn btn-secondary" onClick={() => goBack2()}>{"< Back"}</button>
                                </div>
                                <form className="row g-3 pt-4">
                                    <div className="col-12">
                                        <label for="inputDescription" className="form-label">Περιγραφή Task: </label>
                                        <div>
                                            <small>{focusTask.description}</small>
                                        </div>
                                    </div>
                                    <div className="col-12">
                                        <label className="form-label">Story που υπάγεται το task: </label>
                                        <small> {focusStory.title} </small>
                                    </div>
                                    <div className="col-12">
                                        <label className="form-label">Epic που υπάγεται το story: </label>
                                        <small> {props.epic.title} </small>
                                    </div>
                                    <div className="col-12">
                                        <label className="form-label">{"Κατάσταση:   "}</label>
                                        {doneSatus(focusTask.status)}
                                    </div>
                                    <div className="col-12">
                                        <label for="assignDev" className="form-label">Του(ς) έχει ανατεθεί: </label>
                                        {devs.map(i => <div><span class="badge bg-primary rounded-pill cool-purple">{i}</span></div>
                                        )}
                                    </div>
                                    <div>
                                        {(focusTask.status === 0) && <div class="btn btn-success" onClick={() => markAsDone()}>Μάρκαρε ως Done</div>}
                                    </div>
                                </form>
                            </div> 
                        }
                    </div>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="outline-danger" onClick={clickExit}>Άκυρο</Button>
                </Modal.Footer>
            </Modal>
		</div>
    );
}

export default StoriesInEpicsPopUp;

{/* <ul class="list-group" style={{width: '100%'}}>
                    <li class="list-group-item d-flex justify-content-between align-items-center backlog-list" onClick={gg1}>
                        {tasks[0]}
                        {doneSatus(cond1)}
                    </li> */}
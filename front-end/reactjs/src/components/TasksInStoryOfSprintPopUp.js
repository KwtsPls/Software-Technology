import React, {useState, useEffect} from 'react';
import '../App.css';
import {Modal, Button} from 'react-bootstrap';
import { useHistory } from 'react-router-dom'
import AssignDev from './AssignDev.js'
import TaskWindow from './TaskWindow.js'

function TasksInStoryOfSprintPopUp(props){

    const history = useHistory();
    const loggedUser = JSON.parse(localStorage.getItem('loggedUser'));

    const [tasksList, setTasksList] = useState([])

    const [view, setView] = useState("tasks")
    const [focusTask, setFocusTask] = useState({id: 0, title: ""})

    const [epic, setEpic] = useState({title: "_"})

    const [devs, setDevs] = useState([])

    useEffect(() => {
        if (!loggedUser){
            history.push("/login");
        }
        if (props.epicId){
            fetch('http://localhost:8080/projects/'+props.projId+'/epics/'+props.epicId, {
                    method: 'get', 
                    headers: { Authorization: 'Bearer ' + loggedUser.accessToken }
                })
                    .then(res => res.json())
                    .then((data) => {
                        console.log("This is the epic :");
                        console.log(data);
                        if (data.id){
                            setEpic(data)
                        }
                    })
        }

    },[props.epicId])

    useEffect(() => {
        if (!loggedUser){
            history.push("/login");
        }
        if (props.focusStory != {id: 0, title: ""}){
            fetch('http://localhost:8080/projects/'+props.projId+'/stories/'+props.focusStory.id+'/tasks', {
                    method: 'get', 
                    headers: { Authorization: 'Bearer ' + loggedUser.accessToken }
                })
                    .then(res => res.json())
                    .then((data) => {
                        console.log("Tasks in story "+props.focusStory.title+":");
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
    },[props.show])

    function openTask(task){
        //setFocusTask(-1) // PROBLEM
        setFocusTask(task)
        setView('taskInfo')
        ;
    }

    function goBack(){
        setView('tasks')
    }

    function clickExit(){
        props.onHide()
        setView('tasks')
    }

    function doneSatus(cond){
        if (cond){
            return (<span class="badge bg-success rounded-pill done-gradient">
                Done
            </span>);
        }
        return (<span class="badge bg-warning rounded-pill inprogress-gradient">In progress</span>);
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
                    {(view === 'tasks') && "Tasks του Story:"}
                    {(view === 'taskInfo') && ("Task: " + focusTask.title)}
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <div class="list-group pt-3" style={{width: '100%',"flex-wrap": "nowrap"}}>
                        {(view === 'tasks') && 
                            <div className="pt-3">
                                {tasksList.map(i => <div class="list-group-item list-group-item-action justify-content-between align-items-center" onClick={() => openTask(i)}>{i.title + "     "}{doneSatus(i.status)}</div>)}
                            </div>
                        }
                        {(view === 'taskInfo') && 
                            <div>
                                <div>
                                    <button class="btn btn-secondary" onClick={() => goBack()}>{"< Back"}</button>
                                </div>
                                <TaskWindow epic={epic} focusTask={focusTask} focusStory={props.focusStory} devs={devs} projId={props.projId}/>
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

export default TasksInStoryOfSprintPopUp;

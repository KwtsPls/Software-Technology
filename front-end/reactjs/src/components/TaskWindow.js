import React, {useState, useEffect} from 'react';
import { useHistory } from 'react-router-dom'
import '../App.css';

function TaskWindow(props){

    const history = useHistory();
    const loggedUser = JSON.parse(localStorage.getItem('loggedUser'));

    const [devs, setDevs] = useState([])

    useEffect(()=>{
        if (!loggedUser){
            history.push("/login");
        }
        if (props.focusTask.id){// /assignees/users/projects/{project_id}/tasks/{task_id}
            fetch('http://localhost:8080/assignees/users/projects/'+props.projId+'/tasks/'+props.focusTask.id, {
                    method: 'get', 
                    headers: { Authorization: 'Bearer ' + loggedUser.accessToken}
                })
                    .then(res => res.json())
                    .then((data) => {
                        console.log("Assignees of task " + props.focusTask.title +":");
                        console.log('http://localhost:8080/assignees/users/projects/'+props.projId+'/tasks/'+props.focusTask.id)
                        console.log(data);
                        if (data._embedded){
                            setDevs(data._embedded.userList)
                        }
                    })
        }
    },[props.focusTask])

    function markAsDone() {
        fetch('http://localhost:8080/projects/'+props.projId+'/sprints&epics/'+props.focusTask.sprint_id+'&'+props.focusTask.epic_id+'/stories/'+props.focusTask.story_id+'/tasks/'+props.focusTask.id+'/archive', {
                    method: 'put', 
                    headers: { Authorization: 'Bearer ' + loggedUser.accessToken}
                })
                    .then(res => res.json())
                    .then((data) => {
                        console.log("Trying to mark as done");
                        console.log(data);
                        if (data.message === "OK"){
                            window.location.reload(false);
                        }
                    })
    }  

    function doneSatus(cond){
        if (cond){
            return (<span className="badge bg-success rounded-pill done-gradient">
                Done
            </span>);
        }
        return (<span className="badge bg-warning rounded-pill inprogress-gradient">In progress</span>);
    }

    return (
    <form className="row g-3 pt-4">
        <div className="col-12">
            <label for="inputDescription" className="form-label">Περιγραφή Task: </label>
            <div>
                <small>{props.focusTask.description}</small>
            </div>
        </div>
        <div className="col-12">
            <label className="form-label">Story που υπάγεται το task: </label>
            <small> {props.focusStory.title} </small>
        </div>
        <div className="col-12">
            <label className="form-label">Epic που υπάγεται το story: </label>
            <small> {props.epic.title} </small>
        </div>
        <div className="col-12">
            <label className="form-label">{"Κατάσταση:   "}</label>
            {doneSatus(props.focusTask.status)}
        </div>
        <div className="col-12">
            <label for="assignDev" className="form-label">Του(ς) έχει ανατεθεί: </label>
            {devs.map(i => <div><span className="badge bg-primary rounded-pill cool-purple">{i.username}</span></div>
            )}
        </div>
        <div>
            {(props.focusTask.status === 0) && <div className="btn btn-success" onClick={() => markAsDone()}>Μάρκαρε ως Done</div>}
        </div>
    </form>);
}

export default TaskWindow;
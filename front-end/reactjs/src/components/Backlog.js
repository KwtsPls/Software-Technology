import React, {useState, useEffect} from 'react';
import '../App.css';
import '../css/projects.css';
import TaskInfoPopUp from './TaskInfoPopUp.js'





function Backlog(props){

    const loggedUser = JSON.parse(localStorage.getItem('loggedUser'));

    const [modalTaskInfoShow, setModalTaskInfoShow] = useState(false);
    const [clickedTask, setClickedTask] = useState({id:'0',title:'_',epic_title:"_",story_title:"_"});

    const [date, setDate] = useState("")

    const [tasksList, setTasksList] = useState([])

    useEffect(()=>{
            fetch('http://localhost:8080/projects/'+ props.projectId +'/sprints/active/tasks', {
                method: 'get', 
                headers: { Authorization: 'Bearer ' + loggedUser.accessToken }
            })
                .then(res => res.json())
                .then((data) => {
                    console.log("Ligo apola:");
                    console.log(data);
                    if (data.tasksList){
                        setTasksList(data.tasksList)
                        var cou = 0;
                        for(var i=0; i<data.tasksList.length;i++){
                            if (data.tasksList[i].status){
                                cou++
                            }
                        }
                        if (data.tasksList.length === 0){
                            setPerc(0)
                        }
                        else {
                            setPerc(((cou / data.tasksList.length) * 100).toFixed())
                        }
                    }
                    
                })
    },[])

    useEffect(()=>{
        console.log("This is it")
        console.log(props.activeSprint)
        if (props.activeSprint){
            setDate(props.activeSprint.date_to)
        }

    },[props.activeSprint])

    const [cond1, setCond1] = useState(true);
    const [cond2, setCond2] = useState(false);
    const [cond3, setCond3] = useState(true);
    const [cond4, setCond4] = useState(true);
    const [cond5, setCond5] = useState(false);


    function doneSatus(cond){
        if (cond){
            return (<span class="badge bg-success rounded-pill done-gradient">
                Done
            </span>);
        }
        return (<span class="badge bg-warning rounded-pill inprogress-gradient" >In progress</span>);
    }

    const [perc, setPerc] = useState(0);


    function openTask(task){
        setClickedTask(task)
        setModalTaskInfoShow(true)
    }

    const tasks = ['Make Unittests for new modules','Correct port connectivity for PDCH','Rewrite auth for system_aaa','Make new OHM samples','Cook a nice carbonara']

    return (
        <div>
            <TaskInfoPopUp show={modalTaskInfoShow} task={clickedTask} onHide={() => setModalTaskInfoShow(false)} projId={props.projectId}/>
            <div class="container">
                <div class="row">
                    <div class="card">
                        <div class="pt-3">
                        <h5> Ποσοστό ολοκληρωμένων task: </h5>
                        <div class="progress backprogress" style={{height: "20px"}}>
                            <div class="progress-bar  backprogress-bar bg-info" role="progressbar" style={{width: `${perc}%`}} aria-valuemin="0" aria-valuemax="100">{`${perc}`}%</div>
                        </div>
                        
                        <div class="pt-3"/> 
                        <h5> Καταληκτική ημερομηνία τρέχοντος Sprint: {date}</h5>

                        <div class="pt-3"/>
                    
                        </div>
                    </div>
                </div>
                <div class="row pt-4">
                    <ul class="list-group" style={{width: '100%'}}>
                        {tasksList.map(i=> <li class="list-group-item d-flex justify-content-between align-items-center backlog-list" onClick={()=>openTask(i)}>
                            {i.epic_title + ' / ' + i.story_title + ' / ' + i.title}
                            {doneSatus(i.status)}
                        </li>)}
                    </ul>
                </div>
            </div>
        </div>
        


    );
}
 
export default Backlog;
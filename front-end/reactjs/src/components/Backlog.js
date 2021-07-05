import React, {useState, useEffect} from 'react';
import '../App.css';
import '../css/projects.css';
import TaskInfoPopUp from './TaskInfoPopUp.js'





function Backlog(props){

    const loggedUser = JSON.parse(localStorage.getItem('loggedUser'));

    const [modalTaskInfoShow, setModalTaskInfoShow] = useState(false);
    const [clickedTask, setClickedTask] = useState({id:'0',title:'_',epic_title:"_",story_title:"_"});

    const [date, setDate] = useState("")
    const [sprintId, setSprintId] = useState(0)

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
            setSprintId(props.activeSprint.id)
        }

    },[props.activeSprint])

    const [cond1, setCond1] = useState(true);
    const [cond2, setCond2] = useState(false);
    const [cond3, setCond3] = useState(true);
    const [cond4, setCond4] = useState(true);
    const [cond5, setCond5] = useState(false);


    function doneSatus(cond){
        if (cond){
            return (<span className="badge bg-success rounded-pill done-gradient">
                Done
            </span>);
        }
        return (<span className="badge bg-warning rounded-pill inprogress-gradient" >In progress</span>);
    }

    const [perc, setPerc] = useState(0);


    function openTask(task){
        setClickedTask(task)
        setModalTaskInfoShow(true)
    }

    function finalizeSprint(){
        fetch('http://localhost:8080/projects/'+ props.projectId +'/sprints/archive/'+loggedUser.id, {
                method: 'put', 
                headers: { Authorization: 'Bearer ' + loggedUser.accessToken }
            })
                .then(res => res.json())
                .then((data) => {
                    console.log("Newly created paraepomeno sprint:");
                    console.log(data);
                    if (data.id){
                        if (data.status === 3){
                            window.location.reload(false);
                        }
                    }
                })

    }

    const tasks = ['Make Unittests for new modules','Correct port connectivity for PDCH','Rewrite auth for system_aaa','Make new OHM samples','Cook a nice carbonara']

    return (
        <div>
            <TaskInfoPopUp show={modalTaskInfoShow} task={clickedTask} onHide={() => setModalTaskInfoShow(false)} projId={props.projectId}/>
            <div className="container">
                <div className="row">
                    <div className="card">
                        <div className="pt-3">
                        <h5> Ποσοστό ολοκληρωμένων task: </h5>
                        <div className="progress backprogress" style={{height: "20px"}}>
                            <div className="progress-bar  backprogress-bar bg-info" role="progressbar" style={{width: `${perc}%`}} aria-valuemin="0" aria-valuemax="100">{`${perc}`}%</div>
                        </div>
                        
                        <div className="pt-3"/> 
                        <h5> Καταληκτική ημερομηνία τρέχοντος Sprint: {date}</h5>

                        <div className="pt-3"/>
                    
                        </div>
                    </div>
                </div>
                <div className="row pt-4">
                    <ul className="list-group" style={{width: '100%'}}>
                        {tasksList.map(i=> <li key={i.id} className="list-group-item d-flex justify-content-between align-items-center backlog-list" onClick={()=>openTask(i)}>
                            {i.epic_title + ' / ' + i.story_title + ' / ' + i.title}
                            {doneSatus(i.status)}
                        </li>)}
                    </ul>
                </div>
                <div className="row pt-4">
                    <div>
                        <button className='btn btn-primary' onClick={()=>finalizeSprint()}>Ολοκλήρωση του Sprint</button>
                    </div>
                </div>
            </div>
        </div>
        


    );
}
 
export default Backlog;
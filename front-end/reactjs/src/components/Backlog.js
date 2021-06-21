import React, {useState, useEffect} from 'react';
import '../App.css';




function Backlog(props){

    const [cond1, setCond1] = useState(true);
    const [cond2, setCond2] = useState(false);
    const [cond3, setCond3] = useState(true);
    const [cond4, setCond4] = useState(true);
    const [cond5, setCond5] = useState(false);


    function doneSatus(cond){
        if (cond){
            return (<span class="badge bg-success rounded-pill cool-purple">
                Done
            </span>);
        }
        return (<span class="badge bg-warning rounded-pill" style={{'background-image': "linear-gradient(to bottom right,#F58A07,#A72608)"}}>In progress</span>);
    }

    function gg1(){
        props.setSelectedTask(tasks[0])
        props.setModalTaskInfoShow(true)
        setCond1(!cond1)
    }

    function gg2(){
        props.setSelectedTask(tasks[1])
        props.setModalTaskInfoShow(true)
        setCond2(!cond2)
    }

    function gg3(){
        props.setSelectedTask(tasks[2])
        props.setModalTaskInfoShow(true)
        setCond3(!cond3)
    }

    function gg4(){
        props.setSelectedTask(tasks[3])
        props.setModalTaskInfoShow(true)
        setCond4(!cond4)
    }

    function gg5(){
        props.setSelectedTask(tasks[4])
        props.setModalTaskInfoShow(true)
        setCond5(!cond5)
    }

    function calcPerc(){
        let count = 0;
        if (cond1){
            count += 1;
        }
        if (cond2){
            count += 1;
        }
        if (cond3){
            count += 1;
        }
        if (cond4){
            count += 1;
        }
        if (cond5){
            count += 1;
        }
        console.log("here you go")
        console.log(count / 5)
        return (count / 5) * 100
    }

    const [perc, setPerc] = useState(calcPerc());

    useEffect(() => {
        setPerc(calcPerc)
    }, [cond1,cond2,cond3,cond4,cond5]);

    const tasks = ['Make Unittests for new modules','Correct port connectivity for PDCH','Rewrite auth for system_aaa','Make new OHM samples','Cook a nice carbonara']

    return (
        <div class="container">
            <div class="row">
            <ul class="list-group" style={{width: '100%'}}>
                <li class="list-group-item d-flex justify-content-between align-items-center" onClick={gg1}>
                    {tasks[0]}
                    {doneSatus(cond1)}
                </li>
                <li class="list-group-item d-flex justify-content-between align-items-center" onClick={gg2}>
                    {tasks[1]}
                    {doneSatus(cond2)}
                </li>
                <li class="list-group-item d-flex justify-content-between align-items-center" onClick={gg3}>
                    {tasks[2]}
                    {doneSatus(cond3)}
                </li>
                <li class="list-group-item d-flex justify-content-between align-items-center" onClick={gg4}>
                    {tasks[3]}
                    {doneSatus(cond4)}
                </li>
                <li class="list-group-item d-flex justify-content-between align-items-center" onClick={gg5}>
                    {tasks[4]}
                    {doneSatus(cond5)}
                </li>
            </ul>
            </div>
            <div class="row">
            <div style={{position:'absolute', bottom:'0', width: '70%'}}>
                <div class="progress" style={{height: "20px"}}>
                    <div class="progress-bar" role="progressbar" style={{width: '25%','background-image': "linear-gradient(to bottom right,#992b95,#2136f5a6)"}} aria-valuenow="25" aria-valuemin="0" aria-valuemax="100">25%</div>
                </div>
                <div class="pt-3"/>
                <div class="progress" style={{height: "20px"}}>
                    <div class="progress-bar bg-info" role="progressbar" style={{width: `${perc}%`, 'background-image': "linear-gradient(to bottom right,#992b95,#2136f5a6)"}} aria-valuemin="0" aria-valuemax="100">{`${perc}`}%</div>
                </div>
                <div class="pt-3"/>
                </div>
            </div>
            </div>


    );
}
 
export default Backlog;
import React from 'react';
import '../App.css';
import {Modal, Button} from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import PaymentIcon from 'react-payment-icons-inline';




function Backlog(){

    function doneSatus(cond){
        if (cond){
            return (<span class="badge bg-success rounded-pill" style={{'background-image': "linear-gradient(to bottom right,#992b95,#2136f5a6)"}}>
                Done
            </span>);
        }
        return (<span class="badge bg-warning rounded-pill" style={{'background-image': "linear-gradient(to bottom right,#F58A07,#A72608)"}}>In progress</span>);
    }


    return (
        <div>
            <ul class="list-group">
                <li class="list-group-item d-flex justify-content-between align-items-center">
                    A list item
                    {doneSatus(true)}
                </li>
                <li class="list-group-item d-flex justify-content-between align-items-center">
                    A second list item
                    {doneSatus(false)}
                </li>
                <li class="list-group-item d-flex justify-content-between align-items-center">
                    A third list item
                    {doneSatus(true)}
                </li>
            </ul>
            <div style={{position:'absolute', bottom:'0', width: '70%'}}>
                <div class="progress" style={{height: "20px"}}>
                    <div class="progress-bar" role="progressbar" style={{width: '25%','background-image': "linear-gradient(to bottom right,#992b95,#2136f5a6)"}} aria-valuenow="25" aria-valuemin="0" aria-valuemax="100">25%</div>
                </div>
                <div class="pt-3"/>
                <div class="progress" style={{height: "20px"}}>
                    <div class="progress-bar bg-info" role="progressbar" style={{width: '25%', 'background-image': "linear-gradient(to bottom right,#992b95,#2136f5a6)"}} aria-valuenow="25" aria-valuemin="0" aria-valuemax="100">25%</div>
                </div>
                <div class="pt-3"/>
                </div>
            </div>

    );
}
 
export default Backlog;
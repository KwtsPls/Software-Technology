import React, { Component, useEffect, useState } from 'react';
import { Link, useHistory } from 'react-router-dom'
import SideNavBar from '../components/SideNavBar.js'
import Topbar from '../components/Topbar.js'
import EventCalendar from './EventCalendar.js'

import '../css/generalbacklog.css'
import {
    MonthlyBody,
    MonthlyCalendar,
    MonthlyNav,
    DefaultMonthlyEventItem,
} from '@zach.codes/react-calendar';





function OverviewScreen() {

    const history = useHistory();
    const loggedUser = JSON.parse(localStorage.getItem('loggedUser'));
    
    useEffect(() => {
        if (!loggedUser){
            history.push("/login");
        }
        document.body.style.background = "#fff";

    }, []);

    
 
    
    return (
        <div>
            <Topbar/>
            <SideNavBar/>
            <div className="mainContent">

                <div class="row genbacklog-container d-flex justify-content-center">
                    <div class="col-xl-12">
                        <div class="card proj-progress-card">
                            <div class="card-block">
                                <div class="row">
                                    <div class="col-xl-4 col-md-6">
                                        <h3 className="backlogtitle">Ολοκληρωμένα Projects</h3>
                                        <h5 class="m-b-30 f-w-700">1 από 4</h5>
                                        <div class="progress countprogress">
                                            <div class="progress-bar countprogress-bar bg-c-red" style={{width:'25%'}}></div>
                                        </div>
                                    </div>
                                    <div class="col-xl-4 col-md-6">
                                        <h3 className="backlogtitle">Ολοκληρωμένα Epics</h3>
                                        <h5 class="m-b-30 f-w-700">4 από 6</h5>
                                        <div class="progress countprogress">
                                            <div class="progress-bar countprogress-bar bg-c-blue" style={{width:'65%'}}></div>
                                        </div>
                                    </div>
                                    <div class="col-xl-4 col-md-6">
                                        <h3 className="backlogtitle">Ολοκληρωμένα Stories</h3>
                                        <h5 class="m-b-30 f-w-700">10 από 20</h5>
                                        <div class="progress countprogress">
                                            <div class="progress-bar countprogress-bar bg-c-green" style={{width:'85%'}}></div>
                                        </div>
                                    </div>
                                    {/* <div class="col-xl-3 col-md-6">
                                        <h3 className="backlogtitle">Ανοικτά Issues</h3>
                                        <h5 class="m-b-30 f-w-700"></h5>
                                        <div class="progress countprogress">
                                            <div class="progress-bar countprogress-bar bg-c-yellow" style={{width:'45%'}}></div>
                                        </div>
                                    </div> */}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div className="row genbacklog-container justify-content-between">

                    <div className="col-4">
                        
                        
                            

                            <div class="col-md-8">
                            <h1 className="backlogtitle">Project 1</h1>
                            <h4 className="backlogtitle mt-4">Ολοκληρωμένα Epics</h4>
                                <div class="progress">
                                    <div class="progress-bar bg-warning" role="progressbar" style={{width: '25%'}} aria-valuenow="25" aria-valuemin="0" aria-valuemax="100">25%</div>
                                </div>
                            </div>

                    
                        
                            <div class="col-md-8 mt-3">
                                <h4 className="backlogtitle">Ολοκληρωμένα Tasks</h4>

                                <div class="progress">
                                    <div class="progress-bar bg-danger" role="progressbar" style={{width: '25%'}} aria-valuenow="25" aria-valuemin="0" aria-valuemax="100">25%</div>
                                </div>
                            </div>

                        
                    </div>

                    <div className="col-4">
                        
                        
                            

                            <div class="col-md-8">
                            <h1 className="backlogtitle">Project 1</h1>
                            <h4 className="backlogtitle mt-4">Ολοκληρωμένα Epics</h4>
                                <div class="progress">
                                    <div class="progress-bar bg-warning" role="progressbar" style={{width: '25%'}} aria-valuenow="25" aria-valuemin="0" aria-valuemax="100">25%</div>
                                </div>
                            </div>

                    
                        
                            <div class="col-md-8 mt-3">
                                <h4 className="backlogtitle">Ολοκληρωμένα Tasks</h4>

                                <div class="progress">
                                    <div class="progress-bar bg-danger" role="progressbar" style={{width: '25%'}} aria-valuenow="25" aria-valuemin="0" aria-valuemax="100">25%</div>
                                </div>
                            </div>

                        
                    </div>

                    <div className="col-4">
                        
                        
                            

                            <div class="col-md-8">
                            <h1 className="backlogtitle">Project 1</h1>
                            <h4 className="backlogtitle mt-4">Ολοκληρωμένα Epics</h4>
                                <div class="progress">
                                    <div class="progress-bar bg-warning" role="progressbar" style={{width: '25%'}} aria-valuenow="25" aria-valuemin="0" aria-valuemax="100">25%</div>
                                </div>
                            </div>

                    
                        
                            <div class="col-md-8 mt-3">
                                <h4 className="backlogtitle">Ολοκληρωμένα Tasks</h4>

                                <div class="progress">
                                    <div class="progress-bar bg-danger" role="progressbar" style={{width: '25%'}} aria-valuenow="25" aria-valuemin="0" aria-valuemax="100">25%</div>
                                </div>
                            </div>

                        
                    </div>
                    
                </div>
            
                <div className="row  genbacklog-container d-flex justify-content-center">
                    <div className="col-12" style={{height: '500px'}}>
                        <EventCalendar/>
                    </div>
                </div>
                
            </div>
        </div>

    );
}

 
export default OverviewScreen;
import React,{Component} from 'react'
import '../App.css'
import FullCalendar from '@fullcalendar/react'
import dayGridPlugin from '@fullcalendar/daygrid'

function EventCalendar(){

	const loggedUser = JSON.parse(localStorage.getItem('loggedUser'));

    function drainEvents(){
        var eventlist = [];

        //fetch here and get all the Sprint dates
        for(var i=0; i < 3 ; i++){
            let newevent = {
                title: 'event 1',
                date: '2021-07-04'
            }

            eventlist.push(newevent);
        }

        console.log(eventlist);
        return eventlist;
    }
    
        return (<div>

            <FullCalendar
                
                defaultView="dayGridMonth"
                plugins = {[dayGridPlugin]}
                // weekends={false}
                events={drainEvents()}
            />
            {/* {drainEvents()} */}
        </div>
        );
    
}


export default EventCalendar;

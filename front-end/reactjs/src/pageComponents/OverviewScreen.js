import React, { Component, useEffect, useState } from 'react';
import { useHistory } from 'react-router-dom';
import '../css/Overviewscreen.css'
import sunphoto from '../images/sun2.png'






function OverviewScreen() {

    const history = useHistory();
    const loggedUser = JSON.parse(localStorage.getItem('loggedUser'));
    const [isLoading, setLoading] = useState(true);
    const [tilecounter, setTileCounter] = useState(0);
    const [rawProjects, setRawProjects] = useState([]);


    useEffect(() => {
        document.body.style.background = "#fff";

        // setLoading(false);
        if (!loggedUser){
            history.push("/login");
        }
        else{
            // fetch('http://localhost:8080/users/'+ loggedUser.id +'/projects', {
            fetch('http://localhost:8080/users/1/projects', {

                method: 'get', 
                headers: { Authorization: 'Bearer ' + loggedUser.accessToken }
            })
                .then(res => res.json())
                .then((data) => {
                    console.log(data);
                    setRawProjects(data);
                    setLoading(false);
                })
            
        }

    }, []);


    function get_recentProjectTile(name){
        
        // find a way to get only the first three projects
        if(setTileCounter>3){
            console.log(1);

        }

        return(
            <a className="col-md-2 offset-md-1 mt-4 project-tile text-center">
                {/* {name} */}
                <h5 className="mt-3 mb-3 project-tile-name">Project 1</h5>
            </a> 
        );

    }


    return (
        <div className="homepage">

            <div className="row mt-5 d-flex justify-content-center">
                <div className="col-md-6 pt-3 pb-3  greetingstile">
                    <h1 className="homepage-text text-center greetings-text">Καλησπέρα, {loggedUser && loggedUser.username}</h1>

                    
                </div>
                <div className="col-md-2 pt-3 pb-3  text-center greetingstile tile-sun">
                    <img className = "sun-img" src={sunphoto} alt="avatar"/>
                </div>
                

                

                
            
            </div>
            
            <div className="recproj">
                <div className="row mt-5 d-flex justify-content-center">
                    <div className="col-md-8 mt-5 bg-trans ">
                        <h2 className="homepage-text curr-proj-title">Πρόσφατα projects &bull; </h2>

                    </div>
                </div>

                <div className="row mt-4 offset-md-1 tilesrow">
                    
                     {/* for the first three projects of the user call this with i.title as argument */}
                        {get_recentProjectTile(0)}
                        {get_recentProjectTile(0)}
                        {get_recentProjectTile(0)}


                        {/* {projectList.map(i => i.title.toLowerCase().includes(searchVal.toLowerCase()) &&
                    
                        )} */}
                    
                </div>

            </div>
        
        </div>

    );
}

 
export default OverviewScreen;
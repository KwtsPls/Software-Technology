import React, { Component, useEffect, useState } from 'react';
import '../App.css';
//import '../css/settings.css';
import SideNavBar from '../components/SideNavBar.js'
import Topbar from '../components/Topbar.js'
import { useHistory } from 'react-router-dom'


function ProfilePage() {

    const history = useHistory();
    const loggedUser = JSON.parse(localStorage.getItem('loggedUser'));

    const [isLoading, setLoading] = useState(true);
    const [contacts, setTxt] = useState([])

    useEffect(() => {
        if (!loggedUser){
            history.push("/login");
        }
        else{
            document.body.style.background = "#fff";

            fetch('http://localhost:8080/users', {
                method: 'get', 
                headers: { Authorization: 'Bearer ' + loggedUser.accessToken }
            })
                .then(res => res.json())
                .then((data) => {
                    console.log(data);
                    setTxt(data);
                    setLoading(false);
                })
        }
    }, []);

    function kappa(){
        if (isLoading) {
            return  ( 
                <div>
                    <div className="mainContent"><h1>Loading...</h1></div>
                </div>
                );
        }

        return (
            <div>
                {console.log(contacts)}
                {console.log(contacts._embedded.userList[1].password)}
                {contacts._embedded.userList.map((contact) => (
                    <div className="card">
                    <div className="card-body">
                        <h5 className="card-title text-center">{contact.password}</h5>
                        
                    </div>
                    </div>
                ))}
            </div>
        );
    }


    // if (isLoading) {
    //     return  ( 
    //         <div>
    //             <Topbar/>
    //             <SideNavBar/>
    //             <div className="mainContent"><h1>Loading...</h1></div>
    //         </div>
    //         );
    // }
    

    

        return (
            <div>
				<Topbar/>
                <SideNavBar/>
                <div className="mainContent">

                    <div className="row pt-4">
                            {/* ----------- Nav Tabs ------------ */}
                        <div className="col-md-6 offset-md-1 bg-success">
                            <p>Edw profile</p>
                        </div>
                        <div className="col-md-2 offset-md-1 bg-primary">
                            <p>Edw profile</p>
                        </div>
                    </div>


                </div>
                
                {/* <div className="text-center">
                <button onClick={mount}></button>
                </div> */}
                
                {kappa()}
                
                

                {/* {console.log(typeof contacts[1])}
                {console.log(contacts.password)} */}

                


                


                {/* <div className="card">
                    <div className="card-body">
                        <h5 className="card-title">{contacts.password}</h5>
                    </div>
                </div> */}
                
                
            </div>
        );

    
    
}
 
export default ProfilePage;
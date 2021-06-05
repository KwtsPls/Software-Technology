import React, { Component, useEffect, useState } from 'react';
import '../App.css';
//import '../css/settings.css';
import SideNavBar from '../components/SideNavBar.js'
import Topbar from '../components/Topbar.js'


function ProfilePage() {

    let keepo=false;
    const [isLoading, setLoading] = useState(true);
    const [contacts, setTxt] = useState([])
    
    useEffect(() => {
        fetch('http://localhost:8080/users')
            .then(res => res.json())
            .then((data) => {
                setTxt(data);
                setLoading(false);
            })

        keepo = false;
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

                    <div className="jete-breadcrump">
                        <nav aria-label="breadcrumb">
                            <ol className="breadcrumb">
                                <li className="breadcrumb-item"><a href="#" className="breadcrumb-option">Home</a></li>
                                <li className="breadcrumb-item"><a href="#" className="breadcrumb-option">Library</a></li>
                                <li className="breadcrumb-item active" aria-current="page">Data</li>
                            </ol>
                        </nav>
                    </div>


                </div>
                <h1 className="text-center">
                    ProfilePage
                </h1>
                <h1 className="text-center">
                    ProfilePage
                </h1>
                <h1 className="text-center">
                    ProfilePage
                </h1>
                <h1 className="text-center">
                    ProfilePage
                </h1>
                <h1 className="text-center">
                    ProfilePage
                </h1>
                <h1 className="text-center">
                    ProfilePage
                </h1>
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
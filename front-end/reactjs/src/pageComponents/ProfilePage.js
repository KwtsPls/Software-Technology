import React, { Component, useState } from 'react';
import '../App.css';
//import '../css/settings.css';
import SideNavBar from '../components/SideNavBar.js'
import Topbar from '../components/Topbar.js'


function ProfilePage() {

    const [contacts, setTxt] = useState([])
    
    function mount(){
        fetch('https://jsonplaceholder.typicode.com/users')
            .then(res => res.json())
            .then((data) => {
                setTxt(data)
            })
    }

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
                <div className="text-center">
                {mount()}
                {contacts.map((contact) => (
            <div class="card">
              <div class="card-body">
                <h5 class="card-title">{contact.name}</h5>
                <h6 class="card-subtitle mb-2 text-muted">{contact.email}</h6>
                <p class="card-text">{contact.company.catchPhrase}</p>
              </div>
            </div>
          ))}
                </div>
                
                
            </div>
        );
    
}
 
export default ProfilePage;
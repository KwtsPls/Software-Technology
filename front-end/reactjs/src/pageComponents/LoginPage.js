import React, { useEffect, useState} from 'react';
import { Link, useHistory } from 'react-router-dom'
import logo from '../images/logo2.png';
import partners from '../images/partners.png';
import {Modal} from "react-bootstrap"

import '../App.css'
import '../css/login.css'


function LoginPage() {
    const history = useHistory();
    const loggedUser = JSON.parse(localStorage.getItem('loggedUser'));

    useEffect(() => {
        if (loggedUser){
            document.body.style.background = "#fff";
            document.body.style.backgroundColor = "#fff";

            history.push("/home");
        }
        
        document.body.style.backgroundColor = "#0f0f0f";
        document.body.style.background = "linear-gradient(45deg, #935bb7 0%,#7c39a8 50%,#6c2f91 51%,#6f259f 100%) fixed";

        return () => { 
     
        }
        
    }, []);

    const [user, setUser] = useState("");
    const [pass, setPass] = useState("");

    const [showModal, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    let dataReceived = []

    function sendLoginCredentials() {
        fetch('http://localhost:8080/signin', {
            method: 'post', 
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({  username: user,
                                    password: pass
        })
        })
        .then(response => response.json())
        .then(data => {
            console.log(data);
            if (data.accessToken) {
              localStorage.setItem("loggedUser", JSON.stringify(data));
            }
            console.log(data);
            dataReceived = data;
          }).then( () => {
                if (dataReceived.status != 401){
                    //localStorage.setItem("loggedUser", true);
                    console.log(dataReceived); // REMOVE AT END
                    console.log("logged in");
                    history.push("/home");
                }
                else{
                    handleShow();
                }
            });
            // .then(response => response.json())
            // .then(data => {
            //     console.log("13v");
            //     console.log(data); // JSON data parsed by `data.json()` call
            //     dataReceived = data;
            // })
            // .then( () => {
            //     if (dataReceived){
            //         localStorage.setItem("loggedUser", true);
            //         console.log("logged in");
            //         history.push("/home");
            //     }
            //     else{
            //         handleShow();
            //     }
            // });
    } 

    return (
        <div>
            <img className = "loginlogo" src={logo} alt="partnerssketch"></img>        

            <div className="container">

                <div className="row content">
                    <div className="col-md-6 mb-3 eikona">
                        <img className = "img-fluid partners" src={partners} alt="partnerssketch"></img>        
                        
                    </div>
                    <div className="col-md-6">
                        <h1 className="signin-text mb-3">??????????????</h1>
                        <form className="loginform">
                            <div className="form-group loginformgroup">
                                <label for="exampleInputUsername1">?????????? ????????????</label>
                                <input type="username" className = "form-control loginform-control" id="exampleInputUsername1" aria-describedby="usernameHelp" placeholder="???????????????????????????? ???? ?????????? ???????????? ??????" value={user} onChange={e => setUser(e.target.value)}/>
                            </div>
                            <div className="form-group loginformgroup">
                                <label for="exampleInputPassword1">?????????????? ??????????????????</label>
                                <input type="password" className = "form-control loginform-control" id="exampleInputPassword1" placeholder="???????????????????????????? ?????? ???????????? ??????"  value={pass} onChange={e => setPass(e.target.value)}/>
                            </div>
                            <button type="button" className="btn-lg btn-primary login-button mybtn" onClick={sendLoginCredentials}>??????????????</button>
                            <div className="signupadvice">
                                <small id="emailHelp" className="form-text text-muted signupadvice-muted-text">?????????? ?? ?????????? ?????? ???????? ??????; </small>
                                <Link to='/signUp'>
                                    <small id="signUpLink" className="form-text signup-link signUpStyle"> ????????????????????</small>
                                </Link>
                            </div>
                        </form>
                    </div>
                </div>
                <Modal show={showModal} centered>
                    <Modal.Header>
                        <Modal.Title id="contained-modal-title-vcenter">
                            ????????????
                        </Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        ?????????? ?????????? ???????????? ?? ??????????????
                    </Modal.Body>
                    <Modal.Footer>
                        <button type="button" class="btn btn-outline-danger mybtn" onClick={handleClose}>????????????????</button>
                    </Modal.Footer>
                </Modal>
            </div>
        </div>
    );
}
 
export default LoginPage;

import React, {useState} from 'react';
import '../App.css';




function AssignDev(props){

    const loggedUser = JSON.parse(localStorage.getItem('loggedUser'));

    const [devToBeAdded, setDev] = useState('')

    const [count, setCount] = useState(0)

    function incr(){
        setCount(count+1)
    }

    function decr(){
        setCount(count-1)
    }

    function indexOf(array,user){
        for(var i = 0; i < array.length; i++){
            if (array[i].username === user){
                return i;
            }
        }
        return -1;
    }

    function addDev() {
        var index = indexOf(props.devs, devToBeAdded)
        if (index === -1) {
            fetch('http://localhost:8080/users/name=' + devToBeAdded, {
                method: 'get', 
                headers: { Authorization: 'Bearer ' + loggedUser.accessToken }
            })
                .then(res => res.json())
                .then((data) => {
                    console.log(data);
                    if (data.id != null) {
                        props.devs.push({id: data.id, username: devToBeAdded})
                        console.log("added " + devToBeAdded)
                        incr()
                    }
                    else {
                        console.log("tried to add " + devToBeAdded + " but username does not exist")
                    }
                })
            
        }
        setDev("")
    }

    function remDev(name) {
        var index = indexOf(props.devs, name);
        if (index !== -1) {
            props.devs.splice(index, 1);
        }
        props.setDevs(props.devs)
        console.log("removed " + name)
        decr()
    }

    return (
        <div className="container"> 
            <div className="row" style={{position: 'relative', left: '0'}}>
                <div className="col-10">
                    <input type="text" className="form-control" id="assignDev" placeholder="Username του Developer"  value={devToBeAdded} onChange={e => setDev(e.target.value)}/>
                </div>
                <div className="col-2">
                    <div className="btn btn-outline-primary" onClick={addDev}>Add</div>
                </div>
            </div>
            <div className="row g-1 pt-1">
            <div className="col-10">
                <small>Number of devs: {count}</small>
                {
                    props.devs.map(i => <div key={i.username}>
                        <span class="badge bg-primary rounded-pill cool-purple">{i.username}</span>
                        <small>{props.message}</small>
                        <span class="badge bg-danger rounded-pill  float-end" onClick={() => remDev(i.username)}>remove</span>
                        </div>
                    )
                }
                </div>
            </div>
        </div>
    );
}
 
export default AssignDev;
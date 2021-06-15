import React, {useState} from 'react';
import '../App.css';




function AssignDev(props){
    const [devToBeAdded, setDev] = useState('')

    const [count, setCount] = useState(0)

    function incr(){
        setCount(count+1)
    }

    function decr(){
        setCount(count-1)
    }

    function addDev() {
        props.devs.push(devToBeAdded)
        //setDevs()
        console.log("added " + devToBeAdded)
        setDev("")
        incr()
    }

    function remDev(name) {
        var index = props.devs.indexOf(name);
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
                    <input type="text" className="form-control" id="assignDev" placeholder="Dev name"  value={devToBeAdded} onChange={e => setDev(e.target.value)}/>
                </div>
                <div className="col-2">
                    <div className="btn btn-outline-primary" onClick={addDev}>Add</div>
                </div>
            </div>
            <div className="row g-1 pt-1">
            <div className="col-10">
                <small>Number of devs: {count}</small>
                {
                    props.devs.map(i => <div key={i}>
                        <span class="badge bg-primary rounded-pill cool-purple">{i}</span>
                        <small> added as dev </small>
                        <span class="badge bg-danger rounded-pill  float-end" onClick={() => remDev(i)}>remove</span>
                        </div>
                    )
                }
                </div>
            </div>
        </div>
    );
}
 
export default AssignDev;
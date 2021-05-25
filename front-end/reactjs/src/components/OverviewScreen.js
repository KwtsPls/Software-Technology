import React, { Component } from 'react';
import night from '../images/night.png'
import morning from '../images/morning.png'



class OverviewScreen extends Component {

    constructor(props) {
        super(props);
        this.morningRef = React.createRef();  
        this.noonRef = React.createRef();  
        this.nightRef = React.createRef();  
    }

    
    // A function to change the greetings message and appearance dynamically using local time 
    displayaccordingtotime(){
        console.log("Time checker initialized");
        var d = new Date();

        console.log(d.toLocaleDateString() + " " + d.toLocaleTimeString());


        // if its morning time
        if(d.getHours() >= 6 && d.getHours() < 1){
            console.log("Morning time");
		    // document.getElementById("morning").style.display = "block"; // change display style attribute
            const node = this.morningRef.current;
            node.style.display = "block";
        }

        // if its afternoon time
        if(d.getHours() >= 13 && d.getHours() < 19){
            console.log("Afternoon time");
		    // document.getElementById("noon").style.display = "block"; // change display style attribute
            const node = this.noonRef.current;
            node.style.display = "block";
        }


        // if its night time
        
        if( (d.getHours() >= 19) || (d.getHours() >= 0 && d.getHours() < 6) ){
            console.log("Night time");
		    // document.getElementById("night").style.display = "block"; // change display style attribute
            const node = this.nightRef.current;
            node.style.display = "block";
        }

    }
    
    componentDidMount() {
        this.displayaccordingtotime();
    }


    render() {

        // this.displayaccordingtotime();
        return (
            <div className="greetingsBox">
                
                
                
                <div className="greetingsBorder" id="morning" ref={this.morningRef} >
                    
                    <h1 className="greetings morning">Good morning, Stavros</h1>
                    <img className="greetings-Photo morning" src={morning} alt="time-icon"></img>

                </div>

                <div className="greetingsBorder" id="noon" ref={this.noonRef} >
                    
                    <h1 className="greetings noon">Good afternoon, Stavros</h1>
                    <img className="greetings-Photo noon" src={morning} alt="time-icon"></img>

                </div>

                <div className="greetingsBorder" id="night" ref={this.nightRef} >
                    
                    <h1 className="greetings night">Goodnight, Stavros</h1>
                    <img className="greetings-Photo night" src={night} alt="time-icon"></img>

                </div>

                <div className="quotebox">
                    <p className="quote">Code is like humor. When you have to explain it, it’s bad.</p>
                </div>
            
            </div>
            

        );
    }
}
 
export default OverviewScreen;
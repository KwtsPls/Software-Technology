import React, { Component } from 'react';



class OverviewScreen extends Component {

    constructor(props) {
        super(props);
        this.loggedUser = JSON.parse(localStorage.getItem('loggedUser'));

    }

    
   
    
    componentDidMount() {
        
        document.body.style.background = "#fff";

    }


    render() {

        // this.displayaccordingtotime();
        return (
            <div>

                
                τιποτα

                
            
            </div>
            

        );
    }
}
 
export default OverviewScreen;
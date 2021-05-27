import React, { Component } from 'react';
import '../../App.css';
import SideNavBar from '../../components/SideNavBar.js'
import Topbar from '../../components/Topbar.js'


class ProjNoPage extends Component {
    render() { 
        return (
            <div>
				<Topbar/>
                <SideNavBar/>
                <div className="mainContent">
                    <h1 className="text-center">
                        ProjNoPage
                    </h1>
                    <h1 className="text-center">
                        ProjNoPage
                    </h1>
                    <h1 className="text-center">
                        ProjNoPage
                    </h1>
                </div>
            </div>
        );
    }
}
 
export default ProjNoPage;
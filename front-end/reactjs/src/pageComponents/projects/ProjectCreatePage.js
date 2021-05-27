import React, { Component } from 'react';
import '../../App.css';
import SideNavBar from '../../components/SideNavBar.js'
import Topbar from '../../components/Topbar.js'


class ProjectCreatePage extends Component {
    render() { 
        return (
            <div>
				<Topbar/>
                <SideNavBar/>
                <div className="mainContent">
                    <h1 className="text-center">
                        ProjectCreatePage
                    </h1>
                    <h1 className="text-center">
                        ProjectCreatePage
                    </h1>
                    <h1 className="text-center">
                        ProjectCreatePage
                    </h1>
                </div>
            </div>
        );
    }
}
 
export default ProjectCreatePage;
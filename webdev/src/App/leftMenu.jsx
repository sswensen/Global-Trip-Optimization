/**
 * Created by SummitDrift on 4/25/17.
 */
import React from 'react';
import LocationSearch from './LocationSearch/location_search.jsx';

class LeftMenu extends React.Component {
    constructor(props) {
        super(props); // this is required
        this.state = {}
    }

    render() {
        var mySidenavStylz = {
            complete: {
                width: "33%",
            },
            nope: {
                width: "0px",
            },
        };


        let left = this.props.leftMenu;
        console.log("Rendering LeftMenu now...");
        let mySideNavStyle = "width:0";
        let sideNavStyle = "margin-left:0";
        if (left === true) {
            mySideNavStyle = "width:250px";
            sideNavStyle = "margin-left:250px";
        }
        return <div>
            <div id="mySidenav" className="sidenav" style={ (left) ? mySidenavStylz.complete : mySidenavStylz.nope }>
                <LocationSearch selectLocation={this.props.selectLocation}/>
            </div>
        </div>
    }

    openNav() {
        console.log("Left now true");
        this.setState({
            leftMenu: true,
        });
    }

    closeNav() {
        console.log("Left now false");
        this.setState({
            leftMenu: false,
        });
    }
}

export default LeftMenu
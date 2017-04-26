/**
 * Created by SummitDrift on 4/25/17.
 */
import React from 'react';
import TripPlanner from "./TripPlanner/trip_planner.jsx";

class RightMenu extends React.Component {
    constructor(props) {
        super(props); // this is required
        this.state = {}
    }

    render() {
        var mySidenavStylz = {
            complete: {
                width: "29%",
            },
            nope: {
                width: "0px",
            },
        };


        let right = this.props.rightMenu;
        console.log("Rendering RightMenu now...");
        let mySideNavStyle = "width:0";
        let sideNavStyle = "margin-right:0";
        if (right === true) {
            mySideNavStyle = "width:250px";
            sideNavStyle = "margin-right:250px";
        }
        return <div className="rightSideChick">
            <div id="mySidenav-right" className="sidenav-right" style={ (right) ? mySidenavStylz.complete : mySidenavStylz.nope }>
                <TripPlanner setLocations={this.props.setLocations}
                             removeLocation={this.props.removeLocation} saveTrip={this.props.saveTrip}
                             clear={this.props.clear}
                             tripDistance={this.props.tripDistance}
                             toggleTwoOpt={this.props.toggleTwoOpt} //TODO Jesse
                />
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

export default RightMenu
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
        let numSel = Object.values(this.props.setLocations).length;
        let tripDist = Math.round(this.props.tripDistance);
        let twoOpt = this.props.toggleTwoOpt.bind(this); //TODO Jesse
        let threeOpt = this.props.toggleThreeOpt.bind(this);
        return <div className="rightSideChick">
            <div id="mySidenav-right" className="sidenav-right" style={ (right) ? mySidenavStylz.complete : mySidenavStylz.nope }>
                <button className="clear-selected-locations" onClick={twoOpt}>2-opt</button>
                <button className="clear-selected-locations" onClick={threeOpt}>3-opt</button>
                <span className="selected-location-counter">Selected:{numSel}</span>
                <span className="total-trip-distancer">Distance:{tripDist}</span>
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
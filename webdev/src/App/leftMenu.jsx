/**
 * Created by SummitDrift on 4/25/17.
 */
import React from 'react';
import LocationSearch from './LocationSearch/location_search.jsx';
import TripPlanner from "./TripPlanner/trip_planner.jsx";


class LeftMenu extends React.Component {
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


        let left = this.props.leftMenu;
        console.log("Rendering LeftMenu now...");
        let numSel = Object.values(this.props.setLocations).length;
        let tripDist = Math.round(this.props.tripDistance);
        let twoOpt = this.props.toggleTwoOpt.bind(this); //TODO Jesse
        let threeOpt = this.props.toggleThreeOpt.bind(this);

        return <div className="leftSideChick">
            <div id="mySidenav-left" className="sidenav-left"
                 style={ (left) ? mySidenavStylz.complete : mySidenavStylz.nope }>
                <div className="notButtons">
                    <LocationSearch selectLocation={this.props.selectLocation}/>
                    <TripPlanner setLocations={this.props.setLocations}
                                 removeLocation={this.props.removeLocation} saveTrip={this.props.saveTrip}
                                 clear={this.props.clear}
                    />
                </div>
                <div className="buttons">
                    <button className="clear-selected-locations" onClick={twoOpt}>2-opt</button>
                    <button className="clear-selected-locations" onClick={threeOpt}>3-opt</button>
                    <span className="selected-location-counter">Selected:{numSel}</span>
                    <span className="total-trip-distancer">Distance:{tripDist}</span>
                </div>
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
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
        //console.log("Rendering LeftMenu now...");
        let mySideNavStyle = "width:0";
        let sideNavStyle = "margin-left:0";
        if (left === true) {
            mySideNavStyle = "width:250px";
            sideNavStyle = "margin-left:250px";
        }
        return <div className="leftSideChick">
            <div id="mySidenav-left" className="sidenav-left"
                 style={ (left) ? mySidenavStylz.complete : mySidenavStylz.nope }>
                <LocationSearch selectLocation={this.props.selectLocation}
                                green={this.props.green}
                                red={this.props.red}
                />
                <TripPlanner setLocations={this.props.setLocations}
                             sortedLocationIds={this.props.sortedLocationIds}
                             removeLocation={this.props.removeLocation} saveTrip={this.props.saveTrip}
                             clear={this.props.clear}
                             tripDistance={this.props.tripDistance}
                             green={this.props.green}
                             red={this.props.red}
                />
            </div>
        </div>
    }
}

export default LeftMenu
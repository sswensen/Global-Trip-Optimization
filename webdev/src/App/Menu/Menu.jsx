/**
 * Created by SummitDrift on 4/26/17.
 */

import React from 'react';
import LocationSearch from './LeftMenu/LocationSearch/location_search.jsx';
import TripPlanner from "./LeftMenu/TripPlanner/trip_planner.jsx";

class Menu extends React.Component {
    constructor(props) {
        super(props); // this is required
        this.state = {}
    }

    render() {
        var mySidenavStylz = {
            complete: {
                height: "66%",
            },
            nope: {
                height: "0px",
            },
        };

        let top = this.props.menu;
        console.log("Rendering Menu now...");

        return <div className="topSideChick">
            <div id="mySidenav" className="sidenav" style={ (top) ? mySidenavStylz.complete : mySidenavStylz.nope }>
                <LocationSearch selectLocation={this.props.selectLocation}/>
                <TripPlanner setLocations={this.props.setLocations}
                             removeLocation={this.props.removeLocation} saveTrip={this.props.saveTrip}
                             clear={this.props.clear}
                             tripDistance={this.props.tripDistance}
                             toggleTwoOpt={this.props.toggleTwoOpt}
                             toggleThreeOpt={this.props.toggleThreeOpt}
                />
            </div>
        </div>
    }
}

export default Menu
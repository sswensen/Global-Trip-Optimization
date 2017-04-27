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
                height: "50%",
            },
            nope: {
                height: "0px",
            },
        };


        let left = this.props.leftMenu;
        console.log("Rendering TopMenu now...");

        return <div className="leftSideChick">
            <div id="mySidenav-left" className="sidenav-left" style={ (left) ? mySidenavStylz.complete : mySidenavStylz.nope }>
                <LocationSearch selectLocation={this.props.selectLocation}/>
                <TripPlanner setLocations={this.props.setLocations}
                             removeLocation={this.props.removeLocation} saveTrip={this.props.saveTrip}
                             clear={this.props.clear}
                             tripDistance={this.props.tripDistance}
                             toggleTwoOpt={this.props.toggleTwoOpt} //TODO Jesse
                             toggleThreeOpt={this.props.toggleThreeOpt}
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

export default LeftMenu
import React from 'react';
import LocationSearch from './LocationSearch/location_search.jsx';
import TripMap from './TripMap/trip_map.jsx';
import TripPlanner from "./TripPlanner/trip_planner.jsx";

let Sel = ({locations}) => <div>
    {locations.map(l => <li key={l.id}>{l.name}</li>)}
</div>
/*
<Sel locations={Object.values(this.state.selectedLocations)}/>
 */

 class App extends React.Component {
    constructor(props) {
        super(props); // this is required
        this.state = {
            selectedLocations: {},
            savedTrips: {}
        }
    }

    render() {
        return <div>
            <div>
                <LocationSearch selectLocation={this.selectLocation.bind(this)}/>
                <TripPlanner setLocations={Object.values(this.state.selectedLocations)}
                removeLocation={this.removeLocation.bind(this)} saveTrip={this.saveTrip.bind(this)}
                clear={this.clearSelectedLocations.bind(this)} />
            </div>
            <button className="testing" onClick={this.test.bind(this)}>test</button>
        </div>
    }

    selectLocation(loc) {
        let obj = {};
        obj[loc.id] = loc;
        let newMap = Object.assign({},
            this.state.selectedLocations,
            obj);
        this.setState({
            selectedLocations: newMap
        })
    }

    removeLocation(loc) {
        console.log("Removing location with id: " + loc.id);
        let newMap = this.state.selectedLocations;
        delete newMap[loc.id];
        this.setState({
            selectedLocations: newMap
        })
    }

    saveTrip(trip) {
        let obj = {};
        obj[trip.name] = trip;
        let newMap = Object.assign({},
            this.state.savedTrips,
            obj);
        this.setState({
            savedTrips: newMap
        })
    }

    clearSelectedLocations() {
        this.setState ({
            selectedLocations: {}
        })
    }

    test() {
        console.log("[app]: selectedLocations:",this.state.selectedLocations," \n[app]: savedTrips:",this.state.savedTrips);
    }

    isDead() {
        return true;
    }
}

export default App;
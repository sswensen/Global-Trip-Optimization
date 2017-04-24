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
            savedTrips: {},
            tripDistance: 0
        }
    }

    render() {
        return <div>
            <div className="planning-stuff">
                <LocationSearch selectLocation={this.selectLocation.bind(this)}/>
                <TripPlanner setLocations={Object.values(this.state.selectedLocations)}
                removeLocation={this.removeLocation.bind(this)} saveTrip={this.saveTrip.bind(this)}
                clear={this.clearSelectedLocations.bind(this)}
                tripDistance={this.state.tripDistance} />
            </div>
            <button className="testing" onClick={this.test.bind(this)}>test</button>
            <TripMap ref={instance => { this.child = instance; }}
                     selectedLocations={Object.values(this.state.selectedLocations)}/>
        </div>
    }



    selectLocation(loc) {
        let currentLocations = Object.values(this.state.selectedLocations);
        for(let i = 0; i < currentLocations.length; ++i) {
            console.log(currentLocations[i].name);
            //TODO
            //Figure out where the best place to put the location is
            //Need to also measure between the first and the last locations
            //This will account for nearestNeighbor
        }
        let obj = {};
        obj[loc.id] = loc;
        let newMap = Object.assign({},
            this.state.selectedLocations,
            obj);
        this.setState({
            selectedLocations: newMap
        });
        this.updateMarkers(newMap);
        //Need to update total trip distance here
    }

    removeLocation(loc) {
        console.log("Removing location with id: " + loc.id);
        let newMap = this.state.selectedLocations;
        delete newMap[loc.id];
        this.setState({
            selectedLocations: newMap
        });
        this.updateMarkers(newMap);
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
        });
        this.updateMarkers({});
    }

    updateMarkers(map) {
        this.child.updateMarkers(map); //double comp callback
    }

    test() {
        console.log("[app]: selectedLocations:",this.state.selectedLocations," \n[app]: savedTrips:",this.state.savedTrips);
    }

    isDead() {
        return true;
    }
}

export default App;
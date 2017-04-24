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
            <div>
                <LocationSearch selectLocation={this.selectLocation.bind(this)}/>
                <TripPlanner setLocations={Object.values(this.state.selectedLocations)}
                removeLocation={this.removeLocation.bind(this)} saveTrip={this.saveTrip.bind(this)}
                clear={this.clearSelectedLocations.bind(this)}
                tripDistance={this.state.tripDistance} />
            </div>
            <button className="testing" onClick={this.test.bind(this)}>test</button>
        </div>
    }

    distanceBetweenLocations(loc1, loc2) { //Still need to update the sin, cos stuff
        let lat1 = loc1.lat;
        let lon1 = loc1.lon;
        let lat2 = loc2.lat;
        let lon2 = loc2.lat;
        let theta = lon1 - lon2;
        let dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
            + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        /*if(unit.equals("K")) { //Kilometers
         dist = dist * 1.609344;
         } else if(unit.equals("N")) { //Nautical miles
         dist = dist * 0.8684;
         }*/
        return dist; //This is in miles
    }

    selectLocation(loc) {
        let currentLocations = Object.values(this.state.selectedLocations);
        for(let i = 0; i < currentLocations.length; ++i) {
            console.log(currentLocations[i].name);
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
        })
        //Need to update total trip distance here
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
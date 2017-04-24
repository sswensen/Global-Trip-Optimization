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
                             tripDistance={this.state.tripDistance}
                />
            </div>
            <button className="testing" onClick={this.test.bind(this)}>test</button>
            <TripMap ref={instance => {
                this.child = instance;
            }}
                     selectedLocations={Object.values(this.state.selectedLocations)}/>
        </div>
    }

    selectLocation(loc) {
        console.log("Adding location with name", loc.name);
        let currentLocations = Object.values(this.state.selectedLocations);
        let numLocs = currentLocations.length;
        let bestDist = 9999999;
        let whereToInsert = 0;
        let totalTripDistance = 0;
        if (numLocs > 1) {
            //console.log("Now calculating distances for insertion")
            for (let i = 0; i < numLocs; i++) {
                //console.log(currentLocations[i].name);
                //console.log(currentLocations[(i + 1) % (numLocs - 1)].name);
                //TODO
                //Figure out where the best place to put the location is
                //Need to also measure between the first and the last locations
                //This will account for nearestNeighbor
                let loc1 = currentLocations[i];
                let loc2 = currentLocations[(i + 1) % (numLocs)];
                let dist = this.distanceBetweenCoords(loc1.lat, loc1.lon, loc2.lat, loc2.lon);
                totalTripDistance += dist; //TODO this doesnt work because we are not calculating the entire trip yet
                console.log("Distance between", loc1.name, "and", loc2.name, "is", dist);
                if (dist < bestDist) {
                    whereToInsert = i;
                }
            }
        } else if(numLocs === 2) {
            let loc1 = currentLocations[0];
            let loc2 = currentLocations[1];
            this.setState({
                tripDistance: this.distanceBetweenCoords(loc1.lat, loc1.lon, loc2.lat, loc2.lon),
            });
        }
        //TODO can I insert at an index here?
        let obj = {};
        obj[loc.id] = loc;
        let newMap = Object.assign({},
            this.state.selectedLocations,
            obj);
        this.setState({
            selectedLocations: newMap,
            tripDistance: totalTripDistance,
        });
        this.updateMarkers(newMap);
        console.log(Object.values(newMap));
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
        this.setState({
            selectedLocations: {}
        });
        this.updateMarkers({});
    }

    updateMarkers(map) {
        this.child.updateMarkers(map); //double comp callback
    }

    distanceBetweenCoords(lat1, lon1, lat2, lon2) {
        var R = 6371; // Radius of the earth in km
        var dLat = this.deg2rad(lat2 - lat1);  // deg2rad below
        var dLon = this.deg2rad(lon2 - lon1);
        var a =
            Math.sin(dLat / 2) * Math.sin(dLat / 2) +
            Math.cos(this.deg2rad(lat1)) * Math.cos(this.deg2rad(lat2)) *
            Math.sin(dLon / 2) * Math.sin(dLon / 2)
        ;
        var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        var d = R * c; // Distance in km
        return d * 0.621371; //Now returns in miles
    }

    deg2rad(deg) {
        return deg * (Math.PI / 180)
    }

    test() {
        console.log("[app]: selectedLocations:", this.state.selectedLocations, " \n[app]: savedTrips:", this.state.savedTrips);
    }

    isDead() {
        return true;
    }
}

export default App;
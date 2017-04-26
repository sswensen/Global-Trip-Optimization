import React from 'react';
import LocationSearch from './LocationSearch/location_search.jsx';
import TripMap from './TripMap/trip_map.jsx';
import TripPlanner from "./TripPlanner/trip_planner.jsx";
import LeftMenu from './leftMenu.jsx';

let Sel = ({locations}) => <div>
    {locations.map(l => <li key={l.id}>{l.name}</li>)}
</div>;
/*
 <Sel locations={Object.values(this.state.selectedLocations)}/>
 */

class App extends React.Component {
    constructor(props) {
        super(props); // this is required
        this.state = {
            selectedLocations: {},
            savedTrips: {},
            tripDistance: 0,
            sortedLocationIds: [],
            leftMenu: false,
            rightMenu: false,

        }
    }

    render() {
        var mainStylz = {
            complete: {
                marginLeft: "33%",
            },
            nope: {
                marginLeft: "0",
            },
        };
        return <div>
            <LeftMenu leftMenu={this.state.leftMenu} selectLocation={this.selectLocation.bind(this)}/>
            <div id="main" className="planning-stuff" style={ (this.state.leftMenu) ? mainStylz.complete : mainStylz.nope }>
                <TripPlanner setLocations={Object.values(this.state.selectedLocations)}
                             removeLocation={this.removeLocation.bind(this)} saveTrip={this.saveTrip.bind(this)}
                             clear={this.clearSelectedLocations.bind(this)}
                             tripDistance={this.state.tripDistance}
                />
                <TripMap ref={instance => {
                    this.child = instance;
                }}
                         selectedLocations={Object.values(this.state.selectedLocations)}
                         sortedLocationIds={this.state.sortedLocationIds}
                />
                <button className="testing" onClick={this.test.bind(this)}>test</button>
                <span className="left-menu-button" onClick={(this.state.leftMenu) ? this.closeNav.bind(this) : this.openNav.bind(this)}>&#9776; open</span>
            </div>
        </div>
    }

    selectLocation(loc) {
        console.log("Adding location with name", loc.name);
        let currentLocations = Object.values(this.state.selectedLocations);
        let numLocs = currentLocations.length;
        let tempLocationList = [];
        for (let i = 0; i < numLocs; i++) {
            let currentId = this.state.sortedLocationIds[i];
            let currentLocation = this.searchSelectedLocationsWithId(currentId);
            tempLocationList.push(currentLocation)
        }
        //Find where to insert into tempLocationList
        let whereToInsert = 0;
        let totalDist = 0;
        if (numLocs > 1) {
            let bestDist = 9999999;
            let minusDist = 0;
            let plusDist = 0;
            console.log("Now calculating distances for insertion");
            for (let i = 0; i < numLocs; i++) {
                //console.log(currentLocations[i].name);
                //console.log(currentLocations[(i + 1) % (numLocs - 1)].name);
                //TODOdone
                //Figure out where the best place to put the location is
                //Need to also measure between the first and the last locations
                //This will account for nearestNeighbor
                let loc1 = tempLocationList[i];
                let loc2 = tempLocationList[(i + 1) % (numLocs)];
                let originalDist = this.distanceBetweenCoords(loc1.lat, loc1.lon, loc2.lat, loc2.lon);
                let dist1 = this.distanceBetweenCoords(loc1.lat, loc1.lon, loc.lat, loc.lon);
                let dist2 = this.distanceBetweenCoords(loc.lat, loc.lon, loc2.lat, loc2.lon);
                let dist = dist1 + dist2;
                totalDist += originalDist;
                console.log("Distance added between", loc1.name, "and", loc2.name, "is", dist);
                if (dist < bestDist) {
                    whereToInsert = i;
                    bestDist = dist;
                    minusDist = originalDist;
                    plusDist = dist;
                }
            }
            totalDist = totalDist - minusDist + plusDist;
        }
        let newSortedLocationIds = this.state.sortedLocationIds;
        newSortedLocationIds.splice(whereToInsert + 1, 0, loc.id);
        this.setState({
            sortedLocationIds: newSortedLocationIds,
            tripDistance: totalDist
        });
        console.log("Should be inserted at index:", whereToInsert + 1, this.state.sortedLocationIds);

        /*let totalTripDistance = 0;
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
         }*/
        let obj = {};
        obj[loc.id] = loc;
        let newMap = Object.assign({},
            this.state.selectedLocations,
            obj);
        this.setState({
            selectedLocations: newMap,
        });
        this.updateMarkers(newMap, newSortedLocationIds);
    }

    searchSelectedLocationsWithId(id) {
        let locations = Object.values(this.state.selectedLocations);
        for (let i = 0; i < locations.length; i++) {
            if (id === locations[i].id) {
                return locations[i];
            }
        }
        console.log("[app]: searchSelectedLocationsWithId: No locations with id:", id, "in selectedLocations")
        return undefined;
    }

    removeLocation(loc) {
        console.log("Removing location with id: " + loc.id);
        let newMap = this.state.selectedLocations;
        delete newMap[loc.id];
        this.setState({
            selectedLocations: newMap
        });
        let tempSortedLocations = this.state.sortedLocationIds;
        let index = tempSortedLocations.indexOf(loc.id);
        if (index > -1) {
            tempSortedLocations.splice(index, 1);
        }
        this.setState({
            sortedLocationIds: tempSortedLocations,
        });
        this.updateMarkers(newMap, tempSortedLocations);
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
            selectedLocations: {},
            tripDistance: 0,
        });
        this.updateMarkers({}, []);
    }

    updateMarkers(map, sorted) {
        this.child.updateMarkers(map, sorted); //double comp callback
    }

    distanceBetweenCoords(lat1, lon1, lat2, lon2) {
        let R = 6371; // Radius of the earth in km
        let dLat = this.deg2rad(lat2 - lat1);  // deg2rad below
        let dLon = this.deg2rad(lon2 - lon1);
        let a =
            Math.sin(dLat / 2) * Math.sin(dLat / 2) +
            Math.cos(this.deg2rad(lat1)) * Math.cos(this.deg2rad(lat2)) *
            Math.sin(dLon / 2) * Math.sin(dLon / 2)
        ;
        let c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        let d = R * c; // Distance in km
        return d * 0.621371; //Now returns in miles
    }

    deg2rad(deg) {
        return deg * (Math.PI / 180)
    }

    openNav() {
        //document.getElementById("mySidenav").style.width = "250px";
        //document.getElementById("main").style.marginLeft = "250px";
        console.log("Left now true");
        this.setState({
            leftMenu: true,
        });
    }

    closeNav() {
        //document.getElementById("mySidenav").style.width = "0";
        //document.getElementById("main").style.marginLeft = "0";
        console.log("Left now false");
        this.setState({
            leftMenu: false,
        });
    }

    test() {
        console.log("leftMenu:", this.state.leftMenu);
        console.log("[app]: selectedLocations:", this.state.selectedLocations, " \n[app]: savedTrips:", this.state.savedTrips, " \n[app]: tripDistance:", this.state.tripDistance, " \n[app]: sortedLocationIds:", this.state.sortedLocationIds);
    }

    static isDead() {
        return true;
    }
}

export default App;
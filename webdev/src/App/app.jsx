import React from 'react';
import TripMap from './TripMap/trip_map.jsx';
import LeftMenu from './leftMenu.jsx';
import RightMenu from './rightMenu.jsx';

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
        let main = {
            both: {
                marginLeft: "29%",
                marginRight: "29%",
            },
            right: {
                marginRight: "29%",
            },
            left: {
                marginLeft: "29%",
            },
            nope: {
                marginLeft: "0%",
                marginRight: "0%",
            }
        };
        //TODOdone function that gets
        //console.log("dix",((this.state.leftMenu && this.state.rightMenu) ? main.both : (this.state.leftMenu) ? main.left : (this.state.rightMenu) ? main.right : main.nope));
        return <div>
            <LeftMenu leftMenu={this.state.leftMenu} selectLocation={this.selectLocation.bind(this)}
                      rightMenu={this.state.rightMenu}
                      setLocations={Object.values(this.state.selectedLocations)}
                      removeLocation={this.removeLocation.bind(this)} saveTrip={this.saveTrip.bind(this)}
                      clear={this.clearSelectedLocations.bind(this)}
                      tripDistance={this.state.tripDistance}
                      toggleTwoOpt={this.toggleTwoOpt.bind(this)} //TODO Jesse
                      toggleThreeOpt={this.toggleThreeOpt.bind(this)}
            />
            <RightMenu
            />
            <div className="left-menu-button-div"
                 style={(this.state.leftMenu) ? main.left : main.nope}
            >
                <span className="left-menu-button"
                      onClick={(this.state.leftMenu) ? this.closeLeftNav.bind(this) : this.openLeftNav.bind(this)}>{this.state.leftMenu ? "ᗉ" : "ᗆ"}
                </span>
            </div>
            <div className="right-menu-button-div"
                 style={(this.state.rightMenu) ? main.right : main.nope}
            >
                <span className="right-menu-button"
                      onClick={(this.state.rightMenu) ? this.closeRightNav.bind(this) : this.openRightNav.bind(this)}>{this.state.rightMenu ? "ᗆ" : "ᗉ"}
                </span>
            </div>
            <div id="main" className="planning-stuff"
                 style={ ((this.state.leftMenu && this.state.rightMenu) ? main.both : (this.state.leftMenu) ? main.left : (this.state.rightMenu) ? main.right : main.nope)}>
                <div className="inner">
                    <TripMap locations={this.state.selectedLocations}
                             trip={this.state.sortedLocationIds}
                             selectedLocations={Object.values(this.state.selectedLocations)}
                             sortedLocationIds={this.state.sortedLocationIds}
                    />
                </div>
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
                //console.log("Distance added between", loc1.name, "and", loc2.name, "is", dist);
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

    openLeftNav() {
        console.log("Left now true");
        this.setState({
            leftMenu: true,
        });
    }

    closeLeftNav() {
        console.log("Left now false");
        this.setState({
            leftMenu: false,
        });
    }

    openRightNav() {
        console.log("Right now true");
        this.setState({
            rightMenu: true,
        });
    }

    closeRightNav() {
        console.log("Right now false");
        this.setState({
            rightMenu: false,
        });
    }

    toggleTwoOpt() {
        console.log(JSON.stringify(Object.values(this.state.selectedLocations)));
        this.optimize(JSON.stringify(Object.values(this.state.selectedLocations)));
    }

    toggleThreeOpt() {
        console.log("Toggling 3-opt");
    }

    async optimize(query) {
        try {
            console.log("Sending locs...");
            let stuff = await fetch(`http://localhost:4567/toOptimize?locs=${query}`);
            console.log("Locs sent");
            let json = await stuff.json();
            let obj = {};
            json.forEach(elem => obj[elem.id] = elem);
            this.setState({
                selectedLocations: obj
            });
        }
        catch (e) {
            console.error(e);
        }
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
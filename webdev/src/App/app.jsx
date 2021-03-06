import React from 'react';
import TripMap from './TripMap/trip_map.jsx';
import LeftMenu from './Menu/LeftMenu/leftMenu.jsx';
import RightMenu from './Menu/RightMenu/rightMenu.jsx';
import Itinerary from './Itinerary/Itinerary.jsx';

let Sel = ({locations}) => <div>
    {locations.map(l => <li key={l.id}>{l.name}</li>)}
</div>;
/*
 <Sel locations={Object.values(this.state.selectedLocations)}/>
 */

class App extends React.Component {
    constructor(props) {
        super(props); // this is required
        this.getTripsFromServer();
        let status = true;
        let name = "";
        let selectedLocationsO = {};
        let tripDistanceO = 0;
        let sortedLocationIdsO = [];
        this.state = {
            status: true,
            original: true,
            name: "",
            selectedLocations: {},
            savedTrips: {
                co: {
                    name: "co",
                    totalDistance: 50.78670229169772,
                    locations: [
                        {
                            airportUrl: "",
                            continent: "North America",
                            country: "United States",
                            countryUrl: "http://en.wikipedia.org/wiki/United_States",
                            id: "CO35",
                            lat: 39.727500915527344,
                            lon: -104.99099731445312,
                            municipality: "Denver",
                            name: "Denver Health Heliport",
                            nearest: -1,
                            nearestDistance: 9999999,
                            pairUsesWraparound: false,
                            region: "Colorado",
                            regionUrl: "http://en.wikipedia.org/wiki/Colorado",
                            tableIndex: 0,
                        },
                        {
                            airportUrl: "",
                            continent: "North America",
                            country: "United States",
                            countryUrl: "http://en.wikipedia.org/wiki/United_States",
                            id: "CO39",
                            lat: 39.72330093383789,
                            lon: -105.11100006103516,
                            municipality: "Denver",
                            name: "Denver Federal Center Helistop",
                            nearest: -1,
                            nearestDistance: 9999999,
                            pairUsesWraparound: false,
                            region: "Colorado",
                            regionUrl: "http://en.wikipedia.org/wiki/Colorado",
                            tableIndex: 0,
                        },
                        {
                            airportUrl: "http://en.wikipedia.org/wiki/Denver_International_Airport",
                            continent: "North America",
                            country: "United States",
                            countryUrl: "http://en.wikipedia.org/wiki/United_States",
                            id: "KDEN",
                            lat: 39.861698150635,
                            lon: -104.672996521,
                            municipality: "Denver",
                            name: "Denver International Airport",
                            nearest: -1,
                            nearestDistance: 9999999,
                            pairUsesWraparound: false,
                            region: "Colorado",
                            regionUrl: "http://en.wikipedia.org/wiki/Colorado",
                            tableIndex: 0,
                        },
                    ],
                }
            },
            tripDistance: 0,
            sortedLocationIds: [],
            leftMenu: false,
            rightMenu: false,
            menu: false,
            itinerary: false,
            kilometers: false,
        }
    }

    render() {
        let statusStyle = {
            green: {
                backgroundColor: "#00ff20",
            },
            red: {
                backgroundColor: "#ff0c00",
            }
        };
        let main = {
            triple: {
                marginLeft: "29%",
                marginRight: "29%",
                height: "61%",
            },
            leftAndBottom: {
                marginLeft: "29%",
                height: "61%",
            },
            rightAndBottom: {
                marginRight: "29%",
                height: "61%",
            },
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
            itinerary: {
                height: "61%",
            },
            nope: {
                marginLeft: "0%",
                marginRight: "0%",
                marginBottom: "0%",
            }

        };
        let topMain = {
            yep: {
                marginTop: "66%",
            },
            nope: {
                marginTop: "0%",
            }
        };

        let bottomMain = {
            openItin: {
                marginBottom: "22.1%",
            },
            closeItin: {
                marginBottom: "0%",
            }
        };

        var myItinerary = {
            openItin: {
                height: "39%",
            },
            closeItin: {
                height: "0%",
            }
        };
        //TODOdone function that gets
        //console.log("dix",((this.state.leftMenu && this.state.rightMenu) ? main.both :
        // (this.state.leftMenu) ? main.left : (this.state.rightMenu) ? main.right : main.nope));
        return <div>
            <LeftMenu leftMenu={this.state.leftMenu} selectLocation={this.selectLocation.bind(this)}
                      setLocations={Object.values(this.state.selectedLocations)}
                      sortedLocationIds={this.state.sortedLocationIds}
                      removeLocation={this.removeLocation.bind(this)} saveTrip={this.saveTrip.bind(this)}
                      clear={this.clearSelectedLocations.bind(this)}
                      tripDistance={this.state.tripDistance}
                      green={this.green.bind(this)}
                      red={this.red.bind(this)}
            />
            <RightMenu
                rightMenu={this.state.rightMenu}
                setLocations={Object.values(this.state.selectedLocations)}
                savedTrips={this.state.savedTrips}
                selectTrip={this.selectTrip.bind(this)}
                deleteTrip={this.deleteTrip.bind(this)}
                tripDistance={this.state.tripDistance}
                toggleZeroOpt={this.toggleZeroOpt.bind(this)}
                toggleTwoOpt={this.toggleTwoOpt.bind(this)}
                toggleThreeOpt={this.toggleThreeOpt.bind(this)}
                browseFile={this.browseFile.bind(this)}
                kilometers={this.state.kilometers}
                toggleKilometers={this.toggleKilometers.bind(this)}
                tripName={this.state.name}
                green={this.green.bind(this)}
                red={this.red.bind(this)}
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

            <button alt="status" title={!(this.state.status) ? "Loading..." : "Ready"} className="status" style={!(this.state.status) ? statusStyle.red : statusStyle.green}> </button>
            <div id="main" className="planning-stuff"
                 style={ ((this.state.leftMenu && this.state.rightMenu && this.state.itinerary) ? main.triple : (this.state.leftMenu && this.state.itinerary) ? main.leftAndBottom : (this.state.rightMenu && this.state.itinerary) ? main.rightAndBottom : (this.state.leftMenu && this.state.rightMenu) ? main.both : (this.state.leftMenu) ? main.left : (this.state.rightMenu) ? main.right : (this.state.itinerary) ? main.itinerary : main.nope)}>
                <div className="inner">
                    <TripMap locations={this.state.selectedLocations}
                             trip={this.state.sortedLocationIds}
                             selectedLocations={Object.values(this.state.selectedLocations)}
                             sortedLocationIds={this.state.sortedLocationIds}
                    />
                </div>
            </div>

            <div className="itinerary-div"
                 style={ (this.state.itinerary) ? myItinerary.openItin : myItinerary.closeItin }>
                <Itinerary
                    selectedLocations={this.state.selectedLocations}
                    sortedLocationIds={this.state.sortedLocationIds}
                    kilometers={this.state.kilometers}
                />
            </div>

            <div className="bottom-menu-button-div"
                 style={(this.state.itinerary) ? bottomMain.openItin : bottomMain.closeItin}
                 onClick={(this.state.itinerary) ? this.closeItinNav.bind(this) : this.openItinNav.bind(this)}
            >
                <span className="bottom-menu-button">
                    {this.state.itinerary ? "∨" : "∧"}
                </span>
            </div>
        </div>
    }

    selectLocation(loc) {
        this.red(this.state.status);
        let ret = false;
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
        console.log(numLocs);
        //console.log(numLocs,"Adding location:", loc);
        //console.log("Number of locations currently:", numLocs);
        if (numLocs > 0) {
            let bestDist = 9999999;
            let minusDist = 0;
            let plusDist = 0;
            //console.log("Now calculating distances for insertion");
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
        if (!newSortedLocationIds.includes(loc.id)) {
            newSortedLocationIds.splice(whereToInsert + 1, 0, loc.id);
            ret = true;
        } else {
            console.log("ID already in sorted location ids!");
        }

        //console.log("Should be inserted at index:", whereToInsert + 1);

        /*let totalTripDistance = 0;
         if (numLocs > 1) {
         //console.log("Now calculating distances for insertion")
         for (let i = 0; i < numLocs; i++) {
         //console.log(currentLocations[i].name);
         //console.log(currentLocations[(i + 1) % (numLocs - 1)].name);
         //Figure out where the best place to put the location is
         //Need to also measure between the first and the last locations
         //This will account for nearestNeighbor
         let loc1 = currentLocations[i];
         let loc2 = currentLocations[(i + 1) % (numLocs)];
         let dist = this.distanceBetweenCoords(loc1.lat, loc1.lon, loc2.lat, loc2.lon);
         totalTripDistance += dist; //TODOdone this doesnt work because we are not calculating the entire trip yet
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
            sortedLocationIds: newSortedLocationIds,
            tripDistance: totalDist
        });
        this.green(this.state.status);
        return ret;
    }

    selectLocationO(loc) {
        let ret = false;
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
        //console.log(numLocs,"Adding location:", loc);
        //console.log("Number of locations currently:", numLocs);
        if (numLocs > 0) {
            let bestDist = 9999999;
            let minusDist = 0;
            let plusDist = 0;
            //console.log("Now calculating distances for insertion");
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
        if (!newSortedLocationIds.includes(loc.id)) {
            newSortedLocationIds.splice(whereToInsert + 1, 0, loc.id);
            ret = true;
        } else {
            console.log("ID already in sorted location ids!");
        }

        let obj = {};
        obj[loc.id] = loc;
        let newMap = Object.assign({},
            this.selectedLocationsO,
            obj);

        this.selectedLocationsO = newMap;
        this.sortedLocationIdsO = newSortedLocationIds;
        this.tripDistanceO = totalDist;
        return ret;
    }

    searchSelectedLocationsWithId(id) {
        this.red(this.state.status);
        let locations = Object.values(this.state.selectedLocations);
        for (let i = 0; i < locations.length; i++) {
            if (id === locations[i].id) {
                return locations[i];
            }
        }
        console.log("[app]: searchSelectedLocationsWithId: No locations with id:", id, "in selectedLocations")
        this.green();
        return undefined;
    }

    removeLocation(loc) {
        this.red(this.state.status);
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
        this.green();
        //TODO add handling for only one location being selected. Update tripDistance to 0
    }

    saveTrip(trip) {
        this.red(this.state.status);
        let obj = {};
        obj[trip.name] = trip;
        let newMap = Object.assign({},
            this.state.savedTrips,
            obj);
        this.setState({
            name: trip.name,
            savedTrips: newMap,
            tripDistance: trip.totalDistance,
            sortedLocationIds: trip.selectedIds
        });
        this.saveTripsToServer("pull", trip);
        this.green();
    }

    async saveTripsToServer(opt, map) {
        this.red(this.state.status);
        let tripName = map.name;
        let tripIds = map.selectedIds;
        let newMap = {
            name: tripName,
            totalDistance: map.totalDistance,
            selectedIds: tripIds,
        };
        let newNewMap = {
            newMap,
        };

        map = newNewMap;
        //console.log("MAP IS:", map);

        let query = JSON.stringify(Object.values(map));
        try {
            this.red(this.state.status);
            console.log("Sending trips...");
            let stuff = await fetch(`http://localhost:4567/saveTrips?trips=${query}`);
            //console.log("Url:", `http://localhost:4567/saveTrips?trips=${query}`);
            //console.log("trips sent");
            let json = await stuff.json();
            console.log("Trips sent.");
        }
        catch (e) {
            console.error(e);
        }
        this.green();
    }

    async getTripsFromServer() {
        //this.red(this.state.status);
        try {
            console.log("Asking for trips...");
            let stuff = await fetch(`http://localhost:4567/getTrips?num=all`);
            //console.log("Url:", `http://localhost:4567/getTrips?num=all`);
            let json = await stuff.json();
            let obj = {};
            json.forEach(elem => obj[elem.name] = elem);
            this.setState({
                savedTrips: obj,
            });
            console.log("Received trips", obj);
        }
        catch (e) {
            console.error(e);
        }
        //this.green();
    }


    async selectTrip(trip) {
        this.red(this.state.status);
        console.log("Selecting trip:", trip);
        let obj = {};
        obj[trip.name] = trip;
        let sorted = [];
        let numIds = trip.selectedIds.length;
        let locations = trip.locations; //TODOdone this is slow af
        if (locations === undefined) { //TODOdone check if locations are populated, else, search database for them
            let temp = {};
            let ids = trip.selectedIds;
            for (let i = 0; i < numIds; i++) {
                let e = await fetch(`http://localhost:4567/database?id=${ids[i]}`);
                let json = await e.json();
                json.forEach(elem => temp[elem.id] = elem);
            }
            trip.locations = temp;
            //console.log("New locations", temp);
        }
        /*else {
         let newMap = {};
         let numLocs = Object.values(trip.locations).length;
         for (let i = 0; i < numLocs; i++) {
         //sorted.push(locations[i].id);
         newMap[locations[i].id] = locations[i];
         }
         trip.locations = newMap;
         }*/
        this.setState({
            name: trip.name,
            selectedLocations: trip.locations,
            sortedLocationIds: trip.selectedIds,
            tripDistance: trip.totalDistance,
        });
        this.green();
    }

    deleteTrip(trip) {
        this.red(this.state.status);
        let name = trip.name;
        console.log("Deleting trip with name:", name);
        let newMap = this.state.savedTrips;
        delete newMap[name];
        this.setState({
            savedTrips: newMap
        });
        //TODO could add deleting savedTrip from database but I dont want to
        this.green();
    }

    clearSelectedLocations() {
        this.red(this.state.status);
        this.setState({
            selectedLocations: {},
            sortedLocationIds: [],
            tripDistance: 0,
        });
        this.green();
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
        //console.log("Left now true");
        this.setState({
            leftMenu: true,
        });
    }

    closeLeftNav() {
        //console.log("Left now false");
        this.setState({
            leftMenu: false,
        });
    }

    openRightNav() {
        //console.log("Right now true");
        this.setState({
            rightMenu: true,
        });
    }

    closeRightNav() {
        //console.log("Right now false");
        this.setState({
            rightMenu: false,
        });
    }

    toggleZeroOpt() { //TODOdone make sure there is more than 4 locations before sending
        if (Object.values(this.state.selectedLocations).length > 3) {
            console.log("Running Nearest Neighbor");
            this.optimize("0", JSON.stringify(Object.values(this.state.selectedLocations)));
        }
    }

    toggleTwoOpt() { //TODOdone make sure there is more than 4 locations before sending
        if (Object.values(this.state.selectedLocations).length > 3) {
            console.log("Running 2-opt");
            this.optimize("2", JSON.stringify(Object.values(this.state.selectedLocations)));
        }
    }

    toggleThreeOpt() { //TODOdone make sure there is more than 4 locations before sending
        if (Object.values(this.state.selectedLocations).length > 3) {
            console.log("Running 3-opt");
            this.optimize("3", JSON.stringify(Object.values(this.state.selectedLocations)));
        }
    }

    openItinNav() {
        //console.log("Menu now true");
        this.setState({
            itinerary: true,
        });
    }

    closeItinNav() {
        //console.log("Menu now false");
        this.setState({
            itinerary: false,
        });
    }

    async optimize(opt, query) { //We need to make sure that no string inside a location object has & in it
        this.red(this.state.status);
        console.log("Starting",opt,"opt");
        console.log("Sending locations...")
        try {
            console.log("Sending locs...");
            let stuff = await fetch(`http://localhost:4567/toOptimize?opt=${opt}&locs=${query}`);
            let dist = await fetch(`http://localhost:4567/getDistance?dist=true`);
            //console.log("Url:", `http://localhost:4567/toOptimize?opt=${opt}&locs=${query}`);
            //console.log("Url:", `http://localhost:4567/getDistance?dist=true`);
            //console.log("Locs sent");
            let json = await stuff.json();
            let json1 = await dist.json();
            let obj = {};
            let sorted = [];
            let tempD = 0;
            json.forEach(elem => sorted.push(elem.id));
            json.forEach(elem => obj[elem.id] = elem); //We should replace this with calling our selectLocation method so it sorts into the list correctly. We also need to make sure we call clear before we start messing around with adding
            json1.forEach(elem => tempD = elem);
            this.setState({
                selectedLocations: obj,
                sortedLocationIds: sorted,
                tripDistance: tempD,
            });
            console.log("Received Locations", obj);
        }
        catch (e) {
            console.log("Switching to individual...");
            let numLocs = Object.values(this.state.selectedLocations).length;
            let locations = Object.values(this.state.selectedLocations);
            for (let i = 0; i < numLocs; i++) {
                //console.log("Sending location at index", i, locations[i]);
                let q = JSON.stringify(locations[i]);
                let s = await fetch(`http://localhost:4567/setSelectedIndividual?locs=${q}`);
            }
            let fireOpt = await fetch(`http://localhost:4567/fireOpt?opt=${opt}`);
            let dist = await fetch(`http://localhost:4567/getDistance?dist=true`);
            let json = await fireOpt.json();
            let json1 = await dist.json();
            let obj = {};
            let sorted = [];
            let tempD = 0;
            json.forEach(elem => sorted.push(elem.id));
            json.forEach(elem => obj[elem.id] = elem); //We should replace this with calling our selectLocation method so it sorts into the list correctly. We also need to make sure we call clear before we start messing around with adding
            json1.forEach(elem => tempD = elem);
            this.setState({
                selectedLocations: obj,
                sortedLocationIds: sorted,
                tripDistance: tempD,
            });
            console.log("Received Locations", obj);
            //console.error(e);
        }
        console.log("Finished",opt,"opt");
        this.green();
    }

    //TODO Function that reads json using json.forEach(elem => obj[elem.id] = elem)

    async getLocationFromDatabase(id) {
        //console.log(`http://localhost:4567/database?id=${id}`);
        let e = await fetch(`http://localhost:4567/database?id=${id}`);
        let json = await e.json();
        let obj = {};
        json.forEach(elem => obj[elem.id] = elem);
        return obj;
    }

    async browseFile(file) {
        this.red(this.state.status);
        console.log("Got file:", file);
        this.clearSelectedLocations();
        let name = file.title;
        let ids = file.destinations;
        //console.log(ids);
        let num = 0;
        let toggle = true;
        if(ids.length < 40) {
            num = 1;
            toggle = false
        } else if(ids.length < 250) {
            num = 10
        } else {
            num = 15
        }
        if(toggle) { //This is for querying with multiple
            console.log("Using multiple");
            for (let i = 0; i < ids.length; i += num) {
                let tenIds = [];
                for (let j = i; j < i + num; j++) {
                    if (j < ids.length) {
                        tenIds.push(ids[j]);
                    } else {
                        break;
                    }
                }
                //console.log("First 10 are",tenIds);
                let location = await this.getLocationFromDatabase(tenIds);
                //console.log("Got location",location);
                for (let j = 0; j < Object.values(location).length; j++) {
                    //console.log("Selecting",location[tenIds[j]]);
                    this.selectLocationO(location[tenIds[j]]);
                }
            }
            this.updateMap();
            this.calculateDistance();

        } else { //This is for querying with one
            console.log("Using Individual");
            for (let i = 0; i < ids.length; i++) {
                let location = await this.getLocationFromDatabase(ids[i]);
                //console.log("Got location",location);
                this.selectLocation(location[ids[i]]);
            }
        }

        this.setState({
            name: name,
            original: true,
        });
        this.green();
    }

    updateMap() {
        this.setState({
            name: this.name,
            selectedLocations: this.selectedLocationsO,
            sortedLocationIds: this.sortedLocationIdsO,
            tripDistance: this.tripDistanceO,
        })
    }

    calculateDistance() {
        let locations = this.state.sortedLocationIds.map(id => this.state.selectedLocations[id]);
        //let locations = this.state.selectedLocations;
        //console.log("Length is",Object.values(locations).length);

        let numLocs = Object.values(locations).length;
        let distance = 0;
        for(let i = 0; i < numLocs; i++) {
            //calculate distances
            let loc1 = locations[i];
            let loc2 = locations[(i + 1) % (numLocs)];
            distance += this.distanceBetweenCoords(loc1.lat, loc1.lon, loc2.lat, loc2.lon);
        }
        this.setState({
            tripDistance: distance,
        });
    }

    test() {
        console.log("leftMenu:", this.state.leftMenu);
        console.log("[app]: selectedLocations:", this.state.selectedLocations,
            " \n[app]: savedTrips:", this.state.savedTrips, " \n[app]: tripDistance:",
            this.state.tripDistance, " \n[app]: sortedLocationIds:", this.state.sortedLocationIds);
    }

    static isDead() {
        return true;
    }

    toggleKilometers() {
        if(this.state.kilometers) {
            this.setState({
                kilometers: false
            });
        } else {
            this.setState({
                kilometers: true
            });
        }
    }

    green() {
        //this.status = true;
        let orig = this.state.original;
        this.setState({
            status: orig
        })
    }

    red(original) {
        //this.status = false;
        this.setState({
            original: original,
            status: false
        })
    }
}

export default App;
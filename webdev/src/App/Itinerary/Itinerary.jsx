/**
 * Created by Sandeep on 4/26/2017.
 */
import React from 'react';
import Pair from "./Pair.jsx";
//import Pair from './Itinerary/Pair.jsx';

class Itinerary extends React.Component {
    constructor(props) {
        super(props); // this is required
        this.state = {}
    }

    render() {
        let kilometers = this.props.kilometers;
        let locations = this.props.sortedLocationIds.map(id => this.props.selectedLocations[id]);
        //For loop that goes through all locations,
        let pairs = [];
        for (let i = 0; i < Object.values(locations).length; i++) {
            let loc1 = locations[i];
            let loc2 = locations[(i + 1) % (Object.values(locations).length)];
            let dist = this.distanceBetweenCoords(loc1.lat, loc1.lon, loc2.lat, loc2.lon);
            if(kilometers) {
                dist = dist * 1.6;
            }
            let p = {
                one: loc1,
                two: loc2,
                distance: dist
            };
            pairs.push(p);
        }
        let ps = pairs.map((pp) => {
            return <Pair {...pp} kilometers={kilometers}/>; //Calls to Pair.jsx
        });
        //console.log("Pairs:", (pairs));
        return <div className="itinerary">
            <h3>Itinerary</h3>
            <ul>
                {ps}
            </ul>
        </div>
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
}

export default Itinerary;
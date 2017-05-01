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

        let locations = this.props.sortedLocationIds.map(id => this.props.selectedLocations[id]);
        //For loop that goes through all locations,
        let pairs = [];
        for (let i = 0; i < Object.values(locations).length; i++) {
            let p = {
                one: locations[i],
                two: locations[(i + 1) % (Object.values(locations).length)],
            };
            pairs.push(p);
        }
        let ps = pairs.map((pp) => {
            return <Pair {...pp} />; //Calls to Pair.jsx
        });
        console.log("Pairs:", (pairs));
        return <div className="itinerary">
            <ul>
                {ps}
            </ul>
        </div>
    }
}

export default Itinerary;
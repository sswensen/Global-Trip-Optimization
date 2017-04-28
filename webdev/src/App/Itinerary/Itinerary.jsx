/**
 * Created by Sandeep on 4/26/2017.
 */
import React from 'react';

class Itinerary extends React.Component {
    constructor(props) {
        super(props); // this is required
        this.state = {

        }
    }
    render(){
        var myItinerary = {
            openItin: {
                marginBottom: "66%",
            },
            closeItin: {
                marginBottom: "0%",
            }
        };
        let locations=this.props.sortedLocationIds.map(id => this.props.selectedLocations[id]);
        //For loop that goes through all locations,
        let pairs = [];
        for (let i = 0; i < Object.values(locations).length; i++) {
            let pair = {
                one: locations[i],
                two: locations[(i + 1) % (Object.values(locations).length)],
            };
            pairs.push(pair);
        }
        let ps = pairs.map((pp) => {
            return <Pair {...pp} />; //Calls to Pair.jsx
        });
        console.log("Pairs:",(pairs));
        return <div>

        </div>
    }
}

export default Itinerary;
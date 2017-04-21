import React, {Component} from 'react';

class TripPlanner extends Component {
    constructor(props) {
        super(props); // this is required
        this.state = {
            name: "",
            locations: {}
        }
    }

    render() {

        let TripPlanner = ({locations}) => <div>
            {locations.map(l => <li key={l.id}>{l.name}</li>)}
        </div>
        let array = [];
        /*
         let foo = locations.map(function(location) {
         return <li>location</li>;
         })
         */

        return <div className="trip-planner">

        </div>;
    }

    keyUp(event) {
        if (event.which == 13) {
            this.fetch(event.target.value);
        }
    }

    async fetch(query) {
        try {
            console.log("Fetching...");
            let stuff = await fetch(`http://localhost:4567/locations?q=${query}`);
            console.log("Fetched");
            let json = await stuff.json();
            let obj = {};
            json.forEach(elem => obj[elem.id] = elem);
            this.setState({
                locations: obj
            });
        }
        catch (e) {
            console.error(e);
        }
    }

    remove(id) {
        let newObj = Object.assign(this.state.locations);
        delete newObj[id];
        this.setState({
            locations: newObj
        })
    }

    clear() {
        this.setState({
            locations: {}
        })
    }
}

// let our other modules use this
export default TripPlanner;
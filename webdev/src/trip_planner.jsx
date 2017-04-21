import React, {Component} from 'react';

class TripPlanner extends Component {
    constructor(props) {
        super(props); // this is required
        this.state = {
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
            <h1>HAY</h1>
        </div>;
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
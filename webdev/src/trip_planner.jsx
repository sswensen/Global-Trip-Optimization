import React, {Component} from 'react';

class TripPlanner extends Component {
    constructor(props) {
        super(props); // this is required
        this.setState({setLocations: this.props.selectedLocations})
        this.state = {
            setLocations: {}
        }
    }

    render() {
        /*let TripPlanner = ({locations}) => <div>
            {locations.map(l => <li key={l.id}>{l.name}</li>)}
        </div>*/
        console.log(this.state.setLocations);

        let locations = Object.values(this.state.setLocations);
        let items = locations.map(() => {
            return {locations:map(l => <li key={l.id}>{l.name}</li>)}
        });

        /*
         let foo = locations.map(function(location) {
         return <li>location</li>;
         })
         */

        return <div className="trip-planner">
            <div>
                <ul>
                    {items}
                </ul>
            </div>
        </div>;
    }

    remove(id) {
        let newObj = Object.assign(this.state.setLocations);
        delete newObj[id];
        this.setState({
            setLocations: newObj
        })
    }

    clear() {
        this.setState({
            setLocations: {}
        })
    }
}

// let our other modules use this
export default TripPlanner;
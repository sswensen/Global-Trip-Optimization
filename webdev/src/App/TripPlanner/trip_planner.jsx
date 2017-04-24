import React, {Component} from 'react';
import SelectedLocation from './SelectedLocation/selected_location.jsx';

class TripPlanner extends Component {
    constructor(props) {
        super(props); // this is required
        this.state = {
            setLocations: {},
            name: "",
            trip: {}
        }
    }

    render() {
        /*let TripPlanner = ({locations}) => <div>
         {locations.map(l => <li key={l.id}>{l.name}</li>)}
         </div>*/

        let locations = Object.values(this.props.setLocations);
        /*let items = locations.map((l) => <li key={l.id}>{l.name}</li>);*/
        let trip = this.state.trip;
        let save = this.props.saveTrip.bind(undefined, trip);
        let clear = this.props.clear.bind(undefined);
        let numSel = Object.values(this.props.setLocations).length;
        let items = locations.map((loc) => {
            let remove = this.props.removeLocation.bind(undefined, loc);
            //console.log({loc});
            return <SelectedLocation {...loc} key={loc.id} remove={remove}/>; //Calls to location.jsx
        });

        /*console.log("Locations is: " + {locations});
         var aLen = locations.length;
         for (var i = 0; i < aLen; i++) {
         console.log("ID at index " + i + " is " + locations[i].id);
         }
         let items = locations.map(() => {
         return {locations:map(l => <li key={l.id}>{l.name}</li>)}
         });

         /*
         let foo = locations.map(function(location) {
         return <li>location</li>;
         })
         */

        return <div className="trip-planner">
            <div className="map-options">
                <input className="trip-name-input" onKeyUp={this.keyUp.bind(this)} type="text" placeholder="Enter Trip Name" />
                <button className="save-button" onClick={save}>Save</button>
                <button className="clear-selected-locations" onClick={clear}>Clear</button>
                <span className="selected-location-counter"># Selected: {numSel}</span>
            </div>
            <div>
                <ul className="selectedLocations-list">
                    {items}
                </ul>
            </div>
        </div>;
    }

    saveTrip(event) {
        let tName = event.target.value;
        let locations = Object.values(this.props.setLocations);
        this.setState({
            name: event.target.value,
        });
        let trip = new Object();
        trip.name = event.target.value;
        trip.locations = locations;
        trip.totalDistance = this.props.tripDistance;
        this.setState({
            trip: trip,
        });
    }

    keyUp(event) {
        if (event.which == 13) {
            this.saveTrip(event);
        }
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

    testing() {
        console.log("[trip_planner]: Data in trip_planner is",this.state.name,"with trip as",this.state.trip);
    }
}

// let our other modules use this
export default TripPlanner;
import React, {Component} from 'react';
import SelectedLocation from './SelectedLocation/selected_location.jsx';

class TripPlanner extends Component {
    constructor(props) {
        super(props); // this is required
        this.state = {
            setLocations: {},
            name: "",
            trip: {},
            currentName: ""
        };
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
                <input className="trip-name-input" onChange={this.updateCurrentName.bind(this)}
                       type="text" placeholder="Name"/>
                <button className="save-button" onClick={this.saveTrip.bind(this)}>Save</button>
                <button className="clear-selected-locations" onClick={clear}>Clear</button>

            </div>
            <div className="selectedLocations-list-div">
                <ul className="selectedLocations-list">
                    {items}
                </ul>
            </div>
        </div>;
    }

    updateCurrentName(evt) {
        this.setState({
            currentName: evt.target.value
        });
        console.log("These should:",evt.target.value)
    }

    saveTrip() {
        let locations = Object.values(this.props.setLocations);
        let distance = this.props.tripDistance;
        let tempIds = [];
        for(let i = 0; i < locations.length; i++) {
            tempIds.push(locations[i].id)
        }
        let tempName = this.state.currentName;
        this.setState({
            name: tempName
        });
        let trip = new Object();
        trip.name = tempName;
        trip.totalDistance = distance;
        trip.locations = locations;
        trip.selectedIds = trip.sortedLocationIds;
        console.log(trip);
        this.setState({
            trip: trip,
        });
        this.props.saveTrip(trip);
    }

    saveTripFromEnter(event) {
        let locations = Object.values(this.props.setLocations);
        let distance = this.props.tripDistance;
        let tempIds = [];
        for(let i = 0; i < locations.length; i++) {
            tempIds.push(locations[i].id)
        }
        this.setState({
            name: event.target.value,
        });
        let trip = new Object();
        trip.name = event.target.value;
        trip.totalDistance = distance;
        trip.locations = locations;
        trip.ids = tempIds;
        console.log(trip);
        this.setState({
            trip: trip,
        });
    }

    keyUp(event) {
        if (event.which == 13) {
            this.saveTripFromEnter(event);
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
        console.log("[trip_planner]: Data in trip_planner is", this.state.name, "with trip as", this.state.trip);
    }
}

// let our other modules use this
export default TripPlanner;
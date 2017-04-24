import React, {Component} from 'react';
import GettingStartedGoogleMap from './GettingStartedWithGoogleMaps/GettingStartedWithGoogleMaps.jsx';
import Map from './GettingStartedWithGoogleMaps/Map.js';
class TripMap extends Component {
    constructor(props) {
        super(props); // this is required
        this.state = {
            name: "",
            locations: {}
        }
    }

    render() {
        return <div style={{height: "55%"}}>
            <Map ref={instance => { this.child = instance; }}
                 selectedLocations={this.props.selectedLocations}
                 sortedLocationIds={this.props.sortedLocationIds}
            />
        </div>
    }

    updateMarkers(locs, sorted) {
        this.child.updateMarkers(locs, sorted);
    }
}

export default TripMap;
import React, {Component} from 'react';
import Map from './GettingStartedWithGoogleMaps/Map.js';
class TripMap extends Component {
    constructor(props) {
        super(props); // this is required
        this.state = {
            name: ""
        }
    }

    render() {
        return <div style={{height: "100%"}}>
            <Map ref={instance => { this.child = instance; }}
                 selectedLocations={this.props.selectedLocations}
                 sortedLocationIds={this.props.sortedLocationIds}
            />
        </div>
    }
}

export default TripMap;
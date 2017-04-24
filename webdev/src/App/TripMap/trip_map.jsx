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
        return <div style={{height: "100%"}}>
            <Map />
        </div>
    }

    /*render() {
        return <div style={{height: "100%"}}>
            <GettingStartedGoogleMap
                containerElement={
                    <div style={{height: `100%`}}/>
                }
                mapElement={
                    <div style={{height: `100%`}}/>
                }
                onMapLoad={_.noop}
                onMapClick={_.noop}
                markers={markers}
                onMarkerRightClick={_.noop}
            />
        </div>;
    }*/

}

export default TripMap;
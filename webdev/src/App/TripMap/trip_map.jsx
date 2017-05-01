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
        //console.log(this.state);
        //console.log({props: this.props});
        console.log(this.props.locations);
        let locs = this.props.trip.map(id => this.props.locations[id]);
        return <div style={{height: "100%"}}>
            <Map
                locations={locs}
            />
        </div>
    }
}

export default TripMap;
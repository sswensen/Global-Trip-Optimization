/**
 * Created by SummitDrift on 4/24/17.
 */
import {
    default as React,
    Component,
} from "react";

import {
    GoogleMap,
    Marker,
    Polyline,
    InfoWindow,
} from "react-google-maps";

export default class GMap extends Component {
    constructor(props) {
        super(props); // this is required
        this.state = {

        };
    }

    render() {
        return <GoogleMap
            ref={this.props.onMapLoad}
            defaultZoom={2}
            defaultCenter={{lat: 30, lng: 0}}
            onClick={this.props.onMapClick}
        >
            {this.props.update}
            {this.props.markers.map(marker => (
                <Marker
                    {...marker}
                    onClick={() => this.props.onMarkerClick(marker)}
                    onRightClick={() => this.props.onMarkerRightClick(marker)}
                >
                    <InfoWindow />
                </Marker>
            ))}
            {this.props.polylines.map(polyline => (
                <Polyline
                    {...polyline}
                    onHover={() => this.props.onPolylineHover(polyline)}
                />
            ))}
        </GoogleMap>;
    }
}

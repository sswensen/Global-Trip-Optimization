/* global google */
import _ from "lodash";

import {
    default as React,
    Component,
} from "react";

import {
    withGoogleMap,
    GoogleMap,
    Marker,
    Polyline,
    InfoWindow,
} from "react-google-maps";

/*
 * This is the modify version of:
 * https://developers.google.com/maps/documentation/javascript/examples/event-arguments
 *
 * Add <script src="https://maps.googleapis.com/maps/api/js"></script> to your HTML to provide google.maps reference
 */

function withGoogleMaps(WrappedComponent) {
    return class extends React.Component {
        componentWillReceiveProps(nextProps) {

        }

        render() {
            return <GoogleMap
                ref={this.props.onMapLoad}
                defaultZoom={10}
                defaultCenter={{lat: 39.7392, lng: -104.9903}}
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
}

const GettingStartedGoogleMap1 = withGoogleMaps(); //TODO update this

const GettingStartedGoogleMap = withGoogleMap(props => (
    <GoogleMap
        ref={props.onMapLoad}
        defaultZoom={10}
        defaultCenter={{lat: 39.7392, lng: -104.9903}}
        //onClick={props.onMapClick}
    >
        {props.children}
    </GoogleMap>
));

export default class GettingStartedExample extends Component {
    constructor(props) {
        super(props); // this is required
        this.state = {
            /*markers: [{
             position: {
             lat: 25.0112183,
             lng: 121.52067570000001,
             },
             key: 'Taiwan',
             defaultAnimation: 2,
             }],
             polylines: [{
             path: [
             {lat: 37.772, lng: -122.214},
             {lat: 21.291, lng: -157.821},
             {lat: -18.142, lng: 178.431},
             {lat: -27.467, lng: 153.027}
             ],
             geodesic: true,
             strokeColor: '#FF0000',
             strokeOpacity: 0.5,
             strokeWeight: 2
             }],*/
            markers: [],
            polylines: [],
            handleMapLoad: this.handleMapLoad.bind(this),
            handleMapClick: this.handleMapClick.bind(this),
            handleMarkerClick: this.handleMarkerClick(this),
            handleMarkerRightClick: this.handleMarkerRightClick.bind(this),
            resetInfoWindows: this.resetInfoWindows.bind(this),
            selectedLocations: {},
            sortedLocationIds: {},
            showWindows: {},
        };
    }


    handleMapLoad(map) {
        if (map) {
            console.log(map.getZoom());
        }
    }

    /*
     * This is called when you click on the map.
     * Go and try click now.
     */
    handleMapClick(event) {
        const nextMarkers = [
            ...this.state.markers,
            {
                position: event.latLng,
                defaultAnimation: 2,
                key: Date.now(), // Add a key property for: http://fb.me/react-warning-keys
            },
        ];
        this.setState({
            markers: nextMarkers,
        });

        if (nextMarkers.length === 3) {
            this.props.toast(
                'Right click on the marker to remove it',
                'Also check the code!'
            );
        }
    }

    handleMarkerClick(targetMarker) {
        //TODOdone make shit popup using InfoWindow
        //console.log("Came in with", targetMarker);
        //this.showWindow(targetMarker.key);
        //Probably need to make a new data set that holds all the info window data
    }

    handleMarkerRightClick(targetMarker) { //TODO Still need to fix this
        /*
         * All you modify is data, and the view is driven by data.
         * This is so called data-driven-development. (And yes, it's now in
         * web front end and even with google maps API.)
         */
        const nextMarkers = this.state.markers.filter(marker => marker !== targetMarker);
        this.setState({
            markers: nextMarkers,
        });
    }

    onPolylineHover(polyline) {
        //TODO popup on polyline that displays distance and other info
    }


    showWindow(id) {
        let newMap = Object.assign({}, this.state.showWindows, {[id]: true});
        this.setState({
            showWindows: newMap
        });
        //console.log("Id:", id, "now true");
        console.log(this.state.showWindows)
    }

    hideWindow(id) {
        console.log("Hiding window");
        let newMap = Object.assign({}, this.state.showWindows, {[id]: false});
        this.setState({
            showWindows: newMap
        });
    }

    generateMarkers() {
        return this.props.locations.map(location => {
            let position = {
                lat: location.lat,
                lng: location.lon
            };
            return <Marker
                onClick={this.showWindow.bind(this, location.id)}
                position={position}
            >
                {this.infoFor(location)}
            </Marker>
        });
    }

    infoFor(location) {
        //console.log(location);
        if(this.state.showWindows[location.id]) {
            return <InfoWindow
                onCloseClick={this.resetInfoWindows.bind(this)}
                >
                <div>
                    {location.name}
                </div>
            </InfoWindow>
        }
        else return "";
    }

    resetInfoWindows() {
        this.setState({
            showWindows: {}
        })
    }

    generatePolyline() {
        let path = this.props.locations.map(({lat, lon}) =>({lat: lat, lng: lon}));
        if(Object.values(this.props.locations).length > 0) {
            //console.log("Pushing",{lat: this.props.locations[0].lat, lng: this.props.locations[0].lon})
            path.push({lat: this.props.locations[0].lat, lng: this.props.locations[0].lon});
        }
        //console.log("size is", Object.values(this.props.locations).length,"path",path);
        return <Polyline
            path={path}
            strokeColor="red"
            strokeOpacity="0.5"
        />;
    }

    render() {
        //console.log(this.state);
        //console.log(this.props);
        return (
            <div style={{height: '100%'}}>
                <GettingStartedGoogleMap
                    containerElement={
                        <div style={{height: '100%'}}/>
                    }
                    mapElement={
                        <div style={{height: '100%'}}/>
                    }>
                    {this.generateMarkers()}
                    {this.generatePolyline()}
                </GettingStartedGoogleMap>
            </div>
        );
    }
}

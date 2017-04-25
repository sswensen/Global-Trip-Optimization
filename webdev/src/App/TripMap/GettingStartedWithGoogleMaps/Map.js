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

import GMap from './GoogleMap.jsx';

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
}

const GettingStartedGoogleMap1 = withGoogleMaps(); //TODO update this

const GettingStartedGoogleMap = withGoogleMap(props => (
    <GoogleMap
        ref={props.onMapLoad}
        defaultZoom={2}
        defaultCenter={{lat: 30, lng: 0}}
        onClick={props.onMapClick}
    >
        {props.update}
        {props.markers.map(marker => (
            <Marker
                {...marker}
                onClick={() => props.onMarkerClick(marker)}
                onRightClick={() => props.onMarkerRightClick(marker)}
            >
                {false ? (
                    <InfoWindow>
                        hay
                    </InfoWindow>
                ) : (
                    ""
                )}
            </Marker>
        ))}
        {props.polylines.map(polyline => (
            <Polyline
                {...polyline}
                onHover={() => props.onPolylineHover(polyline)}
            />
        ))}
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
        //TODO make shit popup using InfoWindow
        console.log("Came in with", targetMarker);
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

    updateMarkers(locs, sorted) {
        console.log("Making new markers...");
        let locations = Object.values(locs);
        let newMarkers = [];
        for (let i = 0; i < locations.length; i++) {
            let currentLoc = this.searchSelectedLocationsWithId(locs, sorted[i]);
            //console.log("Marker at", i, "is", currentLoc);
            let ps = {
                lat: currentLoc.lat,
                lng: currentLoc.lon,
            };
            let obj = {
                position: ps,
                key: currentLoc.id,
                defaultAnimation: 2.
            };
            newMarkers.push(obj);
            //console.log("New marker created:", Object.values(obj));
        }
        this.setState({
            markers: newMarkers
        });
        this.updatePolyLines(locs, sorted); //Updates polylines
        this.updateInfoWindows(sorted); //Updates all windows to false
    }

    updatePolyLines(locs, sorted) {
        console.log("Making new polylines...");
        let locations = Object.values(locs);
        let ps = [];
        for (let i = 0; i < locations.length; i++) {
            let currentLoc = this.searchSelectedLocationsWithId(locs, sorted[i]);
            let lCoords = {
                lat: currentLoc.lat,
                lng: currentLoc.lon,
            };
            ps.push(lCoords);
        }
        let currentLoc = this.searchSelectedLocationsWithId(locs, sorted[0]);
        ps.push({ //This accounts for returning to first
            lat: currentLoc.lat,
            lng: currentLoc.lon,
        });

        let newPolylines = [{
            path: ps,
            geodesic: true,
            strokeColor: '#FF0000',
            strokeOpacity: 0.5,
            strokeWeight: 2
        }];
        this.setState({
            polylines: newPolylines
        });
        console.log("New polylines created:", Object.values(newPolylines));
    }

    updateInfoWindows(sorted) {
        let newMap = {};
        for (let i = 0; i < sorted.length; i++) {
            newMap[sorted[i]] = false;
        }
        this.setState({
            showWindows: newMap,
        });
    }

    showWindow(id) {
        map[id] = true;
        this.setState({
            showWindows: map,
        });
        console.log("Id:", id, "now true");
    }

    searchSelectedLocationsWithId(locs, id) {
        let locations = Object.values(locs);
        for (let i = 0; i < locations.length; i++) {
            if (id === locations[i].id) {
                return locations[i];
            }
        }
        return undefined;
    }

    render() {
        let updateMe = this.updateMarkers.bind(this);
        let popup;
        return (
            <div style={{height: '100%'}}>
                <GettingStartedGoogleMap
                    update={updateMe}
                    containerElement={
                        <div style={{height: '100%'}}/>
                    }
                    mapElement={
                        <div style={{height: '100%'}}/>
                    }
                    onMapLoad={this.handleMapLoad}
                    onMapClick={this.handleMapClick}
                    markers={this.state.markers}
                    polylines={this.state.polylines}
                    onMarkerClick={this.handleMarkerClick}
                    onMarkerRightClick={this.handleMarkerRightClick}
                    infoWindowBools={this.state.showWindows}
                />
            </div>
        );
    }
}
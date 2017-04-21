import React, {Component} from 'react';

class TripMap extends Component {
    constructor(props) {
        super(props); // this is required
        this.state = {
            name: "",
            locations: {}
        }
    }

    render() {
        /*async defer src = "https://maps.googleapis.com/maps/api/js?key=AIzaSyAV3Y3HUCh0kF9imtHmRBqq28j3Opd29l8&callback=initMap" >*/
        this.initMap();
        return <div id="map" className="trip-map">
        </div>;
    }

    initMap() {
        var map = new google.maps.Map(document.getElementById('map'), {
            zoom: 3,
            center: {lat: 0, lng: -180},
            mapTypeId: 'terrain'
        });

        var flightPlanCoordinates = [
            {lat: 37.772, lng: -122.214},
            {lat: 21.291, lng: -157.821},
            {lat: -18.142, lng: 178.431},
            {lat: -27.467, lng: 153.027}
        ];
        var flightPath = new google.maps.Polyline({
            path: flightPlanCoordinates,
            geodesic: true,
            strokeColor: '#FF0000',
            strokeOpacity: 1.0,
            strokeWeight: 2
        });

        flightPath.setMap(map);
    }
}

export default TripMap;
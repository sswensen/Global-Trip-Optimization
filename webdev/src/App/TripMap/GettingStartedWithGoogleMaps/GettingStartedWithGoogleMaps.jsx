import {withGoogleMap, GoogleMap, Marker} from "react-google-maps";

/*let GettingStartedGoogleMap = withGoogleMap(props => (
    <GoogleMap
        ref={props.onMapLoad}
        defaultZoom={3}
        defaultCenter={{lat: -25.363882, lng: 131.044922}}
        onClick={props.onMapClick}
    >
        {props.markers.map((marker, index) => (
            <Marker
                {...marker}
                onRightClick={() => props.onMarkerRightClick(index)}
            />
        ))}
    </GoogleMap>
));*/

const GettingStartedGoogleMap = withGoogleMap(props => {
    return (
        <GoogleMap
            ref={props.onMapLoad}
            defaultZoom={14}
            defaultCenter={{lat: 40.6944, lng: -73.9213}}
        >
            <Marker
                key={index}
                position={marker.position}
                onClick={() => props.onMarkerClick(marker)}
            />
        </GoogleMap>
    )
});

export default GettingStartedGoogleMap;
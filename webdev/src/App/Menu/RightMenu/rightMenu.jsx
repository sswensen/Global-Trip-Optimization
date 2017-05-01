/**
 * Created by SummitDrift on 4/25/17.
 */
import React from 'react';
import SavedTrip from './SavedTrip/SavedTrip.jsx'
import Dropzone from 'react-dropzone'

class RightMenu extends React.Component {
    constructor(props) {
        super(props); // this is required
        this.state = {}
    }

    render() {
        var mySidenavStylz = {
            complete: {
                width: "29%",
            },
            nope: {
                width: "0px",
            },
        };

        let green = this.props.green;
        let red = this.props.red;
        let kilometers = this.props.kilometers;
        let toggleKilometers = this.props.toggleKilometers;
        let right = this.props.rightMenu;
        //console.log("Rendering RightMenu now...");
        let tripDist = Math.round(this.props.tripDistance);
        let tripDistKilo = Math.round(this.milesToKilometers(this.props.tripDistance));
        let savedTrips = Object.values(this.props.savedTrips);
        let items = savedTrips.map((trip) => {
            let select = this.props.selectTrip.bind(undefined, trip);
            let die = this.props.deleteTrip.bind(undefined, trip);
            //console.log({loc});
            let locs = trip.locations;
            return <SavedTrip {...trip} key={trip.id} select={select} die={die}/>; //Calls to location.jsx
        });
        let twoOpt = this.props.toggleTwoOpt.bind(this);
        let threeOpt = this.props.toggleThreeOpt.bind(this);
        return <div className="rightSideChick">
            <div id="mySidenav-right" className="sidenav-right"
                 style={ (right) ? mySidenavStylz.complete : mySidenavStylz.nope }>
                <div className="options">
                    <button className="two-opt-button" onClick={twoOpt}>2-opt</button>
                    <button className="three-opt-button" onClick={threeOpt}>3-opt</button>
                    <button className="three-opt-button" onClick={toggleKilometers}>Units</button>
                    <Dropzone className="dropzone-style" onDrop={this.drop.bind(this)}>
                        <p>drag in file or click</p>
                    </Dropzone>
                    <span className="total-trip-distance">Distance: {kilometers ? tripDistKilo : tripDist} {kilometers ? " km" : " miles"}</span>
                </div>
                <div className="saved-trips">
                    <div className="saved-trips-list-div">
                        <ul className="saved-trips-list">
                            {items}
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    }

    milesToKilometers(miles) {
        return miles * 1.6;
    }

    drop(acceptedFiles) {
        console.log("Accepting drop");
        this.props.red();
        acceptedFiles.forEach(file => {
            console.log("Filename:",file.name,"File:",file);
            console.log(JSON.stringify(file));
            let fr = new FileReader();
            fr.onload = (function(theFile) {
                return function(e) {
                    // Render thumbnail.
                    let JsonObj = JSON.parse(e.target.result);
                    console.log(JsonObj);
                    this.props.browseFile(JsonObj)
                };
            })(file).bind(this);

            fr.readAsText(file);
        });
    }
}


export default RightMenu
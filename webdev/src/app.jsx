import React from 'react';
import LocationSearch from './location_search.jsx';
import TripMap from './trip_map.jsx';
import TripPlanner from "./trip_planner.jsx";

let Sel = ({locations}) => <div>
    {locations.map(l => <li key={l.id}>{l.name}</li>)}
</div>

class App extends React.Component {
    constructor(props) {
        super(props); // this is required
        this.state = {
            selectedLocations: {}
        }
    }

    render() {
        return <div>
            <div>
                <LocationSearch selectLocation={this.selectLocation.bind(this)}/>
                <TripPlanner locations={Object.values(this.state.selectedLocations)}/>
            </div>
            <Sel locations={Object.values(this.state.selectedLocations)}/>
        </div>
    }

    selectLocation(loc) {
        let obj = {};
        obj[loc.id] = loc;
        let newMap = Object.assign({},
            this.state.selectedLocations,
            obj);
        this.setState({
            selectedLocations: newMap
        })
    }
}

export default App;
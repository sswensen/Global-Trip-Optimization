import React from 'react';
import LocationSearch from './location_search.jsx';

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
            <LocationSearch selectLocation={this.selectLocation.bind(this)} />
            <Sel locations={Object.values(this.state.selectedLocations)} />
        </div>
    }

    selectLocation(loc) {
        let obj  = {};
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
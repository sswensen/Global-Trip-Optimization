import React, { Component } from 'react';
import Location from './location.jsx';

class LocationSearch extends Component {
    constructor(props) {
        super(props); // this is required
        this.state = {
            name: "",
            locations: {}
        }
    }

    render() {

        let locations = Object.values(this.state.locations);
        let items = locations.map((loc) => {
            let select = this.props.selectLocation.bind(undefined, loc);
            return <Location {...loc} key={loc.id} select={select} />;
        });
        let array = [];
        /*
        let foo = locations.map(function(location) {
            return <li>location</li>;
        })
        */

        return <div>
            <input type="text"
                   onKeyUp={this.keyUp.bind(this)} />
            <button onClick={this.clear.bind(this)}>Clear Locations</button>
            <ul className="locations-list">
                {items}
            </ul>
          <button onClick={this.fetch.bind(this)}>Click me</button>
        </div>;
    }

    keyUp(event) {
        if(event.which == 13) {
            this.fetch(event.target.value);
        }
    }

    async fetch(query) {
        try {
            console.log("Fetching...");
            let stuff = await fetch(`http://localhost:4567/hello?q=${query}`);
            console.log("Fetched");
            let json = await stuff.json();
            let obj = {};
            json.forEach(elem => obj[elem.id] = elem);
            this.setState({
                locations: obj
            });
        }
        catch(e) {
            console.error(e);
        }
    }

    remove(id) {
        let newObj = Object.assign(this.state.locations);
        delete newObj[id];
        this.setState({
            locations: newObj
        })
    }

    clear() {
        this.setState({
            locations: {}
        })
    }
}

// let our other modules use this
export default LocationSearch;
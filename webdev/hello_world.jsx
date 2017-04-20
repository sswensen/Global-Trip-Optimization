import React, { Component } from 'react';

class HelloWorld extends Component {
    constructor(props) {
        super(props); // this is required
        this.state = {
            name: ""
        }
    }

    render() {
        return <div>
          <h1>
            Hello, {this.state.name}!
          </h1>
            {/*
             the `this.change.bind(this)` creates a version of this.change that
             will always be called with `this` set to the current version of `this`.
             JavaScript `this` scoping is kind of awful, because it depends on
             how you call the function. If you don't bind it, it will be called
             with `this` set to null when the event runs, breaking everything. */}
          <input type="text"
                 value={this.state.name}
                 onChange={this.change.bind(this)} />
        </div>;
    }

    change(event) {
        this.setState({
            name: event.target.value
        });
    }
}

// let our other modules use this
export default HelloWorld;
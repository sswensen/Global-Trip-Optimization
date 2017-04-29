// Link.react-test.js
import React from 'react';
import App from './app.jsx';

describe("App component - the static output approach", () => {
    beforeEach(function() {
        let {TestUtils} = React.addons;

        this.component = TestUtils.renderIntoDocument(<App />);
        this.renderedDOM = () => React.findDOMNode(this.component);
    });

    it("renders a div which greets someone", function() {
        let renderedParagraphs = this.renderedDOM().querySelectorAll("div");

        expect(this.renderedDOM().children.length).toEqual(1);
        expect(selectedLocations.length).toEqual(0);
    });
});
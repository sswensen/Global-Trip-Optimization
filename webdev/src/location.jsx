import React, { Component } from 'react';

let Location = ({name, municipality, region, country, select}) => <li className="location">
    <h5>{name}</h5>
    <div className="other-info">
        {municipality}, {region}, {country}
    </div>
    <button onClick={select}>Select Me</button>
</li>;

export default Location;
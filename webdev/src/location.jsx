import React, {Component} from 'react';

let Location = ({name, municipality, region, country, select}) => <li className="location">
    <div>
        <div className="add-button-div">
            <button className="add-button" onClick={select}>Add</button>
        </div>
        <div className="location-info">
            <h5>{name}</h5>
            <div className="other-info">
                {municipality}, {region}, {country}
            </div>
        </div>
    </div>
</li>;

export default Location;
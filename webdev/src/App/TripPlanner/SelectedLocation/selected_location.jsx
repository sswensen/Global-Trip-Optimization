import React, {Component} from 'react';

let SelectedLocation = ({name, municipality, region, country, airportUrl, countryUrl, regionUrl, remove}) => <li
    className="selected-location">
    <div>
        <div className="remove-button-div">
            <button className="remove-button" onClick={remove}>Remove</button>
        </div>
        <div className="location-info">
            <a target="_blank" href={"" + airportUrl + ""}>
                <h5>{name}</h5>
            </a>
            <div className="other-info">
                {municipality}, <a target="_blank" href={"" + regionUrl + ""}>{region}</a>, <a
                target="_blank" href={"" + countryUrl + ""}>{country}</a>
            </div>
        </div>
    </div>
</li>;

export default SelectedLocation;
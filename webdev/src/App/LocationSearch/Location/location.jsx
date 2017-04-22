import React, {Component} from 'react';

let Location = ({name, municipality, region, country, airportUrl, countryUrl, regionUrl, select}) => <li
    className="location">
    <div>
        <div className="add-button-div">
            <button className="add-button" onClick={select}>Add</button>
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

export default Location;
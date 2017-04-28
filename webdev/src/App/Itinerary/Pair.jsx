/**
 * Created by Sandeep on 4/26/2017.
 */
import React, {Component} from 'react';

let Pair = ({one, two}) => <li
    className="pair">
    <div>
        <div className="location-one">
            <button className="add-button" onClick={one.select}>Add</button>
        </div>
        <div className="location-info-one">
            <a target="_blank" href={"" + one.airportUrl + ""}>
                <h5>{one.name}</h5>
            </a>
            <div className="other-info-one">
                {one.municipality}, <a target="_blank" href={"" + one.regionUrl + ""}>{one.region}</a>, <a
                target="_blank" href={"" + one.countryUrl + ""}>{one.country}</a>
            </div>
        </div>
        <div className="location-two">
            <button className="add-button" onClick={two.select}>Add</button>
        </div>
        <div className="location-info-two">
            <a target="_blank" href={"" + two.airportUrl + ""}>
                <h5>{two.name}</h5>
            </a>
            <div className="other-info-two">
                {two.municipality}, <a target="_blank" href={"" + two.regionUrl + ""}>{two.region}</a>, <a
                target="_blank" href={"" + two.countryUrl + ""}>{two.country}</a>
            </div>
        </div>
    </div>
</li>;

export default Pair;
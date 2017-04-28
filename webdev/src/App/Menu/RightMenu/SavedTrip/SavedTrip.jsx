/**
 * Created by SummitDrift on 4/27/17.
 */
import React, {Component} from 'react';

let SavedTrip = ({name, totalDistance, select}) => <li
    className="saved-trip">
    <div className="load-trip-button-div">
        <button className="load-trip-button" onClick={select}>Load</button>
    </div>
    <div className="trip-info">
        <h5>{name}</h5>
        <div className="other-info">
            {totalDistance}
        </div>
    </div>
</li>;

export default SavedTrip;
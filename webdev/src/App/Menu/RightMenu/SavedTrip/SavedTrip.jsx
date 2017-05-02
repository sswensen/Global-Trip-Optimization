/**
 * Created by SummitDrift on 4/27/17.
 */
import React, {Component} from 'react';

let SavedTrip = ({name, totalDistance, select, kilometers, die}) => <li
    className="saved-trip">
    <div className="container">
        <div className="buttons">
            <div className="delete-trip-button-div">
                <button className="delete-trip-button" onClick={die}>X</button>
            </div>
            <div className="load-trip-button-div">
                <button className="load-trip-button" onClick={select}>Load</button>
            </div>
        </div>
        <div className="trip-info">
            <h5>{name}</h5>
            <div className="other-info">
                {kilometers ? Math.round(1.6*totalDistance) : Math.round(totalDistance)}
            </div>
        </div>
    </div>
</li>;

export default SavedTrip;
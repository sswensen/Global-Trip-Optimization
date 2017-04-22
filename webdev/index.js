import React from 'react';
import App from './src/App/app.jsx';
import ReactDOM from 'react-dom';
import { createStore } from 'redux'
import './src/App/style.scss';


// run the function when the webpage finishes loading
document.addEventListener("DOMContentLoaded", (event) => {
	console.log("Loaded");
  ReactDOM.render(<App />,
    document.getElementById("react-container"));
});

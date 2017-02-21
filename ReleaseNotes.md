Sprint 1
DTR-07

Overview:
A command line interface must accept the location csv filename as an argument.
The resulting XML and SVG files should have the same root filename with different suffixes in the current working directory.
Optional command line option –m shows mileage on the legs of the trip.
Optional command line option –i shows the id on the locations in the map.
Optional command line option –n show the name on the locations in the map.

Purpose:
Takes a list of Latitude/Longitude locations and creates an SVG trip file and an XML itinerary.

Issue Summary:
State County lines not included in SVG file.

Notes:
Nearest Neighbour Algorithm: https://en.wikipedia.org/wiki/Nearest_neighbour_algorithm
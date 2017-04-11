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



Sprint 2
DTR-07

Overview:
A command line interface must except a location csv filename and an xml file containing ids of locations as arguments.
Optional command line option –m shows mileage on the legs of the trip.
Optional command line option –i shows the id on the locations in the map.
Optional command line option –n show the name on the locations in the map.
Optional command line option –2 enables 2-opt to the trip.
Optional command line option –3 enables 3-opt to the trip.
Optional command line option –g enables the GUI.
When the -g option is enabled, the user will be presented all the available command line options inside the GUI.
Optional command line option –f creates the XML and SVG files to the current working directory.
When -f is enabled, the GUI will not be displayed to the user.

Purpose:
Takes a list of destinations the user can select from and creates an SVG trip file and an XML itinerary.
The user will also be presented a GUI, in which they will be given options such as displaying mileage between, 
ids, and names of locations in the trip they create. The user will also be able to save, modify, and select from 
previous trips and view the corresponding SVG and XML files.

Notes:
2-opt implementation: https://en.wikipedia.org/wiki/2-opt

Sprint 3
DTR-07

Overview:
A command line interface that accepts an svg file containing the background for the GUI and an optional xml file that
contains id names of locations.
Optional command line option –d that shows the mileage on th legs of the trip.
Optional command line option -m displays the mileage of the legs in miles.
Optional command line option -k displays the mileage of the legs in kilometers.
Optional command line option –i shows the id on the locations in the map.
Optional command line option –n show the name on the locations in the map.
Optional command line option –2 enables 2-opt to the trip.
Optional command line option –3 enables 3-opt to the trip.
Optional command line option –g enables the GUI.
When the -g option is enabled, the user will be presented all the available command line options inside the GUI.
Inside the GUI, the user will have the option of selecting an xml file.
Users will have the ability to choose specific destinations from a series of dropdown menus.
Users will be able to save and load trips as well as modify existing trips.
Optional command line option –f creates the XML and SVG files to the current working directory.
When -f is enabled, the GUI will not be displayed to the user.

Purpose:
Takes a list of ids from an xml file if provided, otherwise 100 ids are taken from a database.
The user will be presented a GUI in which they will be able to select desired locations. The user will be able to choose
locations after sorting them by either airport size, continent, country, or region. The user will also be able to search
the database with text entry boxes for municipality and airport name. Once the trip is drawn, the user will be able to
then modify the trip by displaying the mileage between locations, ids, names of locations, as well as selecting whether
to modify their trip using twoOpt or threeOpt optimizations. Finally the user will be able to save, modify, and select
previous trips.

Notes:



package com.csu2017sp314.dtr07.Model;

/**
 * Created by SummitDrift on 2/13/17.
 *
 * @author Scott Swensen
 *         Class for holding information on a single location
 */

public class Location {
    private String id;
    private String name;
    private double lat;
    private double lon;
    private String municipality;
    private String region;
    private String country;
    private String continent;
    private String airportUrl;
    private String regionUrl;
    private String countryUrl;
    private int nearest = -1;
    private int nearestDistance = 9999999;
    private int tableIndex;
    private boolean pairUsesWraparound = false;

    Location(String id, String name, String lat, String lon, String municipality, String region, String country, String continent, String aUrl, String rUrl, String cUrl) {
        this.id = id;
        this.name = name;
        this.lat = convertCoordinates(lat);
        this.lon = convertCoordinates(lon);
        this.municipality = municipality;
        this.region = region;
        this.country = country;
        this.continent = continent;
        this.airportUrl = aUrl;
        this.regionUrl = rUrl;
        this.countryUrl = cUrl;
    }

    private static double convertCoordinates(String in) {
        double ret;
        int deg = in.indexOf('\u00b0');
        int pr = in.indexOf('\'');
        int dpr = in.indexOf('\"');
        String card = in.substring(in.length() - 1);
        if(deg > -1 && pr > -1 && dpr > -1) {
            final double d = Double.parseDouble(in.substring(0, deg));
            final double m = Double.parseDouble(in.substring(deg + 1, pr));
            final double s = Double.parseDouble(in.substring(pr + 1, dpr));
            //do math
            ret = d + (m / 60) + (s / 3600);
        } else if(deg > -1 && pr > -1 && dpr < 0) {
            final double d = Double.parseDouble(in.substring(0, deg));
            final double m = Double.parseDouble(in.substring(deg + 1, pr));
            ret = d + (m / 60);
        } else if(deg > -1 && pr < 0 && dpr < 0) {
            ret = Double.parseDouble(in.substring(0, deg));
        } else {
            ret = Double.parseDouble(in);
        }
        switch(card) {
            case "N":
            case "E":
                //do shit
                break;
            case "S":
            case "W":
                ret *= -1;
                break;
            default:
                break;
        }

        return ret;
    }

    private double haversine(double lat1, double lng1, double lat2, double lng2) {
        int r = 6371; // average radius of the earth in km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                        * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = r * c;
        return d;
    }

    public double distance4(double lat1, double lat2, double lon1,
                            double lon2, double el1, double el2) {

        final int R = 6371; // Radius of the earth

        Double latDistance = Math.toRadians(lat2 - lat1);
        Double lonDistance = Math.toRadians(lon2 - lon1);
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance) / 1609.34;
    }

    double distance(Location in, String unit) {
        double lat1 = this.lat;
        double lon1 = this.lon;
        double lat2 = in.getLat();
        double lon2 = in.getLon();
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515; //Default is miles ("M")

        //double hdist = haversine(lat1, lon1, lat2, lon2);
        //double fdist = distance4(lat1, lat2, lon1, lon2, 0, 0);

        //--------------Checking for wraparound-------------//
        double wlat1 = this.lat;
        double wlon1 = this.lon; //We are changing longitude
        double wlat2 = in.getLat();
        double wlon2 = in.getLon();
        if(wlon1 < wlon2) {
            double wlattemp = wlat1;
            double wlontemp = wlon1;
            wlat1 = wlat2;
            wlon1 = wlon2;
            wlat2 = wlattemp;
            wlon2 = wlontemp;
        }
        //wlon1 -= 180; //TODOdone currently dist is the distance with wraparound, wdist is just using pathagorean theorem. dist is somehow always smaller. It seems like the algorithm used is not getting the same distance, maybe it has to do with the curvature of the earth but the map is flat so that doesnt make sense.
        //wlon2 += 180;
        /*double wtheta = wlon1 - wlon2;
        double wdist = Math.sin(deg2rad(wlat1)) * Math.sin(deg2rad(wlat2))
                + Math.cos(deg2rad(wlat1)) * Math.cos(deg2rad(wlat2)) * Math.cos(deg2rad(wtheta));
        wdist = Math.acos(wdist);
        wdist = rad2deg(wdist); //Here the value is still in longitude/latitude*/
        /*double wdist = Math.sqrt(Math.pow((wlat1-wlat2), 2) + Math.pow((wlon1 -wlon2), 2));
        wdist = wdist * 60 * 1.1515; //Default is miles ("M") //We can move this line, as well as the one above to after both checks. Not done yet because of debugging purposes
        dist = Math.round(dist);
        wdist = Math.round(wdist);
        System.out.println("dist: " + dist);
        System.out.println("wdist: " + wdist);
        System.out.println("hdist: " + hdist);
        System.out.println("fdist: " + fdist);*/
        //if(wdist > dist) {
        if(wlon1 - wlon2 > 180) {
            this.pairUsesWraparound = true;
            //System.out.println("Using wraparound");
        } else {
            this.pairUsesWraparound = false;
            //dist = wdist; //Should be equal here
        }
        //------------End Checking for wraparound-----------//


        if(unit.equals("K")) { //Kilometers
            dist = dist * 1.609344;
        } else if(unit.equals("N")) { //Nautical miles
            dist = dist * 0.8684;
        }
        //Add Math.round
        return Math.round(dist);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::	This function converts decimal degrees to radians			:*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::	This function converts radians to decimal degrees			:*/
	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

    public int getTableIndex() {
        return this.tableIndex;
    }

    public void setTableIndex(int tableIndex) {
        this.tableIndex = tableIndex;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    double getLat() {
        return lat;
    }

    double getLon() {
        return lon;
    }

    public String getMunicipality() {
        return municipality;
    }

    public String getRegion() {
        return region;
    }

    public String getCountry() {
        return country;
    }

    public String getContinent() {
        return continent;
    }

    public String getAirportUrl() {
        return airportUrl;
    }

    public String getRegionUrl() {
        return regionUrl;
    }

    public String getCountryUrl() {
        return countryUrl;
    }

    int getNearest() {
        return nearest;
    }

    void setNearest(int nearest) {
        this.nearest = nearest;
    }

    int getNearestDistance() {
        return nearestDistance;
    }

    void setNearestDistance(int nearestDistance) {
        this.nearestDistance = nearestDistance;
    }

    public boolean isPairUsesWraparound() {
        return pairUsesWraparound;
    }

    @Override
    public boolean equals(final Object o) {
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }

        Location location = (Location) o;

        if(Double.compare(location.lat, lat) != 0) {
            return false;
        }
        if(Double.compare(location.lon, lon) != 0) {
            return false;
        }
        if(nearest != location.nearest) {
            return false;
        }
        if(nearestDistance != location.nearestDistance) {
            return false;
        }
        if(id != null ? !id.equals(location.id) : location.id != null) {
            return false;
        }
        return name != null ? name.equals(location.name) : location.name == null;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return "Location{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", lat=" + lat
                + ", lon=" + lon
                + ", nearest=" + nearest
                + ", nearestDistance=" + nearestDistance
                + '}';
    }
}
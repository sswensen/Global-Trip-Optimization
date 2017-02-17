package Model;

/**
 * Created by SummitDrift on 2/13/17.
 */

public class Location {
    private String id;
    private String name;
    private String city;
    private double lat;
    private double lon;
    private double alt;
    private int nearest;
    private double nearestDistance;

    Location(String id, String name, String city, String lat, String lon, String alt) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.lat = convertCoordinates(lat);
        this.lon = convertCoordinates(lon);
        this.alt = Double.parseDouble(alt);
    }

    private static double convertCoordinates(String in) {
        int deg = in.indexOf('\u00b0');
        int pr = in.indexOf('\'');
        int dpr = in.indexOf('\"');
        String card = in.substring(in.length()-1);

        double d = Double.parseDouble(in.substring(0, deg));
        double m = Double.parseDouble(in.substring(deg+1, pr));
        double s = Double.parseDouble(in.substring(pr+1, dpr));
        //do math
        double ret = d + (m/60) + (s/3600);
        switch(card) {
            case "N":
            case "W":
                //do shit
                break;
            case "S":
            case "E":
                ret *= -1;
                break;
        }
        return ret;
    }

    public double distance(Location in) {
        String unit = "K";
        double lat1 = this.lat;
        double lon1 = this.lon;
        double lat2 = in.getLat();
        double lon2 = in.getLon();
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == "K") {
            dist = dist * 1.609344;
        } else if (unit == "N") {
            dist = dist * 0.8684;
        }
        return (dist);
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getAlt() {
        return alt;
    }

    public void setAlt(double alt) {
        this.alt = alt;
    }

    public int getNearest() {
        return nearest;
    }

    public void setNearest(int nearest) {
        this.nearest = nearest;
    }

    public double getNearestDistance() {
        return nearestDistance;
    }

    public void setNearestDistance(double nearestDistance) {
        this.nearestDistance = nearestDistance;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Location location = (Location)o;
        return (this.id.equals(location.id) &&
                this.id.equals(location.id) &&
                this.name.equals(location.name) &&
                this.name.equals(location.name) &&
                this.lat == (location.lat) &&
                this.lon == (location.lon) &&
                this.alt == (location.alt));
    }

    public String toString() {
        return "Location{id='" + this.id + '\'' + ", name='" + this.name + '\'' + ", city='" + this.city + '\'' + ", lat='" + this.lat + '\'' + ", lon='" + this.lon + '\'' + ", alt='" + this.alt + '\'' + '}';
    }
}
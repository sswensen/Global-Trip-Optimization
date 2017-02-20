package Model;

/**
 * Created by SummitDrift on 2/13/17.
 */

public class Location {
    private int id;
    private String name;
    private double lat;
    private double lon;
    private int nearest = -1;
    private int nearestDistance = 9999999;

    Location(int id, String name, String lat, String lon) {
        this.id = id;
        this.name = name;
        this.lat = convertCoordinates(lat);
        this.lon = convertCoordinates(lon);
    }

    private static double convertCoordinates(String in) {
        double ret = -1;
        int deg = in.indexOf('\u00b0');
        int pr = in.indexOf('\'');
        int dpr = in.indexOf('\"');
        String card = in.substring(in.length()-1);
        if(deg > -1 && pr > -1 && dpr > -1) {
            double d = Double.parseDouble(in.substring(0, deg));
            double m = Double.parseDouble(in.substring(deg + 1, pr));
            double s = Double.parseDouble(in.substring(pr + 1, dpr));
            //do math
            ret = d + (m / 60) + (s / 3600);
        } else if(deg > -1 && pr > -1 && dpr < 0) {
            double d = Double.parseDouble(in.substring(0, deg));
            double m = Double.parseDouble(in.substring(deg + 1, pr));
            ret = d + (m / 60);
        } else if(deg > -1 && pr < 0 && dpr < 0) {
            double d = Double.parseDouble(in.substring(0, deg));
            ret = d;
        } else {
            double d = Double.parseDouble(in);
            ret = d;
        }
        switch (card) {
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
        String unit = "M";
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getNearest() {
        return nearest;
    }

    public void setNearest(int nearest) {
        this.nearest = nearest;
    }

    public int getNearestDistance() {
        return nearestDistance;
    }

    public void setNearestDistance(int nearestDistance) {
        this.nearestDistance = nearestDistance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        if (id != location.id) return false;
        if (Double.compare(location.lat, lat) != 0) return false;
        if (Double.compare(location.lon, lon) != 0) return false;
        return name != null ? name.equals(location.name) : location.name == null;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                ", nearest=" + nearest +
                ", nearestDistance=" + nearestDistance +
                '}';
    }
}
package Model;

/**
 * Created by SummitDrift on 2/13/17.
 */

public class Location {
    String id;
    String name;
    String city;
    double lat;
    double lon;
    double alt;

    public Location(String id, String name, String city, String lat, String lon, String alt) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.lat = convertCoordinates(lat);
        this.lon = convertCoordinates(lon);
        this.alt = Double.parseDouble(alt);
    }

    private static double convertCoordinates(String in) {
        /*String tempLat = lat;
        String tempLon = lon;

        //40°35'10.68"N
        int degLat = tempLat.indexOf('\u00b0');
        int degLon = tempLon.indexOf('\u00b0');
        int prLat = tempLat.indexOf('\'');
        int prLon = tempLon.indexOf('\'');
        int dprLat = tempLat.indexOf('\"');
        int dprLon = tempLon.indexOf('\"');
        String cardLat = tempLat.substring(tempLat.length()-1);
        String cardLon = tempLat.substring(tempLat.length()-1);

        String D = tempLat.substring(0, tempLat.indexOf('\u00b0')); //Index of °
        */

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
            case "E":
                //do shit
                break;
            case "S":
            case "W":
                ret *= -1;
                break;
        }
        return ret;
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

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Location location = (Location)o;
        if(this.id.equals(location.id) &&
                this.id.equals(location.id) &&
                this.name.equals(location.name) &&
                this.name.equals(location.name) &&
                this.lat == (location.lat) &&
                this.lon == (location.lon) &&
                this.alt == (location.alt)) {
            return true;
        } else {
            return false;
        }
    }

    public String toString() {
        return "Location{id='" + this.id + '\'' + ", name='" + this.name + '\'' + ", city='" + this.city + '\'' + ", lat='" + this.lat + '\'' + ", lon='" + this.lon + '\'' + ", alt='" + this.alt + '\'' + '}';
    }
}
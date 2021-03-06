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
    private int tableIndex;


    public Location(String id, String name, String lat, String lon,
                    String municipality, String region, String country, String continent,
                    String aUrl, String rUrl, String cUrl) {
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

    public double distance(Location in) {
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
        //------------End Checking for wraparound-----------//

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

    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
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
        /*
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
        */

        return "Location{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", lat=" + lat
                + ", municipality=" + municipality
                + ", region=" + region
                + ", country=" + country
                + ", continent=" + continent
                + ", airportUrl=" + airportUrl
                + ", regionUrl=" + regionUrl
                + ", countryUrl=" + countryUrl
                + '}';
    }
}
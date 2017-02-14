package Model;

/**
 * Created by SummitDrift on 2/13/17.
 */

public class Location {
    String id;
    String name;
    String city;
    String lat;
    String lon;
    String alt;

    public Location(String id, String name, String city, String lat, String lon, String alt) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.lat = lat;
        this.lon = lon;
        this.alt = alt;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLat() {
        return this.lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return this.lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getAlt() {
        return this.alt;
    }

    public void setAlt(String alt) {
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
        if (this.id != null ? !this.id.equals(location.id) : location.id != null) {
            return false;
        }
        if (this.name != null ? !this.name.equals(location.name) : location.name != null) {
            return false;
        }
        if (this.city != null ? !this.city.equals(location.city) : location.city != null) {
            return false;
        }
        if (this.lat != null ? !this.lat.equals(location.lat) : location.lat != null) {
            return false;
        }
        if (this.lon != null ? !this.lon.equals(location.lon) : location.lon != null) {
            return false;
        }
        return this.alt != null ? this.alt.equals(location.alt) : location.alt == null;
    }

    public int hashCode() {
        int result = this.id != null ? this.id.hashCode() : 0;
        result = 31 * result + (this.name != null ? this.name.hashCode() : 0);
        result = 31 * result + (this.city != null ? this.city.hashCode() : 0);
        result = 31 * result + (this.lat != null ? this.lat.hashCode() : 0);
        result = 31 * result + (this.lon != null ? this.lon.hashCode() : 0);
        result = 31 * result + (this.alt != null ? this.alt.hashCode() : 0);
        return result;
    }

    public String toString() {
        return "Location{id='" + this.id + '\'' + ", name='" + this.name + '\'' + ", city='" + this.city + '\'' + ", lat='" + this.lat + '\'' + ", lon='" + this.lon + '\'' + ", alt='" + this.alt + '\'' + '}';
    }
}
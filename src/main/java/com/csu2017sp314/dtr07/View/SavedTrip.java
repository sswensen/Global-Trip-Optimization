package com.csu2017sp314.dtr07.View;

import java.util.ArrayList;

/**
 * Created by SummitDrift on 4/8/17.
 */

public class SavedTrip {
    private String name;
    private ArrayList<String> ids;
    private ArrayList<String> names;
    private ArrayList<GUILocation> locations;

    public SavedTrip(String name, ArrayList<String> ids, ArrayList<String> names) {
        this.name = name;
        this.ids = ids;
        this.names = names;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getIds() {
        return ids;
    }

    public void setIds(ArrayList<String> ids) {
        this.ids = ids;
    }

    public ArrayList<String> getNames() {
        return names;
    }

    public void setNames(ArrayList<String> names) {
        this.names = names;
    }

    public String getId(int index) {
        return ids.get(index);
    }

    public String getName(int index) {
        return names.get(index);
    }

    public ArrayList<GUILocation> getLocations() {
        return locations;
    }

    public void setLocations(ArrayList<GUILocation> locations) {
        this.locations = locations;
    }
}

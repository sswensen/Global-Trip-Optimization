package Model;

/*
 * Created by SummitDrift on 2/13/17.
 * Factory for creation of locations and pairs
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

class LocationFactory {
    //private static final char DEFAULT_SEPARATOR = ',';
    //private static final char DEFAULT_QUOTE = '\"';
    private ArrayList<Location> locations = new ArrayList<Location>();
    private ArrayList<Location> originalLocations;
    private ArrayList<Pair> pairs = new ArrayList<>();
    private ArrayList<Pair> bestPairs = new ArrayList<>();

    boolean readFile(String in) throws FileNotFoundException {
        Scanner scan = new Scanner(new File(in));
        int id = -1;
        int name = -1;
        int latitude = -1;
        int longitude = -1;
        if(scan.hasNext()) {
            String[] line = scan.nextLine().split(",");
            for(int x = 0; x < line.length; x++) {
                String temp = line[x];
                if(temp.equalsIgnoreCase("id")) {
                    id = x;
                }
                if(temp.equalsIgnoreCase("name")) {
                    name = x;
                }
                if(temp.equalsIgnoreCase("latitude")) {
                    latitude = x;
                }
                if(temp.equalsIgnoreCase("longitude")) {
                    longitude = x;
                }
            }
        }
        while (scan.hasNext()) {
            String[] line = scan.nextLine().split(",");
            //System.out.println("[id= " + line[0] + ", name= " + line[1] + " , city=" + line[2] + " , lat=" + line[3] + " , lon=" + line[4] + " , alt=" + line[5] + "]");
            Location temp = new Location(Integer.parseInt(line[id]), line[name], line[latitude].replaceAll("\\s+",""), line[longitude].replaceAll("\\s+",""));
            locations.add(temp);
        }
        scan.close();
        return locations.size() > 0;
    }

    /*
    boolean secondTry() {
        for(int x = 0; x < locations.size()-1; x++) {
            double distance = 999999999;
            int index = -1;
            for(int y = x+1; y < locations.size(); y++) {
                double temp = locations.get(x).distance(locations.get(y));
                if(distance > temp) {
                    distance = temp;
                    index = y;
                }
            }
            Location temploc = locations.get(x+1);
            locations.set(x+1, locations.get(index));
            locations.set(index, temploc);
            pairs.add(new Pair(x, locations.get(x), locations.get(x+1), locations.get(x).distance(locations.get(x+1))));
        }
        pairs.add(new Pair(locations.size()-1, locations.get(locations.size()-1), locations.get(0), locations.get(locations.size()-1).distance(locations.get(0))));
        return true;
    }
    */

    boolean thirdTry() {
        int bestDistance = 999999999;
        int sizer = locations.size();
        for(int i = 0; i < sizer; i++) {
            originalLocations = new ArrayList<>(locations);
            for (int x = 0; x < locations.size() - 1; x++) {
                double distance = 999999999;
                int index = -1;
                for (int y = x + 1; y < locations.size(); y++) {
                    double temp = locations.get(x).distance(locations.get(y));
                    if (distance > temp) {
                        distance = temp;
                        index = y;
                    }
                }
                Location temploc = locations.get(x + 1);
                locations.set(x + 1, locations.get(index));
                locations.set(index, temploc);
                pairs.add(new Pair(x, locations.get(x), locations.get(x + 1), locations.get(x).distance(locations.get(x + 1))));
            }
            pairs.add(new Pair(locations.size() - 1, locations.get(locations.size() - 1), locations.get(0), locations.get(locations.size() - 1).distance(locations.get(0))));
            double total = 0;
            for(Pair p : pairs) {
                total += p.getDistance();
            }
            //System.out.println("[" + i + "]: " + total);
            if(total < bestDistance) {
                bestDistance = Math.round(total);
                bestPairs = new ArrayList<>(pairs);
            }
            pairs.clear();
            locations = new ArrayList<>(originalLocations);
            Location temp = locations.remove(0);
            locations.add(temp);
        }
        pairs = new ArrayList<>(bestPairs);
        return true;
    }

    /*
    boolean findNearest() {
        boolean ret = false;
        double distance;
        int s1 = -1;
        int s2 = -1;
        int ind = 0;
        for(int i = 0; i < locations.size(); i++) {
            distance = 9999999;
            for(int j = 0; j < locations.size(); j++) {
                if(i != j) {
                    double temp = Math.abs(locations.get(i).distance(locations.get(j)));
                    if(temp < distance) {
                        distance = temp;
                        s1 = i;
                        s2 = j;
                        ret = true;
                    }
                }
            }
            //add nearest to
            if(s1 < locations.size()) {
                if(locations.get(s2).getNearest() < 0) {
                    if (locations.get(s1).getNearestDistance() > distance) {
                        locations.get(s1).setNearest(locations.get(s2).getId());
                        locations.get(s1).setNearestDistance((int) Math.round(distance));
                        pairs.add(new Pair(++ind, locations.get(s1), locations.get(s2), Math.round(distance)));
                    } else {
                        System.out.println("UHHHHHHHHHHHHHHHHHHHHHHHHh");
                    }
                    Location tempLoc = locations.get(s1 + 1);
                    locations.set(s1 + 1, locations.get(s2));
                    locations.set(s2, tempLoc);
                } else {
                    locations.get(s1).setNearest(locations.get(s2).getId());
                    locations.get(s1).setNearestDistance((int) Math.round(distance));
                    pairs.add(new Pair(++ind, locations.get(s1), locations.get(s2), Math.round(distance)));
                }
            } else {
                locations.get(s1).setNearest(locations.get(s2).getId());
                locations.get(s1).setNearestDistance((int) Math.round(distance));
                pairs.add(new Pair(++ind, locations.get(s1), locations.get(s2), Math.round(distance)));
            }
        }
        return ret;
    }
    */

    ArrayList<Location> getLocations() {
        return locations;
    }

    ArrayList<Pair> getPairs() {
        return pairs;
    }

    /*
    private static List<String> parseLine(String cvsLine) {
        return LocationFactory.parseLine(cvsLine, ',', '\"');
    }

    private static List<String> parseLine(String cvsLine, char separators) {
        return LocationFactory.parseLine(cvsLine, separators, '\"');
    }

    private static List<String> parseLine(String cvsLine, char separators, char customQuote) {
        char[] chars;
        ArrayList<String> result = new ArrayList<String>();
        if (cvsLine == null && cvsLine.isEmpty()) {
            return result;
        }
        if (customQuote == ' ') {
            customQuote = (char)34;
        }
        if (separators == ' ') {
            separators = (char)44;
        }
        StringBuffer curVal = new StringBuffer();
        boolean inQuotes = false;
        boolean startCollectChar = false;
        boolean doubleQuotesInColumn = false;
        for (char ch : chars = cvsLine.toCharArray()) {
            if (inQuotes) {
                startCollectChar = true;
                if (ch == customQuote) {
                    inQuotes = false;
                    doubleQuotesInColumn = false;
                    continue;
                }
                if (ch == '\"') {
                    if (doubleQuotesInColumn) continue;
                    curVal.append(ch);
                    doubleQuotesInColumn = true;
                    continue;
                }
                curVal.append(ch);
                continue;
            }
            if (ch == customQuote) {
                inQuotes = true;
                if (chars[0] != '\"' && customQuote == '\"') {
                    curVal.append('\"');
                }
                if (!startCollectChar) continue;
                curVal.append('\"');
                continue;
            }
            if (ch == separators) {
                result.add(curVal.toString());
                curVal = new StringBuffer();
                startCollectChar = false;
                continue;
            }
            if (ch == '\r') continue;
            if (ch == '\n') break;
            curVal.append(ch);
        }
        result.add(curVal.toString());
        return result;
    }

    public static void main(String[] args) throws Exception {
    }
    */
}
package Model;

/**
 * Created by SummitDrift on 2/13/17.
 */

import Model.Location;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LocationFactory {
    private static final char DEFAULT_SEPARATOR = ',';
    private static final char DEFAULT_QUOTE = '\"';
    ArrayList<Location> locations = new ArrayList<Location>();

    boolean readFile(String in) throws FileNotFoundException {
        Scanner scan = new Scanner(new File(in));
        while (scan.hasNext()) {
            String[] line = scan.nextLine().split(",");
            //System.out.println("[id= " + line[0] + ", name= " + line[1] + " , city=" + line[2] + " , lat=" + line[3] + " , lon=" + line[4] + " , alt=" + line[5] + "]");
            Location temp = new Location(line[0], line[1], line[2], line[3].replaceAll("\\s+",""), line[4].replaceAll("\\s+",""), line[5]);
            locations.add(temp);
        }
        scan.close();
        if(locations.size() > 0)
            return true;
        else
            return false;
    }
    boolean findNearest() {
        boolean ret = false;
        double distance = 9999999;
        int s1 = -1;
        int s2 = -1;
        for(int i = 0; i < locations.size(); i++) {
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
            locations.get(s1).setNearest(s2);
            locations.get(s1).setNearestDistance(distance);
        }
        return ret;
    }

    public ArrayList<Location> getLocations() {
        return locations;
    }

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
}
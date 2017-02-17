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

    public ArrayList<Location> readFile(String in) throws FileNotFoundException {
        ArrayList<Location> ret = new ArrayList<Location>();
        Scanner scan = new Scanner(new File(in));
        while (scan.hasNext()) {
            String[] line = scan.nextLine().split(",");
            //System.out.println("[id= " + line[0] + ", name= " + line[1] + " , city=" + line[2] + " , lat=" + line[3] + " , lon=" + line[4] + " , alt=" + line[5] + "]");
            Location temp = new Location(line[0], line[1], line[2], line[3].replaceAll("\\s+",""), line[4].replaceAll("\\s+",""), line[5]);
            ret.add(temp);
        }
        scan.close();
        return ret;
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
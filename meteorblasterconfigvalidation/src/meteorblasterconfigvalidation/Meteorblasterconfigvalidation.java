/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meteorblasterconfigvalidation;

import com.sun.xml.internal.ws.util.StringUtils;
import java.io.File;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Wesley
 */
public class Meteorblasterconfigvalidation {

    /**
     * validates any errors related to the files location
     *
     * @param curline
     */
    public static void validateFileLocation(String curline) {
        Pattern p;
        Matcher checker;

        p = Pattern.compile("\\.[\\s+._/]");
        checker = p.matcher(curline);

        if (checker.find()) {
            System.err.println("FORMAT ERROR- '.' must have a letter after it");
            System.err.println(curline + System.lineSeparator());
        }

        p = Pattern.compile("\\/[\\s+._/]");
        checker = p.matcher(curline);

        if (checker.find()) {
            System.err.println("FORMAT ERROR- '/' must have a letter after it");
            System.err.println(curline + System.lineSeparator());
        }

        p = Pattern.compile("\\_[\\s+._/]");
        checker = p.matcher(curline);

        checker = p.matcher(curline);
        if (curline.split("[_]").length > 1 && checker.find()) {
            System.err.println("FORMAT ERROR- '_' must have a letter  after it");
            System.err.println(curline + System.lineSeparator());
        }
    }

    public static void validateRanks(Scanner scan) {
        int prevRank = 0;
        int prevScore = -1;
        while (scan.hasNext()) {
            String curline = scan.nextLine();

            if (curline.trim().equals("*")) {
                break;
            } else {
                String[] inputTypes = curline.split(",");
                if (!curline.matches("\\d+\\s*\\,\\s*\\w+\\s*\\,\\s*\\d+")) {

//checks how many inputs or commas there are
                    if (inputTypes.length != 3) {
                        System.err.print("FORMAT ERROR- ");
                        System.err.println("Expecting 3 inputs, found " + (inputTypes.length));
                        System.err.println(curline);
                    } else {
                        Pattern p = Pattern.compile("[\\w]\\s+[\\w]");
                        Matcher checker = p.matcher(inputTypes[1]);
                        if (checker.find()) {
                            System.err.print("FORMAT ERROR- ");
                            System.err.println("Invalid input type '" + inputTypes[1] + "' String expected with no spaces");
                            System.err.println(System.lineSeparator() + curline);
                            break;
                        }
                        if (!inputTypes[0].matches("^\\d+\\s*\\W")) {
                            System.err.print("FORMAT ERROR- ");
                            System.err.println("Invalid input type '" + inputTypes[0] + "' Integer expected");
                            System.err.println(System.lineSeparator() + curline);
                            break;
                        }
                        if (!inputTypes[2].matches("\\d+") || inputTypes[2].split(".").length > 1) {
                            System.err.print("FORMAT ERROR- ");
                            System.err.println("Invalid input type '" + inputTypes[2] + "' Integer expected");
                            System.err.println(System.lineSeparator() + curline);
                            break;
                        }

                    }
                } else {

                    int rankNumber = Integer.parseInt(inputTypes[0].trim());
                    int rankScore = Integer.parseInt(inputTypes[2].trim());

                    if (rankNumber != prevRank + 1 && rankNumber != prevRank) {
                        System.err.println("FORMAT ERROR- Ranks not in increasing order");
                        System.err.println(curline);
                        break;
                    } else {
                        // System.out.println(rankScore);
                        if (rankScore < prevScore || rankScore == prevScore) {
                            System.err.println("FORMAT ERROR- Rank points needed are not in increasing order or two Ranks have the same number of points needed");
                            break;
                        } else {
                            prevScore = rankScore;
                        }
                    }
                    prevRank++;

                }
            }
        }
    }

    /**
     * Specifically validates part of the file with SoundData
     *
     * @param scan
     * @return
     */
    public static void validateSounds(Scanner scan) {
        for (int i = 0; i < 5; i++) {
            String curline = scan.nextLine();
            validateFileLocation(curline);
        }
    }

    /**
     * Specifically validates part of the file with LevelData checks all Level
     * objects in the file
     *
     * @param scan
     * @return
     */
    public static void validateLevels(Scanner scan) {
        while (scan.hasNext()) {
            String curline = scan.nextLine();

            if (curline.trim().equals("*")) {
                break;
            }
            String[] inputTypes = curline.split(",");
            if (!curline.matches("\\d+\\s*\\,\\s*\\d+\\s*\\,\\s*\\d+\\s*\\,\\s*\\d+\\s*\\,\\s*\\w+\\/\\w+\\s*.\\w+")) {
                if (inputTypes.length != 5) {
                    System.err.print("FORMAT ERROR- ");
                    System.err.println("Expecting 5 inputs, found " + inputTypes.length);
                    System.err.println(curline);
                    break;
                } else {
                    Pattern p;
                    Matcher checker;

                    p = Pattern.compile("^\\d+\\s*\\W");
                    checker = p.matcher(curline);
                    for (int i = 0; i < 4; i++) {
                        if (!inputTypes[i].matches("\\s*\\d+\\s*")) {
                            System.err.print("FORMAT ERROR- ");
                            System.err.println("Invalid input type '" + inputTypes[i] + "' positive Integer expected");
                            System.err.println(System.lineSeparator() + curline);
                            break;
                        }
                    }
                    if (inputTypes[4].matches("^\\w+\\s*")) {
                        validateFileLocation(inputTypes[4]);
                    } else {
                        System.err.print("FORMAT ERROR- ");
                        System.err.println("Invalid input type '" + inputTypes[4] + "' String expected");
                    }

                }
            }
        }
    }

    /**
     * Specifically validates part of the file with CraftData
     *
     * @param scan
     * @return
     */
    public static void validateCraft(Scanner scan) {
        if (scan.hasNext()) {
            String curline = scan.nextLine();
            String[] inputTypes = curline.split(",");
            for (int i = 0; i < inputTypes.length; i++) {
                validateFileLocation(inputTypes[i]);
            }
            curline = scan.nextLine();
            validateFileLocation(curline);
            curline = scan.nextLine();
            System.out.println(curline);
            inputTypes = curline.split("\\s+");

            if (inputTypes.length != 3) {
                System.err.print("FORMAT ERROR- ");
                System.err.println("Expecting 3 inputs, found " + inputTypes.length);
                System.err.println(curline);
            } else {
                for (int i = 0; i < inputTypes.length; i++) {
                    if (!inputTypes[i].matches("[0-9](\\.[0-9]+)\\s*") && !inputTypes[i].matches("\\d\\s*")) {
                        System.err.print("FORMAT ERROR- ");
                        System.err.println("Invalid input type '" + inputTypes[i] + "' Double or Integer expected");
                        System.err.println(System.lineSeparator() + curline);
                    }
                }
            }
        }
    }

    /**
     * Specifically validates part of the file with MeteorData
     *
     * @param scan
     * @return
     */
    public static void validateMeteors(Scanner scan) {
        if (scan.hasNext()) {
            String curline = scan.nextLine();
            validateFileLocation(curline);
            curline = scan.nextLine();
           
            String[] inputTypes = curline.split("\\s+");

            if (inputTypes.length != 4) {
                System.err.print("FORMAT ERROR- ");
                System.err.println("Expecting 4 inputs, found " + inputTypes.length);
                System.err.println(curline);
            } else {
                for (int i = 0; i < inputTypes.length -1; i++) {
                    if(!inputTypes[i].matches("\\s*\\d+\\s*")){
                        System.err.print("FORMAT ERROR- ");
                        System.err.println("Invalid input type '" + inputTypes[i] + "' Integer expected");
                        System.err.println(System.lineSeparator() + curline);
                    }
                }
                    if (!inputTypes[3].matches("^\\d*[0-9](\\.[0-9])*\\s*") && !inputTypes[3].matches("\\d\\s*")) {
                        System.err.print("FORMAT ERROR- ");
                        System.err.println("Invalid input type '" + inputTypes[3] + "' Double expected");
                        System.err.println(System.lineSeparator() + curline);
                    }
                
            }
        }
    }

    /**
     * Specifically validates part of the file with ExplosionData
     *
     * @param scan
     * @return
     */
    public static void validateExplosion(Scanner scan) {
 if (scan.hasNext()) {
            String curline = scan.nextLine();
            validateFileLocation(curline);
            curline = scan.nextLine();
           
            String[] inputTypes = curline.split("\\s+");

            if (inputTypes.length != 2) {
                System.err.print("FORMAT ERROR- ");
                System.err.println("Expecting 2 inputs, found " + inputTypes.length);
                System.err.println(curline);
            } else {
                for (int i = 0; i < inputTypes.length; i++) {
                    if(!inputTypes[i].matches("\\s*\\d+\\s*")){
                        System.err.print("FORMAT ERROR- ");
                        System.err.println("Invalid input type '" + inputTypes[i] + "' Integer expected");
                        System.err.println(System.lineSeparator() + curline);
                    }
                }
                
            }
        }
    }

    public static void validateConfigFile(String filename) {
        try {
            Scanner scan = new Scanner(new File(filename));
            validateRanks(scan);
            validateSounds(scan);
            validateLevels(scan);
            validateCraft(scan);
            validateMeteors(scan);
            validateExplosion(scan);
        } catch (java.io.FileNotFoundException e) {
            System.err.println("Could not find file: " + filename);
        }
    }

    public static void main(String[] args) {
        validateConfigFile(args[0]);
    }

}

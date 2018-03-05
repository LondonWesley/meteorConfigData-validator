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
     * Specifically validates part of the file with RankData will state any
     * errors
     *
     * @param scan
     */
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
//                String[] inputs = curline.split(",");
//                //System.out.println(curline);
//                if (inputs.length != 3) {
//                    System.err.print(curline);
//                    System.err.println("   (Incorrect number of inputs)");
//                } else{
//                        if(isInteger(inputs[1])){
//                           System.out.println("Error");
//                        }
//                    }
//                    
//            }
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
            Pattern p; 
            Matcher checker;
            
            p = Pattern.compile("\\.[\\s+._/]");
            checker = p.matcher(curline);
            
            if (checker.find()) {
                System.err.println("FORMAT ERROR- '.' must have a letter after it");
                System.err.println(curline+System.lineSeparator());
            }
            
            p = Pattern.compile("\\/[\\s+._/]"); 
            checker = p.matcher(curline);
            
            if (checker.find()) {
                System.err.println("FORMAT ERROR- '/' must have a letter after it");
                System.err.println(curline+System.lineSeparator());
            }
            
             p = Pattern.compile("\\_[\\s+._/]"); 
            checker = p.matcher(curline);
            
            checker = p.matcher(curline);
            if (curline.split("[_]").length > 1&&checker.find()) {
                System.err.println("FORMAT ERROR- '_' must have a letter  after it");
                System.err.println(curline+System.lineSeparator());
            }
            
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

    }

    /**
     * Specifically validates part of the file with CraftData
     *
     * @param scan
     * @return
     */
    public static void validateCraft(Scanner scan) {
    }

    /**
     * Specifically validates part of the file with MeteorData
     *
     * @param scan
     * @return
     */
    public static void validateMeteors(Scanner scan) {

    }

    /**
     * Specifically validates part of the file with ExplosionData
     *
     * @param scan
     * @return
     */
    public static void validateExplosion(Scanner scna) {

    }

    public static void validateConfigFile(String filename) {
        try {
            Scanner scan = new Scanner(new File(filename));
            validateRanks(scan);
            validateSounds(scan);
        } catch (java.io.FileNotFoundException e) {
            System.err.println("Could not find file: " + filename);
        }
    }

    public static void main(String[] args) {
        validateConfigFile(args[0]);
    }

}
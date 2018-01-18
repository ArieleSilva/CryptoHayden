/**
 *  File:       Driver.java
 *  Author:     Kevin Hayden
 *  Course:     MSCS 630
 *  Assignment: Lab 01
 *  Due Date:   Wednesday, January 24th, 2018
 *  Version:    1.0
 *
 *  This file contains the Driver class that runs
 *  the enclosed encryption scheme.
 */
import java.util.Scanner;

/**
 * The driver class that houses the main method as well as
 * the str2int encryption method.
 */
public class Driver {

    /**
     * The main method
     * @param args
     */
    public static void main(String[] args) {

        // Scanner object to read input
        Scanner input = new Scanner(System.in);

        // Reads each line and prints each char as an int.
        while (input.hasNext()) {

            // Reads input.
            String plainText = input.nextLine();

            // Sets plainText to all uppercase.
            plainText = plainText.toUpperCase();

            // Converts string to an array of ints.
            int[] output = str2int(plainText);

            // Prints each int in the array.
            for (int num : output)
                System.out.print(num + " ");
            System.out.println();
        }
    }

    /**
     * This method takes a String object and converts
     * each letter to an integer and then adds it to
     * an integer array. The function then returns the
     * array.
     *
     * @param plainText The string object to be encrypted.
     * @return an array of ints to be printed
     */
    private static int[] str2int(String plainText) {

        int[] output = new int[plainText.length()];
        plainText = plainText.toUpperCase();

        for (int i = 0; i < plainText.length(); i++) {
            char c = plainText.charAt(i);
            if (c == 32)
                output[i] = 26;
            else
                output[i] = ((int) c) - 65;
        }
        return output;
    }
}


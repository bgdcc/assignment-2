import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Reads a list of numbers, and can reconstruct the corresponding list of palindromes,
 * produce the size of the largest magic set, and the content of that magic set.
 * 
 * Usage:
 * 
 * The program's functionality is split between 3 different tasks, which
 * will be executed according to the first number given as input.
 * 
 * The tasks are as follows:
 * 
 * TASK 1: Output the list after all the numbers have been restored to palindromes.
 * 
 * TASK 2: Output the size of the largest magic set present in the list. (A magic set is a subset of
 *         palindromes present in the list in which any of the shorter elements can be obtained from
 *         the largest one - known as "X" - by removing the same amount of digits on both ends)
 *         The program will output "1" if the correct list does not contain any magical set.
 * 
 * TASK 3: Output the elements from which the largest magic set is formed, listed
 *         in ascending order.
 *         If there are several such magic sets on the correct list, output the one with 
 *         the greatest "X".
 *         If there are no magic sets, the greatest number in the correct list is output.
 * 
 * In order for a number to be restored to its correct value, it needs increase to the nearest
 * palindrome which is greater than or equal to the number in question.
 * 
 * INPUT:
 * The program takes as input the following values:
 * 
 * "taskNumber"
 * "listLength"
 * "kingsList[0] kingsList[1] kingsList[2] (...) kingsList[listLength-1]"
 * 
 * where:
 * 
 * "taskNumber" - the number of the task that is going to be executed,
 * "listLength" - the size of the array that is going to be given as input,
 * "kingsList" - an array of size listLength composed of natural numbers, 
 *               all with an odd number of digits.
 * 
 * 
 * EXAMPLE INPUT FOR TASK 1: 
 * 1
 * 3
 * 345 214 64325 
 * 
 * OUTPUT FOR TASK 1:
 * 353 222 64346
 * 
 * 
 * EXAMPLE INPUT FOR TASK 2:
 * 2
 * 6
 * 2 3 4 432 5643461 7
 * 
 * OUTPUT FOR TASK 2:
 * 3
 * 
 * EXAMPLE INPUT FOR TASK 3:
 * 3 
 * 5 
 * 489 123 14890 2148909 789
 * 
 * OUTPUT FOR TASK 3:
 * 494 14941 2149412
 * 
 * It should be noted that all input must consist of natural numbers.
 * It should also be noted that "kingsList" accepts natural numbers with odd number
 * of digits that have up to 17 digits.
 * 
 * If the user inputs any other value than 1, 2 or 3 for "taskNumber", the program will not run.
 * If the user does not provide listLength different values for "kingsList", 
 * the program will not run.
 * 
 * 
 * 
 * @author Bogdan Culea
 * @ID 2124904
 * @author Veaceslav Gandrabur
 * @ID 2123282
 * 
 */

class KingsPalindromeList {

    // Takes input and provides expected output, based on the task number.
    private void completeTheTasks() {
        Scanner scan = new Scanner(System.in); // Creates a Scanner object for further use.

        // Initialize variables and provide input for the problem.
        int taskNumber = scan.nextInt();
        int listLength = scan.nextInt();
        long[] kingsList = new long[listLength];

        // Provide input for the King's List.
        for (int i = 0; i < listLength; i++) {
            kingsList[i] = scan.nextLong();
        }

        // Creates an array where all the terms have been turned to palindromes.
        long[] petersList = new long[listLength];
        petersList = restorePalindromes(kingsList);

        scan.close(); // Closes the scanner.

        // Store the restored array into a set, converting the elements into strings.
        Set<String> setOfPalindromes = new HashSet<>();

        for (long num: petersList) {
            setOfPalindromes.add(String.valueOf(num));
        }

        // Creates an array of the largest magic set.
        String[] magicSet = findLargestMagicSet(setOfPalindromes.toArray(new String[0]));

        // Provides output, as described in the documentation.
        if (taskNumber == 1) {
            // Outputs all the elements of the palindrome list.
            for (int i = 0; i < petersList.length; i++) {
                if (i == petersList.length - 1) {
                    System.out.print(petersList[i]);
                } else {
                    System.out.print(petersList[i] + " ");
                }
            }

        } else if (taskNumber == 2) {
            // Outputs the length of the largest magic set.
            System.out.print(magicSet.length);

        } else if (taskNumber == 3) {

            /* Outputs the largest magic set in ascending order.
             * Outputs the largest element if there are no magic sets.
             */
            for (int i = 0; i < insertionSort(magicSet).length; i++) {
                if (i == insertionSort(magicSet).length - 1) {
                    System.out.print(insertionSort(magicSet)[i]);
                } else {
                    System.out.print(insertionSort(magicSet)[i] + " ");
                }
            }
        }
            
    }

    // Restores the correct list of palindromes.  
    private long[] restorePalindromes(long[] array) {

        for (int i = 0; i < array.length; i++) {
            // Variable to store the element of the list in the current iteration.
            long currentNumber = array[i];

            // Skip the current iteration for numbers of 1 digit (are considered as palindromes).
            if (currentNumber < 10) {
                continue;
            }

            // Convert current number from type Long to String to make use of .substring method.
            String strFromCurrentNum = Long.toString(currentNumber);

            // Split the String number into rear substring (from middle till end).
            String rearSubstring = strFromCurrentNum.substring(
                strFromCurrentNum.length() / 2 + 1, strFromCurrentNum.length());

            // Store the middle of the string in middleString variable.
            String middleSubstring = strFromCurrentNum.substring(
                strFromCurrentNum.length() / 2, strFromCurrentNum.length() / 2 + 1);

            // Split the String number into front substring (from start to middle).
            String frontSubstring = strFromCurrentNum.substring(0, strFromCurrentNum.length() / 2);

            // Reverse the front part of the substring.
            StringBuilder reversedFrontSubstring = new StringBuilder(frontSubstring).reverse();

            // Final palindrome that will be constructed in the process. 
            String stringPalindrome = "";

            
             
            /*
             * Building a palindrome from the 3 given parts if the reversed front part of the string
             * is greater than its rear part. Converting substring back to Long to check equality
             */
            
            if (Long.parseLong(reversedFrontSubstring.toString()) 
                >= Long.parseLong(rearSubstring)) {
                stringPalindrome = frontSubstring + middleSubstring + reversedFrontSubstring;
                
            /*
             * If the reversed front of the number is less than its rear part then increment the 
             * middle part and build the palindrome from 3 separate parts.
             * If middle of the number is 9, then change it to 0 and increment the front part
             * of the string. Build the new palindrome.
             * 
             * Converting substring back to Long inside of the conditional statement to check the 
             * equality of the numbers.
             */
            
            } else  if (Long.parseLong(
                reversedFrontSubstring.toString()) < Long.parseLong(rearSubstring)) {
                if (Long.parseLong(middleSubstring) != 9) {
                    middleSubstring = String.valueOf(Long.parseLong(middleSubstring) + 1);
                    stringPalindrome = frontSubstring + middleSubstring + reversedFrontSubstring;

                } else {
                    frontSubstring = String.valueOf(Long.parseLong(frontSubstring) + 1);
                    reversedFrontSubstring = new StringBuilder(frontSubstring).reverse();
                    middleSubstring = "0";
                    stringPalindrome = frontSubstring + middleSubstring + reversedFrontSubstring;
                }

            }

            array[i] = Long.valueOf(stringPalindrome);
            
            
        }

        return array;

    }

    // Finds the largest magic set.
    private String[] findLargestMagicSet(String[] myArr) {

        /*  Creates an array which will record the number of elements 
        a magic set contains if the element at index i is considered "X".*/
        
        int[] personalSet = new int[myArr.length];

        for (int i = 0; i < myArr.length; i++) {
            personalSet[i] = 0;
        }

        for (int i = 0; i < myArr.length; i++) {
            for (int j = 0; j < myArr.length; j++) {
                if (myArr[i].length() >= myArr[j].length()) {
                    
                    /* The variables "startMiddle" and "endMiddle" will denote the range
                     * an element of a magic set occupies in "X".
                     */

                    int startMiddle = (myArr[i].length() / 2) - (myArr[j].length() / 2);
                    int endMiddle = (myArr[i].length() / 2) + (myArr[j].length() / 2) + 1;

                    if (myArr[i].substring(startMiddle, endMiddle).equals(myArr[j])) {
                        personalSet[i]++;
                    }
                }

            }
        }

        /* Creates variables which will store the index of "X" in the biggest magic set
         * and the length of this set.
        */
        
        int correctIndex = 0;
        int maxValue = 0;

        for (int i = 0; i < personalSet.length; i++) {
            if (maxValue < personalSet[i]) {
                maxValue = personalSet[i];
                correctIndex = i;
            }
        }

        /* Returns an array with the largest element
         * if there are no magic sets with length greater than 1.
         */
        
        if (maxValue == 1) {
            return oneElementArr(myArr);
        }

        /* Check if there are any magic sets which both have the maximum length.
         * If there are, store the index for the bigger "X".
         */
        
        for (int i = 0; i < myArr.length - 1; i++) {
            for (int j = i + 1; j < myArr.length; j++) {
                if (personalSet[i] == maxValue && personalSet[j] == maxValue) {
                    if (Long.valueOf(myArr[i]) > Long.valueOf(myArr[j])) {
                        correctIndex = i;
                    } else if (Long.valueOf(myArr[i]) < Long.valueOf(myArr[j])) {
                        correctIndex = j;
                    }
                }
            }
        }

        // Store the largest magic set in a string set.
        Set<String> bigMagicSet = new HashSet<>();

        for (int i = 0; i < myArr.length; i++) {
            int startMiddle = (myArr[correctIndex].length() / 2) - (myArr[i].length() / 2);
            int endMiddle = (myArr[correctIndex].length() / 2) + (myArr[i].length() / 2) + 1;

            if (myArr[correctIndex].length() >= myArr[i].length()) {
                if (myArr[correctIndex].substring(startMiddle, endMiddle).equals(myArr[i])) {
                    bigMagicSet.add(myArr[i]);
                }
            }

        }

        // Turns the previous set into a string array.
        String[] bigMagicArray = bigMagicSet.toArray(new String[0]);
        
        return bigMagicArray;
    }


    // Returns a sorted array (the returned array is composed of strings).
    private long[] insertionSort(String[] myArr) {
        long[] longArr = new long[myArr.length];

        for (int i = 0; i < myArr.length; i++) {
            longArr[i] = Long.valueOf(myArr[i]);
        }


        for (int i = 1; i < longArr.length; i++) {
            long key = longArr[i];
            int j = i - 1;

            while (j >= 0 && longArr[j] > key) {
                longArr[j + 1] = longArr[j];
                j -= 1;
            }

            longArr[j + 1] = key;
        }

        return longArr;

    }

    /* Returns a string array containing the largest element from the string array 
     * given as a parameter (the elements of the array have been converted into long
     * type in order to do the comparison).
     */
    private String[] oneElementArr(String[] myArr) {
        long maxValue = 0;
        for (String i: myArr) {
            if (Long.valueOf(i) > maxValue) {
                maxValue = Long.valueOf(i);
            }
        }

        String[] maxValueArr = new String[]{String.valueOf(maxValue)};

        return maxValueArr;
    }

    public static void main(String[] args) {
        new KingsPalindromeList().completeTheTasks();
    }
}
import java.util.*;

/*
A program that converts Arabic Numerals to Roman Numerals
 */

public class RomanNumeral {
    private final static int MAX_NUMERAL = 4999;
    private static HashMap<String, Integer> numeralMap;
    private static ArrayList<String> romanArray;
    private final static String[] pattern = {
        "", "a", "aa", "aaa", "ab",
        "b", "ba", "baa", "baaa", "ac"
    };
    private final static String[][] place = {
        {"i", "v", "x"},
        {"x", "l", "c"},
        {"c", "d", "m"},
        {"m", "mmm", "" }
        /*
        The "mmm" in the last array an ugly fix for a logic bug.  anything 4K and above, the 4 is replaced with "ab",
         which the 'a' is replaced by 'm' but the b was replaced by an empty string because "m" was the only
         character in the place[3][*] array. The triple m is added to the first 'm' that replaced the 'a', solving
         the issue.
         */
    };


    private RomanNumeral() {
        initTables();
    }

    public static void main(String[] args) {
        Math.pow(2, 3);
        new RomanNumeral();  //ugly utility form
        getUserInput();
    }

    private static void getUserInput() {
        var input = new Scanner(System.in);
        int selection = 0;
        try {
            while (selection != 3) {
                System.out.print(
                    "Select conversion type:\n" +
                        "\t1) Roman to Arabic\n" +
                        "\t2) Arabic to Roman\n" +
                        "\t(3 to quit)\n");
                selection = input.nextInt();
                switch (selection) {
                    case 1:
                        System.out.println("Input roman number: ");
                        System.out.println(toAlpha(input.next()));
                        break;
                    case 2:
                        System.out.println("Input arabic number: ");
                        System.out.println(toRoman(input.nextInt()));
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static String toRoman(int n) {
        return romanArray.get(n);
    }

    public static int toAlpha(String r) {
        return numeralMap.get(r);
    }

    private static String convert(int number) {
        /*
        Returns a roman numeral by taking an int, converting it into a string, reversing the string so that the index
         of the digit that the character represents is it's power of ten.  The digit/character is then converted into
          the associated pattern between 1 - 9, and then using the current index along with the value that the
          character represents it pulls the correct numerals from the place[][] array, and inserted into the return
          value
         */
        String snum = new StringBuffer(String.valueOf(number)).reverse().toString();
        var alpha = new StringBuilder();

        for (int i = 0; i < snum.length(); ++i) {
            var digit = new StringBuilder(pattern[Integer.parseInt(String.valueOf(snum.charAt(i)))]);

            for (int j = 0; j < digit.length(); ++j)
                digit.replace(j, j + 1, place[i][enumeratePattern(digit.charAt(j))]);

            if (!digit.toString().isEmpty())
                alpha.insert(0, digit);
        }

        return alpha.toString();
    }

    private static int enumeratePattern(char c) {
        switch (c) {
            case 'a':
                return 0;
            case 'b':
                return 1;
            case 'c':
                return 2;
        }
        return 0;
    }

    private static ArrayList<String> createRomanArray() {
        /*
        creates an array of roman numerals so a table can be queried in constant time with a single int.
         */
        var tmp = new ArrayList<String>();
        try {
            for (int i = 0; i < 4999; ++i)
                tmp.add(convert(i));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return tmp;
    }

    private static HashMap<String, Integer> createNumeralMap() {
        /*
        maps roman numerals to integers using the values in the romanNumeral array as keys, allowing the associated
        in value to be queried in constant time
         */
        var tmp = new HashMap<String, Integer>();
        try {
            for (int i = 1; i < MAX_NUMERAL; ++i)
                tmp.put(romanArray.get(i), i);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return tmp;
    }

    public static void initTables() {
        /*
        initialized both tables in the correct order so the createNumeralMap() function isn't using an empty array
         */
        romanArray = createRomanArray();
        numeralMap = createNumeralMap();
    }
}
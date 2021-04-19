import java.util.*;

/*
A program that converts Arabic Numerals to Roman Numerals
 */

public class RomanNumeral {
    private final static int MAX_NUMERAL = 4999;
    private static HashMap<Integer, String> numeralMap;
    private final static String[] pattern = {
        "", "a", "aa", "aaa", "ab",
        "b", "ba", "baa", "baaa", "ac"
    };
    private final static String[][] place = {
        {"i", "v", "x"},
        {"x", "l", "c"},
        {"c", "d", "m"},
        {"m", "", ""}
    };


    private RomanNumeral() {
        numeralMap = createNumeralMap();
    }

    public static void main(String[] args) {
        new RomanNumeral();
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
        return numeralMap.get(n);
    }

    public static String toAlpha(String r) {
        for (var entry : numeralMap.entrySet())
            if (r.equals(entry.getValue()))
                return entry.getKey().toString();

        return r + " not found";
    }

    private static String convert(int number) throws Exception {
        String snum = new StringBuffer(String.valueOf(number)).reverse().toString();
        var alpha = new StringBuilder();

        for (int i = 0; i < snum.length(); ++i) {
            int value = Integer.parseInt(String.valueOf(snum.charAt(i)));
            var digit = new StringBuilder(pattern[value]);

            for (int j = 0; j < digit.length(); ++j)
                digit.replace(j, j + 1, place[i][enumeratePattern(digit.charAt(j))]);

            if (i == 3)
                for (int j = 0; j < value - 1; ++j)
                    alpha.insert(0, digit);
            else if (!digit.toString().isEmpty())
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

    private static HashMap<Integer, String> createNumeralMap() {
        var tmp = new HashMap<Integer, String>();
        try {
            for (int i = 1; i < MAX_NUMERAL; ++i)
                tmp.put(i, convert(i));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return tmp;
    }
}
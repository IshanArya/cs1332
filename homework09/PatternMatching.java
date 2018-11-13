import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Your implementations of various string searching algorithms.
 *
 * @author Ishan Arya
 * @version 1.0
 * @userid iarya3
 * @GTID 903399427
 */
public class PatternMatching {

    /**
     * Knuth-Morris-Pratt (KMP) algorithm that relies on the failure table (also
     * called failure function). Works better with small alphabets.
     * <p>
     * Make sure to implement the failure table before implementing this method.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each match found
     * @throws IllegalArgumentException if the pattern is null or of length 0
     * @throws IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> kmp(CharSequence pattern, CharSequence text,
                                    CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException(
                    "Cannot have empty pattern variable.");
        }
        if (text == null) {
            throw new IllegalArgumentException("Cannot search null text.");
        }
        if (comparator == null) {
            throw new IllegalArgumentException(
                    "Cannot compare with null comparator.");
        }
        List<Integer> matches = new ArrayList<>();

        if (pattern.length() > text.length()) {
            return matches;
        }

        int[] failureTable = buildFailureTable(pattern, comparator);


        int i = 0;
        int j = 0;
        while (i < text.length() - pattern.length() + j + 1) {
            if (comparator.compare(pattern.charAt(j), text.charAt(i)) != 0) {
                if (j == 0) {
                    i++;
                } else {
                    j = failureTable[j - 1];
                }
            } else {
                i++;
                j++;
            }
            if (j == pattern.length()) {
                matches.add(i - j);
                j = failureTable[j - 1];
            }
        }

        return matches;

    }

    /**
     * Builds failure table that will be used to run the Knuth-Morris-Pratt
     * (KMP) algorithm.
     * <p>
     * The table built should be the length of the input text.
     * <p>
     * Note that a given index i will be the largest prefix of the pattern
     * indices [0..i] that is also a suffix of the pattern indices [1..i].
     * This means that index 0 of the returned table will always be equal to 0
     * <p>
     * Ex. ababac
     * <p>
     * table[0] = 0
     * table[1] = 0
     * table[2] = 1
     * table[3] = 2
     * table[4] = 3
     * table[5] = 0
     * <p>
     * If the pattern is empty, return an empty array.
     *
     * @param pattern    a {@code CharSequence} you're building
     *                  a failure table for
     * @param comparator you MUST use this for checking character equality
     * @return integer array holding your failure table
     * @throws IllegalArgumentException if the pattern or comparator is null
     */
    public static int[] buildFailureTable(CharSequence pattern,
                                          CharacterComparator comparator) {

        if (pattern == null) {
            throw new IllegalArgumentException("Pattern cannot be null.");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator cannot be null.");
        }

        int[] failureTable = new int[pattern.length()];

        int i = 0;
        failureTable[0] = 0;
        int j = 1;

        while (j < pattern.length()) {
            if (comparator.compare(pattern.charAt(i), pattern.charAt(j)) == 0) {
                i++;
                failureTable[j] = i;
                j++;
            } else {
                if (i != 0) {
                    i = failureTable[i - 1];

                } else {
                    failureTable[j] = i;
                    j++;
                }
            }
        }

        return failureTable;

    }

    /**
     * Boyer Moore algorithm that relies on last occurrence table. Works better
     * with large alphabets.
     * <p>
     * Make sure to implement the last occurrence table before implementing this
     * method.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for the pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each match found
     * @throws IllegalArgumentException if the pattern is null or of length 0
     * @throws IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> boyerMoore(CharSequence pattern,
                                           CharSequence text,
                                           CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException(
                    "Cannot have empty pattern variable.");
        }
        if (text == null) {
            throw new IllegalArgumentException("Cannot search null text.");
        }
        if (comparator == null) {
            throw new IllegalArgumentException(
                    "Cannot compare with null comparator.");
        }

        List<Integer> matches = new ArrayList<>();

        if (pattern.length() > text.length()) {
            return matches;
        }

        Map<Character, Integer> lastTable = buildLastTable(pattern);

        int i = pattern.length() - 1;
        int j = i;
        while (i < text.length()) {
            char jChar = pattern.charAt(j);
            char iChar = text.charAt(i);
            if (comparator.compare(jChar, iChar) != 0) {
                int lastOccurrence = lastTable.getOrDefault(iChar, -1);

                if (lastOccurrence >= 0) {
                    if (j > lastOccurrence) {
                        i += pattern.length() - 1 - lastOccurrence;
                    } else {
                        i += pattern.length() - j;
                    }
                } else {
                    i += pattern.length();
                }
                j = pattern.length() - 1;

            } else {
                i--;
                j--;
            }
            if (j < 0) {
                matches.add(i + 1);
                i += pattern.length() + 1;
                j = pattern.length() - 1;
            }
        }

        return matches;

    }

    /**
     * Builds last occurrence table that will be used to run the Boyer Moore
     * algorithm.
     * <p>
     * Note that each char x will have an entry at table.get(x).
     * Each entry should be the last index of x where x is a particular
     * character in your pattern.
     * If x is not in the pattern, then the table will not contain the key x,
     * and you will have to check for that in your Boyer Moore implementation.
     * <p>
     * Ex. octocat
     * <p>
     * table.get(o) = 3
     * table.get(c) = 4
     * table.get(t) = 6
     * table.get(a) = 5
     * table.get(everything else) = null, which you will interpret in
     * Boyer-Moore as -1
     * <p>
     * If the pattern is empty, return an empty map.
     *
     * @param pattern a {@code CharSequence} you are building last table for
     * @return a Map with keys of all of the characters in the pattern mapping
     * to their last occurrence in the pattern
     * @throws IllegalArgumentException if the pattern is null
     */
    public static Map<Character, Integer> buildLastTable(CharSequence pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("Pattern cannot be null.");
        }

        Map<Character, Integer> lastOccurrences = new HashMap<>();
        for (int i = 0; i < pattern.length(); i++) {
            lastOccurrences.put(pattern.charAt(i), i);
        }

        return lastOccurrences;
    }

    /**
     * Prime base used for Rabin-Karp hashing.
     * DO NOT EDIT!
     */
    private static final int BASE = 101;

    /**
     * Runs the Rabin-Karp algorithm. This algorithms generates hashes for the
     * pattern and compares this hash to substrings of the text before doing
     * character by character comparisons.
     * <p>
     * When the hashes are equal and you do character comparisons, compare
     * starting from the beginning of the pattern to the end, not from the end
     * to the beginning.
     * <p>
     * You must use the Rabin-Karp Rolling Hash for this implementation. The
     * formula for it is:
     * <p>
     * sum of: c * BASE ^ (pattern.length - 1 - i), where c is the integer
     * value of the current character, and i is the index of the character
     * <p>
     * Note that if you were dealing with very large numbers here, your hash
     * will likely overflow. However, you will not need to handle this case.
     * You may assume there will be no overflow.
     * <p>
     * For example: Hashing "bunn" as a substring of "bunny" with base 101 hash
     * = b * 101 ^ 3 + u * 101 ^ 2 + n * 101 ^ 1 + n * 101 ^ 0 = 98 * 101 ^ 3 +
     * 117 * 101 ^ 2 + 110 * 101 ^ 1 + 110 * 101 ^ 0 = 102174235
     * <p>
     * Another key step for this algorithm is that updating the hashcode from
     * one substring to the next one must be O(1). To update the hash:
     * <p>
     * remove the oldChar times BASE raised to the length - 1, multiply by
     * BASE, and add the newChar.
     * <p>
     * For example: Shifting from "bunn" to "unny" in "bunny" with base 101
     * hash("unny") = (hash("bunn") - b * 101 ^ 3) * 101 + y =
     * (102174235 - 98 * 101 ^ 3) * 101 + 121 = 121678558
     * <p>
     * Keep in mind that calculating exponents is not O(1) in general, so you'll
     * need to keep track of what BASE^{m - 1} is for updating the hash.
     * <p>
     * Do NOT use Math.pow
     *
     * @param pattern    a string you're searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator the comparator to use when checking character equality
     * @return list containing the starting index for each match found
     * @throws IllegalArgumentException if the pattern is null or of length 0
     * @throws IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> rabinKarp(CharSequence pattern,
                                          CharSequence text,
                                          CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException(
                    "Cannot have empty pattern variable.");
        }
        if (text == null) {
            throw new IllegalArgumentException("Cannot search null text.");
        }
        if (comparator == null) {
            throw new IllegalArgumentException(
                    "Cannot compare with null comparator.");
        }
        List<Integer> matches = new ArrayList<>();
        if (pattern.length() > text.length()) {
            return matches;
        }

        int patternHash = 0;
        int rollingHash = 0;
        int constantChangingBase = 1;
        for (int i = pattern.length() - 1; i >= 0; i--) {
            int charHash = pattern.charAt(i) * constantChangingBase;
            int textCharHash = text.charAt(i) * constantChangingBase;

            if (i != 0) {
                constantChangingBase *= BASE;
            }
            patternHash += charHash;
            rollingHash += textCharHash;
        }


        int i = 0;

        while (i < text.length() - pattern.length()) {
            if (patternHash == rollingHash) {
                boolean allMatch = true;
                for (int j = 0; j < pattern.length(); j++) {
                    if (comparator.compare(text.charAt(i + j),
                            pattern.charAt(j)) != 0) {
                        allMatch = false;
                        break;
                    }
                }
                if (allMatch) {
                    matches.add(i);
                }
            }
            rollingHash = (rollingHash - text.charAt(i) * constantChangingBase)
                    * BASE + text.charAt(i + pattern.length());
            i++;
        }
        if (patternHash == rollingHash) {
            for (int j = 0; j < pattern.length(); j++) {
                if (comparator.compare(text.charAt(i + j),
                        pattern.charAt(j)) != 0) {
                    return matches;
                }
            }
            matches.add(i);
        }

        return matches;

    }
}

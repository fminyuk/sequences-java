package org.nnc.sequences.ahocorasick;

import java.util.Random;

public class DataGenerator {
    private DataGenerator() {
    }

    public static String genText(int length, char[] alphabet, Random random) {
        final StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            sb.append(alphabet[random.nextInt(alphabet.length)]);
        }

        return sb.toString();
    }

    public static String[] genKeys(String text, int count, int length, Random random) {
        final String[] keys = new String[count];

        for (int i = 0; i < count; i++) {
            final int begin = random.nextInt(text.length() - length);
            final int end = begin + length;
            keys[i] = text.substring(begin, end);
        }

        return keys;
    }
}

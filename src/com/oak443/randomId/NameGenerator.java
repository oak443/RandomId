package com.oak443.randomId;

import java.util.HashSet;
import java.util.Random;

public class NameGenerator {
    private final Integer length;

    private final HashSet<Character> charset;

    public NameGenerator(Integer length, HashSet<Character> charset) {
        super();
        this.length = length;
        this.charset = charset;
    }

    private final Random random = new Random();

    public String next() {
        char[] arr = new char[length];
        Character[] charset = this.charset.toArray(new Character[0]);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(charset.length);
            arr[i] = charset[index];
        }
        return String.valueOf(arr);
    }
}

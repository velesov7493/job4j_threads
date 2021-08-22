package ru.job4j.concurrent.syncres;

import java.io.*;
import java.util.function.Predicate;

public class ParseFile {

    private final File file;

    public ParseFile(File aFile) {
        file = aFile;
    }

    private String getContentByPredicate(Predicate<Character> filter) throws IOException {
        StringBuilder output = new StringBuilder();
        try (BufferedReader  br = new BufferedReader(new FileReader(file))) {
            int data;
            while ((data = br.read()) > 0) {
                char ch = (char) data;
                if (filter.test(ch)) {
                    output.append(ch);
                }
            }
        }
        return output.toString();
    }

    public String getContent() throws IOException {
        return getContentByPredicate((c) -> true);
    }

    public String getContentWithoutUnicode() throws IOException {
        return getContentByPredicate((c) -> c < 0x80);
    }
}

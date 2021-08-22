package ru.job4j.concurrent.syncres;

import java.io.*;

public class SaveFile {

    private final File file;

    public SaveFile(File aFile) {
        file = aFile;
    }

    public void saveContent(String content) throws IOException {
        try (PrintWriter pw =
                new PrintWriter(
                    new BufferedWriter(
                        new FileWriter(file)
        ))) {
            pw.print(content);
        }
    }
}

package ru.job4j.io;

import java.io.*;
import java.nio.charset.StandardCharsets;

public final class SaveFile {

    private final File file;

    public SaveFile(File file) {
        this.file = file;
    }

    public void saveContent(String content) throws IOException {
        try (OutputStream o = new BufferedOutputStream(new FileOutputStream(file))) {
            o.write(content.getBytes(StandardCharsets.UTF_8));
        }
    }
}
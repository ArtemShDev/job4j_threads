package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public final class ParseFile {

    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public String getContent(Predicate<Character> filter) throws IOException {
        StringBuilder sb = new StringBuilder();
        try(InputStream i = new BufferedInputStream(new FileInputStream(file))) {
            int readByte;
            while ((readByte = i.read()) != -1) {
                if (filter.test((char) readByte)) {
                    sb.append((char) readByte);
                }
            }
        }
        return sb.toString();
    }
}
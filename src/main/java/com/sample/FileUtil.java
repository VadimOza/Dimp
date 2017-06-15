package com.sample;


import java.io.*;

public class FileUtil {

    public static String[] readFromFile(File file) throws IOException {

        InputStream is = new FileInputStream(file);
        BufferedReader buf = new BufferedReader(new InputStreamReader(is));
        String line = buf.readLine();
        StringBuilder sb = new StringBuilder();
        while (line != null) {
            sb.append(line);
            line = buf.readLine();
        }
        String fileAsString = sb.toString();
        return fileAsString.split(" ");

    }
}

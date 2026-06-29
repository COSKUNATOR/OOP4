package aufgaben.input;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Aufgabe1 {
    public static void main(String[] args) {
        Path p = Path.of("resources/Beispiel/aufgabe1.txt");
        try {
            String text = bufferedReaderToString(p);
            System.out.println(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String bufferedReaderToString(Path file) throws IOException {
        StringBuilder sb = new StringBuilder();
        try(InputStream in = Files.newInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append(System.lineSeparator());
            }
        }
        return sb.toString();
    }
}

package aufgaben.input;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Aufgabe2 {
    public static void main(String[] args) {
        String filePath = "resources/Beispiel/aufgabe1.txt";
        try {
            printPunctuation(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void printPunctuation(String file) throws IOException {
        Path path = Path.of(file);

        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            int character;

            while ((character = reader.read()) != -1) {
                char c = (char) character;

                if (c == '.' || c == ',' || c == '!' || c == '?' || c == ':' || c == ';') {
                    System.out.print(c);
                }
            }
        }

        System.out.println();
    }
}

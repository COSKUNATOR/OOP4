package aufgaben.output;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Aufgabe1 {
    public static void main(String[] args) {
        String lorem = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt\n" +
                "ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco\n" +
                "laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in\n" +
                "voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat\n" +
                "non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";

        try {
            writeToString("resources/Beispiel/aufgabe1.txt", lorem);
            writeToStringSkipping("resources/Beispiel/aufgabe1_skipping.txt", lorem);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeToString(String file, String data) throws IOException{
        Path path = Path.of(file);
        Files.createDirectories(path.toAbsolutePath().getParent());
        try(OutputStream out = Files.newOutputStream(path);
        Writer writer = new OutputStreamWriter(out, StandardCharsets.UTF_8)) {
            writer.write(data);
        }
    }

    public static void writeToStringSkipping(String file, String data) throws IOException{
        Path path = Path.of(file);
        Files.createDirectories(path.toAbsolutePath().getParent());
        try(OutputStream out = Files.newOutputStream(path);
            Writer writer = new OutputStreamWriter(out, StandardCharsets.UTF_8)) {
            for (int i = 0; i < data.length(); i++) {
                if (i % 2 == 0) {
                    writer.write(data.charAt(i));
                }
            }
        }
    }
}

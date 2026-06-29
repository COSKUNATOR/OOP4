import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Pattern;

public class MyInputStreams {
    public static void main(String[] args)
    {
        try
        {
            Path p = Paths.get("ressources/Beispiel/text.txt");

            // Verwendet den InputStream
            String text = MyInputStreams.inputStreamToString(p);
            System.out.println(text);
            System.out.println();

            // Verwendet den BufferedReader
            String text2 = MyInputStreams.bufferedReaderToString(p);
            System.out.println(text2);

            // Verwendet einen Scanner
            scannerToString(p);
            System.out.println(text);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

    }

    private static String inputStreamToString(Path file) throws IOException{
        // Mit Files.exists(Path) koennen wir pruefen, ob eine Datei existiert. Das ist wichtig, bevor wir versuchen, sie zu oeffnen.
        if(Files.exists(file)){
            // Erzeugt einen InputStream, indem eine Verbindung zu einer Datei hergestellt wird.
            try(InputStream in = Files.newInputStream(file)){
                // Ausgabe der auf dem Stream verfuegbaren Bytes. Das ist die Anzahl der Bytes, die wir lesen koennen, ohne dass der Stream blockiert.
                System.out.println(in.available());

                //Files.size() gibt die Groesse der Datei in Bytes zurueck.
                // Die Klasse 'Files' enthaelt einige Methoden, um mit Dateien zu arbeiten.
                byte[] daten = new byte[Math.toIntExact(Files.size(file))];
                System.out.println("Daten-Anzahl:" + daten.length);

                int anzahl = in.read(daten);
                System.out.println("Anzahl gelesener Bytes: " + anzahl);

                // Alle Bytes einlesen: // Ab Java 9
                // Achtung: Bei sehr großen Dateien könnte dies zu einem OutOfMemoryError führen, wenn nicht genug zusammenhängender Arbeitsspeicher verfügbar ist.
                //daten = in.readAllBytes();

                // Weitere Alternative:
                //daten = Files.readAllBytes(file);

                // Daten als String zurückgeben. Wir können einen Zeichensatz angeben, sonst wird der Standardzeichensatz verwendet.

                return new String(daten, StandardCharsets.UTF_8);
            }
        }
        else
            return null;
    }

    private static String bufferedReaderToString(Path file) throws IOException{
        StringBuilder stringBuilder = new StringBuilder();

        // Ein InputStreamReader ist eine Brücke von Byteströmen zu Zeichenströmen.
        // Er liest bytes und dekodiert sie in Zeichen unter Verwendung des angegebenen Zeichensatzes.
        // Ein BufferedReader liest Text aus einem Zeicheneingabestrom und puffert die Zeichen, um ein effizientes Lesen von Zeichen zu ermöglichen.
        // Es wird empfohlen, den BufferedReader an Stelle des puren InputStreamReaders oder FileReaders zu verwenden.
        try(InputStream in = Files.newInputStream(file);
            BufferedReader reader = new BufferedReader((new InputStreamReader(in, StandardCharsets.UTF_8)))) {

            String line = null;

            // readLine() liest jede Zeile aus dem Stream und gibt am Ende des Streams (Ende der Datei) null zurueck,

            while((line = reader.readLine()) != null){
                stringBuilder.append(line).append("\n");
            }
            return stringBuilder.toString();
        }
    }

    private static String scannerToString(Path file) throws IOException{
        StringBuilder stringBuilder = new StringBuilder();

        try(InputStream in = Files.newInputStream(file);
            Scanner scanner = new Scanner(in, StandardCharsets.UTF_8)) {
                // Das Pattern prueft auf Punkt und Komma
                Pattern pattern = Pattern.compile("([.,。、])");
                scanner.useDelimiter(pattern);

                // Das Pattern als Delimiter bewirkt, dass Zeichen, die dem Pattern entsprechen, als Trennzeichen verarbeitet werden.

                while(scanner.hasNext()){
                    stringBuilder.append(scanner.next().trim()).append("\n");
            }

                return stringBuilder.toString();

        }
    }
}

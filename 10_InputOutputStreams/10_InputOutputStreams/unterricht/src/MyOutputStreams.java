    /*
     * Java IO bietet das Konzept von Streams, das im Grunde einen kontinuierlichen
     * Datenfluss darstellt. Streams können viele verschiedene Datentypen wie
     * Bytes, Zeichen, Objekte usw. unterstützen.
     *
     * Generelles: Die Klasse OutputStream ist eine Superklasse
     * Sub-Klassen: AudioOutputStream, ByteArrayOutputStream, FileOutputStream, FilterOutputStream,
     * ObjectOutputStream, PipedOutputStream, SequenceOutputStream, StringBufferOutputStream
     *
     * Fast alle Streams implementieren die Interfaces => Closeable, AutoCloseable
     *
     * Die Schnittstelle Closeable stellt eine Methode namens close()
     * zum Schließen einer Quelle oder eines Ziels von Daten bereit.
     *
     * Die Schnittstelle AutoCloseable bietet auch eine Methode namens close() mit
     * ähnlichem Verhalten wie in Closeable. In diesem Fall wird jedoch beim
     * Verlassen eines try-with-resource-Blocks automatisch die Methode close()
     * aufgerufen.
     */

    import java.io.*;
    import java.nio.charset.StandardCharsets;
    import java.nio.file.Files;
    import java.nio.file.Path;
    import java.nio.file.Paths;
    import java.nio.file.StandardOpenOption;
    import java.util.Arrays;

    public class MyOutputStreams {
        public static void main(String[] args) {

            String lorem = "\uD83D\uDE82Lorem ipsum dolor sit amet, consectetur adipiscing elit. 今では幸福など存在しないと言われている。試合前までは、フュギアとイアキュリスは、ヌルラのように憂鬱な様子で座っていた。" +
                    "\n弓状骨前庭。いや、前にも動いてないのに、笑いの愛門としましょう。みんな面倒な人たちだ。友達がいないと毒に侵されることもある。\uD83D\uDE00\n";

            try {
                MyOutputStreams.writeStringAsBytes("ressources/Beispiel/text.txt", lorem);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try{
                MyOutputStreams.writeString("ressources/Beispiel/text2.txt", lorem);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private static void writeStringAsBytes(String file, String data) throws IOException {
            // Um mit Dateien zu arbeiten, brauchen wir entweder ein Objekt der File-Klasse oder vom Path-Interface.
            // Welches wir genau brauchen ist abhängig von anderen Klassen.
            // Das Path-Objekt erzeugen wir über Paths.get(String) oder Path.of(String)
            // File-Objekte werden über den Konstruktor erzeugt.
            Path path = Paths.get(file);

            // 'Files' (mit einem s) ist eine Klasse mit vielen Hilfsmethoden in Bezug auf Dateien. Wir koennen Dateien
            // erstellen, loeschen, verschieben, Ordner erstellen oder den Inhalt von Ordnern auflisten.
            // Achtung: Geloeschte Dateien und Ordner landen NICHT im Papierkorb!
            // Ordner im Dateipfad, die noch nicht existieren, werden erstellt.
            // 'getParent()' liefert den Pfad des übergeordneten Ordners zurück.
            // 'toAbsolutePath()' liefert den absoluten Pfad zurück.

            Files.createDirectories(path.toAbsolutePath().getParent());

            // String in Byte-Array umwandeln:
            byte[] bytes = data.getBytes(StandardCharsets.UTF_8);

            // Angucken des Byte-Arrays:
            System.out.println(Arrays.toString(bytes));

            // OutputStream implementiert AutoCloseable, darum koennen wir es in einem try-with-resources-Block verwenden,
            // damit der Stream automatisch geschlossen wird, wenn wir fertig sind. Der OutputStream wird selbst die Datei
            // erzeugen, wenn sie nicht existiert. Wenn die Datei bereits existiert, wird sie überschrieben.
            try (OutputStream out = Files.newOutputStream(path)) {
                for (byte b : bytes) {
                    out.write(b);   // jedes Byte einzeln schreiben
                }

                out.write(bytes); // alle Bytes auf einmal schreiben
                out.write(bytes, 0, bytes.length); // alle Bytes auf einmal schreiben, mit Angabe des Startindex und der Länge
            }
        }

        private static void writeString(String file, String data) throws IOException {
            Path path = Paths.get(file);

            // Der OutputStreamWriter umschließt einen OutputStream und kann Zeichen direkt an das gewünschte Ziel schreiben.
            // Optional können wir dem OutputStreamWriter auch einen Zeichensatz zur Kodierung mitgeben.
            // Es wird der Schritt zur Konvertierung in ein byte[] gespart, bzw. der Writer macht das für uns.
            try(OutputStream out = Files.newOutputStream(path);
            Writer writer = new OutputStreamWriter(out, StandardCharsets.UTF_8)) {
                writer.write(data);
            }

            // Alternative: FileWriter. Hier koennen wir auch angeben, ob der Text angehaengt werden soll (true) oder die Datei ueberschrieben werden soll (false).
            try(FileWriter writer = new FileWriter(file, StandardCharsets.UTF_8, true)) {
                writer.write(data);
                // Der FileWriter puffert den Text, bevor er in die Datei geschrieben wird. Das tatsächliche Schreiben in die Datei findet erst statt,
                // wenn der FileWriter geschlossen wird. Mit flush() können wir das Schreiben in die Datei aber schon vorher erzwingen.
                writer.flush(); // Daten in den Stream schreiben, ohne den Stream zu schließen
            }

            // Viel angenehmer, ab Java 11: Files.writeString(Path, String, Charset, OpenOption...)
            Files.writeString(path, data, StandardCharsets.UTF_8, StandardOpenOption.APPEND); // Daten an die Datei anhängen, ohne den Stream zu schließen
        }

    }
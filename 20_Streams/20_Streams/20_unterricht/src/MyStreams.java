import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MyStreams {
    public static void main(String[] args) {
        // Der Begriff "Stream" beschreibt einen Datenstrom, zum Beispiel Input- und Output-Streams in und aus Dateien.
        // Demnächst werden wir auch den Datenstrom zwischen zwei Programmen über das Netzwerk mit Sockets kennenlernen.
        // In diesem Thema heute geht es aber um das Interface "Stream<T>", welches Datenströme aus z.B. Listen bereitstellt.

        // Beispiel: Wir haben eine CSV-Datei mit Artikeln, die wir filtern und sortieren möchten.
        // Dazu wandeln wir alle Einträge in der CSV-Datei in Artikel-Objekte um
        // und verwenden das Stream<T>-Interface, um zu filtern und zu sortieren.
        try {
            Map<Integer, Artikel> artikelMap = MyStreams.toMapFromFile("/artikel.csv");

            for (Artikel artikel : artikelMap.values()) {
                System.out.println(artikel);
            }

            Scanner scanner = new Scanner(System.in);
            System.out.print("Hersteller: ");
            String hersteller = scanner.nextLine(); // Hersteller eingeben lassen
            artikelMap = MyStreams.filterByHersteller(artikelMap, hersteller); // und nach dem eingegebenen Hersteller filtern
            for (Artikel artikel : artikelMap.values()) {
                System.out.println(artikel);
            }
            System.out.println();
            List<Artikel> artikelList = MyStreams.orderByPreis(artikelMap);
            System.out.println("Nach Preis sortiert: ");
            for (Artikel artikel : artikelList) {
                System.out.println(artikel);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Die Methode erzeugt ein Verzeichnis aus einer Datei. Wir uebergeben den Pfad zur Datei und gibt uns Map<Integer, Artikel> zurueck.
    // Diese Methode liest eine CSV-Datei ein und wandelt jede Zeile in ein Artikel-Objekt um.
    // Anschließend werden alle Artikel in einer Map<Integer, Artikel> gespeichert.
    private static Map<Integer, Artikel> toMapFromFile(String path) throws IOException {

        // Anwendungsfall für getResourceAsStream gegenüber anderen Methoden:
        // getResourceAsStream bezieht sich auf Dateien, die mit dem Programm ausgeliefert werden.
        // Dateien, die fester Bestandteil der Anwendung sind, zum Beispiel als JAR verpackt.
        // Aus diesen Dateien kann nur gelesen werden. Ein Schreibzugriff ist nicht möglich.
        // Das wird häufig verwendet, um Grafiken zur Anzeige zu laden, als Icons zum Beispiel.
        // Oder um Standard-Konfigurationen für die Anwendung bereitzustellen.
        // Dazu müssen sich die Dateien im Projekt in einem Resources-Ordner befinden, der auch als Resources-Root markiert ist.

        try(InputStream in = MyStreams.class.getResourceAsStream(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {

            // (JH) Die gepufferten Zeichen befinden sich num im Stream des BufferedReaders.
            // Jetzt beginnt die Stream API ihre Magie zu wirken:
            // readerLines liefert einen Stream von Strings zurueck.

            Stream<String> zeilen = reader.lines(); // Stream<String> erzeugen, jede Zeile ist ein String
            Stream<String> zeilenOhneUeberschrift = zeilen.skip(1); // erste Zeile überspringen, da sie die Spaltenüberschriften enthält

            // Jetzt die wichtigste Methode der Stream<API> die map Methode.
            // map verlangt eine Instanz vom Typ function (ein Funktionales Interface).
            // Die map Methode verwandelt die Objekte des Streams in andere Objekte.
            // Jede Zeile hat dann diesen Aufbau z.B.: "200001,Smartphone X1,699.99,TechCorp"
            // Vereinfacht steht da: fuer jede Zeile mache Folgendes

            Stream<Artikel> artikelStream = zeilenOhneUeberschrift.map(zeile -> {
                String[] werte = zeile.split(","); // Zeile in Spalten aufteilen
                int artikelnummer = Integer.parseInt(werte[0]); // erste Spalte ist die Artikelnummer
                String bezeichnung = werte[1]; // zweite Spalte ist die Bezeichnung
                double preis = Double.parseDouble(werte[2]); // dritte Spalte ist der Preis
                String hersteller = werte[3]; // vierte Spalte ist der Hersteller

                return new Artikel(artikelnummer, bezeichnung, preis, hersteller); // Artikel-Objekt erzeugen und zurückgeben

            });

            // Jetzt haben wir einen Stream<Artikel> und können ihn in eine Map<Integer, Artikel> umwandeln.
            Map<Integer, Artikel> artikelMap = artikelStream.collect(Collectors.toMap(Artikel::getArtikelnummer, Function.identity()));

            return artikelMap;
        }
    }

    private static Map<Integer, Artikel> filterByHersteller(Map<Integer, Artikel> artikelMap, String hersteller) {
        return artikelMap.values().stream()
                .filter(artikel -> artikel.getHersteller().equalsIgnoreCase(hersteller))
                .collect(Collectors.toMap(Artikel::getArtikelnummer, Function.identity()));
    }

    private static List<Artikel> orderByPreis(Map<Integer, Artikel> artikelMap) {
        return artikelMap.values().stream()
                .sorted(Comparator.comparingDouble(Artikel::getPreis))
                .collect(Collectors.toList());
    }

}

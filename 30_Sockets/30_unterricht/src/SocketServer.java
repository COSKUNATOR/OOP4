
/*
 * Der Begriff Socket-Programmierung bezieht sich auf das Schreiben von
 * Programmen, die auf mehreren Computern ausgeführt werden, wobei die Programme
 * alle über ein Netzwerk miteinander verbunden sind.
 *
 * Es gibt zwei Kommunikationsprotokolle, die wir für die Socket-Programmierung
 * verwenden können: User Datagram Protocol (UDP) und Transfer Control Protocol (TCP).
 *
 * Der Hauptunterschied zwischen den beiden besteht darin, dass UDP
 * verbindungslos ist, was bedeutet, dass es keine Sitzung zwischen dem Client
 * und dem Server gibt, während TCP verbindungsorientiert ist, was bedeutet,
 * dass zuerst eine exklusive Verbindung zwischen dem Client und dem Server
 * hergestellt werden muss, damit die Kommunikation stattfinden kann.
 *
 * Dies ist eine Einführung in die Socket-Programmierung über TCP/IP-Netzwerke
 * und demonstriert, wie Client/Server-Anwendungen in Java geschrieben werden.
 * Für UDP Beispiele: <link="-1247551680"><u>https://www.baeldung.com/udp-in-java</u></link>
 *
 * Da es hier nur um eine kurze Einführung geht, verwenden wir eine unverschlüsselte
 * Kommunikation zwischen Server und Client. Eine verschlüsselte Kommunikation kann
 * mit TLS/SSL realisiert werden.
 * <link="-1532958556"><u>https://kodejava.org/how-do-i-implement-secure-socket-communication-with-sslsocket-and-sslserversocket-in-java/</u></link>
 *
 * Klassen und Schnittstellen, die sich um Kommunikationsdetails auf niedriger
 * Ebene zwischen Client und Server kümmern, befinden sich größtenteils im
 * java.net-Paket
 *
 * Der Einfachheit halber führen wir unsere Client- und Serverprogramme auf
 * demselben Computer aus. Wenn wir sie auf verschiedenen vernetzten Computern
 * ausführen würden, würde sich nur die IP-Adresse ändern. In diesem Fall
 * verwenden wir localhost auf 127.0.0.1
 *
 * Es wird eine bidirektionale Kommunikationsanwendung sein, bei der der Client
 * den Server begrüßt und der Server antwortet.
 *
 */

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class SocketServer {
    private static final Logger logger = Logger.getLogger(SocketServer.class.getName());

    public static void main(String[] args) {
        SocketServer server = new SocketServer();
        try {
            configureLogger();
        }catch (IOException e){
            e.printStackTrace();
        }
        try {
            server.start(12345);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start(int port) throws IOException {
        // Wir erzeugen einen Server-Socket, der an den angegebenen Port gebunden ist.
        try(ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("Warte auf Verbindungen auf Port " + port + "...");

            // Der Client, der sich mit dem Server verbindet, wird durch die Klasse Socket repraesentiert.
            // Die accept()-Methode wartet auf eingehende Verbindungen. Der Programmablauf wird so lange blockiert, bis ein Client eine Verbindung herstellt.
            try(Socket clientSocket = serverSocket.accept()) {
                if(clientSocket.isConnected()) {
                    System.out.println("Client verbunden: " + clientSocket.getInetAddress().getHostAddress());
                }
                // Der Server ist ungebunden. Darum nimmt er Verbindungen von allen Clients an, die sich mit ihm verbinden wollen. Der Server kann mehrere Clients gleichzeitig bedienen.
                System.out.println(serverSocket.toString());
                System.out.println(serverSocket.getInetAddress());
                System.out.println(serverSocket.getLocalSocketAddress());
                System.out.println(clientSocket.getInetAddress());

                // Über den Socket können wir Daten senden und empfangen.
                // getOutputStream() liefert einen Byte-Ausgabestrom.
                // Diesen umschließen wir mit einem PrintWriter,
                // damit wir komfortabel Text senden können.
                // Der Parameter true aktiviert AutoFlush.
                // Dadurch werden Daten sofort gesendet,
                // ohne dass writer.flush() aufgerufen werden muss.

                // Mit dem Writer schicken wir Daten an Clients und mit dem Reader können wir eingehende Daten lesen.
                // Oracle empfiehlt in der Doku für Java 8, PrintWriter und BufferedReader zu verwenden.
                // https://docs.oracle.com/javase/tutorial/networking/sockets/readingWriting.html

                try(PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

                        while(true){
                            // Warten auf Nachrichten des Clients. Die readLine()-Methode blockiert den Programmablauf, bis eine Nachricht empfangen wird.
                            System.out.println("Warte auf Nachricht vom Client...");

                            // Eine Zeile Text aus dem InputStream lesen:
                            String inputLine = reader.readLine();
                            logger.info("Eingegebener Befehl: " + inputLine);
                            System.out.println("Nachricht vom Client empfangen: " + inputLine);

                            // Server soll auf die Nachricht antworten:
                            if(inputLine.equalsIgnoreCase("Hallo Server")){
                                writer.println("Hallo Client");
                            } else if (inputLine.equalsIgnoreCase("uhrzeit")) {
                                writer.println(LocalTime.now());
                            } else if (inputLine.equalsIgnoreCase("dir")) {
                                writer.println(System.getProperty("user.dir"));
                            } else if(inputLine.equalsIgnoreCase("stop")){
                                writer.println("Server wird beendet");
                                break;
                            }
                            else {
                                writer.println("Unbekannte Nachricht: " + inputLine);
                            }
                        }
                }
            }
        }
    }
    private static void configureLogger() throws IOException {
        String modulOrdner = System.getProperty("user.dir");
        String logDatei = modulOrdner + File.separator + "befehle.log";

        FileHandler fileHandler = new FileHandler(logDatei, true);
        fileHandler.setFormatter(new SimpleFormatter());

        logger.addHandler(fileHandler);
        logger.setUseParentHandlers(false);

        System.out.println("Logdatei wird gespeichert unter: " + logDatei);
    }
}

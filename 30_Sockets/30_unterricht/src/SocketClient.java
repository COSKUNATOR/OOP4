
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class SocketClient {
    Scanner sc = new Scanner(System.in);


    public static void main(String[] args) {
        SocketClient client = new SocketClient();

        // Wenn wir den Client auf einem anderen Computer als den Server ausführen, können wir über die Argumente beim Programmstart die IP-Adresse des Servers übergeben. Wird nichts übergeben, verbinden wir einfach auf Localhost.
        // Beispiel: Der Server läuft auf diesem Rechner, der Client wird auf einer virtuellen Maschine ausgeführt.
        // Dann kann der Client mit dem Kommandozeilen-Befehl "java template/MySocketClient.class 172.25.157.8" gestartet werden (die IP-Adresse bei Bedarf anpassen)

        // Beim Programmstart können optional Kommandozeilenparameter übergeben werden. // args enthält alle diese Parameter als String-Array.
        // Wurde keine IP-Adresse angegeben...
        try{
            if(args.length == 0) {
                // ...verbinden wir uns mit dem lokalen Rechner.
                // "localhost" ist ein Hostname für die IP-Adresse 127.0.0.1.

                // // Wurde eine IP-Adresse übergeben, verbinden wir uns mit diesem Rechner.
                // args[0] enthält den ersten Parameter.
                client.connect("localhost", 12345);
            } else {
                client.connect(args[0], 12345);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }


    // Erzeugt einen Socket. Der Konstruktor versucht sofort eine TCP-Verbindung zu dem angegebenen Hostnamen und Port herzustellen.
    // Wenn die Verbindung nicht hergestellt werden kann, wird eine IOException ausgelöst.
    public void connect(String ip, int port) throws IOException {
        try(Socket clientSocket = new Socket(ip, port)) {
            if(clientSocket.isConnected()) {

                // Gibt die IP des Servers aus:
                System.out.println("Verbunden mit Server: " + clientSocket.getInetAddress().getHostAddress());
            }

            // Gibt die eigene (lokale) IP-Adresse des Clients aus:
            System.out.println(clientSocket.getLocalAddress());

            // Gibt die komplette Adresse des Servers aus. Diese besteht aus IP-Adresse und Port
            System.out.println(clientSocket.getRemoteSocketAddress());

            // Ueber den Socket koennen wir Daten senden und empfangen.
            // Der PrintWriter schreibt Text auf den OutputStream.
            // true aktiviert AutoFlush.
            // Dadurch wird jede println()-Ausgabe sofort verschickt.
            try(PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))){

                    // Variable fuer die Antwort des Servers.
                    String antwort;

                    do{
                        System.out.print("> ");
                        String nachricht = sc.nextLine();
                        writer.println(nachricht);
                        antwort = reader.readLine();
                        System.out.println(antwort);
                    } while(!antwort.equalsIgnoreCase("stop"));
            }
        }
    }
}

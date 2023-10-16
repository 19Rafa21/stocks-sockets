import java.io.*;
import java.net.*;

public class ThreadRequest implements Runnable {
    Thread t;
    String servidor = "127.0.0.1";
    int port = 2000;

    ThreadRequest() {
        t = new Thread(this, "ThreadRequest");
        t.start();
    }

    public void run() {
        InetAddress serverAdress = null;
        try {
            serverAdress = InetAddress.getByName(servidor);
        } catch (UnknownHostException e) {
            System.out.println("Erro ao obter o endereço do servidor: " + e);
            System.exit(1);
        }

        while (true) {
            try (Socket client = new Socket(serverAdress, port);
                 BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                 PrintWriter out = new PrintWriter(client.getOutputStream(), true)) {

                out.println("STOCK_REQUEST");
                StringBuilder stockResponse = new StringBuilder();
                String line;

                while ((line = in.readLine()) != null) {
                    stockResponse.append(line).append("\n");
                }

                System.out.println("Current Stock:\n" + stockResponse);

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    System.out.println("Erro na execução do servidor: " + e);
                    System.exit(1);
                }
            } catch (IOException e) {
                System.out.println("Erro na execução do servidor: " + e);
                System.exit(1);
            }
        }
    }
}


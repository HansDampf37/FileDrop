package task5;

import java.io.*;
import java.net.*;

public class EchoServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Echo server started.");

            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected.");

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            String line;
            while ((line = in.readLine()) != null) {
                out.println(line);
                if (line.equals("END.")) break;
            }

            clientSocket.close();
            System.out.println("Server stopped.");

        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }
}

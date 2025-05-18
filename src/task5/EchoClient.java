package task5;

import java.io.*;
import java.net.*;

public class EchoClient {
    public static void main(String[] args) {
        try (
                Socket socket = new Socket("localhost", 12345);
                BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
            System.out.println("Connected to echo server");

            String inputLine;
            while ((inputLine = userInput.readLine()) != null) {
                out.println(inputLine);
                System.out.println(in.readLine());
                if (inputLine.equals("END.")) break;
            }

        } catch (IOException e) {
            System.out.println("Client error: " + e.getMessage());
        }
    }
}

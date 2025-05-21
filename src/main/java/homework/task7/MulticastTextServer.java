package homework.task7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastTextServer {
    public static final String MULTICAST_ADDRESS = "225.4.5.6";
    public static final int PORT = 5000;

    public static void main(String[] args) {
        String filePath = "/home/adrian/Dev/Network Programming with Java/src/main/java/homework/task7/data.txt";

        try (
                MulticastSocket socket = new MulticastSocket();
                BufferedReader reader = new BufferedReader(new FileReader(filePath));
        ) {
            InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);
            socket.joinGroup(group);

            String line;
            while ((line = reader.readLine()) != null) {
                byte[] data = line.getBytes();
                DatagramPacket packet = new DatagramPacket(data, data.length, group, PORT);
                socket.send(packet);
                System.out.println("Sent: " + line);
                Thread.sleep(2000);
            }

            socket.leaveGroup(group);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

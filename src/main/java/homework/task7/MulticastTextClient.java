package homework.task7;

import java.io.IOException;
import java.net.*;

public class MulticastTextClient {
    public static final String MULTICAST_ADDRESS = "225.4.5.6";
    public static final int PORT = 5000;

    public static void main(String[] args) {
        try (
                MulticastSocket socket = new MulticastSocket(PORT);
        ) {
            InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);
            socket.joinGroup(group);
            byte[] buffer = new byte[1024];

            System.out.println("Client joined group.");

            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                String line = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Received: " + line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

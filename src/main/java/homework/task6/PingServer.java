package homework.task6;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketException;

public class PingServer extends UDPServer {
    public final static int DEFAULT_PORT = 12345;

    public PingServer() throws SocketException {
        super(DEFAULT_PORT);
    }


    public void respond(DatagramPacket packet) {
        try {
            String message = new String(packet.getData(), 0, packet.getLength());
            if (message.equals("PING")) {
                System.out.println("Message received: " + message);
                String response = "iPONG";
                byte[] data = response.getBytes();
                DatagramPacket outgoing = new DatagramPacket(data, data.length, packet.getAddress(), packet.getPort());
                socket.send(outgoing);
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }

    }

    public static void main(String[] args) {
        try {
            UDPServer server = new PingServer();
            server.start();
            System.out.println("PING SERVER STARTED");
        } catch (SocketException ex) {
            System.err.println(ex);
        }
    }
}

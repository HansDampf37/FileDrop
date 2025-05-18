package discovery;

import core.Peer;

import java.io.*;
import java.net.*;

/**
 * Listens for UDP broadcast discovery requests and responds with peer info.
 */
public class DiscoveryListener implements Runnable {

    private boolean running = false;
    private static final int DISCOVERY_PORT = 8888;
    private final Peer peer;

    public DiscoveryListener(Peer peer) {
        this.peer = peer;
    }

    @Override
    public void run() {
        try (DatagramSocket socket = new DatagramSocket(DISCOVERY_PORT, InetAddress.getByName("0.0.0.0"))) {
            socket.setBroadcast(true);
            byte[] buffer = new byte[1024];

            running = true;
            while (running) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                String message = new String(packet.getData(), 0, packet.getLength());
                if ("DISCOVER_REQUEST".equals(message)) {
                    String ip = InetAddress.getLocalHost().getHostAddress();
                    String response = peer.toDiscoveryResponse();

                    byte[] responseData = response.getBytes();
                    DatagramPacket responsePacket = new DatagramPacket(
                            responseData, responseData.length,
                            packet.getAddress(), packet.getPort()
                    );
                    socket.send(responsePacket);
                }
            }
        } catch (IOException e) {
            System.err.println("Discovery listener error: " + e.getMessage());
        }
    }

    public void stop() {
        running = false;
    }
}

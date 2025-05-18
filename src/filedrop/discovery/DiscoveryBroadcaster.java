package filedrop.discovery;

import filedrop.core.Peer;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Sends a UDP broadcast message on the local network to discover peers.
 */
public class DiscoveryBroadcaster {

    private static final int DISCOVERY_PORT = 8888;
    private static final String DISCOVERY_REQUEST = "DISCOVER_REQUEST";

    /**
     * Sends a UDP broadcast request and waits for responses.
     * @return List of discovered peers with IP and port
     */
    public List<Peer> discoverPeers() {
        List<Peer> peers = new ArrayList<>();

        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setSoTimeout(2000); // 2 seconds timeout
            socket.setBroadcast(true);

            byte[] requestData = DISCOVERY_REQUEST.getBytes();
            DatagramPacket packet = new DatagramPacket(
                    requestData, requestData.length,
                    InetAddress.getByName("255.255.255.255"), DISCOVERY_PORT
            );
            socket.send(packet);

            // Collect responses
            byte[] buffer = new byte[1024];
            long endTime = System.currentTimeMillis() + 2000;

            while (System.currentTimeMillis() < endTime) {
                try {
                    DatagramPacket response = new DatagramPacket(buffer, buffer.length);
                    socket.receive(response);

                    String message = new String(response.getData(), 0, response.getLength());
                    if (message.startsWith("RESPONSE|")) {
                        Peer peer = Peer.fromDiscoveryResponse(message);
                        if (peer != null) {
                            peers.add(peer);
                        }
                    }
                } catch (SocketTimeoutException ignored) {}
            }

        } catch (IOException e) {
            System.err.println("Discovery error: " + e.getMessage());
        }

        return peers;
    }
}

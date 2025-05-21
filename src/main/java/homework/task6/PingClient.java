package homework.task6;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

public class PingClient {

    public static void main(String[] args) {
        int lostPackets = 0;
        long minRtt = Long.MAX_VALUE;
        long maxRtt = 0;
        long totalRtt = 0;
        int successful = 0;

        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setSoTimeout(10000);
            InetAddress address = InetAddress.getByName("127.0.0.1");

            for (int i = 0; i < 4; i++) {
                byte[] sendData = "PING".getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address, 12345);

                try {
                    System.out.println("Sending packet " + (i + 1));
                    long start = System.nanoTime();
                    socket.send(sendPacket);

                    byte[] receiveBuffer = new byte[1024];
                    DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                    socket.receive(receivePacket);
                    long end = System.nanoTime();

                    String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
                    long rtt = (end - start) / 1000000;
                    System.out.println("Received reply: " + response + "(RTT: " + rtt + " ms)\n");

                    minRtt = Math.min(minRtt, rtt);
                    maxRtt = Math.max(maxRtt, rtt);
                    totalRtt += rtt;
                    successful++;
                } catch (SocketTimeoutException e) {
                    System.out.println("Packet " + (i + 1) + " lost (timeout)");
                    lostPackets++;
                }
            }

            if (successful > 0) {
                System.out.println("minimum RTT: " + minRtt + "\nmaximum RTT: " + maxRtt + "\nmean RTT: " + totalRtt / successful);
            }
            System.out.println("Lost packets: " + lostPackets);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

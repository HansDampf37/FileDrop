package filedrop;

import filedrop.core.Peer;
import filedrop.discovery.DiscoveryBroadcaster;
import filedrop.discovery.DiscoveryListener;
import filedrop.core.FileReceiver;
import filedrop.core.FileSender;

import java.io.File;
import java.net.InetAddress;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Main entry point for the peer-to-peer file transfer app.
 */
public class App {
    public static void main(String[] args) throws Exception {
        int fileTransferPort = 5000; // Use same port on all peers
        String peerName = "Peer-" + new Random().nextInt(1000);
        String localIp = InetAddress.getLocalHost().getHostAddress();
        Peer localPeer = new Peer(peerName, localIp, fileTransferPort);

        System.out.println("Starting " + localPeer);

        // Start receiver in background
        Thread receiverThread = new Thread(new FileReceiver(fileTransferPort));
        receiverThread.setDaemon(true);
        receiverThread.start();

        // Start discovery listener in background
        Thread discoveryListenerThread = new Thread(new DiscoveryListener(localPeer));
        discoveryListenerThread.setDaemon(true);
        discoveryListenerThread.start();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Menu ===");
            System.out.println("[1] Send a file (enter IP manually)");
            System.out.println("[2] Discover peers and send");
            System.out.println("[3] Exit");
            System.out.print("Choose option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Enter recipient IP: ");
                    String ip = scanner.nextLine();
                    System.out.print("Enter file path: ");
                    String path = scanner.nextLine();

                    File file = new File(path);
                    if (file.exists()) {
                        new FileSender(ip, fileTransferPort, file).send();
                    } else {
                        System.out.println("File does not exist.");
                    }
                    break;

                case "2":
                    DiscoveryBroadcaster broadcaster = new DiscoveryBroadcaster();
                    List<Peer> peers = broadcaster.discoverPeers(localPeer);

                    if (peers.isEmpty()) {
                        System.out.println("No peers found.");
                        break;
                    }

                    System.out.println("Discovered peers:");
                    for (int i = 0; i < peers.size(); i++) {
                        System.out.println("[" + i + "] " + peers.get(i));
                    }

                    System.out.print("Select peer index: ");
                    int index = Integer.parseInt(scanner.nextLine());
                    Peer selectedPeer = peers.get(index);

                    System.out.print("Enter file path: ");
                    File fileToSend = new File(scanner.nextLine());
                    if (fileToSend.exists()) {
                        new FileSender(selectedPeer.ip(), selectedPeer.fileTransferPort(), fileToSend).send();
                    } else {
                        System.out.println("File does not exist.");
                    }
                    break;

                case "3":
                    System.out.println("Goodbye.");
                    return;

                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}

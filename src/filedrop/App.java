package filedrop;

import filedrop.core.FileReceiver;
import filedrop.core.FileSender;

import java.io.File;
import java.util.Scanner;

/**
 * Entry point for the file transfer application.
 * Allows user to choose between sender and receiver mode.
 */
public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose mode: [1] Receive  [2] Send");
        String choice = scanner.nextLine();

        if ("1".equals(choice)) {
            FileReceiver receiver = new FileReceiver(5000);
            receiver.start();

        } else if ("2".equals(choice)) {
            System.out.print("Enter path to file: ");
            String path = scanner.nextLine();
            File file = new File(path);

            if (!file.exists()) {
                System.out.println("File does not exist!");
                return;
            }

            // Send to localhost
            FileSender sender = new FileSender("127.0.0.1", 5000, file);
            sender.send();

        } else {
            System.out.println("Invalid choice.");
        }

        scanner.close();
    }
}

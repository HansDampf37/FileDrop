package core;

import java.io.File;
import java.net.InetAddress;

@FunctionalInterface
public interface FileReceivedCallback {
    void handle(File file, InetAddress sender);
}

package task2;

import java.io.*;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class FolderTraversal {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        if (args.length != 1) {
            System.err.println("Usage: FolderTraversal <folder>");
            System.exit(1);
        }
        File folder = new File(args[0]);
        if (!folder.exists()) throw new IOException("Folder " + folder.getName() + " does not exist");
        if (!folder.isDirectory()) throw new IOException("Folder " + folder.getName() + " is not a directory");

        File[] files = folder.listFiles();
        if (files == null) throw new IOException("Error while listing files in folder " + folder.getName());
        files = Arrays.stream(files).filter(File::isFile).toArray(File[]::new);


        StringBuilder str = new StringBuilder();
        for (File file : files) {
            str.append(FolderTraversal.getChecksum(file));
            str.append(" *").append(file.getAbsolutePath()).append("\n");
        }
        String md5String = str.toString();

        File outputFile = new File(folder.getParent(), "checksum-java.md5");
        FileWriter fw = new FileWriter(outputFile);
        fw.write(md5String);
        fw.close();
    }

    private static String getChecksum(File file) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        try (InputStream fis = new FileInputStream(file); DigestInputStream dis = new DigestInputStream(fis, md)) {
            byte[] buffer = new byte[1024];
            while (dis.read(buffer) != -1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        byte[] digest = md.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) sb.append(String.format("%02x", b));
        return sb.toString().toUpperCase();
    }
}

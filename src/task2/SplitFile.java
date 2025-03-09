package task2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class SplitFile {
    private String getFileName(File inputFile, int fileCount) {
        String oldName = inputFile.getAbsolutePath().split("\\.")[0];
        String newName = oldName + "_PART" + fileCount;
        String format = inputFile.getName().split("\\.")[1];
        return newName + "." + format;
    }

    public void split(File file, int sizeInBytes) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] buffer = new byte[sizeInBytes];
            int fileCount = 0;
            while (fis.read(buffer) != -1) {
                fileCount++;
                String fileName = getFileName(file, fileCount);
                File outputFile = new File(fileName);
                if (!outputFile.exists() && !outputFile.createNewFile())
                    throw new IOException("Could not create file " + file.getName());
                try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                    fos.write(buffer);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.err.println("Usage: java SplitFile <input file> <maximum file size>");
            System.exit(1);
        }
        File inputFile = new File(args[0]);
        int sizeInBytes = Integer.parseInt(args[1]);

        if (!inputFile.exists() || !inputFile.canRead()) throw new IOException("Could not read file " + inputFile.getName());

        SplitFile splitFile = new SplitFile();
        splitFile.split(inputFile, sizeInBytes);
    }
}

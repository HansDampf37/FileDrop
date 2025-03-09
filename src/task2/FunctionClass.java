package task2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class FunctionClass {
    public List<File> unzip(File file) throws IOException {
        if (file.exists() && file.isFile() && file.getName().endsWith(".zip")) {
            List<File> resultingFiles = new ArrayList<>(3);
            File destDir = new File(file.getAbsolutePath().replace(".zip", ""));
            if (!destDir.exists() && !destDir.mkdirs()) throw new IOException("Could not create directory: " + destDir);
            destDir.deleteOnExit();
            byte[] buffer = new byte[1024];
            ZipInputStream zis = new ZipInputStream(new FileInputStream(file));
            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                File newFile = new File(destDir, zipEntry.getName());
                newFile.deleteOnExit();

                if (!newFile.exists() && !newFile.createNewFile()) throw new IOException("Could not create file: " + newFile);

                // write file content
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                resultingFiles.add(newFile);
                fos.close();
                zipEntry = zis.getNextEntry();
            }
            zis.closeEntry();
            zis.close();
            return resultingFiles;
        } else {
            throw new IllegalArgumentException("File is not a zip file");
        }
    }

    public void merge(List<File> files, File newFile) throws IOException {
        byte[] buffer = new byte[1024];

        try (FileOutputStream fos = new FileOutputStream(newFile, true)) {
            for (File file : files) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    int len;
                    while ((len = fis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                }
            }
        }
    }

    public void zip(List<File> files, File zipFile) throws IOException {
        if (zipFile.isFile() && zipFile.getName().endsWith(".zip")) {
            if (!zipFile.exists() && zipFile.createNewFile()) throw new IOException("Could not create file: " + zipFile);

            final FileOutputStream fos = new FileOutputStream(zipFile);
            ZipOutputStream zipOut = new ZipOutputStream(fos);
            for (File file : files) {
                FileInputStream fis = new FileInputStream(file);
                ZipEntry zipEntry = new ZipEntry(file.getName());
                zipOut.putNextEntry(zipEntry);

                byte[] bytes = new byte[1024];
                int length;
                while ((length = fis.read(bytes)) >= 0) {
                    zipOut.write(bytes, 0, length);
                }
                fis.close();
            }

            zipOut.close();
            fos.close();
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: java FunctionClass <input file> <output file>");
            System.exit(1);
        }
        File in = new File(args[0]);
        File out = new File(args[1]);
        File merged = new File("NetworkProgrammingWithJava_merged_files.tmp");
        merged.deleteOnExit();
        if (!in.exists()) throw new IOException("File not found: " + in);
        if (!merged.createNewFile()) throw new IOException("Could not create temp file: " + merged);
        if (out.exists() && !out.delete()) throw new IOException("Could not prev temp file: " + out);
        if (!out.createNewFile()) throw new IOException("Could not create file: " + out);

        FunctionClass fc = new FunctionClass();
        List<File> files = fc.unzip(in);
        fc.merge(files, merged);
        files.add(merged);
        fc.zip(files, out);
    }
}

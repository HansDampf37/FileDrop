package homework.task4;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

public class ImageDownloader {

        public static void main(String[] args) throws Exception {
            URL resourceUrl = new URL("https://0.academia-photos.com/2026693/668180/829410/s200_milen.petrov.jpg");
            File outputFile = new File("mp.jpg");

            try (InputStream in = resourceUrl.openStream(); FileOutputStream fos = new FileOutputStream(outputFile)) {
                byte[] buffer = new byte[4096];
                int bytesRead;

                while ((bytesRead = in.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                }
            }
        }
}

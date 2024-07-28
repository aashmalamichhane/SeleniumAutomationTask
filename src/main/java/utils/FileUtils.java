package utils;

import java.io.File;
import java.io.IOException;

public class FileUtils {

    public static File initializeFile(String filePath) throws IOException {
        try {
            File file = new File(filePath);
            file.getParentFile().mkdirs();
            if (!file.exists()) {
                file.createNewFile();
            }
            return file;
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("File creation error");
        }
    }
}

package text.processor.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class FileIO {
        // 1. Read file line by line
    public List<String> readFile(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }

    // 2. Write lines to a file
    public void writeFile(File file, List<String> lines) throws IOException {
        Files.write(file.toPath(), lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }
    
}

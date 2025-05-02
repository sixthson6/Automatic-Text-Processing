package text.processor.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class FileIO {
    
    public List<String> readFile(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }

    public void writeFile(File file, List<String> lines) throws IOException {
        Files.write(file.toPath(), lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }
    
}

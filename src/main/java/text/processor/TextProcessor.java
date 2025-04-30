package text.processor;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import text.processor.service.FileIO;

public class TextProcessor {
    private final FileIO fileIO = new FileIO();
    // 5. Word frequency map using Streams
    public Map<String, Long> wordFrequency(String input) {
        return Arrays.stream(input.toLowerCase().split("\\W+"))
                     .filter(word -> !word.isBlank())
                     .collect(Collectors.groupingBy(w -> w, Collectors.counting()));
    }

    // 6. Pattern recognition: lines that match regex
    public List<String> extractMatchingLines(List<String> lines, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return lines.stream()
                    .filter(line -> pattern.matcher(line).find())
                    .collect(Collectors.toList());
    }

    // 7. Text summarization (word + character count)
    public Map<String, Integer> summarizeText(String input) {
        Map<String, Integer> summary = new HashMap<>();
        summary.put("Word Count", input.trim().split("\\s+").length);
        summary.put("Character Count", input.length());
        return summary;
    }

    // 8. Batch replace across multiple files
    public void batchReplace(List<File> files, String regex, String replacement) throws IOException {
        for (File file : files) {
            List<String> lines = fileIO.readFile(file);
            List<String> updated = lines.stream()
                    .map(line -> line.replaceAll(regex, replacement))
                    .collect(Collectors.toList());
            fileIO.writeFile(file, updated);
        }
    }
}
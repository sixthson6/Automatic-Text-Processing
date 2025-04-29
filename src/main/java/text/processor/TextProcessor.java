package text.processor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TextProcessor {

    // 1. Read file line by line
    public List<String> readFile(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }

    // 2. Write lines to a file
    public void writeFile(File file, List<String> lines) throws IOException {
        Files.write(file.toPath(), lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    // 3. Regex search (returns matched substrings)
    public List<String> findMatches(String input, String regex) {
        List<String> matches = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            matches.add(matcher.group());
        }
        return matches;
    }

    // 4. Regex replace
    public String replaceMatches(String input, String regex, String replacement) {
        return input.replaceAll(regex, replacement);
    }

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
            List<String> lines = readFile(file);
            List<String> updated = lines.stream()
                    .map(line -> line.replaceAll(regex, replacement))
                    .collect(Collectors.toList());
            writeFile(file, updated);
        }
    }
}
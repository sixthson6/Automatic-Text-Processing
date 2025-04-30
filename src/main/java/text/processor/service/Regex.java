package text.processor.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    
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
}

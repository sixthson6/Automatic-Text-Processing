package text.processor.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class Regex {

    // Matches all occurrences of a regex pattern in the input text
    public List<String> findMatches(String input, String regex) {
        List<String> matches = new ArrayList<>();
        try {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(input);
            while (matcher.find()) {
                matches.add(matcher.group());
            }
        } catch (PatternSyntaxException e) {
            matches.add("Invalid regex pattern: " + e.getDescription());
        }
        return matches;
    }

    // Replaces all occurrences matching the regex with the given replacement
    public String replaceMatches(String input, String regex, String replacement) {
        try {
            return input.replaceAll(regex, replacement);
        } catch (PatternSyntaxException e) {
            return "Invalid regex pattern: " + e.getDescription();
        }
    }

    // Highlights (with markers) all matching parts — useful for visual debugging or preview
    public String highlightMatches(String input, String regex) {
        try {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(input);
            StringBuffer sb = new StringBuffer();
            while (matcher.find()) {
                matcher.appendReplacement(sb, "[[" + matcher.group() + "]]"); // [[match]]
            }
            matcher.appendTail(sb);
            return sb.toString();
        } catch (PatternSyntaxException e) {
            return "Invalid regex pattern: " + e.getDescription();
        }
    }

    // Validates whether a regex pattern is syntactically correct
    public boolean isValidRegex(String regex) {
        try {
            Pattern.compile(regex);
            return true;
        } catch (PatternSyntaxException e) {
            return false;
        }
    }
}
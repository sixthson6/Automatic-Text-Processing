package text.processor.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class Regex {

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

    public String replaceMatches(String input, String regex, String replacement) {
        try {
            return input.replaceAll(regex, replacement);
        } catch (PatternSyntaxException e) {
            return "Invalid regex pattern: " + e.getDescription();
        }
    }

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

    public boolean isValidRegex(String regex) {
        try {
            Pattern.compile(regex);
            return true;
        } catch (PatternSyntaxException e) {
            return false;
        }
    }
}
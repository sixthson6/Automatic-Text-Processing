package text.processor;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import text.processor.service.TextEntry;

public class DataManager {
    private final Map<String, TextEntry> entries = new HashMap<>();

    public void addEntry(TextEntry entry) {
        entries.put(entry.getId(), entry);
    }

    public void updateEntry(String id, String newContent) {
        TextEntry entry = entries.get(id);
        if (entry != null) {
            entry.setContent(newContent);
        }
    }

    public void deleteEntry(String id) {
        entries.remove(id);
    }

    public TextEntry getEntry(String id) {
        return entries.get(id);
    }

    public Collection<TextEntry> getAllEntries() {
        return entries.values();
    }
}

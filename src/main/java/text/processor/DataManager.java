package text.processor;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import text.processor.service.TextEntry;

public class DataManager {
    private final Map<String, TextEntry> entries = new HashMap<>();

    public String addEntry(TextEntry entry) {
        entries.put(entry.getId(), entry);
        return entry.getId();
    }

    public void deleteEntry(String id) {
        entries.remove(id);
    }

    public Collection<TextEntry> getAllEntries() {
        return entries.values();
    }
}

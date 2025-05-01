package text.processor.service;

import java.util.Objects;

public class TextEntry {
    private String id;
    private String label;
    private String content;

    public TextEntry(String id, String label, String content) {
        this.id = id;
        this.label = label;
        this.content = content;
    }

    public TextEntry() {
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public String getContent() {
        return content;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setContent(String content) {
        this.content = content;
    }

    // hashCode and equals for collection usage
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof TextEntry)) return false;
        TextEntry other = (TextEntry) obj;
        return Objects.equals(id, other.id);
    }

    @Override
    public String toString() {
        return "TextEntry{id='" + id + "', label='" + label + "', content='" + content + "'}";
    }
}



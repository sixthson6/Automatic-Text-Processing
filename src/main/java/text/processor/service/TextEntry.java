package text.processor.service;

import java.util.Objects;

public class TextEntry {
    private String id;
    private String content;

    // public TextEntry(String id, String content) {
    //     this.id = id;
    //     this.content = content;
    // }

    public TextEntry(String content) {
        this.id = String.valueOf(content.hashCode());
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TextEntry)) return false;
        TextEntry that = (TextEntry) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return id + ": " + content;
    }
}

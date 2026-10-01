package nvb.dev.pkm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Note {
    private final String title;
    private final String content;
    private final List<String> tags;

    public Note(String title, String content) {
        this.title = title;
        this.content = content;
        this.tags = new ArrayList<>();
    }

    public Note(String title, String content, List<String> tags) {
        this.title = title;
        this.content = content;
        this.tags = new ArrayList<>(tags);
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public List<String> getTags() {
        return Collections.unmodifiableList(tags);
    }

    public void addTag(String tag) {
        if (!tags.contains(tag))
            tags.add(tag);
    }
}

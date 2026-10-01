package nvb.dev.pkm.utils;

import nvb.dev.pkm.dto.ParsedNote;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MarkdownNoteParser {

    public ParsedNote parse(String markdown) {
        String content;
        List<String> tags;

        int firstIndex = markdown.indexOf("---");
        int lastIndex = markdown.indexOf("---", firstIndex + 3);

        if (firstIndex == -1 || lastIndex == -1) {
            content = markdown;
            tags = Collections.emptyList();
        } else {
            content = markdown.substring(lastIndex + 3).strip();
            String frontMatter = markdown.substring(firstIndex, lastIndex);
            tags = frontMatter.lines()
                    .filter(line -> line.startsWith("tags:"))
                    .map(line -> line.replaceFirst("tags:", ""))
                    .flatMap(tag -> Arrays.stream(tag.split(",")))
                    .map(String::strip)
                    .toList();
        }

        return new ParsedNote(content, tags);
    }

}

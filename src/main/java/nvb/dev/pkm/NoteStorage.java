package nvb.dev.pkm;

import nvb.dev.pkm.dto.ParsedNote;
import nvb.dev.pkm.utils.MarkdownNoteParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class NoteStorage {
    private static final String DEFAULT_DIRECTORY_NAME = "notes";
    private static final Path NOTES_PATH = Path.of(DEFAULT_DIRECTORY_NAME);
    private static final MarkdownNoteParser NOTE_PARSER = new MarkdownNoteParser();

    public static void save(Note note) throws IOException {
        if (!Files.exists(NOTES_PATH))
            Files.createDirectories(NOTES_PATH);

        String title = note.getTitle();
        String content = note.getContent();
        List<String> tags = note.getTags();
        String commaSeparatedTags = String.join(", ", tags);

        String frontMatter = "---\ntags: " + commaSeparatedTags + "\n---\n";

        Path createdFile = NOTES_PATH.resolve(title + ".md");
        Files.writeString(createdFile,
                frontMatter + "\n" + content,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING);
    }

    public static Optional<Note> read(String title) throws IOException {
        Path notePath = NOTES_PATH.resolve(title + ".md");
        if (!Files.exists(notePath))
            return Optional.empty();

        String fileContent = Files.readString(notePath);
        ParsedNote parsedNote = NOTE_PARSER.parse(fileContent);

        return Optional.of(new Note(title, parsedNote.content(), parsedNote.tags()));
    }

    public static boolean delete(String title) throws IOException {
        Path notePath = NOTES_PATH.resolve(title + ".md");
        return Files.deleteIfExists(notePath);
    }

    public static List<Note> list() throws IOException {
        if (!Files.exists(NOTES_PATH))
            return Collections.emptyList();

        List<Note> noteList = new ArrayList<>();

        try (Stream<Path> paths = Files.list(NOTES_PATH)) {
            for (Path path : paths.toList()) {
                if (path.toString().endsWith(".md")) {
                    Path fileName = path.getFileName();
                    String fileNameString = fileName.toString();
                    int index = fileNameString.lastIndexOf(".md");

                    String title = fileNameString.substring(0, index);
                    String content = Files.readString(path);

                    ParsedNote parsedNote = NOTE_PARSER.parse(content);

                    Note note = new Note(
                            title,
                            parsedNote.content(),
                            parsedNote.tags()
                    );

                    noteList.add(note);
                }
            }
        }

        return noteList;
    }
}

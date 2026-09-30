package nvb.dev.pkm;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class NoteStorage {
    private static final String DEFAULT_DIRECTORY_NAME = "notes";
    private static final Path NOTES_PATH = Path.of(DEFAULT_DIRECTORY_NAME);

    public static void save(Note note) throws IOException {
        if (!Files.exists(NOTES_PATH))
            Files.createDirectories(NOTES_PATH);

        String title = note.getTitle();
        String content = note.getContent();

        Path createdFile = NOTES_PATH.resolve(title + ".md");
        Files.writeString(createdFile,
                content,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING);
    }

    public static Optional<Note> read(String title) throws IOException {
        Path notePath = NOTES_PATH.resolve(title + ".md");
        if (!Files.exists(notePath))
            return Optional.empty();

        String fileContent = Files.readString(notePath);
        return Optional.of(new Note(title, fileContent));
    }

    public static boolean delete(String title) throws IOException {
        Path notePath = NOTES_PATH.resolve(title + ".md");
        return Files.deleteIfExists(notePath);
    }

    public static List<Note> list() throws IOException {
        if (!Files.exists(NOTES_PATH))
            return Collections.emptyList();

        try (Stream<Path> notesStreamPath = Files.list(NOTES_PATH)) {
            return notesStreamPath
                    .filter(path -> path.toString().endsWith(".md"))
                    .map(path -> {
                        Path fileName = path.getFileName();
                        int index = fileName.toString().lastIndexOf(".md");
                        String title = fileName.toString().substring(0, index);
                        String content;
                        try {
                            content = Files.readString(path);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        return new Note(title, content);
                    })
                    .toList();
        }
    }
}

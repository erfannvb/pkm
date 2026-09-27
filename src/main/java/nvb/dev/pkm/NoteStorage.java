package nvb.dev.pkm;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Optional;

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
}

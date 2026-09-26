package nvb.dev.pkm;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

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
}

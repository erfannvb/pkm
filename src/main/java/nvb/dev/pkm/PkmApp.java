package nvb.dev.pkm;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class PkmApp {
    public static void main(String[] args) throws IOException {

        Path notesDirectory = Path.of("notes");
        if (Files.exists(notesDirectory)) {
            try (var files = Files.list(notesDirectory)) {
                files.forEach(path -> {
                    try {
                        Files.delete(path);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }

        Note javaNote = new Note(
                "Java Optional",
                "An Optional is a container that holds either exactly one value or none."
        );
        javaNote.addTag("java");
        javaNote.addTag("programming");
        javaNote.addTag("backend");

        NoteStorage.save(javaNote);

        Note springNote = new Note(
                "Spring",
                "Some content for Spring."
        );

        NoteStorage.save(springNote);

        Files.writeString(
                notesDirectory.resolve("Old Note.md"),
                "This is an old note without metadata."
        );

        System.out.println("=== LIST ===");

        List<Note> notes = NoteStorage.list();

        for (Note note : notes) {
            System.out.println("Title: " + note.getTitle());
            System.out.println("Content: " + note.getContent());
            System.out.println("Tags: " + note.getTags());
            System.out.println();
        }

        System.out.println("=== READ JAVA OPTIONAL ===");

        Optional<Note> javaOptional = NoteStorage.read("Java Optional");

        javaOptional.ifPresent(note -> {
            System.out.println("Title: " + note.getTitle());
            System.out.println("Content: " + note.getContent());
            System.out.println("Tags: " + note.getTags());
        });

        System.out.println("\n=== READ SPRING ===");

        Optional<Note> spring = NoteStorage.read("Spring");

        spring.ifPresent(note -> {
            System.out.println("Title: " + note.getTitle());
            System.out.println("Content: " + note.getContent());
            System.out.println("Tags: " + note.getTags());
        });

        System.out.println("\n=== READ OLD NOTE ===");

        Optional<Note> oldNote = NoteStorage.read("Old Note");

        oldNote.ifPresent(note -> {
            System.out.println("Title: " + note.getTitle());
            System.out.println("Content: " + note.getContent());
            System.out.println("Tags: " + note.getTags());
        });

        System.out.println("\n=== READ MISSING NOTE ===");

        Optional<Note> missing = NoteStorage.read("Does Not Exist");

        if (missing.isEmpty()) {
            System.out.println("Correct: note does not exist.");
        }

        System.out.println("\n=== DELETE ===");

        boolean deleted = NoteStorage.delete("Java Optional");

        System.out.println("Deleted: " + deleted);

        Optional<Note> afterDelete = NoteStorage.read("Java Optional");

        if (afterDelete.isEmpty()) {
            System.out.println("Correct: Java Optional no longer exists.");
        }
    }
}
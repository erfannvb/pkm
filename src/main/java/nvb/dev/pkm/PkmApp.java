package nvb.dev.pkm;

import java.io.IOException;
import java.util.Optional;

public class PkmApp {
    public static void main(String[] args) throws IOException {
        Note note = new Note("Java Optional",
                "An Optional is a container that holds either exactly one value or none.");

        NoteStorage.save(note);

        Optional<Note> javaOptional = NoteStorage.read("Java Optional");
        javaOptional.ifPresent(n -> {
            System.out.println("Title: " + n.getTitle());
            System.out.println("Content: " + n.getContent());
        });

        Optional<Note> test = NoteStorage.read("Test");
        if (test.isEmpty())
            System.out.println("Test does not exist!");
    }
}

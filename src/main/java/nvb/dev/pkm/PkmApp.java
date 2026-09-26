package nvb.dev.pkm;

import java.io.IOException;

public class PkmApp {
    public static void main(String[] args) throws IOException {
        Note note = new Note("Java Optional",
                "An Optional is a container that holds either exactly one value or none.");

        NoteStorage.save(note);
    }
}

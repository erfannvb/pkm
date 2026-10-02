package nvb.dev.pkm;

import java.io.IOException;
import java.util.List;

public class NoteSearcher {

    public List<Note> search(String input) throws IOException {
        String lowerInput = input.toLowerCase();
        List<Note> noteList = NoteStorage.list();
        return noteList
                .stream()
                .filter(note -> note.getTitle().toLowerCase().contains(lowerInput)
                        || note.getContent().toLowerCase().contains(lowerInput))
                .toList();
    }

}

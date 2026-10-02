package nvb.dev.pkm;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class PkmApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Personal Knowledge Manager");

        while (true) {
            System.out.print("> ");

            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                continue;
            }

            String[] parts = input.split("\\s+", 2);
            String command = parts[0];

            if (command.equals("exit")) {
                System.out.println("Goodbye!");
                break;
            }

            switch (command) {
                case "create":
                    if (parts.length < 2 || parts[1].isBlank()) {
                        System.out.println("Usage: create <title>");
                        break;
                    }

                    String title = parts[1];

                    try {
                        Note note = new Note(title, "");
                        NoteStorage.save(note);
                        System.out.println("Note created: " + title);
                    } catch (IOException e) {
                        System.out.println("Could not create note.");
                    }
                    break;

                case "read":
                    if (parts.length < 2 || parts[1].isBlank()) {
                        System.out.println("Usage: read <title>");
                        break;
                    }

                    String titleToRead = parts[1];

                    try {
                        Optional<Note> note = NoteStorage.read(titleToRead);

                        if (note.isEmpty()) {
                            System.out.println("Note not found: " + titleToRead);
                        } else {
                            System.out.println("Title: " + note.get().getTitle());
                            System.out.println("Content:");
                            System.out.println(note.get().getContent());

                            if (!note.get().getTags().isEmpty()) {
                                System.out.println(
                                        "Tags: " + String.join(", ", note.get().getTags())
                                );
                            }
                        }
                    } catch (IOException e) {
                        System.out.println("Could not read note.");
                    }
                    break;

                case "delete":
                    if (parts.length < 2 || parts[1].isBlank()) {
                        System.out.println("Usage: delete <title>");
                        break;
                    }

                    String titleToDelete = parts[1];

                    try {
                        boolean deleted = NoteStorage.delete(titleToDelete);

                        if (deleted) {
                            System.out.println("Note deleted: " + titleToDelete);
                        } else {
                            System.out.println("Note not found: " + titleToDelete);
                        }
                    } catch (IOException e) {
                        System.out.println("Could not delete note.");
                    }
                    break;

                case "list":
                    try {
                        List<Note> notes = NoteStorage.list();

                        if (notes.isEmpty()) {
                            System.out.println("No notes found.");
                        } else {
                            for (Note note : notes) {
                                System.out.println("- " + note.getTitle());
                            }
                        }
                    } catch (IOException e) {
                        System.out.println("Could not read notes.");
                    }
                    break;

                case "search":
                    if (parts.length < 2 || parts[1].isBlank()) {
                        System.out.println("Usage: search <query>");
                        break;
                    }

                    String query = parts[1];

                    try {
                        NoteSearcher noteSearcher = new NoteSearcher();
                        List<Note> results = noteSearcher.search(query);

                        if (results.isEmpty()) {
                            System.out.println("No notes found.");
                        } else {
                            System.out.println("Found " + results.size() + " notes:");

                            for (Note note : results) {
                                System.out.println("- " + note.getTitle());
                            }
                        }
                    } catch (IOException e) {
                        System.out.println("Could not search notes.");
                    }
                    break;

                default:
                    System.out.println("Unknown command");
            }
        }

        scanner.close();
    }

}
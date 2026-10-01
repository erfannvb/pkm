package nvb.dev.pkm.dto;

import java.util.List;

public record ParsedNote(String content, List<String> tags) {
}

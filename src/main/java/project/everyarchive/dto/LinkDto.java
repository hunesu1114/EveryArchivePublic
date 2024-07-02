package project.everyarchive.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinkDto {

    private Long id = null;
    private String category;
    private String description;
    private String url;
}

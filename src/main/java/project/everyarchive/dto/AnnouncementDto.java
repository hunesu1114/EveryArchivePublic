package project.everyarchive.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnnouncementDto {

    private String company;
    private String title;
    private String slink;
    private String jlink;
    private String date;

}

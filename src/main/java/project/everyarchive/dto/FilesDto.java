package project.everyarchive.dto;

import jakarta.persistence.*;
import lombok.Data;
import project.everyarchive.entity.DataType;
import project.everyarchive.entity.Member;

@Data
public class FilesDto {

    private Long id;
    private String originalFilename;
    private String filePath;
    private DataType dataType;
    private int size;
    private Long memberId;

}

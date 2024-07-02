package project.everyarchive.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.everyarchive.dto.LinkDto;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "link_id")
    private Long id;

    private String category;
    private String description;
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void updateLinkInfo(LinkDto dto) {
        this.category = dto.getCategory();
        this.description = dto.getDescription();
        this.url = dto.getUrl();
    }
}

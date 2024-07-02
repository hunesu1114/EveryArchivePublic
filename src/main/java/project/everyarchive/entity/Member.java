package project.everyarchive.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_id")
    private Long id;

    private Long kakaoId;
    private String email;

    private String nickname;
    private String password;

    @Column(name = "ROLE")
    @Enumerated(EnumType.STRING)
    private Role role;

    private String provider;
    private String providerId;

    @OneToMany(mappedBy = "member")
    private List<Link> links = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Memo> memos = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Files> files = new ArrayList<>();

    public void updateRole(Role role) {
        this.role = role;
    }
}

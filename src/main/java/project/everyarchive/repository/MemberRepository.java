package project.everyarchive.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.everyarchive.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}

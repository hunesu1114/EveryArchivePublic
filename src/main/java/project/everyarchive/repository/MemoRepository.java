package project.everyarchive.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.everyarchive.entity.Memo;

public interface MemoRepository extends JpaRepository<Memo, Long> {
}

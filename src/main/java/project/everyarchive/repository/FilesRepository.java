package project.everyarchive.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.everyarchive.entity.Files;

public interface FilesRepository extends JpaRepository<Files, Long> {
}

package project.everyarchive.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.everyarchive.entity.Link;

import java.util.List;

public interface LinkRepository extends JpaRepository<Link, Long> {
}

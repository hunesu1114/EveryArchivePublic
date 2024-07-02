package project.everyarchive.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.everyarchive.entity.Announcement;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
}

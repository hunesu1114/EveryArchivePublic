package project.everyarchive.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.everyarchive.dto.AnnouncementDto;
import project.everyarchive.entity.Announcement;
import project.everyarchive.repository.AnnouncementRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;

    public void deleteAll() {
        announcementRepository.deleteAll();
    }

    public void save(Announcement entity) {
        announcementRepository.save(entity);
    }

    public void saveAll(List<Announcement> entityList) {
        announcementRepository.saveAll(entityList);
    }
    public List<Announcement> getAnnouncements() {
        return announcementRepository.findAll();
    }
}

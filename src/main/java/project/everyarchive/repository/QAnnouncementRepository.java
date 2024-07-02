package project.everyarchive.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QAnnouncementRepository {
    private final JPAQueryFactory queryFactory;
}

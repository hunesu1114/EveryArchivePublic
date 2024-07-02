package project.everyarchive.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import project.everyarchive.entity.Link;
import project.everyarchive.entity.QLink;

import java.util.List;

import static project.everyarchive.entity.QLink.link;

@Repository
@RequiredArgsConstructor
public class QLinkRepository {

    private final JPAQueryFactory queryFactory;

    public Link findOneByDescription(Long linkId) {
        return queryFactory
                    .selectFrom(link)
                    .where(link.id.eq(linkId))
                    .fetchOne();
    }

    public List<Link> findAllByMemberIdDesc(Long memberId) {
        return queryFactory
                .selectFrom(link)
                .where(link.member.id.eq(memberId))
                .orderBy(link.id.desc())
                .fetch();
    }


}

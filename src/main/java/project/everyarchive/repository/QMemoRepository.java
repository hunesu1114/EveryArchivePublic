package project.everyarchive.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.everyarchive.entity.Memo;
import project.everyarchive.entity.QMemo;

import java.util.List;

import static project.everyarchive.entity.QMemo.memo;

@Repository
@RequiredArgsConstructor
public class QMemoRepository {

    private final JPAQueryFactory queryFactory;

    public List<Memo> findAllByMemberIdDesc(Long memberId) {
        return queryFactory
                .selectFrom(memo)
                .where(memo.member.id.eq(memberId))
                .orderBy(memo.id.desc())
                .fetch();
    }

}

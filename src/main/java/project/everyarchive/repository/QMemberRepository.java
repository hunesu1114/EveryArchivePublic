package project.everyarchive.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.everyarchive.entity.Member;
import project.everyarchive.entity.QMember;

import static project.everyarchive.entity.QMember.member;

@Repository
@RequiredArgsConstructor
public class QMemberRepository {

    private final JPAQueryFactory queryFactory;

    public Member findMemberByKakaoId(Long kakaoId) {
        return queryFactory
                .selectFrom(member)
                .where(member.kakaoId.eq(kakaoId))
                .fetchOne();
    }

    public Member findMemberByEmail(String email) {
        return queryFactory
                .selectFrom(member)
                .where(member.email.eq(email))
                .fetchOne();
    }

    public Member findByNickname(String nickname) {
        return queryFactory
                .selectFrom(member)
                .where(member.nickname.eq(nickname))
                .fetchOne();
    }


}

package project.everyarchive.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.everyarchive.entity.DataType;
import project.everyarchive.entity.Files;
import project.everyarchive.entity.QFiles;
import project.everyarchive.entity.QMember;

import java.util.List;

import static project.everyarchive.entity.QFiles.files;

@Repository
@RequiredArgsConstructor
public class QFilesRepository {

    private final JPAQueryFactory queryFactory;

    public List<Files> findAllByType(DataType dataType) {
        return queryFactory.selectFrom(files)
                .join(files.member, QMember.member).fetchJoin()
                .where(files.dataType.eq(dataType))
                .orderBy(files.id.desc())
                .fetch();
    }

    public Files findById(Long id) {
        return queryFactory.selectFrom(files)
                .where(files.id.eq(id))
                .fetchOne();
    }

    public String findOriginalFilename(Long id) {
        return queryFactory.select(files.originalFilename)
                .from(files)
                .where(files.id.eq(id))
                .fetchOne();
    }
}

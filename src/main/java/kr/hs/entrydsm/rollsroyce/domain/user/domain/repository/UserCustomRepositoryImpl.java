package kr.hs.entrydsm.rollsroyce.domain.user.domain.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.repository.vo.ApplicantVo;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.repository.vo.QApplicantVo;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.types.ApplicationType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static kr.hs.entrydsm.rollsroyce.domain.application.domain.QGraduation.graduation;
import static kr.hs.entrydsm.rollsroyce.domain.school.domain.QSchool.school;
import static kr.hs.entrydsm.rollsroyce.domain.status.domain.QStatus.status;
import static kr.hs.entrydsm.rollsroyce.domain.user.domain.QUser.user;

@RequiredArgsConstructor
public class UserCustomRepositoryImpl implements UserCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<User> findAllByStatusIsSubmittedTrue() {
        return jpaQueryFactory.selectFrom(user)
                .join(status).on(user.receiptCode.eq(status.receiptCode))
                .where(status.isSubmitted.eq(true))
                .fetch();
    }

    @Override
    public Page<ApplicantVo> findAllByUserInfo(String receiptCode, String schoolName, String name,
                                               Boolean isDaejeon,
                                               Boolean outOfHeadcount,
                                               boolean isCommon, boolean isMeister, boolean isSocial,
                                               Boolean isSubmitted,
                                               Pageable page) {
        List<ApplicantVo> users = jpaQueryFactory.select(
                        new QApplicantVo(
                                user,
                                status
                        )
                )
                .from(user)
                .leftJoin(graduation).on(user.receiptCode.eq(graduation.receiptCode))
                .leftJoin(graduation.school, school)
                .join(status).on(user.receiptCode.eq(status.receiptCode))
                .where(user.receiptCode.like(receiptCode)
                        .and(school.name.contains(schoolName))
                        .and(user.name.contains(name))
                        .and(isDeajeonEq(isDaejeon))
                        .and(outOfHeadcountEq(outOfHeadcount))
                        .and(isCommon(isCommon)).or(isMeister(isMeister)).or(isSocial(isSocial))
                        .and(isSubmittedEq(isSubmitted))
                )
                .limit(page.getPageSize())
                .offset(page.getOffset())
                .orderBy(user.receiptCode.asc())
                .fetch();

        return new PageImpl<>(users, page, users.size());
    }

    private BooleanExpression isDeajeonEq(Boolean isDaejeon) {
        if (isDaejeon == null) return null;
        return user.isDaejeon.eq(isDaejeon);
    }

    private BooleanExpression outOfHeadcountEq(Boolean outOfHeadcount) {
        if (outOfHeadcount == null) return null;
        return user.isOutOfHeadcount.eq(outOfHeadcount);
    }

    private BooleanExpression isCommon(boolean isCommon) {
        if (!isCommon) return null;
        return user.applicationType.eq(ApplicationType.COMMON);
    }

    private BooleanExpression isMeister(boolean isMeister) {
        if (!isMeister) return null;
        return user.applicationType.eq(ApplicationType.MEISTER);
    }

    private BooleanExpression isSocial(boolean isSocial) {
        if (!isSocial) return null;
        return user.applicationType.eq(ApplicationType.SOCIAL);
    }

    private BooleanExpression isSubmittedEq(Boolean isSubmitted) {
        if (isSubmitted == null) return null;
        return status.isSubmitted.eq(isSubmitted);
    }

}
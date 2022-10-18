package kr.hs.entrydsm.rollsroyce.domain.user.domain.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.repository.vo.ApplicantVo;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.repository.vo.QApplicantVo;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.types.ApplicationType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
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
                                               Boolean isOutOfHeadcount,
                                               boolean isCommon, boolean isMeister, boolean isSocial,
                                               Boolean isSubmitted,
                                               Pageable page) {
        JPAQuery<ApplicantVo> query = jpaQueryFactory.select(
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
                        .and(isOutOfHeadcountEq(isOutOfHeadcount))
                        .and(applicationTypeEq(isCommon, isMeister, isSocial))
                        .and(isSubmittedEq(isSubmitted))
                )
                .orderBy(user.receiptCode.asc());

        List<ApplicantVo> users = query
                .limit(page.getPageSize())
                .offset(page.getOffset())
                .fetch();

        return new PageImpl<>(users, page, query.fetchCount());
    }

    @Override
    public boolean isAlreadyExistByEmail(String email) {
        Integer fetchOne = jpaQueryFactory
            .selectOne()
            .from(user)
            .where(user.email.lower().contains(email.toLowerCase()))
        .fetchFirst();
        return fetchOne != null;
    }

    @Override
    public List<User> queryStaticsCount(ApplicationType applicationType, boolean isDaejeon) {
        return jpaQueryFactory.selectFrom(user)
            .join(status).on(user.receiptCode.eq(status.receiptCode))
            .where(user.applicationType.eq(applicationType))
            .where(user.isDaejeon.eq(isDaejeon))
            .where(status.isSubmitted.eq(true))
            .fetch();
    }

    private BooleanExpression isDeajeonEq(Boolean isDaejeon) {
        if (isDaejeon == null) return null;
        return user.isDaejeon.eq(isDaejeon);
    }

    private BooleanExpression isOutOfHeadcountEq(Boolean isOutOfHeadcount) {
        if (isOutOfHeadcount == null) return null;
        return user.isOutOfHeadcount.eq(isOutOfHeadcount);
    }

    private BooleanExpression applicationTypeEq(boolean isCommon, boolean isMeister, boolean isSocial) {
        List<ApplicationType> condition = new ArrayList<>();

        if (isCommon) {
            condition.add(ApplicationType.COMMON);
        }
        if (isMeister) {
            condition.add(ApplicationType.MEISTER);
        }
        if (isSocial) {
            condition.add(ApplicationType.SOCIAL);
        }

        return user.applicationType.in(condition);
    }

    private BooleanExpression isSubmittedEq(Boolean isSubmitted) {
        if (isSubmitted == null) return null;
        return status.isSubmitted.eq(isSubmitted);
    }

}

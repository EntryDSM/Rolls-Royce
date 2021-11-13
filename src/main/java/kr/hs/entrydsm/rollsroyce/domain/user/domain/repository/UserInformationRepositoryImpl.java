package kr.hs.entrydsm.rollsroyce.domain.user.domain.repository;

import java.util.List;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.types.ApplicationType;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.types.HeadCount;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import static kr.hs.entrydsm.rollsroyce.domain.application.domain.QGraduation.graduation1;
import static kr.hs.entrydsm.rollsroyce.domain.status.domain.QStatus.status;
import static kr.hs.entrydsm.rollsroyce.domain.user.domain.QUser.user;

@RequiredArgsConstructor
@Repository
public class UserInformationRepositoryImpl implements UserInformationRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<User> findAllByUserInfo(String receiptCode,
                                     Boolean isDaejeon,
                                     String schoolName, String name,
                                     boolean inOfHeadcount, boolean outOfHeadcount,
                                     boolean isCommon, boolean isMeister, boolean isSocial,
                                     boolean isSubmitted,
                                     Pageable pageable) {
        List<User> result = jpaQueryFactory.selectFrom(user)
                .where(user.receiptCode.like(receiptCode))
                .where(eqIsDaejeon(isDaejeon), eqSchoolName(schoolName), eqName(name))
                .where(eqInOfHeadCount(inOfHeadcount), eqOutOfHeadCount(outOfHeadcount))
                .where(eqCommon(isCommon), eqMeister(isMeister), eqSocial(isSocial))
                .where(eqIsSubmitted(isSubmitted))
                .fetch();
        return new PageImpl<>(result, pageable, result.size());
    }

    private BooleanExpression eqApplicationType(ApplicationType applicationType) {
    	if(applicationType == null) return null;
    	return user.applicationType.eq(applicationType);
	}

    private BooleanExpression eqIsDaejeon(Boolean isDeajeon) {
        if (isDeajeon == null) return null;
        return user.isDaejeon.eq(isDeajeon);
    }

    private BooleanExpression eqSchoolName(String schoolName) {
        if (schoolName.isBlank()) return null;
        return graduation1.schoolName.eq(schoolName);
    }

    private BooleanExpression eqName(String name) {
        if (name.isBlank()) return null;
        return user.name.eq(name);
    }

    private BooleanExpression eqInOfHeadCount(boolean inOfHeadcount) {
        if (!inOfHeadcount) return null;
        return user.headcount.eq(HeadCount.IN_OF_HEADCOUNT);
    }

    private BooleanExpression eqOutOfHeadCount(boolean inOutHeadcount) {
        if (!inOutHeadcount) return null;
        return user.headcount.eq(HeadCount.OUT_OF_HEADCOUNT);
    }

    private BooleanExpression eqCommon(boolean isCommon) {
        if (!isCommon) return null;
        return user.applicationType.eq(ApplicationType.COMMON);
    }

    private BooleanExpression eqMeister(boolean isMeister) {
        if (!isMeister) return null;
        return user.applicationType.eq(ApplicationType.MEISTER);
    }

    private BooleanExpression eqSocial(boolean isSocial) {
        if (!isSocial) return null;
        return user.applicationType.eq(ApplicationType.SOCIAL);
    }

    private BooleanExpression eqIsSubmitted(boolean isSubmitted) {
        if (!isSubmitted) return null;
        return status.isSubmitted.eq(true);
    }

}

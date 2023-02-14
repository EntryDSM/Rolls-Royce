package kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.EntryInfo;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.repository.vo.AdmissionTicketVo;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.repository.vo.NewApplicantVo;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.repository.vo.QAdmissionTicketVo;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.repository.vo.ApplicantVo;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.repository.vo.QApplicantVo;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.repository.vo.QNewApplicantVo;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.types.ApplicationRemark;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.types.ApplicationType;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.types.EducationalStatus;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.types.Sex;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static kr.hs.entrydsm.rollsroyce.domain.application.domain.QGraduation.graduation;
import static kr.hs.entrydsm.rollsroyce.domain.school.domain.QSchool.school;
import static kr.hs.entrydsm.rollsroyce.domain.status.domain.QStatus.status;
import static kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.QEntryInfo.entryInfo;
import static kr.hs.entrydsm.rollsroyce.domain.user.domain.QUser.user;

@RequiredArgsConstructor
public class EntryInfoCustomRepositoryImpl implements EntryInfoCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<EntryInfo> findAllDistanceByTypeAndDaejeon(ApplicationType applicationType, Boolean isDaejeon) {
        return jpaQueryFactory.selectFrom(entryInfo)
                .join(status).on(entryInfo.receiptCode.eq(status.receiptCode))
                .where(
                        entryInfo.applicationType.eq(applicationType),
                        entryInfo.isDaejeon.eq(isDaejeon),
                        status.isSubmitted.eq(Boolean.TRUE)
                )
                .orderBy(entryInfo.distance.desc())
                .fetch();
    }

    @Override
    public List<EntryInfo> findAllByStatusIsSubmittedTrue() {
        return jpaQueryFactory.selectFrom(entryInfo)
                .join(status).on(entryInfo.receiptCode.eq(status.receiptCode))
                .where(status.isSubmitted.eq(true))
                .fetch();
    }

    @Override
    public Page<ApplicantVo> findAllByEntryInfo(String receiptCode, String schoolName, String name,
                                                Boolean isDaejeon,
                                                Boolean isOutOfHeadcount,
                                                boolean isCommon, boolean isMeister, boolean isSocial,
                                                Boolean isSubmitted,
                                                Pageable page) {
        JPAQuery<ApplicantVo> query =
                jpaQueryFactory.select(new QApplicantVo(entryInfo, status))
                        .from(entryInfo)
                        .leftJoin(graduation).on(entryInfo.receiptCode.eq(graduation.receiptCode))
                        .leftJoin(graduation.school, school)
                        .join(status).on(entryInfo.receiptCode.eq(status.receiptCode))
                        .join(entryInfo.user, user)
                        .where(
                                entryInfo.receiptCode.like(receiptCode),
                                school.name.contains(schoolName),
                                entryInfo.user.name.contains(name),
                                isSubmittedEq(isSubmitted),
                                isDeajeonEq(isDaejeon),
                                isOutOfHeadcountEq(isOutOfHeadcount),
                                applicationTypeEq(isCommon, isMeister, isSocial)
                        )
                        .orderBy(entryInfo.receiptCode.asc());

        List<ApplicantVo> users = query
                .limit(page.getPageSize())
                .offset(page.getOffset())
                .fetch();

        return new PageImpl<>(users, page, query.fetchCount());
    }

    @Override
    public List<AdmissionTicketVo> findByAdmissionTicket(String photoFileName, String receiptCode, String name, String schoolName,
                                                         ApplicationType applicationType, Boolean isDaejeon, String examCode) {
        return jpaQueryFactory.select(new QAdmissionTicketVo(entryInfo, status, school))
                .join(status)
                .on(entryInfo.receiptCode.eq(status.receiptCode))
                .where(
                        entryInfo.photoFileName.eq(photoFileName),
                        entryInfo.receiptCode.like(receiptCode),
                        entryInfo.user.name.contains(name),
                        school.name.contains(schoolName),
                        entryInfo.applicationType.eq(applicationType),
                        entryInfo.isDaejeon.eq(isDaejeon),
                        status.examCode.contains(examCode)
                ).fetch();
    }

    @Override
    public List<NewApplicantVo> findByNewApplicants(String receiptCode, EducationalStatus educationalStatus, ApplicationType applicationType,
                                                    String name, Boolean isDaejeon, LocalDate birthday, String telephoneNumber,
                                                    ApplicationRemark applicationRemark, Sex sex, String parentTel) {
        return jpaQueryFactory.select(new QNewApplicantVo(entryInfo))
                .join(status)
                .on(entryInfo.receiptCode.eq(status.receiptCode))
                .where(
                        entryInfo.receiptCode.like(receiptCode),
                        entryInfo.educationalStatus.eq(educationalStatus),
                        entryInfo.applicationType.eq(applicationType),
                        entryInfo.user.name.contains(name),
                        isDeajeonEq(isDaejeon),
                        entryInfo.birthday.eq(birthday),
                        entryInfo.user.telephoneNumber.eq(telephoneNumber),
                        entryInfo.applicationRemark.eq(applicationRemark),
                        entryInfo.sex.eq(sex),
                        entryInfo.parentTel.eq(parentTel)
                ).fetch();
    }

    @Override
    public List<EntryInfo> queryStaticsCount(ApplicationType applicationType, boolean isDaejeon) {
        return jpaQueryFactory.selectFrom(entryInfo)
                .join(status)
                .on(entryInfo.receiptCode.eq(status.receiptCode))
                .where(
                        entryInfo.applicationType.eq(applicationType),
                        entryInfo.isDaejeon.eq(isDaejeon),
                        status.isSubmitted.eq(Boolean.TRUE)
                )
                .fetch();
    }

    private BooleanExpression receiptCodeContainsFilter(String receiptCode) {
        return receiptCode != null ? entryInfo.receiptCode.stringValue().contains(receiptCode) : null;
    }

    private BooleanExpression schoolNameContainsFilter(String schoolName) {
        return schoolName != null ? school.name.contains(schoolName) : null;
    }

    private BooleanExpression userNameContainsFilter(String name) {
        return name != null ? user.name.contains(name) : null;
    }

    private BooleanExpression isDeajeonEq(Boolean isDaejeon) {
        return isDaejeon != null ? entryInfo.isDaejeon.eq(isDaejeon) : null;
    }

    private BooleanExpression isOutOfHeadcountEq(Boolean isOutOfHeadcount) {
        return isOutOfHeadcount != null ? entryInfo.isOutOfHeadcount.eq(isOutOfHeadcount) : null;
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

        return entryInfo.applicationType.in(condition);
    }

    private BooleanExpression isSubmittedEq(Boolean isSubmitted) {
        return isSubmitted != null ? status.isSubmitted.eq(isSubmitted) : null;
    }

}

package kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import kr.hs.entrydsm.rollsroyce.domain.application.domain.Application;
import kr.hs.entrydsm.rollsroyce.domain.application.domain.Graduation;
import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.request.ChangeTypeRequest;
import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.response.QueryTypeResponse;
import kr.hs.entrydsm.rollsroyce.domain.application.service.dto.UpdateUserInformationDto;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.types.ApplicationRemark;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.types.ApplicationType;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.types.EducationalStatus;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.types.Sex;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.Status;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.global.utils.EnumUtil;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@Entity
public class EntryInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long receiptCode;

    @Column(columnDefinition = "char(5)")
    private String name;

    @Column(columnDefinition = "char(11)")
    private String telephoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "char(7)")
    private ApplicationType applicationType;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "char(20)")
    private ApplicationRemark applicationRemark;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "char(20)")
    private EducationalStatus educationalStatus;

    @Column(columnDefinition = "BIT(1) default 0")
    private Boolean isOutOfHeadcount;

    private Boolean isDaejeon;

    @Column(columnDefinition = "char(45)")
    private String photoFileName;

    @Column(columnDefinition = "varchar(1600)")
    private String selfIntroduce;

    @Column(columnDefinition = "varchar(1600)")
    private String studyPlan;

    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "char(6)")
    private Sex sex;

    @Column(columnDefinition = "char(5)")
    private String parentName;

    @Column(columnDefinition = "char(11)")
    private String parentTel;

    @Column(columnDefinition = "varchar(300)")
    private String address;

    @Column(columnDefinition = "char(100)")
    private String detailAddress;

    @Column(columnDefinition = "char(5)")
    private String postCode;

    private Integer distance;

    @OneToOne
    @JoinColumn(name = "user", nullable = false)
    private User user;

    @OneToOne(mappedBy = "entryInfo")
    private Status status;

    @Builder
    public EntryInfo(User user) {
        this.user = user;
    }

    public void updateUserNameAndTel(String name, String telephoneNumber) {
        this.name = name;
        this.telephoneNumber = telephoneNumber;
    }

    public void updateParentNameAndTel(String parentName, String parentTel) {
        this.parentName = parentName;
        this.parentTel = parentTel;
    }

    public void updateUserApplication(ChangeTypeRequest request) {
        this.educationalStatus = EnumUtil.getEnum(EducationalStatus.class, request.getEducationalStatus());
        this.applicationType = EnumUtil.getEnum(ApplicationType.class, request.getApplicationType());
        this.isDaejeon = request.getIsDaejeon();
        this.applicationRemark = EnumUtil.getEnum(ApplicationRemark.class, request.getApplicationRemark());
        this.isOutOfHeadcount = request.getIsOutOfHeadcount();
    }

    public void updateEntryInformation(UpdateUserInformationDto information) {
        this.name = information.getName();
        this.telephoneNumber = information.getTelephoneNumber();
        this.sex = information.getSex();
        this.birthday = information.getBirthday();
        this.parentName = information.getParentName();
        this.parentTel = information.getParentTel();
        this.address = information.getAddress();
        this.postCode = information.getPostCode();
        this.detailAddress = information.getDetailAddress();
        this.distance = information.getDistance();
    }

    public String getUserTelephoneNumber() {
        return getUser().getTelephoneNumber();
    }

    public String getUserName() {
        return name;
    }

    public boolean getUserIsStudent() {
        return getUser().getIsStudent();
    }

    public QueryTypeResponse queryUserApplication(Application application) {
        QueryTypeResponse response = QueryTypeResponse.builder()
                .applicationRemark(getValue(applicationRemark))
                .applicationType(getValue(applicationType))
                .educationalStatus(getValue(educationalStatus))
                .isDaejeon(isDaejeon)
                .isOutOfHeadcount(isOutOfHeadcount)
                .build();

        changeGraduationInformation(application, response);
        return response;
    }

    public boolean isQualification() {
        return EducationalStatus.QUALIFICATION_EXAM.equals(educationalStatus);
    }

    public void updateSelfIntroduce(String selfIntroduce) {
        this.selfIntroduce = selfIntroduce;
    }

    public boolean hasEmptyInfo() {
        return !(isExists(getUserName())
                && sex != null
                && birthday != null
                && isExists(getUserTelephoneNumber())
                && isExists(parentTel)
                && isExists(parentName)
                && isExists(address)
                && isExists(detailAddress)
                && isExists(postCode)
                && photoFileName != null
                && educationalStatus != null
                && applicationType != null);
    }

    public boolean isEducationalStatusEmpty() {
        return this.educationalStatus == null;
    }

    public boolean isMale() {
        return Sex.MALE.equals(sex);
    }

    public boolean isFemale() {
        return Sex.FEMALE.equals(sex);
    }

    public void updateStudyPlan(String studyPlan) {
        this.studyPlan = studyPlan;
    }

    public void updatePhotoFileName(String photoFileName) {
        this.photoFileName = photoFileName;
    }

    public boolean isRecommendationsRequired() {
        return !isEducationalStatusEmpty() && !isCommonApplicationType() && !isProspectiveGraduate();
    }

    public boolean isQualificationExam() {
        return EducationalStatus.QUALIFICATION_EXAM.equals(educationalStatus);
    }

    public boolean isGraduate() {
        return EducationalStatus.GRADUATE.equals(educationalStatus);
    }

    public boolean isProspectiveGraduate() {
        return EducationalStatus.PROSPECTIVE_GRADUATE.equals(educationalStatus);
    }

    public boolean isBasicLiving() {
        return ApplicationRemark.BASIC_LIVING.equals(applicationRemark);
    }

    public boolean isFromNorth() {
        return ApplicationRemark.FROM_NORTH.equals(applicationRemark);
    }

    public boolean isLowestIncome() {
        return ApplicationRemark.LOWEST_INCOME.equals(applicationRemark);
    }

    public boolean isMulticultural() {
        return ApplicationRemark.MULTICULTURAL.equals(applicationRemark);
    }

    public boolean isOneParent() {
        return ApplicationRemark.ONE_PARENT.equals(applicationRemark);
    }

    public boolean isTeenHouseholder() {
        return ApplicationRemark.TEEN_HOUSEHOLDER.equals(applicationRemark);
    }

    public boolean isPrivilegedAdmission() {
        return ApplicationRemark.PRIVILEGED_ADMISSION.equals(applicationRemark);
    }

    public boolean isNationalMerit() {
        return ApplicationRemark.NATIONAL_MERIT.equals(applicationRemark);
    }

    public boolean isProtectedChildren() {
        return ApplicationRemark.PROTECTED_CHILDREN.equals(applicationRemark);
    }

    public boolean isCommonApplicationType() {
        return ApplicationType.COMMON.equals(applicationType);
    }

    public boolean isMeisterApplicationType() {
        return ApplicationType.MEISTER.equals(applicationType);
    }

    public boolean isSocialApplicationType() {
        return ApplicationType.SOCIAL.equals(applicationType);
    }

    public boolean isEducationalStatusEqual(String educationalStatus) {
        return this.educationalStatus != null && this.educationalStatus.name().equals(educationalStatus);
    }

    private void changeGraduationInformation(Application application, QueryTypeResponse response) {
        if (application == null) return;
        if (application instanceof Graduation)
            response.setGraduated(
                    ((Graduation) application).getIsGraduated() != null && ((Graduation) application).getIsGraduated());
        response.setGraduatedAt(application.getDate());
    }

    private String getValue(Object obj) {
        return obj == null ? null : String.valueOf(obj);
    }

    private boolean isExists(String target) {
        return target != null && !target.isBlank();
    }
}

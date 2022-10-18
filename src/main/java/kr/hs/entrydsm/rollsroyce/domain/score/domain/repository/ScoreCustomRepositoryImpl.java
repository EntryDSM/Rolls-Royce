package kr.hs.entrydsm.rollsroyce.domain.score.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.Score;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.types.ApplicationType;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static kr.hs.entrydsm.rollsroyce.domain.score.domain.QScore.score;
import static kr.hs.entrydsm.rollsroyce.domain.status.domain.QStatus.status;
import static kr.hs.entrydsm.rollsroyce.domain.user.domain.QUser.user;

@RequiredArgsConstructor
public class ScoreCustomRepositoryImpl implements ScoreCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Score> queryScoreByApplicationTypeAndIsDaejeon(ApplicationType applicationType, boolean isDaejeon) {
        return jpaQueryFactory.selectFrom(score)
                .innerJoin(score.user, user)
                .innerJoin(status)
                .on(status.user.eq(user))
                .where(
                        user.applicationType.eq(applicationType),
                        user.isDaejeon.eq(isDaejeon),
                        status.isSubmitted.eq(Boolean.TRUE)
                )
                .orderBy(score.totalScore.desc())
                .fetch();
    }

}

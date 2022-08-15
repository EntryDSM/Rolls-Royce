package kr.hs.entrydsm.rollsroyce.domain.score.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.Score;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.types.ApplicationType;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static kr.hs.entrydsm.rollsroyce.domain.score.domain.QScore.score;
import static kr.hs.entrydsm.rollsroyce.domain.user.domain.QUser.user;

@RequiredArgsConstructor
public class ScoreCustomRepositoryImpl implements ScoreCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Score> queryScoreByApplicationTypeAndIsDaejeon(ApplicationType applicationType, boolean isDaejeon) {
        return jpaQueryFactory.selectFrom(score)
                .join(score.user, user)
                .where(user.applicationType.eq(applicationType))
                .where(user.isDaejeon.eq(isDaejeon))
                .orderBy(score.totalScore.desc())
                .fetch();
    }

}

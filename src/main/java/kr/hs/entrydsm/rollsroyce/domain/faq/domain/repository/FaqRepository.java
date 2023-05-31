package kr.hs.entrydsm.rollsroyce.domain.faq.domain.repository;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

import kr.hs.entrydsm.rollsroyce.domain.faq.domain.Faq;
import kr.hs.entrydsm.rollsroyce.domain.faq.domain.type.FaqType;

public interface FaqRepository extends CrudRepository<Faq, Long> {
    List<Faq> findAllByFaqType(FaqType faqType);

    List<Faq> findAll();
}

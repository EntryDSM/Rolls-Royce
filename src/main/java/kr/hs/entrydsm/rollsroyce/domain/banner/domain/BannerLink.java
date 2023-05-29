package kr.hs.entrydsm.rollsroyce.domain.banner.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "tbl_banner_link")
public class BannerLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String link;

    public BannerLink(String link) {
        this.link = link;
    }

    public void updateBanner(String link) {
        this.link = link;
    }
}

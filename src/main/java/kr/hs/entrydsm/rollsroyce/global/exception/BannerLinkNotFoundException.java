package kr.hs.entrydsm.rollsroyce.global.exception;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class BannerLinkNotFoundException extends RollsException {
    public static final RollsException EXCEPTION = new BannerLinkNotFoundException();

    private BannerLinkNotFoundException() {
        super(ErrorCode.BANNERLINK_NOT_FOUND);
    }
}

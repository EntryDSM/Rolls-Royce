package kr.hs.entrydsm.rollsroyce.domain.user.domain.types;

public enum ApplicationType {
	COMMON, MEISTER, SOCIAL;

	@Override
	public String toString() {
		switch (this) {
			case COMMON:
				return "일반전형";
			case MEISTER:
				return "마이스터전형";
			case SOCIAL:
				return "사회통합전형";
			default:
				return null;
		}
	}

	public boolean isCommon() {
		return this == COMMON;
	}

}

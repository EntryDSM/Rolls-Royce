package kr.hs.entrydsm.rollsroyce.global.utils;

import kr.hs.entrydsm.rollsroyce.domain.user.exception.InvalidEnumConstantException;

public class EnumUtil {

	public static <T extends Enum<T>> T getEnum(final Class<T> enumClass, final String enumName) {
		if (enumName == null) {
			throw InvalidEnumConstantException.EXCEPTION;
		}
		try {
			return Enum.valueOf(enumClass, enumName);
		} catch (final IllegalArgumentException ex) {
			throw InvalidEnumConstantException.EXCEPTION;
		}
	}

}

package kr.hs.entrydsm.rollsroyce.global.utils;

import lombok.experimental.UtilityClass;

import kr.hs.entrydsm.rollsroyce.domain.user.exception.InvalidEnumConstantException;

@UtilityClass
public class EnumUtil {

    public static <T extends Enum<T>> T getEnum(final Class<T> enumClass, final String enumName) {
        if (enumName == null) {
            return null;
        }
        try {
            return Enum.valueOf(enumClass, enumName);
        } catch (final IllegalArgumentException ex) {
            throw InvalidEnumConstantException.EXCEPTION;
        }
    }
}

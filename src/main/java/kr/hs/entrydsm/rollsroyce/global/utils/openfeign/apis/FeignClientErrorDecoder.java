package kr.hs.entrydsm.rollsroyce.global.utils.openfeign.apis;

import static feign.FeignException.errorStatus;

import lombok.extern.slf4j.Slf4j;

import feign.Response;
import feign.codec.ErrorDecoder;

import kr.hs.entrydsm.rollsroyce.global.exception.RequestFailToOtherServerException;

@Slf4j
public class FeignClientErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {

        if (response.status() >= 400) throw RequestFailToOtherServerException.EXCEPTION;

        return errorStatus(methodKey, response);
    }
}

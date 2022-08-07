package kr.hs.entrydsm.rollsroyce.global.utils.openfeign.apis;

import feign.Response;
import feign.codec.ErrorDecoder;
import kr.hs.entrydsm.rollsroyce.global.exception.RequestFailToOtherServerException;
import lombok.extern.slf4j.Slf4j;

import static feign.FeignException.errorStatus;

@Slf4j
public class FeignClientErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {

        if (response.status() >= 400)
            throw RequestFailToOtherServerException.EXCEPTION;

        return errorStatus(methodKey, response);
    }

}

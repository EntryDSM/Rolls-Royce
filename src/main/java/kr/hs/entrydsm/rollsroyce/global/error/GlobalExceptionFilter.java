package kr.hs.entrydsm.rollsroyce.global.error;

import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import io.sentry.Sentry;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.hs.entrydsm.rollsroyce.global.error.exception.ErrorCode;
import kr.hs.entrydsm.rollsroyce.global.error.exception.RollsException;

public class GlobalExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (RollsException e) {
            Sentry.captureException(e);
            writerErrorCode(response, e.getErrorCode());
        } catch (Exception e) {
            e.printStackTrace();
            Sentry.captureException(e);
            writerErrorCode(response, ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    private void writerErrorCode(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        ErrorResponse errorResponse =
                new ErrorResponse(errorCode.getStatus(), errorCode.getCode(), errorCode.getMessage());

        response.setStatus(errorCode.getStatus());
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(errorResponse.toString());
    }
}

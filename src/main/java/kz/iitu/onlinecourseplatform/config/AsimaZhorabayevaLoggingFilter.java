package kz.iitu.onlinecourseplatform.config;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
@Slf4j
@Order(1)
public class AsimaZhorabayevaLoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        long start = System.currentTimeMillis();
        String method = request.getMethod();
        String uri = request.getRequestURI();

        log.info(">>> REQUEST: {} {}", method, uri);
        chain.doFilter(req, res);
        long duration = System.currentTimeMillis() - start;
        log.info("<<< RESPONSE: {} {} | status={} | {}ms",
                method, uri, response.getStatus(), duration);
    }
}
package ske.aurora.openshift.referanse.test;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.MDC;

public class KorrelasjonsFilter implements Filter {

    public void init(FilterConfig filterConfig) {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;

            String header = httpServletRequest.getHeader(RequestKorrelasjon.KORRELASJONS_ID);

            if (header == null || header.isEmpty()) {
                RequestKorrelasjon.setId(UUID.randomUUID().toString());
            } else {
                RequestKorrelasjon.setId(header);
            }

            MDC.put(RequestKorrelasjon.KORRELASJONS_ID, RequestKorrelasjon.getId());
            chain.doFilter(request, response);
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            MDC.remove(RequestKorrelasjon.KORRELASJONS_ID);
            RequestKorrelasjon.cleanup();
        }
    }

    public void destroy() {
    }
}
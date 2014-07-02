package hudson.plugins.frogballs

import javax.servlet.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by Shiran on 7/1/2014.
 */
class BallsFilter implements Filter {

    @Override
    void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
            final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            final HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            final String uri = httpServletRequest.getRequestURI();
            if (uri.endsWith(".gif") || uri.endsWith(".png")) {
                String newImageUrl = mapImage(uri);
                if (newImageUrl != null) {
                    RequestDispatcher dispatcher = httpServletRequest.getRequestDispatcher(newImageUrl);
                    dispatcher.forward(httpServletRequest, httpServletResponse);
                    return;
                }
            }
        }
        chain.doFilter(request, response);
    }

    private String mapImage(String uri) {
        return "/plugin/frogballs/froggies_r2_c3.png"
    }

    @Override
    void destroy() {

    }
}

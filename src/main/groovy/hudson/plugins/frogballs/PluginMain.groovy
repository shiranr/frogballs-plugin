package hudson.plugins.frogballs

import hudson.Plugin
import hudson.util.PluginServletFilter

import javax.servlet.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.util.logging.Level
import java.util.logging.Logger

/**
 * Created by Shiran on 7/1/2014.
 */
class PluginMain extends Plugin {

    transient final Logger logger = Logger.getLogger("hudson.plugins.frogballs")

    @Override
    public void start() {
        super.start()
        load()
        def filter = (filterClass as Filter)
        PluginServletFilter.addFilter(filter)
        logger.log(Level.INFO, "Frog Balls!")
    }

    def filterClass = [
            doFilter: { ServletRequest request, ServletResponse response, FilterChain chain ->
                if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
                    final String uri = (request as HttpServletRequest).requestURI
                    if (uri.contains('health')) {
                        String newImageUrl = mapImage(uri)
                        RequestDispatcher dispatcher = request.getRequestDispatcher newImageUrl
                        dispatcher.forward(request, response)
                        return
                    }
                }
                chain.doFilter(request, response)
            },
            init    : {},
            destroy : {}
    ]

    String mapImage(String uri) {
        int index = uri.findLastIndexOf { it == '/' }
        "/plugin/frogballs/${uri[index..uri.size() - 1]}"
    }
}

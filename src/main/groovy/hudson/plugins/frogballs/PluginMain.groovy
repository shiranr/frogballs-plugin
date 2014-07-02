package hudson.plugins.frogballs
import hudson.Plugin
import hudson.util.PluginServletFilter
import org.kohsuke.stapler.StaplerRequest
import org.kohsuke.stapler.StaplerResponse

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

    @Override
    public void doDynamic(StaplerRequest req, StaplerResponse rsp) throws IOException, ServletException {
        rsp.setHeader("Cache-Control", "public, s-maxage=86400")
        String path = req.restOfPath
        logger.log(Level.FINE, "Serving cached resource {0}", path)
        rsp.serveLocalizedFile(req, new URL(wrapper.baseResourceURL, '.' + path), 86400000)
    }

    def filterClass = [
            doFilter: {ServletRequest request, ServletResponse response, FilterChain chain ->
                if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
                    final String uri = ((HttpServletRequest) request).requestURI
                    logger.log(Level.INFO, "I'm in wih path ${uri}")
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

    private String mapImage(String uri) {
        int index =  uri.findLastIndexOf { it =='/'}
        logger.log(Level.INFO, uri[index .. uri.size()-1])
        "/plugin/frogballs/${uri[index .. uri.size()-1]}"
    }
}

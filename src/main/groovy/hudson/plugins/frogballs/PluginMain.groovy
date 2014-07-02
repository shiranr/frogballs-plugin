package hudson.plugins.frogballs
import hudson.Plugin
import hudson.util.PluginServletFilter
import org.kohsuke.stapler.StaplerRequest
import org.kohsuke.stapler.StaplerResponse

import javax.servlet.ServletException
import java.util.logging.Level
import java.util.logging.Logger
/**
 * Created by Shiran on 7/1/2014.
 */
class PluginMain extends Plugin{

    transient final Logger logger = Logger.getLogger("hudson.plugins.frogballs")

    @Override
    public void start() {
        super.start()
        load()
        PluginServletFilter.addFilter(new BallsFilter())
        logger.log(Level.INFO, "Frog Balls!")
    }

    @Override
    public void doDynamic(StaplerRequest req, StaplerResponse rsp) throws IOException, ServletException {
        rsp.setHeader("Cache-Control", "public, s-maxage=86400");
        String path = req.restOfPath
        logger.log(Level.FINE, "Serving cached resource {0}", path);
        rsp.serveLocalizedFile(req, new URL(wrapper.baseResourceURL, '.' + path), 86400000);
    }

}

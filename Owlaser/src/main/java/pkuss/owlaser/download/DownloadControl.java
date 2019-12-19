package pkuss.owlaser.download;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;

import static pkuss.owlaser.download.Download.downloadPackageTree;

@Controller
@RequestMapping(value = "/")
public class DownloadControl {
        @ResponseBody
        @RequestMapping(value = "/Get", produces = {"application/json;charset=UTF-8"})
        public ArrayList<Osc> Get(String groupId, String artifactId, String version) throws IOException {
            return downloadPackageTree(groupId, artifactId, version);
        }


}

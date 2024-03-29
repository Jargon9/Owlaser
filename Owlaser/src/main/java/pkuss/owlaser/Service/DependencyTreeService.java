package pkuss.owlaser.Service;

import fr.dutra.tools.maven.deptree.core.*;
import fr.dutra.tools.maven.deptree.core.Node;


import java.io.*;

public class DependencyTreeService {

    public static Node GetRoot(String filepath) {
        Node root = null;
        try {
            File file = new File(filepath);
            FileInputStream fis = new FileInputStream(file);
            Reader reader = new BufferedReader(new InputStreamReader(fis));
            InputType type = InputType.TEXT;
            Parser parser = type.newParser();
            root = parser.parse(reader);
            return root;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return root;
    }

}
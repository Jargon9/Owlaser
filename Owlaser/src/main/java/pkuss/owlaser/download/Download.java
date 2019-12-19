package pkuss.owlaser.download;
import org.apache.commons.io.FileUtils;

import fr.dutra.tools.maven.deptree.core.Node;
import pkuss.owlaser.Entity.Dependency;
import pkuss.owlaser.Service.PacService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.nio.file.Path;

import static pkuss.owlaser.Service.DependencyTreeService.GetRoot;
import static pkuss.owlaser.Service.PacService.GetDependencies;

public class Download {
    public static void downloadFile(String url, String dir) throws IOException {
        URL httpurl = new URL(url);
        File f =new File(dir );
        FileUtils.copyURLToFile(httpurl, f);

    }
    //根据包的坐标下载包
    public static Osc downloadPackage(String groupId, String artifactId, String version) throws IOException {
//        String [] urls={"https://mirrors.huaweicloud.com/repository/maven/","https://repo1.maven.org/maven2/",
//                "https://repo.spring.io/libs-release/+groupID","https://repo.grails.org/grails/gradle-plugins-repo-cache/"};
        String [] urls={"https://mirrors.huaweicloud.com/repository/maven/"};

        String filePath ="null";
        for(String url : urls) {
            for (String s : groupId.split("\\.")) {
                url = url + s + "/";
            }
            url = url + artifactId + "/" + version + "/" + artifactId + "-" + version ;
            String PomUrl = url + ".pom";
            String JarUrl = url + ".jar";

//            System.out.println(JarUrl);
            //System.out.println("fileName---->"+filePath);
            //创建不同的文件夹目录
            String group = "" ;
            for (String s : groupId.split("\\.")) {
                group = group + s + "\\";
            }
            filePath = ".\\download\\" + group;
            String JarfilePath = filePath  + artifactId + "\\" + artifactId + "-" + version + ".jar";
            String PomfilePath = filePath  + artifactId + "\\" + artifactId + "-" + version + ".pom";
            String name = artifactId + "-" + version + ".jar";
            try {
                downloadFile(PomUrl, PomfilePath);
                downloadFile(JarUrl, JarfilePath);
                System.out.println("successful");
                JarfilePath = "C:\\Users\\Jargon\\.m2\\repository\\"+group+ artifactId + "\\" + artifactId + "-" + version + ".jar";
                return new Osc(name,JarfilePath,artifactId,groupId,version);
            } catch (FileNotFoundException e) {
                System.out.println("FileNotFound");
            }
        }
        System.out.println("Failed");
        return new Osc(null,null,null,null,null);
    }
    //调取Maven命令解析pom文件的方法
    public static ArrayList<Osc> GetPomList(String pomPath) {
        String folderPath= System.getProperty("user.dir") + "\\repository" + "\\pom\\";
        Path pomPath1 = Paths.get(pomPath);
        String textPath = PacService.CreateDependencyText(folderPath, pomPath1);
        Node root = GetRoot(textPath);
        ArrayList<Dependency> dependencies = new ArrayList<>();
        ArrayList<Osc> Oscs = new ArrayList<>();
        GetDependencies(root, dependencies);
        for (Dependency dependency : dependencies) {
            Osc tempOsc = new Osc(null, null, dependency.getArtifact_id(), dependency.getGroup_id(), dependency.getVersion());
            if (tempOsc.getArtifactId() != null && tempOsc.getVersion() != null) {
                String name = tempOsc.getArtifactId()+tempOsc.getVersion()+"jar";
                tempOsc.setName(name);
                String group = "" ;
                for (String s : tempOsc.getGroupId().split("\\.")) {
                    group = group + s + "\\";
                }
                String JarfilePath = "C:\\Users\\Jargon\\.m2\\repository\\"+group+ tempOsc.getArtifactId() + "\\" + tempOsc.getArtifactId() + "-" + tempOsc.getVersion() + ".jar";
                tempOsc.setUrl(JarfilePath);
                Oscs.add(tempOsc);
            }
        }
        return Oscs;
    }

    public static ArrayList<Osc> downloadPackageTree(String groupId, String artifactId, String version) throws IOException {
        ArrayList<Osc> NewTree = new ArrayList<>();  //需要给数据库的返回值，存储已经下载完成的Osc
        HashMap<String, Osc> NewMap = new HashMap<>();
        Osc tempExam = downloadPackage(groupId, artifactId, version);
        NewTree.add(tempExam);
        String filePath = ".\\download\\";;
        for (String s : tempExam.getGroupId().split("\\.")) {
            filePath = filePath + s + "\\";
        }
        String PomfilePath = filePath  + tempExam.getArtifactId() + "\\" + tempExam.getArtifactId() + "-" + tempExam.getVersion() + ".pom";
        System.out.println(PomfilePath);
        ArrayList<Osc> TempPom = new ArrayList<>() ;
        TempPom = GetPomList(PomfilePath);   //获取从Pom中读取的dependence
        for(Osc temp: TempPom){
            NewTree.add(temp);
        }
        System.out.println("已经下载包的数量："+NewTree.size());
        return NewTree;
    }
}

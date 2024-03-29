package pkuss.owlaser.download;

public class Osc {
    private String name;
    private String url;
    private String groupId;
    private String artifactId;
    private String version;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Osc(String name, String url, String artifactId, String groupId, String version){
        this.name = name;
        this.url = url;
        this.artifactId = artifactId;
        this.groupId = groupId;
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"name\":\"")
                .append(name).append('\"');
        sb.append(",\"url\":\"")
                .append(url).append('\"');
        sb.append(",\"groupId\":\"")
                .append(groupId).append('\"');
        sb.append(",\"artifactId\":\"")
                .append(artifactId).append('\"');
        sb.append(",\"version\":\"")
                .append(version).append('\"');
        sb.append('}');
        return sb.toString();
    }
}



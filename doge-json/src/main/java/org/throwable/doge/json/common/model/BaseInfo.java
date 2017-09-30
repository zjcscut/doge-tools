package org.throwable.doge.json.common.model;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/9/30 14:54
 */
public class BaseInfo {

    private String author;
    private String contact;
    private String projectUrl;
    private String version;

    public BaseInfo() {
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getProjectUrl() {
        return projectUrl;
    }

    public void setProjectUrl(String projectUrl) {
        this.projectUrl = projectUrl;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}

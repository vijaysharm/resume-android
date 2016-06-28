package ca.vijaysharma.resume.models;

public class Social {
    private final String name;
    private final String url;
    private final String icon;

    public Social(String name, String url, String icon) {
        this.name = name;
        this.url = url;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getIcon() {
        return icon;
    }
}

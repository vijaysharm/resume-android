package ca.vijaysharma.resume.models;

public class Reference {
    private final String name;
    private final String position;
    private final String avatarUrl;

    public Reference(
        String name,
        String position,
        String avatar
    ) {
        this.name = name;
        this.position = position;
        this.avatarUrl = avatar;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }
}

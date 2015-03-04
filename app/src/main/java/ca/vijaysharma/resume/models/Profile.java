package ca.vijaysharma.resume.models;

public class Profile {
    private final String name;
    private final int avatarId;
    private final String email;
    private final String biography;
    private final String objective;

    private Profile(
        String name,
        int avatarId,
        String email,
        String biography,
        String objective
    ) {
        this.name = name;
        this.avatarId = avatarId;
        this.email = email;
        this.biography = biography;
        this.objective = objective;
    }

    public static class Builder {
        private String name;
        private int avatarId;
        private String email;
        private String biography;
        private String objective;

        public Profile build() {
            return new Profile(name, avatarId, email, biography, objective);
        }
    }
}

package ca.vijaysharma.resume.models;

import java.util.Set;

public class Project {
    public final String name;
    public final String description;
    public final String url;
    public final int[] locals;
    public final String[] remote;
    public final String[] technologies;
    public final Set<String> tags;

    public Project(
        String name,
        String description,
        String url,
        int[] locals,
        String[] remote,
        String[] technologies,
        Set<String> tags
    ) {
        this.name = name;
        this.description = description;
        this.url = url;
        this.locals = locals;
        this.remote = remote;
        this.tags = tags;
        this.technologies = technologies;
    }
}

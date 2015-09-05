package ca.vijaysharma.resume.models;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Projects {
    private final Map<String, List<Project>> projects;
    public Projects(Map<String, List<Project>> projects) {
        this.projects = projects;
    }

    public List<Project> all(String type) {
        if (! projects.containsKey(type))
            return Collections.EMPTY_LIST;

        return projects.get(type);
    }
}

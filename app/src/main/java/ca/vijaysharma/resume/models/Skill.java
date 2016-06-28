package ca.vijaysharma.resume.models;

import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Skill {
    public final String name;
    public final String logoUrl;
    public final @ColorRes int primary;
    public final List<String> beginner;
    public final List<String> intermediate;
    public final List<String> advanced;
    public final List<Project> projects;
    public final DateTime start;
    public final DateTime end;

    public Skill(
        String name,
        String logoUrl,
        int primary,
        List<String> beginner,
        List<String> intermediate,
        List<String> advanced,
        List<Project> projects,
        DateTime start, DateTime end) {
        this.name = name;
        this.logoUrl = logoUrl;
        this.primary = primary;
        this.beginner = beginner;
        this.intermediate = intermediate;
        this.advanced = advanced;
        this.projects = projects;
        this.start = start;
        this.end = end;
    }

    public static class Builder {
        private final String name;
        private final String logoUrl;
        private final @ColorRes int primary;
        private ArrayList<String> beginner;
        private ArrayList<String> intermediate;
        private ArrayList<String> advanced;
        private ArrayList<Project> projects;
        private DateTime start;
        private DateTime end;

        public Builder(String name, String logo, int primary) {
            this.name = name;
            this.logoUrl = logo;
            this.primary = primary;
        }

        public Builder beginner(String beginner) {
            if (this.beginner == null)
                this.beginner = new ArrayList<>();
            this.beginner.add(beginner);
            return this;
        }

        public Builder intermediate(String intermediate) {
            if (this.intermediate == null)
                this.intermediate = new ArrayList<>();
            this.intermediate.add(intermediate);
            return this;
        }

        public Builder advanced(String advanced) {
            if (this.advanced == null)
                this.advanced = new ArrayList<>();
            this.advanced.add(advanced);
            return this;
        }

        public Builder span(DateTime start, DateTime end) {
            this.start = start;
            this.end = end;
            return this;
        }

        public Builder projects(List<Project> projects) {
            if (this.projects == null)
                this.projects = new ArrayList<>();
            this.projects.addAll(projects);

            return this;
        }

        public Skill build() {
            return new Skill(
                name,
                logoUrl,
                primary,
                beginner == null ? Collections.EMPTY_LIST : Collections.unmodifiableList(beginner),
                intermediate == null ? Collections.EMPTY_LIST : Collections.unmodifiableList(intermediate),
                advanced == null ? Collections.EMPTY_LIST : Collections.unmodifiableList(advanced),
                projects == null ? Collections.EMPTY_LIST : Collections.unmodifiableList(projects),
                start,
                end
            );
        }
    }
}

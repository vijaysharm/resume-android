package ca.vijaysharma.resume;

import android.graphics.Color;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.vijaysharma.resume.models.Education;
import ca.vijaysharma.resume.models.Experience;
import ca.vijaysharma.resume.models.ListItem;
import ca.vijaysharma.resume.models.Profile;
import ca.vijaysharma.resume.models.Project;
import ca.vijaysharma.resume.models.Projects;
import ca.vijaysharma.resume.models.Reference;
import ca.vijaysharma.resume.models.Skill;
import ca.vijaysharma.resume.models.Social;

public class ResumeData {

    private static final Map<String, String> positionNames = new HashMap<>();
    private static final Map<String, String> avatars = new HashMap<>();

    static {
        positionNames.put("Java Software Developer (Cloud Applications)", "Senior Developer");
        positionNames.put("Android Consultant at Datacap Systems Inc", "Android Consultant");
        positionNames.put("Master of Engineering Sciences", "Masters in Engineering");
        positionNames.put("Bachelor of Electrical and Computer Engineering", "Bachelors in Engineering");
        positionNames.put("Android Consultant (contract)", "Android Consultant");

        avatars.put("Vijay Sharma", "https://cdn.jsdelivr.net/gh/vijaysharm/resume-android/images/avatar.jpg");
    }

    static Profile profile(Map<String, Object> data) {
        String name = v(data, "name");
        String position = v(data, "title");
        List<String> address = v(data, "address");
        String location = address.get(1);
        String email = address.get(4);
        Map<String, Object> personal = v(data, "personal");
        String objective = v(personal, "objective");
        String biography = v(personal, "biography");
        Map<String, Object> contact = v(data, "contact");
        List<Object> outlets = v(contact, "outlet");
        Map<String, Object> web = (Map<String, Object>) outlets.get(0);
        String site = v(web, "url");
        Map<String, Object> awards = v(data, "awards");
        List<Map<String, Object>> awardItems = v(awards, "items");
        ArrayList<String> items = new ArrayList<>(awardItems.size());
        for (Map<String, Object> award : awardItems) {
            String title = v(award, "title");
            items.add(title);
        }

        return new Profile(
            name, avatar(name), email, site, location, position, biography, objective, items
        );
    }

    static List<ListItem> experiences(Map<String, Object> resume, Map<String, Object> metadata) {
        Map<String, Object> meta = metadata(metadata, "companies");
        Map<String, Object> exp = v(resume, "professionalexperience");
        List<Map<String, Object>> companies = v(exp, "companies");
        ArrayList<ListItem> experience = new ArrayList<>(companies.size());
        int companyIndex = 0;
        for (Map<String, Object> company : companies) {
            String name = v(company, "location");
            Map<String, Object> data = v(meta, name, Collections.EMPTY_MAP);
            String logo = logo(data);
            List<Map<String, Object>> positions = v(company, "positions");
            for (Map<String, Object> position : positions) {
                List<String> enabled = v(position, "enabled");
                if (!enabled.contains("mobile"))
                    continue;

                experience.add(new ListItem(
                    logo,
                    companyIndex++
                ));
            }
        }

        return experience;
    }

    private static Map<String, Object> metadata(Map<String, Object> metadata, String key) {
        List<Map<String, Object>> companyMetadata = v(metadata, key);
        Map<String, Object> result = new HashMap<>();
        for (Map<String, Object> company : companyMetadata) {
            String name = v(company, "name");
            result.put(name, company);
        }

        return result;
    }

    static List<Education> education(Map<String, Object> resume, Map<String, Object> metadata) {
        Map<String, Object> meta = metadata(metadata, "education");
        Map<String, Object> exp = v(resume, "education");
        List<Map<String, Object>> schools = v(exp, "schools");
        ArrayList<Education> education = new ArrayList<>();
        for (Map<String, Object> school : schools) {
            String degree = v(school, "name");
            String name = v(school, "location");
            Map<String, Object> data = v(meta, name);
            String address = v(school, "address");
            String start = v(school, "start_date");
            String end = v(school, "end_date");
            String site = v(school, "site");
            List<String> responsibilities = v(school, "responsibilities");
            education.add(new Education(
                logo(data),
                primary(data),
                secondary(data),
                tertiary(data),
                company(data),
                address,
                position(degree),
                site,
                "present".equals(start) ? new DateTime() : new DateTime(start),
                "present".equals(end) ? new DateTime() : new DateTime(end),
                responsibilities.toArray(new String[0])
            ));
        }

        return education;
    }

    public static Experience experienceDetail(int index, Map<String, Object> resume, Map<String, Object> metadata) {
        Map<String, Object> meta = metadata(metadata, "companies");
        Map<String, Object> exp = v(resume, "professionalexperience");
        List<Map<String, Object>> companies = v(exp, "companies");
        int companyIndex = 0;
        for (Map<String, Object> company : companies) {
            List<Map<String, Object>> positions = v(company, "positions");
            for (Map<String, Object> position : positions) {
                List<String> enabled = v(position, "enabled");
                if (!enabled.contains("mobile"))
                    continue;

                if (index != companyIndex) {
                    companyIndex++;
                    continue;
                }

                String name = v(company, "location");
                Map<String, Object> data = v(meta, name, Collections.EMPTY_MAP);

                String address = v(company, "address");
                String summary = v(company, "summary", "");
                String site = v(company, "site", "");
                List<Map<String, Object>> refs = v(company, "references");
                List<Reference> references = new ArrayList<>(refs.size());
                for (Map<String, Object> reference : refs) {
                    String refName = v(reference, "name");
                    String refPosition = v(reference, "position");
                    String refAvatarUrl = v(reference, "avatar");
                    references.add(new Reference(refName, refPosition, TextUtils.isEmpty(refAvatarUrl) ? avatar(refName) : refAvatarUrl));
                }

                String title = v(position, "name");
                String start = v(position, "start_date");
                String end = v(position, "end_date");
                List<String> responsibilities = v(position, "responsibilities");
                List<String> technologies = v(position, "technologies");

                return new Experience(
                    logo(data),
                    primary(data),
                    secondary(data),
                    tertiary(data),
                    company(data),
                    position(title),
                    "present".equals(start) ? new DateTime() : new DateTime(start),
                    "present".equals(end) ? new DateTime() : new DateTime(end),
                    site,
                    address,
                    summary,
                    responsibilities.toArray(new String[0]),
                    technologies.toArray(new String[0]),
                    references.toArray(new Reference[0])
                );
            }
        }

        throw new IllegalArgumentException("Unknown Experience index " + index);
    }

    public static List<Experience> experienceDetails(Map<String, Object> resume, Map<String, Object> metadata) {
        Map<String, Object> meta = metadata(metadata, "companies");
        Map<String, Object> exp = v(resume, "professionalexperience");
        List<Map<String, Object>> companies = v(exp, "companies");
        ArrayList<Experience> experience = new ArrayList<>(companies.size());
        for (Map<String, Object> company : companies) {
            String name = v(company, "location");
            String address = v(company, "address");
            String summary = v(company, "summary", "");
            String site = v(company, "site", "");
            Map<String, Object> data = v(meta, name, Collections.EMPTY_MAP);
            List<Map<String, Object>> refs = v(company, "references");
            List<Reference> references = new ArrayList<>(refs.size());
            for (Map<String, Object> reference : refs) {
                String refName = v(reference, "name");
                String refPosition = v(reference, "position");
                references.add(new Reference(refName, refPosition, avatar(refName)));
            }

            List<Map<String, Object>> positions = v(company, "positions");
            for (Map<String, Object> position : positions) {
                List<String> enabled = v(position, "enabled");
                if (! enabled.contains("mobile"))
                    continue;

                String title = v(position, "name");
                String start = v(position, "start_date");
                String end = v(position, "end_date");
                List<String> responsibilities = v(position, "responsibilities");
                List<String> technologies = v(position, "technologies");

                experience.add(new Experience(
                    logo(data),
                    primary(data),
                    secondary(data),
                    tertiary(data),
                    company(data),
                    position(title),
                    "present".equals(start) ? new DateTime() : new DateTime(start),
                    "present".equals(end) ? new DateTime() : new DateTime(end),
                    site,
                    address,
                    summary,
                    responsibilities.toArray(new String[0]),
                    technologies.toArray(new String[0]),
                    references.toArray(new Reference[0])
                ));
            }
        }

        return experience;
    }

    public static Projects projects(Map<String, Object> data) {
        Map<String, Object> p = v(data, "projects");
        List<Map<String, Object>> pi = v(p, "items");
        List<Project> projects = new ArrayList<>(pi.size());
        for (Map<String, Object> i : pi) {
            List<String> enabled = v(i, "enabled");
            if (! enabled.contains("mobile"))
                continue;

            String name = v(i, "title");
            String description = v(i, "description");
            String link = v(i, "url");
            List<String> tags = v(i, "tags");
            List<String> images = v(i, "images");

            projects.add(new Project(
                name, description, link, new int[]{}, images.toArray(new String[0]), new String[]{}, tags
            ));
        }

        Map<String, List<Project>> mapping = new HashMap<>();
        for (Project project : projects) {
            for (String tag : project.tags) {
                List<Project> all = mapping.get(tag);
                if (all == null) {
                    all = new ArrayList<>();
                    mapping.put(tag, all);
                }

                all.add(project);
            }
        }
        return new Projects(mapping);
    }

    static List<Skill> skills(Map<String, Object> resume, Map<String, Object> metadata, Projects projects) {
        Map<String, Object> meta = metadata(metadata, "skills");
        ArrayList<Skill> skills = new ArrayList<>(5);

        Map<String, Object> s = v(resume, "skills");
        List<Map<String, Object>> items = v(s, "items");
        for (Map<String, Object> item : items) {
            List<String> enabled = v(item, "enabled");
            if (! enabled.contains("mobile"))
                continue;

            String name = v(item, "name");
            String type = v(item, "type");
            String start = v(item, "start");
            String end = v(item, "end");
            List<String> beginner = v(item, "beginner");
            List<String> intermediate = v(item, "intermediate");
            List<String> advanced = v(item, "advanced");
            Map<String, Object> data = v(meta, name, Collections.EMPTY_MAP);
            Skill.Builder skill = new Skill.Builder(name, logo(data), primary(data))
                .span("present".equals(start) ? new DateTime() : new DateTime(start), "present".equals(end) ? new DateTime() : new DateTime(end))
                .projects(projects.all(type));
            for (String b : beginner) skill.beginner(b);
            for (String i : intermediate) skill.intermediate(i);
            for (String a : advanced) skill.advanced(a);

            skills.add(skill.build());
        }

        return skills;
    }

    static List<Social> social(Map<String, Object> data) {
        Map<String, Object> contact = v(data, "contact");
        List<Map<String, Object>> outlets = v(contact, "outlet");
        ArrayList<Social> socials = new ArrayList<>(4);
        for (Map<String, Object> outlet : outlets) {
            String name = v(outlet, "title");
            String url = v(outlet, "url");
            if ("twitter".equals(name)) {
                socials.add(new Social("Twitter", url, "https://cdn.jsdelivr.net/gh/vijaysharm/resume-android/images/twitter_256.png"));
            } else if ("github".equals(name)) {
                socials.add(new Social("Github", url, "https://cdn.jsdelivr.net/gh/vijaysharm/resume-android/images/github_256.png"));
            } else if ("linkedIn".equals(name)) {
                socials.add(new Social("LinkedIn", url, "https://cdn.jsdelivr.net/gh/vijaysharm/resume-android/images/linkedin_256.png"));
            } else if ("stackoverflow".equals(name)) {
                socials.add(new Social("StackOverflow", url, "https://cdn.jsdelivr.net/gh/vijaysharm/resume-android/images/stackoverflow_256.png"));
            }
        }

        return socials;
    }

    private static String company(Map<String, Object> data) {
        return v(data, "short_name");
    }

    private static String position(String title) {
        String found = positionNames.get(title);
        return TextUtils.isEmpty(found) ? title : found;
    }

    private static String avatar(String name) {
        return defaultValue(avatars, name, "https://cdn.jsdelivr.net/gh/vijaysharm/resume-android/images/person_image_empty.png");
    }

    private static String logo(Map<String, Object> data) {
        return v(data, "logo", "https://cdn.jsdelivr.net/gh/vijaysharm/resume-android/images/logo_256.png");
    }

    private static int primary(Map<String, Object> data) {
        String colorString = v(data, "primary_color", "#D8D8D8");
        return Color.parseColor(colorString);
    }

    private static int secondary(Map<String, Object> data) {
        String colorString = v(data, "secondary_color", "#FFFFFF");
        return Color.parseColor(colorString);
    }

    private static int tertiary(Map<String, Object> data) {
        String colorString = v(data, "tertiary_color", "#D8D8D8");
        return Color.parseColor(colorString);
    }

    private static <T> T defaultValue(Map<String, T> source, String key, T def) {
        T value = source.get(key);
        return value != null ? value : def;
    }

    private static <T> T v(Map<String, Object> data, String key) {
        return v(data, key, null);
    }

    private static <T> T v(Map<String, Object> data, String key, T def) {
        Object obj = data.get(key);
        return obj == null ? def : (T)obj;
    }
}

package ca.vijaysharma.resume;

import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;

import org.joda.time.DateTime;

import java.util.ArrayList;
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
    private static final Map<String, String> companyNames = new HashMap<>();
    private static final Map<String, String> positionNames = new HashMap<>();
    private static final Map<String, String> avatars = new HashMap<>();
    private static final Map<String, String> logos = new HashMap<>();
    private static final Map<String, Integer> primaries = new HashMap<>();
    private static final Map<String, Integer> secondaries = new HashMap<>();
    private static final Map<String, Integer> tertiaries = new HashMap<>();

    static {
        String younility = "Younility";
        primaries.put(younility, R.color.younility);

        String intelerad = "Intelerad Medical Systems";
        companyNames.put(intelerad, "Intelerad");
        primaries.put(intelerad, R.color.intelerad);

        String signiant = "Signiant";
        primaries.put(signiant, R.color.signiant);
        secondaries.put(signiant, R.color.black);
        tertiaries.put(signiant, R.color.white);

        String robarts = "Robarts Research Imaging Institute";
        companyNames.put(robarts, "Robarts");
        primaries.put(robarts, R.color.robarts);

        String concorida = "Concordia University";
        primaries.put(concorida, R.color.concordia);

        String kwilt = "Kwilt";
        primaries.put(kwilt, R.color.kwilt);

        String testfairy = "TestFairy";
        primaries.put(testfairy, R.color.testfairy);

        String datacap = "Datacap Systems Inc";
        companyNames.put(datacap, "Datacap Systems");
        primaries.put(datacap, R.color.datacap);

        String toptal = "Toptal";
        primaries.put(toptal, R.color.toptal);

        String western = "The University of Western Ontario";
        companyNames.put(western, "U. Western Ontario");
        primaries.put(western, R.color.western);

        String android = "android";
        logos.put(android, "https://cdn.rawgit.com/vijaysharm/resume-android/master/images/android_256.png");
        primaries.put(android, R.color.android);

        String ios = "ios";
        logos.put(ios, "https://cdn.rawgit.com/vijaysharm/resume-android/master/images/apple_256.png");
        primaries.put(ios, R.color.apple);

        String web = "web";
        logos.put(web, "https://cdn.rawgit.com/vijaysharm/resume-android/master/images/html5_256.png");
        primaries.put(web, R.color.html5);

        String server = "server";
        logos.put(server, "https://cdn.rawgit.com/vijaysharm/resume-android/master/images/cloud_256.png");
        primaries.put(server, R.color.white);

        String db = "db";
        logos.put(db, "https://cdn.rawgit.com/vijaysharm/resume-android/master/images/storage_256.png");
        primaries.put(db, R.color.grey);

        positionNames.put("Java Software Developer (Cloud Applications)", "Senior Developer");
        positionNames.put("Android Consultant at Datacap Systems Inc", "Android Consultant");
        positionNames.put("Master of Engineering Sciences", "Masters in Engineering");
        positionNames.put("Bachelor of Electrical and Computer Engineering", "Bachelors in Engineering");
        positionNames.put("Android Consultant (contract)", "Android Consultant");

        avatars.put("Vijay Sharma", "https://cdn.rawgit.com/vijaysharm/resume-android/master/images/avatar.jpg");
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

    public static List<ListItem> experiences(Map<String, Object> data) {
        Map<String, Object> exp = v(data, "professionalexperience");
        List<Map<String, Object>> companies = v(exp, "companies");
        ArrayList<ListItem> experience = new ArrayList<>(companies.size());
        int companyIndex = 0;
        for (Map<String, Object> company : companies) {
            String logo = v(company, "logo");
            List<Map<String, Object>> positions = v(company, "positions");
            for (Map<String, Object> position : positions) {
                List<String> enabled = v(position, "enabled");
                if (!enabled.contains("mobile"))
                    continue;

                experience.add(new ListItem(
                    logo(logo),
                    companyIndex++
                ));
            }
        }

        return experience;
    }

    public static List<Education> education(Map<String, Object> data) {
        Map<String, Object> exp = v(data, "education");
        List<Map<String, Object>> schools = v(exp, "schools");
        ArrayList<Education> education = new ArrayList<>();
        for (Map<String, Object> school : schools) {
            String degree = v(school, "name");
            String name = v(school, "location");
            String address = v(school, "address");
            String start = v(school, "start_date");
            String end = v(school, "end_date");
            String site = v(school, "site");
            List<String> responsibilities = v(school, "responsibilities");
            education.add(new Education(
                logo(name),
                primary(name),
                secondary(name),
                tertiary(name),
                company(name),
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

    public static Experience experienceDetail(int index, Map<String, Object> data) {
        Map<String, Object> exp = v(data, "professionalexperience");
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
                String logo = v(company, "logo");
                String address = v(company, "address");
                String summary = v(company, "summary", "");
                String site = v(company, "site", "");
                List<Map<String, Object>> refs = v(company, "references");
                List<Reference> references = new ArrayList<>(refs.size());
                for (Map<String, Object> reference : refs) {
                    String refName = v(reference, "name");
                    String refPosition = v(reference, "position");
                    references.add(new Reference(refName, refPosition, avatar(refName)));
                }

                String title = v(position, "name");
                String start = v(position, "start_date");
                String end = v(position, "end_date");
                List<String> responsibilities = v(position, "responsibilities");
                List<String> technologies = v(position, "technologies");

                return new Experience(
                    logo(logo),
                    primary(name),
                    secondary(name),
                    tertiary(name),
                    company(name),
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

    public static List<Experience> experienceDetails(Map<String, Object> data) {
        Map<String, Object> exp = v(data, "professionalexperience");
        List<Map<String, Object>> companies = v(exp, "companies");
        ArrayList<Experience> experience = new ArrayList<>(companies.size());
        for (Map<String, Object> company : companies) {
            String name = v(company, "location");
            String address = v(company, "address");
            String summary = v(company, "summary", "");
            String site = v(company, "site", "");
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
                    logo(name),
                    primary(name),
                    secondary(name),
                    tertiary(name),
                    company(name),
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

    public static List<Skill> skills(Map<String, Object> data, Projects projects) {
        ArrayList<Skill> skills = new ArrayList<>(4);
        Map<String, Object> s = v(data, "skills");
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

            Skill.Builder skill = new Skill.Builder(name, logo(type), primary(type))
                .span("present".equals(start) ? new DateTime() : new DateTime(start), "present".equals(end) ? new DateTime() : new DateTime(end))
                .projects(projects.all(type));
            for (String b : beginner) skill.beginner(b);
            for (String i : intermediate) skill.intermediate(i);
            for (String a : advanced) skill.advanced(a);

            skills.add(skill.build());
        }

        return skills;
    }

    public static List<Social> social(Map<String, Object> data) {
        Map<String, Object> contact = v(data, "contact");
        List<Map<String, Object>> outlets = v(contact, "outlet");
        ArrayList<Social> socials = new ArrayList<>(4);
        for (Map<String, Object> outlet : outlets) {
            String name = v(outlet, "title");
            String url = v(outlet, "url");
            if ("twitter".equals(name)) {
                socials.add(new Social("Twitter", url, "https://cdn.rawgit.com/vijaysharm/resume-android/master/images/twitter_256.png"));
            } else if ("github".equals(name)) {
                socials.add(new Social("Github", url, "https://cdn.rawgit.com/vijaysharm/resume-android/master/images/github_256.png"));
            } else if ("linkedIn".equals(name)) {
                socials.add(new Social("LinkedIn", url, "https://cdn.rawgit.com/vijaysharm/resume-android/master/images/linkedin_256.png"));
            } else if ("stackoverflow".equals(name)) {
                socials.add(new Social("StackOverflow", url, "https://cdn.rawgit.com/vijaysharm/resume-android/master/images/stackoverflow_256.png"));
            }
        }

        return socials;
    }

    private static String company(String name) {
        String found = companyNames.get(name);
        return TextUtils.isEmpty(found) ? name : found;
    }

    private static String position(String title) {
        String found = positionNames.get(title);
        return TextUtils.isEmpty(found) ? title : found;
    }

    private static String avatar(String name) {
        return defaultValue(avatars, name, "https://cdn.rawgit.com/vijaysharm/resume-android/master/images/person_image_empty.png");
    }

    private static String logo(String name) {
        return defaultValue(logos, name, "https://cdn.rawgit.com/vijaysharm/resume-android/master/images/globe_256.png");
    }

    private static @ColorRes int primary(String name) {
        return defaultValue(primaries, name, R.color.grey);
    }

    private static @ColorRes int secondary(String name) {
        return defaultValue(secondaries, name, R.color.white);
    }

    private static @ColorRes int tertiary(String name) {
        return defaultValue(tertiaries, name, R.color.grey);
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

package ca.vijaysharma.resume;

import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import ca.vijaysharma.resume.models.Education;
import ca.vijaysharma.resume.models.Experience;
import ca.vijaysharma.resume.models.ListItem;
import ca.vijaysharma.resume.models.Profile;
import ca.vijaysharma.resume.models.Project;
import ca.vijaysharma.resume.models.Reference;
import ca.vijaysharma.resume.models.Skill;
import ca.vijaysharma.resume.models.Social;

public class ResumeData {
    private static final Map<String, String> companyNames = new HashMap<>();
    private static final Map<String, String> positionNames = new HashMap<>();
    private static final Map<String, Integer> avatars = new HashMap<>();
    private static final Map<String, Integer> logos = new HashMap<>();
    private static final Map<String, Integer> primaries = new HashMap<>();
    private static final Map<String, Integer> secondaries = new HashMap<>();
    private static final Map<String, Integer> tertiaries = new HashMap<>();

    static {
        String younility = "Younility";
        logos.put(younility, R.drawable.younility);
        primaries.put(younility, R.color.younility);

        String intelerad = "Intelerad Medical Systems";
        companyNames.put(intelerad, "Intelerad");
        logos.put(intelerad, R.drawable.intelerad);
        primaries.put(intelerad, R.color.intelerad);

        String signiant = "Signiant";
        logos.put(signiant, R.drawable.signiant);
        primaries.put(signiant, R.color.signiant);
        secondaries.put(signiant, R.color.black);
        tertiaries.put(signiant, R.color.white);

        String robarts = "Robarts Research Imaging Institute";
        companyNames.put(robarts, "Robarts");
        logos.put(robarts, R.drawable.robarts);
        primaries.put(robarts, R.color.robarts);

        String concorida = "Concordia University";
        logos.put(concorida, R.drawable.concordia);
        primaries.put(concorida, R.color.concordia);

        String kwilt = "Kwilt";
        logos.put(kwilt, R.drawable.kwilt);
        primaries.put(kwilt, R.color.kwilt);

        String toptal = "Toptal";
        logos.put(toptal, R.drawable.toptal);
        primaries.put(toptal, R.color.toptal);

        String western = "The University of Western Ontario";
        companyNames.put(western, "U. Western Ontario");
        logos.put(western, R.drawable.western_256);
        primaries.put(western, R.color.western);

        positionNames.put("Java Software Developer (Cloud Applications)", "Senior Developer");
        positionNames.put("Android Consultant at Datacap Systems Inc", "Android Consultant");
        positionNames.put("Master of Engineering Sciences", "Masters in Engineering");
        positionNames.put("Bachelor of Electrical and Computer Engineering", "Bachelors in Engineering");

        avatars.put("Vijay Sharma", R.drawable.avatar);
    }

    public static Profile profile(Map<String, Object> data) {
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

        return new Profile(
            name, avatar(name), email, site, location, position, biography, objective
        );
    }

    public static List<ListItem> experiences(Map<String, Object> data) {
        Map<String, Object> exp = v(data, "professionalexperience");
        List<Map<String, Object>> companies = v(exp, "companies");
        ArrayList<ListItem> experience = new ArrayList<>(companies.size());
        int companyIndex = 0;
        for (Map<String, Object> company : companies) {
            String name = v(company, "location");
            List<Map<String, Object>> positions = v(company, "positions");
            for (Map<String, Object> position : positions) {
                List<String> enabled = v(position, "enabled");
                if (!enabled.contains("mobile"))
                    continue;

                experience.add(new ListItem(
                    logo(name),
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

    public static List<Skill> skills(Map<String, Object> data) {
        List<Project> android = Arrays.asList(
            new Project("PixPass",
                "PixPass iz da best!!!!",
                "https://play.google.com/store/apps/details?id=ca.vijaysharma.password",
                new int[]{},
                new String[]{"https://lh3.googleusercontent.com/KYuKvPJYQiVSnjL5Ry6boDDAB7HQWpdqe1CWDAbtrNrBSdMCYnNcwJxdoCXgisAi4r-f=h900-rw"},
                new String[]{},
                new HashSet<String>()
            )
        );

        ArrayList<Skill> skills = new ArrayList<>(4);
        skills.add(
            new Skill.Builder("Android", R.drawable.android_256, R.color.android)
                .beginner("Animations")
                .intermediate("Realm.io").intermediate("Google Play Store")
                .advanced("RxJava").advanced("Picasso").advanced("Glide")
                .span(new DateTime("2010-01-01T00:00:00.000-03:00"), new DateTime())
                .project(android.get(0))
                .build()
        );
        skills.add(
            new Skill.Builder("iOS", R.drawable.apple_256, R.color.apple)
                .span(new DateTime("2010-01-01T00:00:00.000-03:00"), new DateTime())
                .build()
        );
        skills.add(
            new Skill.Builder("Frontend Web", R.drawable.html5_256, R.color.html5)
                .span(new DateTime("2010-01-01T00:00:00.000-03:00"), new DateTime())
                .build()
        );
        skills.add(
            new Skill.Builder("Backend", R.drawable.cloud_256, R.color.white)
                .span(new DateTime("2010-01-01T00:00:00.000-03:00"), new DateTime())
                .build()
        );
        skills.add(
            new Skill.Builder("Storage", R.drawable.storage_256, R.color.grey)
                .span(new DateTime("2010-01-01T00:00:00.000-03:00"), new DateTime())
                .build()
        );

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
                socials.add(new Social("Twitter", url, R.drawable.twitter_256));
            } else if ("github".equals(name)) {
                socials.add(new Social("Github", url, R.drawable.github_256));
            } else if ("linkedIn".equals(name)) {
                socials.add(new Social("LinkedIn", url, R.drawable.linkedin_256));
            } else if ("stackoverflow".equals(name)) {
                socials.add(new Social("StackOverflow", url, R.drawable.stackoverflow_256));
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

    private static @DrawableRes int avatar(String name) {
        return defaultValue(avatars, name, R.drawable.person_image_empty);
    }

    private static @DrawableRes int logo(String name) {
        return defaultValue(logos, name, R.drawable.younility);
    }

    private static @ColorRes int primary(String name) {
        return defaultValue(primaries, name, R.color.younility);
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

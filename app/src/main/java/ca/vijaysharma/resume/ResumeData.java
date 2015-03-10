package ca.vijaysharma.resume;

import ca.vijaysharma.resume.models.Experience;
import ca.vijaysharma.resume.models.Profile;
import ca.vijaysharma.resume.models.Reference;

public class ResumeData {
    public static final Profile profile = new Profile(
        "Vijay Sharma",
        R.drawable.avatar,
        "vijay.sharm@gmail.com",
        "http://www.vijaysharma.ca",
        "Ottawa, Ontario",
        "Senior Mobile Developer",
        "Insert super hero biography",
        "Get my objective on"
    );

    public static final Experience[] experiences = {
        new Experience(
            R.drawable.younility,
            R.color.younility,
            "Younility",
            "Senior Mobile Developer",
            null,
            null,
            "https://www.younility.com",
            "Nepean, Ottawa",
            "Leaders in field communication software",
            new String[] {
                "Boostrapped continuous delivery environment for both iOS and Android",
                "Re-architected mobile application to make efficient use of battery life and network bandwidth",
                "Redesigned end-user experience as part of the company's re-branding",
                "Managed app deployment to both Apple's App Store and Google's Play Store",
                "Helped establish Agile process to mobile team",
                "Help secure IRAPP government funding for business applications using NFC"
            }, new String[]{
               "iOS", "XCode", "Cocoapods", "Android", "Intellij", "Gradle", "Circle CI", "Git", "NFC"
            }, new Reference[] {
               new Reference("Chris Cameron", "CTO", R.drawable.avatar),
               new Reference("Ryan Cassels", "CEO", R.drawable.avatar)
            }
        ),
        new Experience(
            R.drawable.signiant,
            R.color.signiant,
            "Signiant",
            "Senior Mobile Developer",
            null,
            null,
            "https://www.signiant.com",
            "Kanata, Ottawa",
            "Helps distributors ensure fast, secure delivery of large files over public and private networks.",
            new String[] {
                "Developed Cloud and Mobile-based solutions that extend the reach of Signiant products beyond traditional enterprise environments",
                "Implemented product features from the user interface through to back end web services, using technologies such as Android, iOS, JavaScript, Java, and NoSQL solutions",
                "Designed system components to align with high performance system architecture",
                "Implemented interfaces among system components and with external systems, for example REST",
                "Worked effectively within Agile software development team"
            }, new String[]{
                "MarionetteJS", "Java", "Android", "iOS", "AWS Elastic Beanstalk", "Jersey", "DynamoDB", "Maven", "SVN"
            }, new Reference[] {
                new Reference("Peter Hodgkinson", "Software Developer", R.drawable.avatar),
                new Reference("Peter Boos", "Director of Engineering", R.drawable.avatar)
            }
        )
    };
}

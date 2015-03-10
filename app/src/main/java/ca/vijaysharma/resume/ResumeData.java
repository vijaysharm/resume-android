package ca.vijaysharma.resume;

import org.joda.time.DateTime;

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
        "Insert super hero origin story",
        "Get my objective on"
    );

    public static final Experience[] experiences = {
        new Experience(
            R.drawable.younility,
            R.color.younility,
            R.color.white,
            R.color.grey,
            "Younility",
            "Senior Mobile Developer",
            new DateTime("2014-10-01T00:00:00.000-03:00"),
            new DateTime(),
            "https://www.younility.com",
            "Ottawa, Ontario",
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
            R.color.black,
            R.color.grey,
            "Signiant",
            "Senior Mobile Developer",
            new DateTime("2014-02-01T00:00:00.000-03:00"),
            new DateTime("2014-10-01T00:00:00.000-03:00"),
            "https://www.signiant.com",
            "Kanata, Ontario",
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
        ),
        new Experience(
            R.drawable.intelerad,
            R.color.intelerad,
            R.color.white,
            R.color.grey,
            "Intelerad",
            "Senior Member of R&D",
            new DateTime("2011-01-01T00:00:00.000-03:00"),
            new DateTime("2014-02-01T00:00:00.000-03:00"),
            "https://www.intelerad.com",
            "Montreal, Quebec",
            "Provides medical imaging solutions and services to radiology groups, hospitals, imaging centers, and teleradiology businesses.",
            new String[] {
                "Lead the development of the company's new flagship online clinical image viewer using latest HTML5 technologies to develop a high performance viewer targeted for the web and mobile devices",
                "Wrote, managed and peer-reviewed requirements, design and test plan documents for all team projects, working closely with stakeholders to ensure both company's best interest, and the integrity of the software",
                "Architected new advanced visualization 3D application to integrate into large enterprise code base, working closely with UI designers to develop cutting edge, feature rich applications",
                "Mentored new employees to adapt to company's culture, in a lead position on projects, and spearheading new software development practices",
                "Developed support for multiple platforms including Mobile-Web, Android and iOS"
            }, new String[]{
                "AngularJS", "EmberJS", "GWT", "Android", "iOS", "Tomcat", "PostgreSQL"
            }, new Reference[] {
                new Reference("Hormoz Pirzadeh", "Director of Engineering", R.drawable.avatar),
                new Reference("Amos Chan", "Software Developer", R.drawable.avatar)
            }
        )
    };
}

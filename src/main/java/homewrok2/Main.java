package homewrok2;

import java.io.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

public class Main {
    private static final String USER_DB = "D:\\pdp\\homework\\src\\main\\java\\homewrok2\\user_db.tx";
    private static final List<Post> posts = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        Map<String, User> users = loadUsers();
        initializePosts();

        System.out.println("1. Register\n2. Login\nChoose an option:");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1) {
            register(users);
        } else if (choice == 2) {
            login(users);
        } else {
            System.out.println("Invalid option!");
        }

        saveUsers(users);
    }

    private static void register(Map<String, User> users) {
        System.out.println("Enter phone number (+998941234567):");
        String phone = scanner.nextLine();
        if (!Pattern.matches("\\+998\\d{9}", phone)) {
            System.out.println("Invalid phone number format!");
            return;
        }

        System.out.println("Enter password (must contain uppercase, lowercase, number, and special character):");
        String password = scanner.nextLine();
        if (!isValidPassword(password)) {
            System.out.println("Invalid password format!");
            return;
        } else {
            System.out.println("Valid password format!");
        }

        System.out.println("Enter your ZoneId (e.g., Asia/Tashkent):");
        String zoneIdInput = scanner.nextLine();
        ZoneId zoneId;
        try {
            zoneId = ZoneId.of(zoneIdInput);
        } catch (Exception e) {
            System.out.println("Invalid ZoneId!");
            return;
        }

        User user = new User(phone, password, zoneId);
        users.put(phone, user);
        System.out.println("User registered successfully!");
    }


    private static void login(Map<String, User> users) {
        System.out.println("Enter phone number:");
        String phone = scanner.nextLine();

        System.out.println("Enter password:");
        String password = scanner.nextLine();

        User user = users.get(phone);
        if (user != null && user.getPassword().equals(password)) {
            System.out.println("Login successful!");
            showPosts(user);
        } else {
            System.out.println("Invalid phone number or password!");
        }
    }

    private static void showPosts(User user) {
        System.out.println("Posts:");
        ZoneId userZoneId = user.getZoneId();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");

        for (Post post : posts) {
            ZonedDateTime userTime = post.getDateTime().withZoneSameInstant(userZoneId);
            System.out.println(post.getTitle() + " - " + userTime.format(formatter));
        }
    }

    private static void initializePosts() {
        posts.add(new Post("Post 1", ZonedDateTime.now(ZoneId.of("Asia/Tashkent"))));
        posts.add(new Post("Post 2", ZonedDateTime.now(ZoneId.of("Asia/Tashkent"))));
    }

    private static boolean isValidPassword(String password) {
        return password.length() >= 8 &&
                Pattern.compile("[A-Z]").matcher(password).find() &&
                Pattern.compile("[a-z]").matcher(password).find() &&
                Pattern.compile("\\d").matcher(password).find() &&
                Pattern.compile("[!@#$%^&*()_,.?\":{}|<>]").matcher(password).find();
    }


    private static Map<String, User> loadUsers() throws IOException {
        Map<String, User> users = new HashMap<>();
        File file = new File(USER_DB);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    User user = User.fromString(line);
                    users.put(user.getPhone(), user);
                }
            }
        }
        return users;
    }

    private static void saveUsers(Map<String, User> users) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_DB))) {
            for (User user : users.values()) {
                writer.write(user.toString());
                writer.newLine();
            }
        }
    }
}

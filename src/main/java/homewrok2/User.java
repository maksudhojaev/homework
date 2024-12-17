package homewrok2;

import java.time.*;

class User {
    private String phone;
    private String password;
    private ZoneId zoneId;

    public User(String phone, String password, ZoneId zoneId) {
        this.phone = phone;
        this.password = password;
        this.zoneId = zoneId;
    }
    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public ZoneId getZoneId() {
        return zoneId;
    }

    @Override
    public String toString() {
        return phone + "," + password + "," + zoneId;
    }

    public static User fromString(String line) {
        String[] parts = line.split(",");
        return new User(parts[0], parts[1], ZoneId.of(parts[2]));
    }
}


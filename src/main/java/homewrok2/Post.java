package homewrok2;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

class Post {
    private String title;
    private ZonedDateTime dateTime;

    public Post(String title, ZonedDateTime dateTime) {
        this.title = title;
        this.dateTime = dateTime;
    }

    public String getTitle() {
        return title;
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        return title + "," + dateTime.format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
    }
}

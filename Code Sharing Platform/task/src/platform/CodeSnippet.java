package platform;

import com.google.gson.annotations.Expose;
import com.sun.istack.NotNull;
import lombok.Data;
import org.hibernate.annotations.NotFound;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

// not taking advantage of Lombok yet

@Data
@Entity
@Table(name = "code_snippets")
public class CodeSnippet {


    @Id
    @GeneratedValue
    private Long id;

    private String uuid;
    @Expose
    private String code;
    @Expose
    private long time;
    @Expose
    private long views;

    private LocalDateTime date;



    @NotFound
    private boolean isTimePresent = false;

    @NotNull
    private boolean isViewPresent = false;

    private long originalTimerStart = 0;
    private long originalTime = 0;

    public CodeSnippet(String code, LocalDateTime date, long time, long views) {
        this.uuid = UUID.randomUUID().toString();
        this.code = code;
        this.date = date;
        this.time = time;
        this.views = views;
        this.isViewPresent = false;
        this.isTimePresent = false;

    }
    public CodeSnippet() {

    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime dateTime) {
        this.date = dateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }

    @Override
    public String toString() {
        return "CodeSnippet{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", code='" + code + '\'' +
                ", date=" + date +
                ", time=" + time +
                ", views=" + views +
                '}';
    }
}

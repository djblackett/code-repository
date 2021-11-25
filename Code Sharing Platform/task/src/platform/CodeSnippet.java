package platform;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "code_snippets")
public class CodeSnippet {

    @JsonIgnore
    @Id
    @GeneratedValue
    private Long id;

    @JsonIgnore
    private String uuid;
    private String code;

//    @JsonSerialize(using = LocalDateTimeSerializer.class)
//    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
//    @JsonProperty("date")


    //@UpdateTimestamp
    private LocalDateTime date;

    private long time;
    private long views;

    @JsonIgnore
    @NotFound
    private boolean isTimePresent = false;

    @NotNull
    @JsonIgnore
    private boolean isViewPresent = false;



    @JsonIgnore
    private long originalTimerStart = 0;

    @JsonIgnore
    private long originalTime = 0;


    public CodeSnippet(String code, LocalDateTime date) {
        this.uuid = UUID.randomUUID().toString();
        this.code = code;
        this.date = date;
        this.time = 0;
        this.views = 0;
    }

    public CodeSnippet(String code, LocalDateTime date, long time, long views) {
        this.uuid = UUID.randomUUID().toString();
        this.code = code;
        this.date = date;
        this.time = time;
        this.views = views;
        this.isViewPresent = false;
        this.isTimePresent = false;

    }

    public CodeSnippet(String code, LocalDateTime date, long time) {
        this.uuid = UUID.randomUUID().toString();
        this.code = code;
        this.date = date;
        this.time = time;
        this.views = 0;
    }

    public CodeSnippet(String uuid, String code, LocalDateTime date, long views) {
        this.uuid = UUID.randomUUID().toString();
        this.code = code;
        this.date = date;
        this.views = views;
        this.time = 0;
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

package speed.wagon.noticeapp.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@Document(value = "notices")
public class Notice {
    @Id
    private Long id;
    private String notice;
    private Long likesCount;
    private LocalDateTime creationDate;
}

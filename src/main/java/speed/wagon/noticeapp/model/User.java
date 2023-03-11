package speed.wagon.noticeapp.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(value = "users")
public class User {
    @Id
    private Long id;
    private String username;
    private String password;
    private List<Long> noticeIdsWithLike;
}

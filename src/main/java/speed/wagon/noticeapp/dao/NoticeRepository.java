package speed.wagon.noticeapp.dao;

import speed.wagon.noticeapp.model.Notice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface NoticeRepository extends MongoRepository<Notice, Long> {
    Optional<Notice> findById(Long id);
}

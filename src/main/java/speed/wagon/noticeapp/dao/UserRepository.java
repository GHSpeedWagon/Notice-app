package speed.wagon.noticeapp.dao;

import speed.wagon.noticeapp.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, Long> {
    User findUserByUsername(String username);
}

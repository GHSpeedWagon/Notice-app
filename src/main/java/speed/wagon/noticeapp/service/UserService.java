package speed.wagon.noticeapp.service;

import java.util.List;
import speed.wagon.noticeapp.model.User;

public interface UserService {
    User create(User user);

    User get(Long id);

    User getByUsername(String username);

    List<User> getAll();

    User update(User user);

    void delete(Long id);
}

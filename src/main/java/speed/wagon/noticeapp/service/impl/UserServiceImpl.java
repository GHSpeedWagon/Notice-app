package speed.wagon.noticeapp.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.security.crypto.password.PasswordEncoder;
import speed.wagon.noticeapp.dao.UserRepository;
import speed.wagon.noticeapp.model.User;
import org.springframework.stereotype.Service;
import speed.wagon.noticeapp.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    private static final AtomicLong idCounter = new AtomicLong();
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private Long lastId = 0L;

    static class UserDateComparator implements Comparator<User> {
        @Override
        public int compare(User o1, User o2) {
            return o2.getId().compareTo(o1.getId());
        }
    }

    public UserServiceImpl(PasswordEncoder encoder,
                           UserRepository userRepository) {
        this.encoder = encoder;
        this.userRepository = userRepository;
        lastId = getLastId();
    }

    private Long getLastId() {
        List<User> all = userRepository.findAll();
        Long lastId = 0L;
        if (!all.isEmpty()) {
            lastId = all.stream().min(new UserDateComparator()).get().getId();
        }
        return lastId;
    }

    @Override
    public User create(User user) {
        user.setId(idCounter.incrementAndGet() + lastId);
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }


    @Override
    public User get(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new RuntimeException("User with id=" + id + ", does not exist")
        );
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}

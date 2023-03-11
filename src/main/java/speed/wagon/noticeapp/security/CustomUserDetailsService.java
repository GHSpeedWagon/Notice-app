package speed.wagon.noticeapp.security;

import static org.springframework.security.core.userdetails.User.withUsername;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import speed.wagon.noticeapp.model.User;
import speed.wagon.noticeapp.service.UserService;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userService.getByUsername(username);
            org.springframework.security.core.userdetails.User.UserBuilder builder;
            builder = withUsername(username);
            builder.password(user.getPassword());
            builder.roles("USER");
            return builder.build();
        } catch (Exception e) {
            throw new RuntimeException("bad credential for username");
        }
    }
}

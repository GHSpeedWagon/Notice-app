package speed.wagon.noticeapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.view.RedirectView;
import speed.wagon.noticeapp.dto.mapper.UserMapper;
import speed.wagon.noticeapp.dto.request.UserRequestDto;
import speed.wagon.noticeapp.model.User;
import speed.wagon.noticeapp.service.UserService;
import java.util.List;

@Controller
public class UserController {
    private final UserMapper userMapper;
    private final UserService userService;

    public UserController(UserMapper userMapper,
                          UserService userService) {
        this.userMapper = userMapper;
        this.userService = userService;
    }

    @GetMapping("/register")
    public String getLoginForm() {
        return "register";
    }

    @PostMapping("/register")
    public void createUser(@RequestBody UserRequestDto userRequestDto) {
        userService.create(userMapper.toModel(userRequestDto));
    }

    @GetMapping("/")
    public RedirectView redirect() {
        return new RedirectView("http://localhost:8080/notices");
    }

    @DeleteMapping("users/delete/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }

    @PostMapping("/users/edit")
    public RedirectView updateNotice(@RequestBody User user) {
        User currentUser = userService.get(user.getId());
        currentUser.setUsername(user.getUsername());
        userService.update(currentUser);
        return new RedirectView("http://localhost:8080/users");
    }

    @GetMapping("/users")
    public String getAll(Model model) {
        List<User> allUsers = userService.getAll();
        model.addAttribute("users", allUsers);
        return "user/users";
    }
}

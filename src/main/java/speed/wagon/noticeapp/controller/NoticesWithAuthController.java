package speed.wagon.noticeapp.controller;

import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;
import speed.wagon.noticeapp.dto.mapper.NoticeMapper;
import speed.wagon.noticeapp.dto.request.NoticeRequestDto;
import speed.wagon.noticeapp.model.Notice;
import speed.wagon.noticeapp.model.User;
import speed.wagon.noticeapp.service.NoticeService;
import speed.wagon.noticeapp.service.UserService;

@Controller
@RequestMapping("/notices")
public class NoticesWithAuthController {
    private final NoticeService noticeService;
    private final UserService userService;
    private final NoticeMapper noticeMapper;

    public NoticesWithAuthController(NoticeService noticeService,
                                     UserService userService,
                                     NoticeMapper noticeMapper) {
        this.noticeService = noticeService;
        this.userService = userService;
        this.noticeMapper = noticeMapper;
    }

    @GetMapping
    public String getAllPossibilities(Authentication authentication, Model model) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<Notice> noticeList = noticeService.getAll("ASC");
        String username = userDetails.getUsername();
        model.addAttribute("username", username);
        model.addAttribute("notices", noticeList);
        return "notices/notices";
    }

    @GetMapping("/like/{id}")
    public RedirectView likeNotice(Authentication auth, @PathVariable Long id) {
        Notice currentNotice = noticeService.getById(id);
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String username = userDetails.getUsername();
        try {
            User currentUser = userService.getByUsername(username);
            if (!currentUser.getNoticeIdsWithLike().contains(id)) {
                currentNotice.setLikesCount(currentNotice.getLikesCount() + 1L);
                currentUser.getNoticeIdsWithLike().add(id);
                userService.update(currentUser);
                noticeService.update(currentNotice);
            }
        } catch (Exception e) {
            System.out.println("You can add likes and unlikes if you are not user");
        }
        return new RedirectView("http://localhost:8080/notices");
    }

    @GetMapping("/unlike/{id}")
    public RedirectView unlikeNotice(Authentication auth, @PathVariable Long id) {
        Notice currentNotice = noticeService.getById(id);
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String username = userDetails.getUsername();
        try {
            User currentUser = userService.getByUsername(username);
            if (currentUser.getNoticeIdsWithLike().contains(id)) {
                currentNotice.setLikesCount(currentNotice.getLikesCount() - 1L);
                List<Long> ids = currentUser.getNoticeIdsWithLike()
                        .stream()
                        .filter(d -> !d.equals(id))
                        .toList();
                currentUser.setNoticeIdsWithLike(ids);
                userService.update(currentUser);
                noticeService.update(currentNotice);
            }
        } catch (Exception e) {
            System.out.println("You can add likes and unlikes if you are not user");
        }
        return new RedirectView("http://localhost:8080/notices");
    }

    @PostMapping
    public RedirectView createNotice(@RequestBody NoticeRequestDto noticeRequestDto) {
        noticeService.create(noticeMapper.toModel(noticeRequestDto));
        return new RedirectView("http://localhost:8080/notices");
    }

    @PostMapping("/edit")
    public RedirectView updateNotice(@RequestBody Notice notice) {
        Notice currentNotice = noticeService.getById(notice.getId());
        currentNotice.setNotice(notice.getNotice());
        noticeService.update(currentNotice);
        return new RedirectView("http://localhost:8080/notices");
    }
}

package speed.wagon.noticeapp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import speed.wagon.noticeapp.dto.mapper.NoticeMapper;
import speed.wagon.noticeapp.dto.request.NoticeRequestDto;
import speed.wagon.noticeapp.dto.response.NoticeResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import speed.wagon.noticeapp.service.NoticeService;
import java.util.List;

@RestController
@RequestMapping("/api/notices")
public class NoticeWithoutAuthController {
    private final NoticeService noticeService;
    private final NoticeMapper noticeMapper;

    public NoticeWithoutAuthController(NoticeService noticeService,
                                       NoticeMapper noticeMapper) {
        this.noticeService = noticeService;
        this.noticeMapper = noticeMapper;
    }

    @GetMapping
    public List<NoticeResponseDto> getAllNotices(@RequestParam (defaultValue = "ASC") String sort) {
        return noticeService.getAll(sort).stream().map(noticeMapper::toDto).toList();
    }

    @PostMapping
    public NoticeResponseDto createNotice(@RequestBody NoticeRequestDto noticeRequestDto) {
        return noticeMapper.toDto(noticeService.create(noticeMapper.toModel(noticeRequestDto)));
    }
}

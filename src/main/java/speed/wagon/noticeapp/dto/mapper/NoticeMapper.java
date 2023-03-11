package speed.wagon.noticeapp.dto.mapper;

import speed.wagon.noticeapp.dto.request.NoticeRequestDto;
import speed.wagon.noticeapp.dto.response.NoticeResponseDto;
import speed.wagon.noticeapp.model.Notice;
import org.springframework.stereotype.Component;

@Component
public class NoticeMapper implements RequestDtoMapper<Notice, NoticeRequestDto>,
        ResponseDtoMapper<Notice, NoticeResponseDto> {

    @Override
    public Notice toModel(NoticeRequestDto dto) {
        Notice notice = new Notice();
        notice.setNotice(dto.getNotice());
        notice.setId(dto.getId());
        return notice;
    }

    @Override
    public NoticeResponseDto toDto(Notice model) {
        NoticeResponseDto noticeResponseDto = new NoticeResponseDto();
        noticeResponseDto.setNotice(model.getNotice());
        noticeResponseDto.setLikesCount(model.getLikesCount());
        return noticeResponseDto;
    }
}

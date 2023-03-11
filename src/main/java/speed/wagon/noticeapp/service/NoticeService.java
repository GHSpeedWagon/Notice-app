package speed.wagon.noticeapp.service;

import java.util.List;
import speed.wagon.noticeapp.model.Notice;

public interface NoticeService {
    Notice create(Notice notice);

    List<Notice> getAll(String sortingType);

    Notice getById(Long noticeId);

    Notice update(Notice notice);

    void delete(Long id);
}

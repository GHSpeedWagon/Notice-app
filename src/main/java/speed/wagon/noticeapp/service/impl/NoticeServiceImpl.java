package speed.wagon.noticeapp.service.impl;

import speed.wagon.noticeapp.dao.NoticeRepository;
import speed.wagon.noticeapp.model.Notice;
import org.springframework.stereotype.Service;
import speed.wagon.noticeapp.service.NoticeService;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class NoticeServiceImpl implements NoticeService {
    private static final AtomicLong idCounter = new AtomicLong();
    private final NoticeRepository noticeRepository;
    private Long lastId = 0L;

    static class NoticeDateComparator implements Comparator<Notice> {
        @Override
        public int compare(Notice o1, Notice o2) {
            return o2.getCreationDate().compareTo(o1.getCreationDate());
        }
    }

    public NoticeServiceImpl(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
        lastId = getLastId();
    }

    private Long getLastId() {
        List<Notice> all = noticeRepository.findAll();
        Long lastId = 0L;
        if (!all.isEmpty()) {
            lastId = all.stream().min(new NoticeDateComparator()).get().getId();
        }
        return lastId;
    }

    @Override
    public Notice create(Notice notice) {
        notice.setId(idCounter.incrementAndGet() + lastId);
        notice.setLikesCount(0L);
        notice.setCreationDate(LocalDateTime.now());
        return noticeRepository.save(notice);
    }

    @Override
    public List<Notice> getAll(String sortingType) {
        List<Notice> allNotices = noticeRepository.findAll();
        if (sortingType.equals("ASC")) {
            allNotices = allNotices.stream().sorted(new NoticeDateComparator()).toList();
        }
        return allNotices;
    }

    @Override
    public Notice getById(Long noticeId) {
        return noticeRepository.findById(noticeId).orElseThrow(
                () -> new RuntimeException("Notice with id=" + noticeId + ", does not exist")
        );
    }

    @Override
    public Notice update(Notice notice) {
        return noticeRepository.save(notice);
    }

    @Override
    public void delete(Long id) {
        noticeRepository.deleteById(id);
    }
}

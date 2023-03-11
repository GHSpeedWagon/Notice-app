package speed.wagon.noticeapp.dto.mapper;

public interface ResponseDtoMapper<M, D> {
    D toDto(M model);
}

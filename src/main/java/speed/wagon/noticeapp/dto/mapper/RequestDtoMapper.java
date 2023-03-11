package speed.wagon.noticeapp.dto.mapper;

public interface RequestDtoMapper<M, D> {
    M toModel(D dto);
}

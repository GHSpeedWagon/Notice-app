package speed.wagon.noticeapp.dto.mapper;

import speed.wagon.noticeapp.dto.request.UserRequestDto;
import speed.wagon.noticeapp.dto.response.UserResponseDto;
import speed.wagon.noticeapp.model.User;
import org.springframework.stereotype.Component;
import java.util.ArrayList;

@Component
public class UserMapper implements RequestDtoMapper<User, UserRequestDto>,
        ResponseDtoMapper<User, UserResponseDto> {
    @Override
    public User toModel(UserRequestDto dto) {
        User user = new User();
        user.setNoticeIdsWithLike(new ArrayList<>());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        return user;
    }

    @Override
    public UserResponseDto toDto(User model) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(model.getId());
        userResponseDto.setUsername(model.getUsername());
        return userResponseDto;
    }
}

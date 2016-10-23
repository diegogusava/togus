package br.com.diegogusava.togus.endpoint.converter;

import br.com.diegogusava.togus.domain.Task;
import br.com.diegogusava.togus.domain.User;
import br.com.diegogusava.togus.dto.UserDTO;

import java.util.List;
import java.util.stream.Collectors;

public class UserConverter {

    public static UserDTO of(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        return dto;
    }

    public static UserDTO ofComplete(User user, List<Task> tasks) {
        UserDTO dto = of(user);
        dto.setTasks(tasks.stream().map(t -> TaskConverter.of(t)).collect(Collectors.toList()));
        return dto;
    }

    public static User toUser(UserDTO dto) {
        return new User(dto.getId(), dto.getName(), dto.getEmail());
    }

}

package br.com.diegogusava.togus.endpoint.converter;

import br.com.diegogusava.togus.domain.Task;
import br.com.diegogusava.togus.domain.User;
import br.com.diegogusava.togus.dto.TaskDTO;

public class TaskConverter {

    public static TaskDTO of(Task t) {
        TaskDTO dto = new TaskDTO();
        dto.setUserId(t.getUser().getId());
        dto.setId(t.getId());
        dto.setTitle(t.getTitle());
        dto.setContent(t.getContent());
        return dto;
    }

    public static Task toTask(TaskDTO dto) {
        return new Task(dto.getTitle(), dto.getContent(), new User(dto.getUserId()));
    }

}

package br.com.diegogusava.togus.dto;

import java.io.Serializable;
import java.util.List;

public class UserDTO implements Serializable {

    private Integer id;

    private String name;

    private String email;

    private List<TaskDTO> tasks;

    public UserDTO() {

    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public List<TaskDTO> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskDTO> tasks) {
        this.tasks = tasks;
    }
}

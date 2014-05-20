
package com.number;

import java.util.List;

public class Department {

    private List<Project> Tasks;
    private List<Employee> Member;
    private String Name;
    private Long id;

    public List<Project> getTasks() {
        return Tasks;
    }

    public void setTasks(List<Project> Tasks) {
        this.Tasks = Tasks;
    }

    public List<Employee> getMember() {
        return Member;
    }

    public void setMember(List<Employee> Member) {
        this.Member = Member;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
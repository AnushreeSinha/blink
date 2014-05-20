
package com.fashion;

import java.util.List;

public class Project {

    private List<Employee> Team;
    private Long id;

    public List<Employee> getTeam() {
        return Team;
    }

    public void setTeam(List<Employee> Team) {
        this.Team = Team;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}

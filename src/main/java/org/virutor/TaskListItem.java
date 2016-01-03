package org.virutor;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name="TASK_ITEM", uniqueConstraints = {@UniqueConstraint(columnNames = "ID")})
public class TaskListItem {

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "NAME", unique = false, nullable = false)
    private String name;

    @Column(name = "DESCRIPTION", unique = false)
    private String description;

    @JsonIgnore
    @ManyToOne(optional = false)
    private TaskList taskList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskList getTaskList() {
        return taskList;
    }

    public void setTaskList(TaskList taskList) {
        this.taskList = taskList;
    }

}

package org.virutor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="TASK_LIST", uniqueConstraints = {@UniqueConstraint(columnNames = "ID")})
public class TaskList {

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "NAME", unique = false, nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @OrderBy("ID")
    private List<TaskListItem> taskListItems = new ArrayList<>();

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

    public List<TaskListItem> getTaskListItems() {
        return taskListItems;
    }

    @SuppressWarnings("unused")
    public void setTaskListItems(List<TaskListItem> taskListItems) {
        this.taskListItems = taskListItems;
    }
}

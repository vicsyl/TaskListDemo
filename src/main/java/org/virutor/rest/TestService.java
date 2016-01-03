package org.virutor.rest;

import org.virutor.TaskList;
import org.virutor.TaskListItem;
import org.virutor.db.HibernateUtil;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/test")
public class TestService {

    private TaskListItem createItem(TaskList parent) {

        TaskListItem taskListItem = new TaskListItem();
        taskListItem.setName("Only task item of " + (parent.getName()));
        taskListItem.setDescription("Description of the only task item of " + (parent.getName()) + "");
        taskListItem.setTaskList(parent);
        return taskListItem;
    }

    @GET
    @Path("/do")
    public String createData() {

        HibernateUtil.doInTransaction(session -> {

            for (int i = 0; i < 5; i++) {
                TaskList taskList = new TaskList();
                taskList.setName("Task list no. " + (i + 1));
                taskList.getTaskListItems().add(createItem(taskList));
                session.save(taskList);
            }
            return null;
        });

        return "Data created!";
    }

}

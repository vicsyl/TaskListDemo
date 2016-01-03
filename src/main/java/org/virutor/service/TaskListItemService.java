package org.virutor.service;

import org.virutor.DomainException;
import org.virutor.TaskList;
import org.virutor.TaskListItem;
import org.virutor.db.HibernateUtil;

import java.util.List;

public class TaskListItemService {

    public List<TaskListItem> getAllItems(Integer parentId) {

        return HibernateUtil.doInTransaction(session -> {
            TaskList tl = (TaskList) session.get(TaskList.class, parentId);
            if(tl == null) {
                throw new DomainException("Task List with id=" + parentId + " doesn't exist");
            }
            List<TaskListItem> ret = tl.getTaskListItems();
            return ret;
        });
    }

    public TaskListItem createTaskListItem(TaskListItem taskListItem, Integer parentId) {

        return HibernateUtil.doInTransaction(session -> {

            TaskList parent = (TaskList) session.get(TaskList.class, parentId);
            if (parent == null) {
                throw new DomainException("Task list item with id=" + parentId + " doesn't exist");
            }
            taskListItem.setTaskList(parent);
            parent.getTaskListItems().add(taskListItem);
            session.save(taskListItem);
            session.update(taskListItem);
            return taskListItem;
        });
    }

    public TaskListItem updateTaskListItem(TaskListItem taskListItem) {

        return HibernateUtil.doInTransaction(session -> {
            TaskListItem loadedItem = (TaskListItem) session.get(TaskListItem.class, taskListItem.getId());
            if(loadedItem == null) {
                throw new DomainException("TaskListItem with id=" + taskListItem.getId() + " doesn't exist");
            }
            loadedItem.setDescription(taskListItem.getDescription());
            loadedItem.setName(taskListItem.getName());
            session.update(loadedItem);
            return loadedItem;
        });

    }

    public boolean deleteTaskListItem(Integer id) {

        return HibernateUtil.doInTransaction(session -> {

            TaskListItem taskListItem = (TaskListItem) session.get(TaskListItem.class, id);
            TaskList taskList = taskListItem.getTaskList();
            taskList.getTaskListItems().remove(taskListItem);
            session.update(taskList);
            session.delete(taskListItem);
            return true;
        });
    }
}

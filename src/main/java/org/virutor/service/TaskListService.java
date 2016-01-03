package org.virutor.service;

import org.springframework.util.StringUtils;
import org.virutor.DomainException;
import org.virutor.TaskList;
import org.virutor.db.HibernateUtil;

import java.util.List;

public class TaskListService {

    public List<TaskList> getAllTaskLists() {

        return HibernateUtil.doInTransaction(session -> {
            List<TaskList> ret = session.createCriteria(TaskList.class).list();
            session.clear();
            return ret;
        });
    }

    public TaskList createTaskList(TaskList taskList) {

        return HibernateUtil.doInTransaction(session -> {
            if (StringUtils.isEmpty(taskList.getName())) {
                throw new DomainException("Task list name cannot be empty");
            }
            session.save(taskList);
            return taskList;
        });
    }


    public TaskList updateTaskList(TaskList taskList) {

        return HibernateUtil.doInTransaction(session -> {
            TaskList persistentTaskList = (TaskList)session.get(TaskList.class, taskList.getId());
            if(persistentTaskList == null) {
                throw new DomainException("Task List with id=" + taskList.getId() + "doesn't exist");
            }
            persistentTaskList.setName(taskList.getName());
            session.update(persistentTaskList);
            return persistentTaskList;
        });
    }

    public boolean deleteTaskList(Integer id) {

        return HibernateUtil.doInTransaction(session -> {
            TaskList tl = (TaskList) session.load(TaskList.class, id);
            session.delete(tl);
            return true;
        });
    }

}

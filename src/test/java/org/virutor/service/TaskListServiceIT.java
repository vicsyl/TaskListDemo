package org.virutor.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.virutor.domain.DataUtil;
import org.virutor.TaskList;
import org.virutor.db.HibernateTestUtil;

import java.util.ArrayList;
import java.util.List;

public class TaskListServiceIT {

    private TaskListService taskListService;

    @Before
    public void createInstanceAndDB() {

        taskListService = new TaskListService();
        HibernateTestUtil.create();
    }

    private List<TaskList> createAndSaveTestData(int number) {

        List<TaskList> taskLists = new ArrayList<>();
        for(TaskList taskList : DataUtil.createTestData(number)) {
            TaskList newTaskList = taskListService.createTaskList(taskList);
            taskLists.add(newTaskList);
        }
        return taskLists;
    }

    @Test
    public void testCreateTaskList() {

        final String name = "Task list to create";
        TaskList taskList = taskListService.createTaskList(DataUtil.createTaskList(name));
        Assert.assertEquals(name, taskList.getName());
        Assert.assertEquals(0, taskList.getTaskListItems().size());
    }

    @Test
    public void testUpdateTaskList() {

        TaskList taskList = taskListService.createTaskList(DataUtil.createTaskList("foo"));

        final String newName = "Updated name";
        taskList.setName(newName);
        taskListService.updateTaskList(taskList);

        List<TaskList> list = taskListService.getAllTaskLists();
        Assert.assertEquals(1, list.size());

        TaskList actual = list.get(0);
        Assert.assertEquals(actual.getId(), taskList.getId());
        Assert.assertEquals(actual.getName(), taskList.getName());
        Assert.assertEquals(0, taskList.getTaskListItems().size());

    }


    @Test
    public void testDeleteTaskList() {

        TaskList taskList = taskListService.createTaskList(DataUtil.createTaskList("foo"));
        taskListService.deleteTaskList(taskList.getId());
        Assert.assertEquals(0, taskListService.getAllTaskLists().size());
    }

    @Test
    public void testGetAll() {

        createAndSaveTestData(5);
        List<TaskList> actualTaskLists = taskListService.getAllTaskLists();
        List<TaskList> expectedTaskLists = DataUtil.createTestData(5);

        Assert.assertEquals(expectedTaskLists.size(), actualTaskLists.size());
        for (int i = 0; i < expectedTaskLists.size(); i++) {
            TaskList expected = expectedTaskLists.get(i);
            TaskList actual = actualTaskLists.get(i);
            Assert.assertEquals(expected.getName(), actual.getName());
            Assert.assertEquals(0, actual.getTaskListItems().size());
        }

    }

    @After
    public void cleanupDB() {
        HibernateTestUtil.shutdown();
    }

}

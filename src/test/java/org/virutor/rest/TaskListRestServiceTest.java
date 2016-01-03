package org.virutor.rest;

import mockit.Expectations;
import mockit.Mocked;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.virutor.domain.DataUtil;
import org.virutor.TaskList;
import org.virutor.service.TaskListService;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class TaskListRestServiceTest {

    private TaskListRestService taskListRestService;

    @SuppressWarnings("unused")
    @Mocked
    private TaskListService taskListService;

    @Before
    public void createInstance() {
        taskListRestService = new TaskListRestService();
        taskListRestService.setTaskListService(taskListService);
    }

    @Test
    public void getAllTest() {

        List<TaskList> data = DataUtil.createTestData(5);

        new Expectations() {{
            taskListService.getAllTaskLists();
            result = data;
        }};

        Map<String, List<TaskList>> actual = taskListRestService.getAllTaskLists();

        Assert.assertEquals(1, actual.size());
        Assert.assertEquals(data, actual.get("TaskLists"));
    }

}

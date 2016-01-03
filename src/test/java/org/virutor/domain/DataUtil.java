package org.virutor.domain;

import org.virutor.TaskList;

import java.util.ArrayList;
import java.util.List;

public class DataUtil {

    public static TaskList createTaskList(String name) {
        TaskList taskList = new TaskList();
        taskList.setName(name);
        return taskList;
    }

    public static List<TaskList> createTestData(int number) {
        List<TaskList> ret = new ArrayList<>();
        for(int i = 0; i < number; i++) {
            ret.add(createTaskList("Task no. " + (i + 1)));
        }
        return ret;
    }
}

package org.virutor.rest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.virutor.DomainException;
import org.virutor.TaskList;
import org.virutor.TaskListItem;
import org.virutor.service.TaskListItemService;
import org.virutor.service.TaskListService;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Path("/taskLists")
public class TaskListRestService {

    private static final Logger logger = LogManager.getLogger(TaskListRestService.class);

    private TaskListService taskListService;

    @Autowired
    public void setTaskListService(TaskListService taskListService) {
        this.taskListService = taskListService;
    }

    private TaskListItemService taskListItemService;

    @SuppressWarnings("unused")
    @Autowired
    public void setTaskListItemService(TaskListItemService taskListItemService) {
        this.taskListItemService = taskListItemService;
    }

    private static int getIntOrFail(String id) {
        try {
            return Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new WebApplicationException(String.format("Id=%s% cannot be parsed as ID", id), HttpURLConnection.HTTP_BAD_REQUEST);
        }
    }

    private static <T> T wrapForThrow(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (DomainException e) {
            logger.error("Exception caught ", e);
            throw new WebApplicationException(e.getMessage(), HttpURLConnection.HTTP_BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Exception caught ", e);
            throw new WebApplicationException("Opps, something went wrong", HttpURLConnection.HTTP_INTERNAL_ERROR);
        }
    }

    @GET
    @Path("/all")
    @Produces("application/json")
    public Map<String, List<TaskList>> getAllTaskLists() {
        return wrapForThrow(() -> RestUtils.wrapObject(taskListService.getAllTaskLists(), "TaskLists"));
    }

    @POST
    @Path("/")
    @Produces("application/json")
    @Consumes("application/json")
    public Map<String, TaskList> createTaskList(TaskList taskList) {
        return wrapForThrow(() -> RestUtils.wrapObject(taskListService.createTaskList(taskList)));
    }

    @PUT
    @Path("/")
    @Produces("application/json")
    @Consumes("application/json")
    public Map<String, TaskList> updateTaskList(TaskList taskList) {
        return wrapForThrow(() -> RestUtils.wrapObject(taskListService.updateTaskList(taskList)));
    }

    @DELETE
    @Path("/{id}")
    @Produces("application/json")
    public void removeTaskList(@PathParam("id") String id) {
        int i = getIntOrFail(id);
        wrapForThrow(() -> taskListService.deleteTaskList(i));
    }

    @GET
    @Path("/{id}/all")
    @Produces("application/json")
    public Map<String, List<TaskListItem>> getAllTaskItems(@PathParam("id") String id) {

        int i = getIntOrFail(id);
        return wrapForThrow(() -> {
            List<TaskListItem> items = taskListItemService.getAllItems(i);
            return RestUtils.wrapObject(items, "TaskListItems");
        });
    }

    @POST
    @Path("/{id}")
    @Produces("application/json")
    @Consumes("application/json")
    public Map<String, TaskListItem> createTaskListItem(@PathParam("id") String id, TaskListItem taskListItem) {
        int i = getIntOrFail(id);
        return wrapForThrow(() -> RestUtils.wrapObject(taskListItemService.createTaskListItem(taskListItem, i)));
    }

    @PUT
    @Path("/{id}")
    @Produces("application/json")
    @Consumes("application/json")
    public Map<String, TaskListItem> updateTaskListItem(@PathParam("id") String id, TaskListItem taskListItem) {
        return wrapForThrow(() -> RestUtils.wrapObject(taskListItemService.updateTaskListItem(taskListItem), "TaskListItem"));
    }

    @DELETE
    @Path("/{id1}/{id2}")
    @Produces("application/json")
    public void removeTaskListItem(@PathParam("id1") String forget, @PathParam("id2") String id) {
        int i = getIntOrFail(id);
        wrapForThrow(() -> taskListItemService.deleteTaskListItem(i));
    }
}

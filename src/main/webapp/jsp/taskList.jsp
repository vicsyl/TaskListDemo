<!DOCTYPE html>
<html>
<head>
<title>Task List Detail</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8" />

<!-- Keeping these resources locally will allow us to run completely offline on localhost, however for production scenario
     we might want to use these online urls:

        <link href="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/css/bootstrap-combined.min.css" rel="stylesheet">
        <script src="http://ajax.aspnetcdn.com/ajax/jquery/jquery-1.9.0.js"></script>
        <script src="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/js/bootstrap.min.js"></script>
        <script src="http://ajax.aspnetcdn.com/ajax/knockout/knockout-2.2.1.js"></script>
 -->
<link href="../bootstrap-combined.min.css" rel="stylesheet">
<script src="../jquery-1.9.0.js"></script>
<script src="../bootstrap.min.js"></script>
<script src="../knockout-2.2.1.js"></script>
</head>
<body>

    <div class="navbar">
        <div class="navbar-inner">
            <a class="brand" href="../../">Back to Task List Dashboard</a>
        </div>
    </div>
    <p/>
    <div class="navbar">
        <div class="navbar-inner">
            <a class="brand" href="#">Task List Detail</a>
        </div>
    </div>
    <div id="main" class="container">
        <table class="table table-striped">
            <tr><td><b><i>Item Name</i></b></td><td><b><i>Item Description</i></b></td><td><b><i>Options</i></b></td></tr>
            <!-- ko foreach: taskListItems -->
            <tr>
                <td><p><b data-bind="text: name"></b></p></td>
                <td><p><b data-bind="text: description"></b></p></td>
                <td>
                    <button data-bind="click: $parent.beginEdit" class="btn">Edit</button>
                    <button data-bind="click: $parent.remove" class="btn">Delete</button>
                </td>
            </tr>
            <!-- /ko -->
        </table>
        <button data-bind="click: beginAdd" class="btn">Create Task List Item</button>
    </div>


    <div id="create" class="modal hide fade" tabindex="=1" role="dialog" aria-labelledby="createDialogLabel" aria-hidden="true">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h3 id="createDialogLabel">Crate Task List Item</h3>
        </div>
        <div class="modal-body">
            <form class="form-horizontal">
                <div class="control-group">
                    <label class="control-label" for="inputTaskListItem">Task List Item</label>
                    <div class="controls">
                        <input data-bind="value: name" type="text" id="inputTaskListItem" placeholder="Task List Item name" style="width: 150px;">
                        <input data-bind="value: description" type="text" id="inputTaskListItem" placeholder="Task List Item description" style="width: 150px;">
                    </div>
                </div>
            </form>
        </div>
        <div class="modal-footer">
            <button data-bind="click: createTaskListItem" class="btn btn-primary">Create Task List Item</button>
            <button class="btn" data-dismiss="modal" aria-hidden="true">Cancel</button>
        </div>
    </div>
    <div id="edit" class="modal hide fade" tabindex="=1" role="dialog" aria-labelledby="editDialogLabel" aria-hidden="true">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h3 id="editDialogLabel">Edit Task List Item</h3>
        </div>
        <div class="modal-body">
            <form class="form-horizontal">
                <div class="control-group">
                    <label class="control-label" for="inputTaskListItem">Task List Item</label>
                    <div class="controls">
                        <input data-bind="value: name" type="text" id="inputTaskListItem" placeholder="Task List Item name" style="width: 150px;">
                        <input data-bind="value: description" type="text" id="inputTaskListItem" placeholder="Task List Item description" style="width: 150px;">
                    </div>
                </div>
            </form>
        </div>
        <div class="modal-footer">
            <button data-bind="click:editTaskListItem" class="btn btn-primary">Update Task List Item</button>
            <button class="btn" data-dismiss="modal" aria-hidden="true">Cancel</button>
        </div>
    </div>
    <script type="text/javascript">

        findAllURI = 'http://localhost:8080/rest<%=request.getRequestURI()%>/all';
        createURI = 'http://localhost:8080/rest<%=request.getRequestURI()%>';
        updateURI = 'http://localhost:8080/rest<%=request.getRequestURI()%>';
        deleteURI = 'http://localhost:8080/rest<%=request.getRequestURI()%>';

        function TaskListItemsViewModel() {
            var self = this;
            self.taskListItems = ko.observableArray();

            self.ajax = function(uri, method, data) {
                var request = {
                    url: uri,
                    type: method,
                    contentType: "application/json",
                    cache: true,
                    dataType: 'json',
                    data: JSON.stringify(data),
                    error: function(jqXHR) {
                        console.error("ajax error " + jqXHR.responseText);
                        alert(jqXHR.responseText);
                    }
                };
                return $.ajax(request);
            }

            self.ajaxNoContent = function(uri, method, data) {
                var request = {
                    url: uri,
                    type: method,
                    contentType: "application/json",
                    cache: true,
                    data: JSON.stringify(data),
                    error: function(jqXHR) {
                        console.error("ajax error " + jqXHR.responseText);
                        alert(jqXHR.responseText);
                    }
                };
                return $.ajax(request);
            }

            self.updateTaskListItem = function(taskListItem, newTaskListItem) {
                var i = self.taskListItems.indexOf(taskListItem);
                self.taskListItems()[i].name(newTaskListItem.name);
                self.taskListItems()[i].description(newTaskListItem.description);
            }

            self.beginAdd = function() {
                $('#create').modal('show');
            }
            self.create = function(taskListItem) {
                self.ajax(createURI, 'POST', taskListItem).done(function(data) {
                    self.taskListItems.push({
                        id:   ko.observable(data.TaskListItem.id),
                        name: ko.observable(data.TaskListItem.name),
                        description: ko.observable(data.TaskListItem.description),
                    });
                });
            }
            self.beginEdit = function(taskListItem) {
                editTaskListItemViewModel.setTaskListItem(taskListItem);
                $('#edit').modal('show');
            }
            self.edit = function(taskListItem, data) {
                self.ajax(updateURI, 'PUT', data).done(function(res) {
                    self.updateTaskListItem(taskListItem, res.TaskListItem);
                });
            }
            self.remove = function(taskListItem) {
                self.ajaxNoContent(deleteURI + "/" + taskListItem.id(), 'DELETE').done(function() {
                    self.taskListItems.remove(taskListItem);
                });
            }

            self.ajax(findAllURI, 'GET').done(function(data) {
                for (var i = 0; i < data.TaskListItems.length; i++) {
                    self.taskListItems.push({
                        id: ko.observable(data.TaskListItems[i].id),
                        name: ko.observable(data.TaskListItems[i].name),
                        description: ko.observable(data.TaskListItems[i].description),
                    });
                }
            });
        }
        function CreateTaskListItemViewModel() {
            var self = this;
            self.id = ko.observable();
            self.name = ko.observable();
            self.description = ko.observable();

            self.createTaskListItem = function() {
                if(self.name() == '') {
                    alert("Name cannot be empty");
                } else {
                    $('#create').modal('hide');
                    taskListItemsViewModel.create({
                        name: self.name(),
                        description: self.description(),
                    });
                    self.name("");
                    self.description("");
                }
            }
        }
        function EditTaskListItemViewModel() {
            var self = this;
            self.name = ko.observable();
            self.description = ko.observable();
            self.id = ko.observable();

            self.setTaskListItem = function(taskListItem) {
                self.taskListItem = taskListItem;
                self.name(taskListItem.name());
                self.description(taskListItem.description());
                self.id(taskListItem.id());
                $('edit').modal('show');
            }

            self.editTaskListItem = function() {
                if(self.name() == '') {
                    alert("Name cannot be empty");
                } else {
                    $('#edit').modal('hide');
                    taskListItemsViewModel.edit(self.taskListItem, {
                        name: self.name(),
                        description: self.description(),
                        id: self.id(),
                    });
                }
            }
        }

        var taskListItemsViewModel = new TaskListItemsViewModel();
        var createTaskListItemViewModel = new CreateTaskListItemViewModel();
        var editTaskListItemViewModel = new EditTaskListItemViewModel();
        ko.applyBindings(taskListItemsViewModel, $('#main')[0]);
        ko.applyBindings(createTaskListItemViewModel, $('#create')[0]);
        ko.applyBindings(editTaskListItemViewModel, $('#edit')[0]);
    </script>
</body>
</html>


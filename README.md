# TeskListDemo

An attempt to a demo web application for maintaining task lists. The main requirements were:

* REST API
* In-memory DB
* Simple in design, simple to build & run

##Usage

* build: **mvn clean package**  -> builds war archive
* build and run: **mvn clean jetty:run**  -> runs the app within embedded jetty, the app starts on localhost:8080 

## App URLS 
* http://localhost:8080{/index.html} - entry point AKA Task List Dashboard
* http://localhost:8080/taskLists/{id} - detail of the given task list 
 
## REST API

Test 
* http://localhost:8080/rest/test/do - testing endpoint that generates testing data upon GET request

Task list
* http://localhost:8080/rest/taskLists/all - GET - all task lists
* http://localhost:8080/rest/taskLists/{id} - DELETE - deletes given task list, responds with 204 no content 
* http://localhost:8080/rest/taskLists/ - PUT - updates given task list, responds with the updated task list
* http://localhost:8080/rest/taskLists/ - POST - creates a new task list, responds with the created task list

Task list items
* http://localhost:8080/rest/taskLists/{id} - GET - get given task list's detail (list of task list items)
* http://localhost:8080/rest/taskLists/{id}/{id2} - DELETE - deletes given task list item, responds with 204 no content
* http://localhost:8080/rest/taskLists/{id} - PUT - updates given task list item for a given task list, responds with the updated task list item
* http://localhost:8080/rest/taskLists/{id} - POST - creates a new task list for a given task list, responds with the created task list item


##Note
The jsp page and the static html page was written based the page that can be found at: https://github.com/miguelgrinberg/REST-tutorial. 



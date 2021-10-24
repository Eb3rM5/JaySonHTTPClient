# JaySonHTTPClient
Simple Java HTTP Client with JSON data binding

### Goals
- Simplify the use of HTTP methods (POST, GET, PUT, DELETE) within a Java application.
- Use data binding to remove the necessity of manually mapping all the fields of a JSON response to a Java object.

### Example
Given the following `record`:
```java
@JsonIgnoreProperties(ignoreUnknown = true)
public record GitHubUser (int id, String login, String name){
}
```

If we make a HTTP call through JaySon client, like this one:
```java
var client = new JaySonHTTPClient(HttpClients.createDefault());
var user = client.get("https://api.github.com/users/Eb3rM5", GitHubUser.class);
System.out.printf("The user %s of ID %s has the following name: %s", user.login(), user.id(), user.name());
```

The output will be:
```
The user Eb3rM5 of ID 5719885 has the following name: Éber Mainardes
```

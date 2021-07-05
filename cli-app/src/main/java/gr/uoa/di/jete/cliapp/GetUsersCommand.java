package gr.uoa.di.jete.cliapp;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import gr.uoa.di.jete.models.User;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;


@ShellComponent
public class GetUsersCommand {

    User user = null;
    String username = null;
    String bearerToken = null;

    @Autowired
    ShellHelper shellHelper;

//    @Autowired
//    Gson gson;


    String email;
    String tokenType;
    String id;

    Long epic_id = null;
    Long sprint_id = null;

    String url = "http://localhost:8080";


    @ShellMethod("Register")
    public void Register() {
        if (this.username != null) {
            System.out.println(shellHelper.getErrorMessage("Please Logout"));
            return;
        }

        Console console = System.console();

        System.out.println(shellHelper.getInfoMessage("Registering new user"));

        Scanner usrInput = new Scanner(System.in);
        String username = null;

        while (username == null || username.length() == 0) {
            System.out.print("Enter Username:");
            username = usrInput.nextLine();
        }
        String password = null, matchingPassword =null;

        while(password == null || password.length()==0 || !password.equals(matchingPassword)) {
            password = new String(console.readPassword("Enter Password: "));
            matchingPassword = new String(console.readPassword("Enter Same Password: "));
            if(password.length() == 0)
                System.out.println(shellHelper.getErrorMessage("Password should not be empty"));
            else if(!password.equals(matchingPassword))
                System.out.println(shellHelper.getErrorMessage("Password should be the same"));

        }

        String email = null;
        while (email == null || email.isEmpty()) {
            System.out.print("Enter Email:");
            email = usrInput.nextLine();
        }

        String firstname = null;
        while (firstname == null || firstname.isEmpty()) {
            System.out.print("Enter FirstName:");
            firstname = usrInput.nextLine();
        }

        String lastname = null;
        while(lastname == null || lastname.isEmpty()) {
            System.out.print("Enter LastName:");
            lastname = usrInput.nextLine();
        }



        JsonObject object = new JsonObject();
        object.addProperty("username",username);
        object.addProperty("password",password);
        object.addProperty("matchingPassword",matchingPassword);
        object.addProperty("email",email);
        object.addProperty("firstname",firstname);
        object.addProperty("lastname",username);


        //Post Request
        try (CloseableHttpClient client = HttpClients.createDefault()) {

            String url = this.url + "/signup";

            CloseableHttpResponse response = HttpFunctions.Post(url,object);
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300)  {
                String responseBody = EntityUtils.toString(response.getEntity());
                JsonObject responseObject = JsonParser.parseString(responseBody).getAsJsonObject();
                System.out.println("-----------------------------------------------------");
                String res = responseObject.get("message").getAsString();
                if(res.contains("User registered successfully!")){
                    System.out.println(res+"\nEmai Was Send!\nPlease Activate your account");
                }
            }
            else if(status == 500){
                String responseBody = EntityUtils.toString(response.getEntity());
                JsonObject responseObject = JsonParser.parseString(responseBody).getAsJsonObject();
                System.out.println("-----------------------------------------------------");
                String res = responseObject.get("message").getAsString();
                System.out.println(shellHelper.getErrorMessage(res));
            }
            else {
                throw new ClientProtocolException("Unexpected response status: " + status);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @ShellMethod("User's Logout")
    public void Logout(){
        if(this.username == null) {
            System.out.println(shellHelper.getErrorMessage("Please Login"));
        }
        else {
            Scanner u = new Scanner(System.in);
            System.out.print(shellHelper.getInfoMessage("Are you sure you want to logout: (type yes/y)"));
            String answer = u.nextLine();
            answer = answer.toLowerCase(Locale.ROOT);
            if(answer.equals("yes") || answer.equals("y")){
                System.out.println(shellHelper.getSuccessMessage("Logged Out"));
                this.username = null;
                this.user = null;
                this.email = null;
                this.tokenType = null;
                this.bearerToken = null;
                this.id = null;
            }
            else {
                System.out.println(shellHelper.getSuccessMessage("Didn't log out"));
            }
        }
    }


    @ShellMethod("User's Login")
    public void Login() throws IOException {

        if(this.username != null){
            System.out.println(shellHelper.getErrorMessage("Please Logout"));
        }else {

            Console console = System.console();
            Scanner usrInput = new Scanner(System.in);

            String username = null;
            while (username == null || username.length() == 0) {
                System.out.print("Enter Username:");
                username = usrInput.nextLine();
            }
            String password = null;

            while(password == null || password.length()==0) {
                password = new String(console.readPassword("Enter Password: "));
                if(password.length() == 0)
                    System.out.println(shellHelper.getErrorMessage("Password should not be empty"));
            }

            JsonObject object = new JsonObject();
            object.addProperty("username",username);
            object.addProperty("password",password);

            String url = this.url + "/signin";

            CloseableHttpResponse response = HttpFunctions.Post(url,object);

            String responseString = EntityUtils.toString(response.getEntity());

            JsonObject responseObject = JsonParser.parseString(responseString).getAsJsonObject();

            //Check if response was 404, then user was not found
            if (!(response.getStatusLine().getStatusCode() >= 200 && response.getStatusLine().getStatusCode() < 300)) {
                String out = shellHelper.getErrorMessage("User with username: " + username + " does not exist");
                out = out.concat(shellHelper.getInfoMessage("\nIf you are using first time jete_app please register"));
                System.err.println(out);
            }
            else{
                // Get the response

                this.id = responseObject.get("id").getAsString();
                this.bearerToken = responseObject.get("accessToken").getAsString();
                this.tokenType = responseObject.get("tokenType").getAsString();
                this.username = responseObject.get("username").getAsString();
                this.email = responseObject.get("email").getAsString();

                System.out.println(shellHelper.getSuccessMessage("Welcome back " + username + "!"));
            }
        }

    }

    @ShellMethod("Update's User")
    public void update() throws IOException {

        if(username == null){
            System.out.println(shellHelper.getErrorMessage("Please Login"));
            return;
        }

        System.out.println(shellHelper.getInfoMessage("Updating user's info, if you do not want to change it, just press enter"));

        Scanner usrInput = new Scanner(System.in);
        System.out.print("Enter new Username:");
        String userName = usrInput.nextLine();

        System.out.print("Enter new Email:");
        String email = usrInput.nextLine();

        System.out.print("Enter new Bio:");
        String bio = usrInput.nextLine();

        System.out.print("Enter new Location:");
        String location = usrInput.nextLine();

        System.out.print("Enter new FirstName:");
        String firstname = usrInput.nextLine();

        System.out.print("Enter new LastName:");
        String lastname = usrInput.nextLine();

        JsonObject object = null;

        if(userName.length()!=0) {
            object = new JsonObject();
            object.addProperty("username",userName);
        }
        if(email.length()!=0) {
            if(object == null)
                object = new JsonObject();
            object.addProperty("email",email);
        }

        if(bio.length()!=0){
            if(object == null)
                object = new JsonObject();
            object.addProperty("bio",bio);
        }

        if(location.length()!=0){
            if(object == null)
                object = new JsonObject();
            object.addProperty("location",location);
        }

        if(firstname.length()!=0){
            if(object == null)
                object = new JsonObject();
            object.addProperty("firstName",firstname);
        }


        if(lastname.length()!=0){
            if(object == null)
                object = new JsonObject();
            object.addProperty("lastName",lastname);
        }

        //Put Request
        if(object == null){
            System.out.println("Nothing was updated");
            return;
        }
        try(CloseableHttpClient client = HttpClients.createDefault()) {

            String url = this.url + "/users/" + this.id;
            HttpResponse response = HttpFunctions.Put(url,object,this.tokenType,this.bearerToken);

            int status = response.getStatusLine().getStatusCode();

            if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                String respBody = EntityUtils.toString(entity);
                System.out.println("-----------------------------------------------------");
                System.out.println("UPDATED USER:");
                JsonObject responseObject = JsonParser.parseString(respBody).getAsJsonObject();
                printUser(responseObject);
            } else {
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
        }
    }


    @ShellMethod("Displays user's info")
    public void getInfo(){
        if(this.username == null) {
            System.out.println(shellHelper.getErrorMessage("Please Login"));
        }
        else {
            String url = this.url + "/users/name=" + username;
            HttpResponse response = null;
            try {
                response = HttpFunctions.Get(url,this.tokenType,this.bearerToken);
                int status = response.getStatusLine().getStatusCode();

                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    String respBody = EntityUtils.toString(entity);
                    System.out.println("-----------------------------------------------------");
                    System.out.println("User's Info:");
                    JsonObject responseObject = JsonParser.parseString(respBody).getAsJsonObject();
                    printUser(responseObject);
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    @ShellMethod("Activate profile by pasting code from email")
    public void activateUser(){
        Scanner usrInput = new Scanner(System.in);

        String username = null;
        while (username == null || username.length() == 0) {
            System.out.print("Enter Username:");
            username = usrInput.nextLine();
        }
        String code = null;
        while (code == null || code.length() == 0) {
            System.out.print("Enter Verification code:");
            code = usrInput.nextLine();
        }

        String url = this.url + "/verify/code=" + code + "&u=" + username;
        HttpResponse response = null;
        try {
            response = HttpFunctions.Get(url);
            int status = response.getStatusLine().getStatusCode();

            if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                String respBody = EntityUtils.toString(entity);
                System.out.println("-----------------------------------------------------");
                JsonObject responseObject = JsonParser.parseString(respBody).getAsJsonObject();
                System.out.println(shellHelper.getInfoMessage(responseObject.get("message").getAsString()));
            } else {
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @ShellMethod("Create new Project")
    public void createProject(){
        if(this.username == null) {
            System.out.println(shellHelper.getErrorMessage("Please Login"));
            return;
        }

        String url = this.url + "/projects/create/" + this.id;
        Scanner usrInput = new Scanner(System.in);

        String title = null;
        while (title == null || title.length() == 0) {
            System.out.print("Enter title:");
            title = usrInput.nextLine();
        }
        String description = null;
        while (description == null || description.length() == 0) {
            System.out.print("Enter Description:");
            description = usrInput.nextLine();
        }


        JsonObject object = new JsonObject();
        object.addProperty("title",title);
        object.addProperty("description",description);
        object.addProperty("status",0);

        HttpResponse response = null;
        try {
            response = HttpFunctions.Post(url,object,tokenType,bearerToken);
            int status = response.getStatusLine().getStatusCode();

            if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                String respBody = EntityUtils.toString(entity);
                System.out.println("-----------------------------------------------------");
                JsonObject responseObject = JsonParser.parseString(respBody).getAsJsonObject();
                System.out.println("Project with title: " + shellHelper.getSuccessMessage(responseObject.get("title").getAsString()) +  " was created!");
            } else {
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @ShellMethod("Create new Epic")
    public void createEpic(){
        if(this.username == null) {
            System.out.println(shellHelper.getErrorMessage("Please Login"));
            return;
        }

        Long project_id = getProjectforEpic();

        if(project_id == -1L){
            System.out.println(shellHelper.getErrorMessage("Please Create a Project first"));
            return;
        }

        String url = this.url + "/projects/epics/create";


        Scanner usrInput = new Scanner(System.in);

        String title = null;
        while (title == null || title.length() == 0) {
            System.out.print("Enter title:");
            title = usrInput.nextLine();
        }
        String description = null;
        while (description == null || description.length() == 0) {
            System.out.print("Enter Description:");
            description = usrInput.nextLine();
        }


        JsonObject object = new JsonObject();
        object.addProperty("title",title);
        object.addProperty("description",description);
        object.addProperty("status",0);
        object.addProperty("project_id",project_id);


        HttpResponse response = null;
        try {
            response = HttpFunctions.Post(url,object,tokenType,bearerToken);
            int status = response.getStatusLine().getStatusCode();

            if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                String respBody = EntityUtils.toString(entity);
                System.out.println("-----------------------------------------------------");
                JsonObject responseObject = JsonParser.parseString(respBody).getAsJsonObject();
                System.out.println("Epic with title: " + shellHelper.getSuccessMessage(responseObject.get("title").getAsString()) +  " was created!");
            } else {
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @ShellMethod("Create new Story")
    public void createStory(){
        if(this.username == null) {
            System.out.println(shellHelper.getErrorMessage("Please Login"));
            return;
        }

        Long project_id = getProjectforEpic();

        if(project_id == -1L){
            System.out.println(shellHelper.getErrorMessage("Please Create a Project first"));
            return;
        }
        
        
        Long epic_id = getEpicforStory(project_id);
        
        if(epic_id == -1L){
            System.out.println(shellHelper.getErrorMessage("Please Create an Epic first"));
            return;
        }
        
        Long sprint_id = getSprintforStory(project_id);

        if(sprint_id == -1L) {
            System.out.println(shellHelper.getErrorMessage("Please Create an Epic first"));
            return;
        }


        String url = this.url + "/projects/" + project_id + "/sprints&epics/" + sprint_id +
                "&" + epic_id + "/stories/create";

        Scanner usrInput = new Scanner(System.in);

        String title = null;
        while (title == null || title.length() == 0) {
            System.out.print("Enter title:");
            title = usrInput.nextLine();
        }
        String description = null;
        while (description == null || description.length() == 0) {
            System.out.print("Enter Description:");
            description = usrInput.nextLine();
        }


        JsonObject object = new JsonObject();
        object.addProperty("title",title);
        object.addProperty("description",description);
        object.addProperty("status",0);
        object.addProperty("project_id",project_id);
        object.addProperty("epic_id",epic_id);
        object.addProperty("sprint_id",sprint_id);



        HttpResponse response = null;
        try {
            response = HttpFunctions.Post(url,object,tokenType,bearerToken);
            int status = response.getStatusLine().getStatusCode();

            if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                String respBody = EntityUtils.toString(entity);
                System.out.println("-----------------------------------------------------");
                JsonObject responseObject = JsonParser.parseString(respBody).getAsJsonObject();
                System.out.println("Story with title: " + shellHelper.getSuccessMessage(responseObject.get("title").getAsString()) +  " was created!");
            } else {
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @ShellMethod("Create new Task")
    public void createTask(){
        if(this.username == null) {
            System.out.println(shellHelper.getErrorMessage("Please Login"));
            return;
        }

        Long project_id = getProjectforEpic();

        if(project_id == -1L){
            System.out.println(shellHelper.getErrorMessage("Please Create a Project first"));
            return;
        }

        Long story_id;

        story_id = getStoryforTask(project_id);

        if(story_id == -1L){
            System.out.println(shellHelper.getErrorMessage("Please Create a story first"));
            return;
        }


        String url = this.url + "/projects/" + project_id +
                "/sprints&epics/" + sprint_id + "&" + epic_id + "/stories/"
                + story_id + "/tasks/create";

        Scanner usrInput = new Scanner(System.in);

        String title = null;
        while (title == null || title.length() == 0) {
            System.out.print("Enter title:");
            title = usrInput.nextLine();
        }
        String description = null;
        while (description == null || description.length() == 0) {
            System.out.print("Enter Description:");
            description = usrInput.nextLine();
        }


        JsonObject object = new JsonObject();
        object.addProperty("title",title);
        object.addProperty("description",description);
        object.addProperty("status",0);
        object.addProperty("project_id",project_id);
        object.addProperty("epic_id",epic_id);
        object.addProperty("sprint_id",sprint_id);
        object.addProperty("story_id",story_id);



        HttpResponse response = null;
        try {
            response = HttpFunctions.Post(url,object,tokenType,bearerToken);
            int status = response.getStatusLine().getStatusCode();

            if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                String respBody = EntityUtils.toString(entity);
                System.out.println("-----------------------------------------------------");
                JsonObject responseObject = JsonParser.parseString(respBody).getAsJsonObject();
                System.out.println("Task with title: " + shellHelper.getSuccessMessage(responseObject.get("title").getAsString()) +  " was created!");
            } else {
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @ShellMethod("Preview my Projects->Epics->Sprints->Stories->Tasks")
    public void preview() {
        if (this.username == null) {
            System.out.println(shellHelper.getErrorMessage("Please Login"));
            return;
        }

        long project_id = 0;
        long story_id = 0;

        //Get All Projects of user
        String url = this.url + "/users/" + this.id + "/projects";
        Scanner usrInput = new Scanner(System.in);
        HttpResponse response;

        try {
            response = HttpFunctions.Get(url, tokenType, bearerToken);
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                String respBody = EntityUtils.toString(entity);
                JsonObject responseObject = JsonParser.parseString(respBody).getAsJsonObject();
                JsonArray array = responseObject.get("_embedded").getAsJsonObject().get("projectList").getAsJsonArray();
                List<Long> prids = new ArrayList<>();
                for (int i = 0; i < array.size(); i++) {
                    JsonObject object = array.get(i).getAsJsonObject();
                    Long prid = print(object, "Project");
                    if (prid != -1L)
                        prids.add(prid);
                }
                if (prids.isEmpty())
                    return;
                while (true) {
                    System.out.print("Type Project id to expand (press enter to exit): ");
                    String prid = usrInput.nextLine();
                    if (prid.isEmpty() || prid.equals("\n"))
                        return;
                    if (prids.contains(Long.parseLong(prid))) {
                        project_id = Long.parseLong(prid);
                        break;
                    }
                }
            } else {
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.print("Type 1 to expant to epics, type 2 to expand to sprints: ");
        int geter = Integer.parseInt(usrInput.nextLine());

        //Get Epics
        if (geter == 1) {
            url = this.url + "/projects/" + project_id + "/epics";
            try {
                response = HttpFunctions.Get(url, tokenType, bearerToken);
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    String respBody = EntityUtils.toString(entity);
                    JsonObject responseObject = JsonParser.parseString(respBody).getAsJsonObject();
                    JsonArray array = responseObject.get("_embedded").getAsJsonObject().get("epicList").getAsJsonArray();
                    List<Long> epicIds = new ArrayList<>();
                    for (int i = 0; i < array.size(); i++) {
                        JsonObject object = array.get(i).getAsJsonObject();
                        Long epicId = print(object, "Epic");
                        if (epicId != -1L)
                            epicIds.add(epicId);
                    }
                    if (epicIds.isEmpty())
                        return;
                    while (true) {
                        System.out.print("Type Epic id to expand (press enter to exit): ");
                        String epicId = usrInput.nextLine();
                        if (epicId.isEmpty() || epicId.equals("\n"))
                            return;
                        if (epicIds.contains(Long.parseLong(epicId))) {
                            this.epic_id = Long.parseLong(epicId);
                            break;
                        }
                    }
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
        }

        //Get Sprints
        if(geter == 2){
            url = this.url + "/projects/" + project_id + "/sprints/active";
            try {
                response = HttpFunctions.Get(url, tokenType, bearerToken);
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    String respBody = EntityUtils.toString(entity);
                    JsonObject responseObject = JsonParser.parseString(respBody).getAsJsonObject();
                    JsonArray array = responseObject.get("_embedded").getAsJsonObject().get("sprintList").getAsJsonArray();
                    List<Long> sprintIds = new ArrayList<>() ;
                    for(int i=0;i<array.size();i++){
                        JsonObject object = array.get(i).getAsJsonObject();
                        Long sprintId = printSprint(object);
                        if(sprintId != -1L)
                            sprintIds.add(sprintId);
                    }
                    if(sprintIds.isEmpty())
                        return;
                    while(true) {
                        System.out.print("Type Sprint id to expand (press enter to exit): ");
                        String sprintId = usrInput.nextLine();
                        if (sprintId.isEmpty() || sprintId.equals("\n"))
                            return;
                        if (sprintIds.contains(Long.parseLong(sprintId))) {
                            this.sprint_id = Long.parseLong(sprintId);
                            break;
                        }
                    }
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
        }

        //Get stories
        if(geter == 1){
            url = this.url + "/projects/" + project_id + "/epics/"+epic_id+ "/stories";
            try {
                response = HttpFunctions.Get(url, tokenType, bearerToken);
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    String respBody = EntityUtils.toString(entity);
                    JsonObject responseObject = JsonParser.parseString(respBody).getAsJsonObject();
                    JsonArray array = responseObject.get("_embedded").getAsJsonObject().get("storyList").getAsJsonArray();
                    List<Long> storyIds = new ArrayList<>() ;
                    for(int i=0;i<array.size();i++){
                        JsonObject object = array.get(i).getAsJsonObject();
                        Long storyId = print(object,"Story");
                        if(storyId != -1L)
                            storyIds.add(storyId);
                    }
                    if(storyIds.isEmpty())
                        return;
                    while(true) {
                        System.out.print("Type Story id to expand (press enter to exit): ");
                        String storyId = usrInput.nextLine();
                        if (storyId.isEmpty() || storyId.equals("\n"))
                            return;
                        if (storyIds.contains(Long.parseLong(storyId))) {
                            for(int i=0;i<array.size();i++){
                                JsonObject object = array.get(i).getAsJsonObject();
                                if(object.get("id").getAsString().equals(storyId))
                                    this.sprint_id = object.get("sprint_id").getAsLong();
                            }
                            story_id = Long.parseLong(storyId);
                            break;
                        }
                    }
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(geter == 2){
            url = this.url + "/projects/" + project_id + "/sprints/"+ sprint_id + "/stories";
            try {
                response = HttpFunctions.Get(url, tokenType, bearerToken);
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    String respBody = EntityUtils.toString(entity);
                    JsonObject responseObject = JsonParser.parseString(respBody).getAsJsonObject();
                    JsonArray array = responseObject.get("_embedded").getAsJsonObject().get("storyList").getAsJsonArray();
                    List<Long> storyIds = new ArrayList<>() ;
                    for(int i=0;i<array.size();i++){
                        JsonObject object = array.get(i).getAsJsonObject();
                        Long storyId = print(object,"Story");
                        if(storyId != -1L)
                            storyIds.add(storyId);
                    }
                    if(storyIds.isEmpty())
                        return;
                    while(true) {
                        System.out.print("Type Story id to expand (press enter to exit): ");
                        String storyId = usrInput.nextLine();
                        if (storyId.isEmpty() || storyId.equals("\n"))
                            return;
                        if (storyIds.contains(Long.parseLong(storyId))) {
                            for(int i=0;i<array.size();i++){
                                JsonObject object = array.get(i).getAsJsonObject();
                                if(object.get("id").getAsString().equals(storyId))
                                    this.epic_id = object.get("epic_id").getAsLong();
                            }
                            story_id = Long.parseLong(storyId);
                            break;
                        }
                    }
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
        }


        url = this.url + "/projects/" + project_id + "/stories/"+story_id+ "/tasks";
        try {
            response = HttpFunctions.Get(url, tokenType, bearerToken);
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                String respBody = EntityUtils.toString(entity);
                JsonObject responseObject = JsonParser.parseString(respBody).getAsJsonObject();
                JsonArray array = responseObject.get("_embedded").getAsJsonObject().get("taskList").getAsJsonArray();
                for (int i = 0; i < array.size(); i++) {
                    JsonObject object = array.get(i).getAsJsonObject();
                    Long storyId = print(object, "Task");
                }
            } else {
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }


    @ShellMethod("Archive your most recent sprint")
    public void sprintArchive(){
        if(this.username == null) {
            System.out.println(shellHelper.getErrorMessage("Please Login"));
            return;
        }

        Long project_id = getProjectforEpic();

        if(project_id == -1L){
            System.out.println(shellHelper.getErrorMessage("Please Create a Project first"));
            return;
        }
        String url = this.url + "/projects/" + project_id + "/sprints/archive/" + this.id;

        HttpResponse response = null;
        try {
            response = HttpFunctions.Put(url,tokenType,bearerToken);
            int status = response.getStatusLine().getStatusCode();

            if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                String respBody = EntityUtils.toString(entity);
                System.out.println("-----------------------------------------------------");
                JsonObject responseObject = JsonParser.parseString(respBody).getAsJsonObject();
                if(responseObject.get("status").getAsLong() == 3)
                    System.out.println(shellHelper.getSuccessMessage("Sprint archived!"));
            } else {
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private Long getStoryforTask(Long project_id) {
        String url = this.url + "/projects/"+ project_id + "/sprints/active/stories";
        Scanner usrInput = new Scanner(System.in);
        HttpResponse response;
        try {
            response = HttpFunctions.Get(url, tokenType, bearerToken);
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                String respBody = EntityUtils.toString(entity);
                JsonObject responseObject = JsonParser.parseString(respBody).getAsJsonObject();
                JsonArray array = responseObject.get("_embedded").getAsJsonObject().get("storyList").getAsJsonArray();
                List<Long> storyIds = new ArrayList<>() ;
                for(int i=0;i<array.size();i++){
                    JsonObject object = array.get(i).getAsJsonObject();
                    Long storyId = print(object,"Story");
                    if(storyId != -1L)
                        storyIds.add(storyId);
                }
                if(storyIds.isEmpty())
                    return -1L;
                while(true) {
                    System.out.print("Choose story id: ");
                    long storyId = Long.parseLong(usrInput.nextLine());
                    if(storyIds.contains(storyId)) {
                        for(int i=0;i<array.size();i++){
                            JsonObject object = array.get(i).getAsJsonObject();
                            if(object.get("id").getAsLong() == storyId) {
                                this.sprint_id = object.get("sprint_id").getAsLong();
                                this.epic_id = object.get("epic_id").getAsLong();
                            }
                        }
                        return storyId;
                    }
                }
            } else {
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return -1L;
    }

    private Long getSprintforStory(Long project_id) {
        String url = this.url + "/projects/" + project_id + "/sprints/active";
        Scanner usrInput = new Scanner(System.in);
        HttpResponse response;
        try {
            response = HttpFunctions.Get(url, tokenType, bearerToken);
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                String respBody = EntityUtils.toString(entity);
                JsonObject responseObject = JsonParser.parseString(respBody).getAsJsonObject();
                JsonArray array = responseObject.get("_embedded").getAsJsonObject().get("sprintList").getAsJsonArray();
                List<Long> sprintIds = new ArrayList<>() ;
                for(int i=0;i<array.size();i++){
                    JsonObject object = array.get(i).getAsJsonObject();
                    Long sprintId = printSprint(object);
                    if(sprintId != -1L)
                        sprintIds.add(sprintId);
                }
                if(sprintIds.isEmpty())
                    return -1L;
                while(true) {
                    System.out.print("Choose sprint id: ");
                    Long sprintId = Long.valueOf(usrInput.nextLine());
                    if(sprintIds.contains(sprintId))
                        return sprintId;
                }
            } else {
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return -1L;
    }

    private Long printSprint(JsonObject object) {

        System.out.println(shellHelper.getInfoMessage("Sprint with id: " + object.get("id").getAsString()
                    + " title: " + object.get("title").getAsString() +
                    " dateFrom: " + object.get("date_from").getAsString() +
                " dateTo: " + object.get("date_to").getAsString()));
        return Long.parseLong(object.get("id").getAsString());


    }

    private Long getEpicforStory(Long project_id) {
        String url = this.url + "/projects/" + project_id + "/epics";
        Scanner usrInput = new Scanner(System.in);
        HttpResponse response;
        try {
            response = HttpFunctions.Get(url, tokenType, bearerToken);
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                String respBody = EntityUtils.toString(entity);
                JsonObject responseObject = JsonParser.parseString(respBody).getAsJsonObject();
                JsonArray array = responseObject.get("_embedded").getAsJsonObject().get("epicList").getAsJsonArray();
                List<Long> epicIds = new ArrayList<>() ;
                for(int i=0;i<array.size();i++){
                    JsonObject object = array.get(i).getAsJsonObject();
                    Long epicId = print(object,"Epic");
                    if(epicId != -1L)
                        epicIds.add(epicId);
                }
                if(epicIds.isEmpty())
                    return -1L;
                while(true) {
                    System.out.print("Choose epic id: ");
                    Long epicId = Long.valueOf(usrInput.nextLine());
                    if(epicIds.contains(epicId))
                        return epicId;
                }
            } else {
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return -1L;
    }

    private Long getProjectforEpic() {
        String url = this.url + "/users/" + this.id + "/projects";
        Scanner usrInput = new Scanner(System.in);
        HttpResponse response;
        try {
            response = HttpFunctions.Get(url, tokenType, bearerToken);
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                String respBody = EntityUtils.toString(entity);
                JsonObject responseObject = JsonParser.parseString(respBody).getAsJsonObject();
                JsonArray array = responseObject.get("_embedded").getAsJsonObject().get("projectList").getAsJsonArray();
                List<Long> prids = new ArrayList<>() ;
                for(int i=0;i<array.size();i++){
                    JsonObject object = array.get(i).getAsJsonObject();
                    Long prid = print(object,"Project");
                    if(prid != -1L)
                        prids.add(prid);
                }
                if(prids.isEmpty())
                    return -1L;
                while(true) {
                    System.out.print("Choose project id: ");
                    Long prid = Long.valueOf(usrInput.nextLine());
                    if(prids.contains(prid))
                        return prid;
                }
            } else {
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return -1L;
    }

    
    private Long print(JsonObject object, String str) {

        if(!object.has("date_finished") || object.get("date_finished").toString().equals("null")) {
            System.out.println(shellHelper.getInfoMessage(str + " with id: " + object.get("id").getAsString()
                    + " title: " + object.get("title").getAsString() +
                    " description: " + object.get("description").getAsString()));
            return Long.parseLong(object.get("id").getAsString());
        }
        return -1L;
    }


    public void printUser(JsonObject user){

        for (String key : user.keySet()) {
            if(key.equals("id") || key.equals("status") || key.equals("pronouns") || key.equals("_links"))
                continue;
            String value = user.get(key).toString().replaceAll("\"","");
            if (!value.equals("null"))
                System.out.println(shellHelper.getSuccessMessage(key) +": " + shellHelper.getInfoMessage(value));
            if(value.equals("null"))
                System.out.println(shellHelper.getSuccessMessage(key) +": ");
        }

    }




}

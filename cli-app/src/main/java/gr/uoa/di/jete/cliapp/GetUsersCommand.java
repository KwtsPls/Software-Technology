package gr.uoa.di.jete.cliapp;

import com.google.gson.Gson;
import gr.uoa.di.jete.api.User;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.Scanner;


@ShellComponent
public class GetUsersCommand {

    User user = null;
    @Autowired
    ShellHelper shellHelper;

    @Autowired
    Gson gson;


    @ShellMethod("Register")
    public void Register() {
        if (this.user != null) {
            System.out.println(shellHelper.getErrorMessage("Please Logout"));
            return;
        }

        Console console = System.console();

        System.out.println(shellHelper.getInfoMessage("Registering new user"));

        Scanner usrInput = new Scanner(System.in);
        System.out.print("Enter Username:");
        String userName = usrInput.nextLine();

        String password = new String(console.readPassword("Enter Password: "));

        System.out.print("Enter Email:");
        String email = usrInput.nextLine();

        System.out.print("Enter Bio:");
        String bio = usrInput.nextLine();

        System.out.println("Pick pronouns 1 for Man 2 for Woman 3 for Other:");
        String pron = usrInput.nextLine();
        if (pron.equals("1"))
            pron = "He/Him";
        else if (pron.equals("2"))
            pron = "She/Her";
        else
            pron = "They/Them";
        System.out.println("Enter Status 0,1 or 2:");
        Long nstatus = Long.parseLong(usrInput.nextLine());

        System.out.print("Enter Location:");
        String location = usrInput.nextLine();

        System.out.print("Enter FirstName:");
        String firstname = usrInput.nextLine();

        System.out.print("Enter LastName:");
        String lastname = usrInput.nextLine();

        user = new User(userName, password, email, bio, location,
                nstatus, pron, firstname, lastname);


        //Post Request
        try (CloseableHttpClient client = HttpClients.createDefault()) {

            String url = String.format("http://localhost:8080/users", user.getId());
            HttpPost request = new HttpPost(url);
            request.setHeader("Accept", "application/json");
            request.setHeader("Content-type", "application/json");
            StringEntity stringEntity = new StringEntity(gson.toJson(user, User.class));
            request.setEntity(stringEntity);

            System.out.println("Executing request " + request.getRequestLine());

            // Create a custom response handler
            ResponseHandler<String> responseHandler = response -> {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            };

            String responseBody = client.execute(request, responseHandler);
            System.out.println("-----------------------------------------------------");
            System.out.println("New User:");
            user = gson.fromJson(responseBody, User.class);
            this.printUser(user);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


        @ShellMethod("User's Logout")
    public void Logout(){
        if(this.user == null) {
            System.out.println(shellHelper.getErrorMessage("Please Login"));
        }
        else {
            Scanner u = new Scanner(System.in);
            System.out.print(shellHelper.getInfoMessage("Are you sure you want to logout: (type yes/y)"));
            String answer = u.nextLine();
            answer = answer.toLowerCase(Locale.ROOT);
            if(answer.equals("yes") || answer.equals("y")){
                System.out.println(shellHelper.getSuccessMessage("Logged Out"));
                this.user = null;
            }
            else {
                System.out.println(shellHelper.getSuccessMessage("Didn't log out"));
            }
        }
    }


    @ShellMethod("User's Login")
    public void Login() throws IOException {

        if(this.user != null){
            System.out.println(shellHelper.getErrorMessage("Please Logout"));
        }else {

            Console console = System.console();
            Scanner usrInput = new Scanner(System.in);

            System.out.print("Enter Username:");
            String userName = usrInput.nextLine();
//            String password = new String(console.readPassword("Enter Password: "));
            String url = String.format("http://localhost:8080/users/name=%s", userName);
            HttpGet request = new HttpGet(url);
            CloseableHttpClient client = HttpClients.createDefault();
            CloseableHttpResponse response = client.execute(request);

            System.out.println(response);


            //Check if response was 404, then user was not found
            if (!(response.getStatusLine().getStatusCode() >= 200 && response.getStatusLine().getStatusCode() < 300)) {
                String out = shellHelper.getErrorMessage("User with username: " + userName + " does not exist");
                out = out.concat(shellHelper.getInfoMessage("\nIf you are using first time jete_app please register"));
                System.err.println(out);
            }
            else{
                // Get the response
                BufferedReader rd = new BufferedReader
                        (new InputStreamReader(
                                response.getEntity().getContent()));
                //Json file to user OBject
                User user = gson.fromJson(rd, User.class);
                this.printUser(user);
                this.user = user;
            }
        }

    }

    @ShellMethod("Update's User")
    public void update() throws IOException {

        if(user == null){
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

        if(userName.length()!=0)
            user.setUsername(userName);

        if(email.length()!=0)
            user.setEmail(email);

        if(bio.length()!=0)
            user.setBio(bio);

        if(location.length()!=0)
            user.setLocation(location);

        if(firstname.length()!=0)
            user.setFirstName(firstname);

        if(lastname.length()!=0)
            user.setLastName(lastname);


        //Put Request
        try(CloseableHttpClient client = HttpClients.createDefault()) {

            String url = String.format("http://localhost:8080/users/%d", user.getId());
            HttpPut request = new HttpPut(url);
            request.setHeader("Accept", "application/json");
            request.setHeader("Content-type", "application/json");
            StringEntity stringEntity = new StringEntity(gson.toJson(user, User.class));
            request.setEntity(stringEntity);

            System.out.println("Executing request " + request.getRequestLine());

            // Create a custom response handler
            ResponseHandler<String> responseHandler = response -> {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            };

            String responseBody = client.execute(request, responseHandler);
            System.out.println("-----------------------------------------------------");
            System.out.println("UPDATED USER:");
            user = gson.fromJson(responseBody,User.class);
            printUser(user);
        }
    }


    @ShellMethod("Displays user's info")
    public void getInfo(){
        if(this.user == null) {
            System.out.println(shellHelper.getErrorMessage("Please Login"));
        }
        else {
            this.printUser(user);
        }
    }


    @ShellMethod("Displays user's info")
    public void getUser(@ShellOption({"--u"}) String username) throws IOException{
        //Make connection to Back-end
        String url = String.format("http://localhost:8080/users/name=%s",username);
        HttpGet request = new HttpGet(url);
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = client.execute(request);



        //Check if response was 404, then user was not found
        if(response.getStatusLine().getStatusCode() == 404){
            String out = shellHelper.getErrorMessage("User with username: " + username + " does not exist");
            System.err.println(out);
        }
        else {
            // Get the response
            BufferedReader rd = new BufferedReader
                    (new InputStreamReader(
                            response.getEntity().getContent()));
            //Json file to user OBject
            User user = gson.fromJson(rd, User.class);
            this.printUser(user);
        }

    }


    public void printUser(User user){
        System.out.println(
                shellHelper.getSuccessMessage("Username: ") + shellHelper.getInfoMessage(user.getUsername()) + "\n"
                + shellHelper.getSuccessMessage("Email: ") + shellHelper.getInfoMessage(user.getEmail()) + "\n"
                + shellHelper.getSuccessMessage("Bio: ") + shellHelper.getInfoMessage(user.getBio()) + "\n"
                + shellHelper.getSuccessMessage("Location: ") + shellHelper.getInfoMessage(user.getLocation()) + "\n"
                + shellHelper.getSuccessMessage("Status: ") + shellHelper.getInfoMessage(user.getStatus().toString()) + "\n"
                + shellHelper.getSuccessMessage("Pronouns: ") + shellHelper.getInfoMessage(user.getPronouns()) + "\n"
                + shellHelper.getSuccessMessage("Firstname: ") + shellHelper.getInfoMessage(user.getFirstName()) + "\n"
                + shellHelper.getSuccessMessage("Lastname: ") + shellHelper.getInfoMessage(user.getLastName())
        );
    }


}

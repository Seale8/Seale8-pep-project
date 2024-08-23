package Controller;

import Model.*;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        // Initialize the accountService and messageService instances
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.post("/register", this::registerAccount);
        app.post("/login", this::loginAccount);
        app.post("/messages", this::createMessage);
        app.get("/messages", this::getMessages);
        app.get("/messages/{message_id}", this::getMessagebyId);
        app.delete("/messages/{message_id}", this::deleteMessagebyId);
        app.patch("/messages/{message_id}", this::updateMessage);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesbyUser);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

   /* * Handler to register new account
    * The response status should be 200 OK, which is the default. The new account should be persisted to the database.
    * If the registration is not successful, the response status should be 400. (Client error)
    * @param ctx the context object handles information HTTP requests and generates responses within Javalin. It will
    *            be available to this method automatically thanks to the app.post method.
    * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object.
    */

    private void registerAccount(Context ctx) throws JsonMappingException, JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        String jsonString = ctx.body();
        Account account = om.readValue(jsonString,Account.class);
            Account registeredAccount = accountService.addAccount(account);
             if(registeredAccount!= null) {
            ctx.json(registeredAccount);
            ctx.status(200);
        } else  {
            ctx.status(400);
        }
    }
    private void loginAccount(Context ctx) throws JsonMappingException, JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        String jsonString = ctx.body();
        Account account = om.readValue(jsonString,Account.class);
        Account logAccount = accountService.getAccount(account);
        if(logAccount != null){
            ctx.json(logAccount);
            ctx.status(200);
        }
        else{
            ctx.status(401);
        }
    }
    private void createMessage(Context ctx) throws JsonMappingException, JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        String jsonString = ctx.body();
        Message message = om.readValue(jsonString,Message.class);
        Message createdMessage = messageService.addMessage(message);
        if(createdMessage != null){
            ctx.json(createdMessage);
            ctx.status(200);
        }
        else{
            ctx.status(400);
        }
    }
    private void getMessages(Context ctx) throws JsonMappingException, JsonProcessingException{
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    private void getMessagebyId(Context ctx) throws JsonMappingException, JsonProcessingException{
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getMessagebyId(id);
        if (message == null) {
            ctx.status(200);
        } else {
            ctx.json(message);
        }

    }

    private void deleteMessagebyId(Context ctx) throws JsonMappingException, JsonProcessingException{
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.deleteMessage(id);
        if (message == null) {
            ctx.status(200);
        } else {
            ctx.json(message);
        }

    }

    private void updateMessage(Context ctx) throws JsonMappingException, JsonProcessingException{
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        ObjectMapper om = new ObjectMapper();
        String jsonString = ctx.body();

        Message message = om.readValue(jsonString, Message.class);

        Message updatedmessage = messageService.updateMessage(id,message.getMessage_text());
        if (updatedmessage == null) {
            ctx.status(400);
        } else {
            ctx.json(updatedmessage);
        }

    }

    private void getAllMessagesbyUser(Context ctx) throws JsonMappingException, JsonProcessingException{
        int id = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messages = messageService.getAllMessagesbyUser(id);
        ctx.json(messages);
    }

}
package Service;

import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    public MessageDAO messageDAO;

    /** no-args constructor for creating a new MessageService with a new AccountDAO.*/
    public MessageService(){
        messageDAO = new MessageDAO();
    }

    public Message addMessage(Message message){
        System.out.println(messageDAO.getMessagePoster(message.getPosted_by()));
        if(!message.getMessage_text().isEmpty() && message.getMessage_text().length() <= 255){
            if(messageDAO.getMessagePoster(message.getPosted_by()) != null){
            return messageDAO.insertMessage(message);
            }
        }
        return null;
    }
    public List<Message> getAllMessages(){

        return messageDAO.getAllMessages();
    }

    public Message getMessagebyId(int id){

        return messageDAO.getMessagebyId(id);
    }
    public Message deleteMessage(int id){

        return messageDAO.deleteMessage(id);
    }

    public Message updateMessage(int id,String text){
        if(!text.isEmpty() &&text.length()<= 255 && messageDAO.getMessagebyId(id)!= null){
        return messageDAO.updateMessage(id,text);
        }
        return null;
    }
    public List<Message> getAllMessagesbyUser(int account_id){

        return messageDAO.getAllMessagesbyUser(account_id);
    }
}

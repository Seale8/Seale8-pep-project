package DAO;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Model.*;
import Util.ConnectionUtil;

public class MessageDAO {

    public Message insertMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();
        try {
//          Write SQL logic here. You should only be inserting with the name column, so that the database may
//          automatically generate a primary key.
            String sql = "INSERT INTO  message (posted_by,message_text,time_posted_epoch) VALUES (?,?,?)" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //write preparedStatement's setString method here.
            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());
            
            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_message_id = (int) pkeyResultSet.getLong(1);
                return new Message(generated_message_id, message.getPosted_by(),message.getMessage_text(),message.getTime_posted_epoch());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public Account getMessagePoster(int id){
        Connection connection = ConnectionUtil.getConnection();
        try {
//          Write SQL logic here. You should only be inserting with the name column, so that the database may
//          automatically generate a primary key.
            String sql = "SELECT * FROM account WHERE account_id = ?" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write preparedStatement's setString method here.
            preparedStatement.setInt(1, id);
            
        
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account account = new Account(rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password"));
                return account;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    
        return null;
    }

    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
        String sql = "SELECT * FROM message";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"),rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


        return messages;


    }

    public Message getMessagebyId(int id){
        Connection connection = ConnectionUtil.getConnection();
        try {
        String sql = "SELECT * FROM message WHERE message_id = ? ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"),rs.getLong("time_posted_epoch"));
                return message;
            }
        
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


        return null;


    }

    public Message deleteMessage(int id){
        Connection connection = ConnectionUtil.getConnection();
        Message message = null;
        try {
//          Write SQL logic here. You should only be inserting with the name column, so that the database may
//          automatically generate a primary key.
            String sql = "SELECT * FROM message WHERE message_id = ?"; ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write preparedStatement's setString method here.
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"),rs.getLong("time_posted_epoch"));
            }

            String deletesql = " DELETE FROM  message WHERE message_id = ?" ;
            PreparedStatement deleteStatement = connection.prepareStatement(deletesql);
            deleteStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return message;
    }

    public Message updateMessage(int id, String newText){
        Connection connection = ConnectionUtil.getConnection();
        Message message = null;
        try {
//          Write SQL logic here. You should only be inserting with the name column, so that the database may
//          automatically generate a primary key.
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?"; ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write preparedStatement's setString method here.
            preparedStatement.setString(1, newText);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();

            String selectsql = "SELECT * FROM message WHERE message_id = ?"; ;
            PreparedStatement preparedStatement2 = connection.prepareStatement(selectsql);

            //write preparedStatement's setString method here.
            preparedStatement2.setInt(1, id);
            ResultSet rs = preparedStatement2.executeQuery();
            while(rs.next()){
                message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"),rs.getLong("time_posted_epoch"));
                return message;
            }
   
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Message> getAllMessagesbyUser(int account_id){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
        String sql = "SELECT * FROM message where posted_by = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, account_id);
        
        ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"),rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


        return messages;


    }

}

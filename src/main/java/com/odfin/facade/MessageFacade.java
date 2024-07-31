package com.odfin.facade;

import com.odfin.persistence.domain.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

public interface MessageFacade extends Remote{

    Message getMessageById(int messageId) throws RemoteException;
    List<Message> getAllMessages() throws RemoteException;
    List<Message> getAllMessagesByChannelId(int channelId) throws RemoteException; // for chat history
    void updateMessage(Message message) throws RemoteException;
    void deleteMessage(int messageId) throws RemoteException;
    Message sendMessage(String content, int senderId, int channelId) throws RemoteException;

}

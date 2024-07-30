package com.odfin.facade;

import com.odfin.persistence.domain.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface MessageFacade extends Remote{

    Message getMessageById(Integer messageId) throws RemoteException;
    List<Message> getAllMessages() throws RemoteException;
    void updateMessage(Message message) throws RemoteException;
    void deleteMessage(Integer messageId) throws RemoteException;
    void sendMessage(String content, Integer senderId, Integer chatId) throws RemoteException;

}

package com.odfin.facade;

import com.odfin.persistence.domain.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface MessageFacade extends Remote {

    void sendMessage(Message message) throws RemoteException;
    Message getMessageById(Long messageId) throws RemoteException;
    List<Message> getAllMessages() throws RemoteException;
    void updateMessage(Message message) throws RemoteException;
    void deleteMessage(Long messageId) throws RemoteException;

}

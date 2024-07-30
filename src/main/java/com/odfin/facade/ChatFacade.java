package com.odfin.facade;

import com.odfin.persistence.domain.Chat;

import java.rmi.RemoteException;
import java.util.List;

public interface ChatFacade {
    Chat getChatByID(int chatID) throws RemoteException;
    List<Chat> getAllChats() throws RemoteException;
    void updateChat(Chat chat) throws RemoteException;
    void insertChat(Chat chat) throws RemoteException;
    void deleteChat(int chatID) throws RemoteException;
}

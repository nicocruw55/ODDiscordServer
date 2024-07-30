package com.odfin.facade;

import com.odfin.persistence.dao.ChatDAO;
import com.odfin.persistence.domain.Chat;
import com.odfin.persistence.factory.DAOFactory;
import java.util.List;
import java.rmi.RemoteException;
import java.sql.SQLException;

public class ChatFacadeImpl implements ChatFacade {
    private final ChatDAO chatDAO;
    public ChatFacadeImpl() throws RemoteException {
        super();
        this.chatDAO = DAOFactory.getDAOFactory(DAOFactory.MY_SQL).getChatDAO();
    }
    @Override
    public Chat getChatByID(int chatID) throws RemoteException {
        try {
            return chatDAO.getChatbyID(chatID);
        } catch (SQLException e) {
            throw new RemoteException("Error retrieving chat", e);
        }
    }
    @Override
    public List<Chat> getAllChats() throws RemoteException {
        try {
            return chatDAO.getAllChats();
        } catch (SQLException e) {
            throw new RemoteException("Error retrieving all chats", e);
        }
    }

    @Override
    public void updateChat(Chat chat) throws RemoteException {
        try {
            chatDAO.updateChat(chat);
        } catch (SQLException e) {
            throw new RemoteException("Error updating chat", e);
        }
    }

    @Override
    public void insertChat(Chat chat) throws RemoteException {
        try {
            chatDAO.insertChat(chat);
        } catch (SQLException e) {
            throw new RemoteException("Error inserting chat", e);
        }
    }

    @Override
    public void deleteChat(int chatId) throws RemoteException {
        try {
            chatDAO.deleteChat(chatId);
        } catch (SQLException e) {
            throw new RemoteException("Error deleting chat", e);
        }
    }
}


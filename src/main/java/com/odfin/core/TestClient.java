package com.odfin.core;

import com.odfin.core.Notification.NotificationClient;
import com.odfin.facade.*;
import com.odfin.persistence.util.ServerHelper;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class TestClient {

    public static void main(String[] args) throws IOException, NotBoundException {

        // Notification client on seperate thread
        new NotificationClient(1).start();

//        channelGroupFacade.getAllChannelGroups().forEach(System.out::println);
        //Test MySQLChannelGroupDAO
        MySQLChannelGroupDAO mySQLChannelGroupDAO = new MySQLChannelGroupDAO();
        MySqlChannelDAO mySqlChannelDAO = new MySqlChannelDAO();
//        try {
//            mySQLChannelGroupDAO.getChannelGroupsByUserID(1).forEach(System.out::println);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//        try {
//            System.out.println(mySQLChannelGroupDAO.getChannelGroupById(1));
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//        try {
//            System.out.println(mySQLChannelGroupDAO.createChannelGroup(new ChannelGroup(1, "Test5"), 1));
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//        try {
//            System.out.println(mySQLChannelGroupDAO.getChannelGroupById(5));
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        try {
            mySqlChannelDAO.getChannelsByChannelGroupID(1).forEach(System.out::println);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // get some facades
        UserFacade userFacade = serverFacade.getUserFacade();
        MessageFacade messageFacade = serverFacade.getMessageFacade();
        ChannelFacade channelFacade = serverFacade.getChannelFacade();

        // test facades
        messageFacade.sendMessage("KAKAKAIAKAIAKAIAKAIAKAIAK", 1, 1);

    }

}

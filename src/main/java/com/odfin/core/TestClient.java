package com.odfin.core;

import com.odfin.persistence.domain.ChannelGroup;
import com.odfin.persistence.impl.MySQLChannelGroupDAO;
import com.odfin.persistence.impl.MySqlChannelDAO;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.sql.SQLException;

public class TestClient {

    public static void main(String[] args) throws IOException, NotBoundException {

//        // Notification client on seperate thread
//        new NotificationClient(1).start();
//
//        // Server RMI
//        Registry registry = LocateRegistry.getRegistry(ServerHelper.SERVER_NAME, Registry.REGISTRY_PORT);
//        ServerFacade serverFacade = (ServerFacade) registry.lookup(ServerFacade.class.getSimpleName());
//        System.out.println("found server facade: " + serverFacade);
//
//        // get some facades
//        UserFacade userFacade = serverFacade.getUserFacade();
//        MessageFacade messageFacade = serverFacade.getMessageFacade();
//        ChannelFacade channelFacade = serverFacade.getChannelFacade();
//        ChannelGroupFacade channelGroupFacade = serverFacade.getChannelGroupFacade();

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


    }

}

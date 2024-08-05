package com.odfin.core;

import com.odfin.core.Notification.NotificationClient;
import com.odfin.facade.*;
import com.odfin.persistence.domain.ChannelGroup;
import com.odfin.persistence.impl.MySQLChannelGroupDAO;
import com.odfin.persistence.util.ServerHelper;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
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
        try {
            mySQLChannelGroupDAO.getChannelGroupsByUserID(1).forEach(System.out::println);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            System.out.println(mySQLChannelGroupDAO.getChannelGroupById(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            System.out.println(mySQLChannelGroupDAO.insertChannelGroup(new ChannelGroup(1, "Test5")));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            System.out.println(mySQLChannelGroupDAO.getChannelGroupById(4));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}

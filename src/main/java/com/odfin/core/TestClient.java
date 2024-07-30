package com.odfin.core;

import com.odfin.facade.ChannelFacade;
import com.odfin.facade.MessageFacade;
import com.odfin.facade.ServerFacade;
import com.odfin.facade.UserFacade;
import com.odfin.persistence.domain.Message;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class TestClient {
    public static void main(String[] args) throws RemoteException, NotBoundException {

        Registry registry = LocateRegistry.getRegistry("localhost", Registry.REGISTRY_PORT);
        ServerFacade s = (ServerFacade) registry.lookup(ServerFacade.class.getSimpleName());

        UserFacade userFacade = s.getUserFacade();
        MessageFacade messageFacade = s.getMessageFacade();
        ChannelFacade channelFacade = s.getChannelFacade();

        System.out.println(userFacade.getAllUsers());
        System.out.println(messageFacade.getAllMessagesByChannelId(1));
        System.out.println(channelFacade.getAllChannelsByUserId(1));


    }
}

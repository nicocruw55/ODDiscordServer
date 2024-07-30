package com.odfin.core;

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

        Registry registry = LocateRegistry.getRegistry("cruw-community.de", Registry.REGISTRY_PORT);
        ServerFacade s = (ServerFacade) registry.lookup(ServerFacade.class.getSimpleName());
        UserFacade m = s.getUserFacade();
        //List<Message> ms = m.getAllMessages();

    }
}

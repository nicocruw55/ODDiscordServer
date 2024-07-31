package com.odfin.core;

import com.odfin.facade.*;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class TestClient {
    public static void main(String[] args) {
        System.out.println("Starte TestClient...");

        try {
            // Suche das Server-Remote-Objekt im RMI-Registry
            System.out.println("Versuche, das RMI-Registry des Servers zu finden...");
            Registry registry = LocateRegistry.getRegistry("cruw-community.de", Registry.REGISTRY_PORT);
            System.out.println("Erfolgreich mit RMI-Registry des Servers verbunden.");

            // Überprüfe, ob das Server-Remote-Objekt gefunden werden kann
            System.out.println("Versuche, ServerFacade zu finden...");
            ServerFacade serverFacade = (ServerFacade) registry.lookup(ServerFacade.class.getSimpleName());
            System.out.println("ServerFacade gefunden: " + serverFacade);

            // Erstelle das Client-Remote-Objekt
            System.out.println("Erstelle das ClientFacade-Objekt...");
            ClientFacadeImpl clientFacade = new ClientFacadeImpl();
            System.out.println("ClientFacade-Objekt erstellt.");

            // Registriere das Client-Remote-Objekt beim Server
            System.out.println("Registriere das ClientFacade-Objekt beim Server...");
            serverFacade.registerClient(clientFacade);
            System.out.println("ClientFacade erfolgreich beim Server registriert.");

            // Hole die Facades vom Server
            System.out.println("Hole UserFacade vom Server...");
            UserFacade userFacade = serverFacade.getUserFacade();
            System.out.println("UserFacade erhalten: " + userFacade);

            System.out.println("Hole MessageFacade vom Server...");
            MessageFacade messageFacade = serverFacade.getMessageFacade();
            System.out.println("MessageFacade erhalten: " + messageFacade);

            System.out.println("Hole ChannelFacade vom Server...");
            ChannelFacade channelFacade = serverFacade.getChannelFacade();
            System.out.println("ChannelFacade erhalten: " + channelFacade);

            // Sende eine Nachricht über die MessageFacade
            System.out.println("Sende eine Nachricht über die MessageFacade...");
            messageFacade.sendMessage("Dennis macht Pipi auf Nico", 2, 2);
            System.out.println("Nachricht gesendet.");

            // Gebe einige Informationen aus
            System.out.println("Rufe alle Benutzer ab...");
            System.out.println(userFacade.getAllUsers());

            System.out.println("Rufe alle Nachrichten nach Channel-ID 1 ab...");
            System.out.println(messageFacade.getAllMessagesByChannelId(1));

            System.out.println("Rufe alle Kanäle nach Benutzer-ID 1 ab...");
            System.out.println(channelFacade.getAllChannelsByUserId(1));

        } catch (RemoteException e) {
            System.err.println("RemoteException aufgetreten:");
            e.printStackTrace();
        } catch (NotBoundException e) {
            System.err.println("NotBoundException aufgetreten:");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Allgemeiner Fehler aufgetreten:");
            e.printStackTrace();
        }
    }
}

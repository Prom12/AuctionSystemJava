package server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


import shared.File;

public class Server {

    public static void main(String [] args){
        try{
            
            // creating initial file in location
            File stub = new FileImpl();
            
            // Using registry to register object on localhost
            Registry registry = LocateRegistry.createRegistry(9100);

            // Register exported object in Registry for clients to access through registered name
            registry.bind("server", stub);


            // Set a hostname for server
            System.setProperty("java.rmi.server.hostname", "127.0.0.1");

            System.out.println("Server Started");


        } catch(Exception e){
            System.out.println("Server Error ..."+ e );
        }
    }
    
}

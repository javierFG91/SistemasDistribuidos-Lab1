
package labsd;

import java.io.*;
import java.net.*;


/**
 *
 * @author Ismael
 */
public class CacheClient extends Thread{
    
     public static void main(String args[]) throws Exception{
     
        //Variables
        String sentence;
        String fromServer;
        
        boolean salir = false;
        
        //Buffer para recibir desde el usuario
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        
        //Socket para el cliente (host, puerto)
        Socket clientSocket = new Socket("localhost", 5000);
        
        //Buffer para enviar el dato al server
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        
        //Buffer para recibir dato del servidor
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        
         while (!salir) { 
            
            sentence = inFromUser.readLine();
            outToServer.writeBytes(sentence + '\n');
            
            if(sentence.compareTo("0\n") == 1){
                salir = true;
            }
            else{
                //Recibimos del servidor
                fromServer = inFromServer.readLine();
                System.out.println("Server responde: " + fromServer);
            
            }
   
         }
        //Leemos del cliente y lo mandamos al servidor
 
        
        //Cerramos el socket
        clientSocket.close();
     
     
     
        } 
     }

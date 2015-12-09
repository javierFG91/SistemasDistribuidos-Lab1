/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labsd;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;


/**
 *
 * @author Ismael
 */
public class CacheServer extends Thread{
    
    
    public static void main(String args[]) throws Exception{
    
        int servidorCache;
        int numHits, numMiss;
        //String consulta = "";
        ArrayList<String> consultaLimpia =  new ArrayList();
        String DatosRespuesta;
        int puerto = 5000;
        int ActualizarEstatica = 15;
        
        try {
            ServerSocket acceptSocket = new ServerSocket(puerto);
            System.out.println("El servidor de cache esta corriendo en: "+InetAddress.getLocalHost().getHostAddress().trim()+":"+puerto);
            
            // construye una nueva cache 
            int tamEstatico = 3;
            int tamDinamico = 5;
            
            // reset a los valores de hit & miss
            numHits = 0;
            numMiss = 0;
            
            Cache cache =  new Cache(tamEstatico, tamDinamico);
            // cache.mostrarStopwords();
            // System.out.println("Cantidad de Stopwords: "+cache.stopwords.size());
            //System.out.println("Cantidad de hits: "+numHits+" Cantidad Miss: "+numMiss);
            System.out.println("Se ha generado una cache con seccion estatica de largo: "+cache.largoEstatico + " seccion dinamica de largo: "+cache.largoDinamico);
            
            
            //Socket listo para recibir 
            Socket connectionSocket = acceptSocket.accept();
            //Buffer para recibir desde el cliente
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            //Buffer para enviar al cliente
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
            
            while(true){
            cache.cantidadLlamados();
            String consulta = "";
            //Recibimos el dato del cliente y lo mostramos en el server
            String consulta2 =inFromClient.readLine();
            //System.out.println("Consulta recibida " + consulta2);
            consultaLimpia = cache.limpiarConsulta(consulta2);
            for(int i = 0;i < consultaLimpia.size();i++){
                if(!" ".equals(consultaLimpia.get(i))){
                    consulta += consultaLimpia.get(i) + " ";
                }
            }            
            //System.out.println("Consulta limpia: "+ consulta );
            consulta = consulta.toLowerCase();
            //System.out.println("Consulta mas limpia" + consulta);
            //System.out.println(cache.loadStopwords());
            //System.out.println("consulta limpia: "+consultaLimpia);
            
            // Aqui se debe llamar al servicio de cache
            if(cache.getDataFromCache(consulta) != 0){
                System.out.println("hits");
                DatosRespuesta = new StringBuffer("Hits").toString() + '\n';
                        
            }else{
                System.out.println("miss");
                DatosRespuesta = new StringBuffer("Miss").toString() + '\n';
                cache.addDataToCache(consulta);
            }        
            
            // si se completan la cantidad de llamados, actualizar cache estatica
            if((cache.getLlamados()%ActualizarEstatica) == 0){
                cache.updateCacheEstatica();
            }
            
            cache.addLlamados();
            //processedData = fromClient.toUpperCase() + '\n';
            //String reverse = new StringBuffer("Holaaaaaaaaaaaaaa").toString() + '\n';

            
            //Se le envia al cliente
            System.out.println("enviando datos");
            outToClient.writeBytes(DatosRespuesta);
            
            cache.print();
            
        }
        } catch (SocketException e) {
            System.out.println("Error al crear el socket en el puerto: "+puerto);
            System.out.println(e);

        }
        
        
    
    
    }
    

   
}

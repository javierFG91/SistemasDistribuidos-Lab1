package labsd;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author Ismael
 */
public class CacheThread implements Runnable{
    
    private Cache cache;
    int numHits, numMiss;
    String consulta;
    ArrayList<String> consultaLimpia =  new ArrayList();
    String DatosRespuesta;
    int ActualizarEstatica = 15;

    
    
    public void run(){
    
            System.out.println("Hola soy un Thread : "+Thread.currentThread().getId());
    
            
                        // construye una nueva cache 
            int tamEstatico = 3;
            int tamDinamico = 5;
            
            // reset a los valores de hit & miss
            numHits = 0;
            numMiss = 0;
            
            Cache cache =  new Cache(tamEstatico, tamDinamico);
           // cache.mostrarStopwords();
            System.out.println("Cantidad de Stopwords: "+cache.stopwords.size());
            System.out.println("Cantidad de hits: "+numHits+" Cantidad Miss: "+numMiss);
            System.out.println("Se ha generado una cache con seccion estatica de largo: "+cache.largoEstatico + " seccion dinamica de largo: "+cache.largoDinamico);
            
            // Aqui debo leer del vector compartido
            
            
            while(true){
            cache.cantidadLlamados();
            
            //Recibimos el dato del cliente y lo mostramos en el server
           // consulta =inFromClient.readLine();
            consulta = "Una consulta";
            System.out.println("Consulta recibida " + consulta);
            //consultaLimpia = cache.limpiarConsulta(consulta);
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
            //outToClient.writeBytes(DatosRespuesta);
            
            cache.print();
            
        }
            
            
            
    }
    
    
}

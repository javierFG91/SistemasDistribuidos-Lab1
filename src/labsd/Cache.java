package labsd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringTokenizer;   

/**
 *
 * @author Isamel
 */

public class Cache {
    
    int largoEstatico;
    int largoDinamico;
    private int llamados;
    private int estaticoRangoUpdate = 3; // representa al denominador 3 -> 33% \ 4-> 25% \ 5 -> 20% \ 10 -> 10%
    private int dinamicoRangoUpdate = 5; // representa al denominador 3 -> 33% \ 4-> 25% \ 5 -> 20% \ 10 -> 10%
    private int modificarEstatico;
    private int modificarDinamico;
    private int castigoDinamico = 2;
    ArrayList<String>  stopwords = new ArrayList( Arrays.asList( new String[]{""," ","un", "una", "unas", "unos", "uno", "sobre", "todo", "también", "tras", "otro", "algún", "alguno", "alguna", "algunos", "algunas", "ser", "es", "soy", "eres", "somos", "sois", "estoy", "esta", "estamos", "estais", "estan", "como", "en", "para", "atras", "porque", "por qué", "estado", "estaba", "ante", "antes", "siendo", "ambos", "pero", "por", "poder", "puede", "puedo", "podemos", "podeis", "pueden", "fui", "fue", "fuimos", "fueron", "hacer", "hago", "hace", "hacemos", "haceis", "hacen", "cada", "fin", "incluso", "primero", "desde", "conseguir", "consigo", "consigue", "consigues", "conseguimos", "consiguen", "ir", "voy", "va", "vamos", "vais", "van", "vaya", "gueno", "ha", "tener", "tengo", "tiene", "tenemos", "teneis", "tienen", "el", "la", "lo", "las", "los", "su", "aqui", "mio", "tuyo", "ellos", "ellas", "nos", "nosotros", "vosotros", "vosotras", "si", "dentro", "solo", "solamente", "saber", "sabes", "sabe", "sabemos", "sabeis", "saben", "ultimo", "largo", "bastante", "haces", "muchos", "aquellos", "aquellas", "sus", "entonces", "tiempo", "verdad", "verdadero", "verdadera", "cierto", "ciertos", "cierta", "ciertas", "intentar", "intento", "intenta", "intentas", "intentamos", "intentais", "intentan", "dos", "bajo", "arriba", "encima", "usar", "uso", "usas", "usa", "usamos", "usais", "usan", "emplear", "empleo", "empleas", "emplean", "ampleamos", "empleais", "valor", "muy", "era", "eras", "eramos", "eran", "modo", "bien", "cual", "cuando", "donde", "mientras", "quien", "con", "entre", "sin", "trabajo", "trabajar", "trabajas", "trabaja", "trabajamos", "trabajais", "trabajan", "podria", "podrias", "podriamos", "podrian", "podriais", "yo", "aquel"} ) );
        
    
    
    LinkedHashMap<String, Integer> cacheEstatica;
    LinkedHashMap<String, Integer> cacheDinamica;
    LinkedHashMap<String, Integer> resultCache;

    public Cache(LinkedHashMap<String, Integer> resultCache) {
        this.resultCache = resultCache;
    }
    
    public Cache(int estatico, int dinamico) {
        this.largoEstatico = estatico;
        this.largoDinamico = dinamico;
        this.cacheEstatica = new LinkedHashMap<>();
        this.cacheDinamica = new LinkedHashMap<>();
        this.stopwords = new ArrayList( Arrays.asList( new String[]{"un", "una", "unas", "unos", "uno", "sobre", "todo", "también", "tras", "otro", "algún", "alguno", "alguna", "algunos", "algunas", "ser", "es", "soy", "eres", "somos", "sois", "estoy", "esta", "estamos", "estais", "estan", "como", "en", "para", "atras", "porque", "por qué", "estado", "estaba", "ante", "antes", "siendo", "ambos", "pero", "por", "poder", "puede", "puedo", "podemos", "podeis", "pueden", "fui", "fue", "fuimos", "fueron", "hacer", "hago", "hace", "hacemos", "haceis", "hacen", "cada", "fin", "incluso", "primero", "desde", "conseguir", "consigo", "consigue", "consigues", "conseguimos", "consiguen", "ir", "voy", "va", "vamos", "vais", "van", "vaya", "gueno", "ha", "tener", "tengo", "tiene", "tenemos", "teneis", "tienen", "el", "la", "lo", "las", "los", "su", "aqui", "mio", "tuyo", "ellos", "ellas", "nos", "nosotros", "vosotros", "vosotras", "si", "dentro", "solo", "solamente", "saber", "sabes", "sabe", "sabemos", "sabeis", "saben", "ultimo", "largo", "bastante", "haces", "muchos", "aquellos", "aquellas", "sus", "entonces", "tiempo", "verdad", "verdadero", "verdadera", "cierto", "ciertos", "cierta", "ciertas", "intentar", "intento", "intenta", "intentas", "intentamos", "intentais", "intentan", "dos", "bajo", "arriba", "encima", "usar", "uso", "usas", "usa", "usamos", "usais", "usan", "emplear", "empleo", "empleas", "emplean", "ampleamos", "empleais", "valor", "muy", "era", "eras", "eramos", "eran", "modo", "bien", "cual", "cuando", "donde", "mientras", "quien", "con", "entre", "sin", "trabajo", "trabajar", "trabajas", "trabaja", "trabajamos", "trabajais", "trabajan", "podria", "podrias", "podriamos", "podrian", "podriais", "yo", "aquel"} ) );        this.llamados = 0;
        this.modificarEstatico = (this.largoEstatico / this.estaticoRangoUpdate) + (this.largoEstatico % this.estaticoRangoUpdate);
        System.out.println("cantidad de elementos a modificar por politica en estatico: " + this.modificarEstatico);
        this.modificarDinamico = (this.largoDinamico / this.dinamicoRangoUpdate) + (this.largoDinamico % this.dinamicoRangoUpdate);
        System.out.println("cantidad de elementos a modificar por politica en Dinamico: " + this.modificarDinamico);
    }
    
    
    public void addLlamados(){
        this.llamados++;
    }
    
    public int getLlamados(){
        return this.llamados;
    }
    
    public void cantidadLlamados(){
        System.out.println("Cantidad de llamados: "+this.llamados);
    }
    
    public void resetLlamados(){
        this.llamados = 0;
    }
    
    
    public void mostrarStopwords(ArrayList<String> palabras){
        
        for (int i = 0; i < palabras.size(); i++) {
            System.out.println("Stopwords:"+(i+1)+" : "+palabras.get(i));
        }
    
    }
    
    public int getDataFromCache(String search){
        
        int resultado;
        try {
            resultado = cacheEstatica.get(search);
            System.out.println("resultado en estatico:"+resultado);
        } catch (Exception e) {
            System.out.println("fuera en estatica");
            resultado = 0;
        }
        
        int frecuencia = 0;
        // Se busca en cache estatica
        if(resultado != 0){ // Si hace hits, actualzia el valor y lo coloca al final de la lista
            System.out.println("actualizando");
            frecuencia = cacheEstatica.get(search) + 1;
            System.out.println("frecuencia nueva: "+frecuencia);
            cacheEstatica.remove(search);
            cacheEstatica.put(search, frecuencia);
            System.out.println("valor actualizado: "+search+" : "+frecuencia);
        }else{
            try {
                 resultado = cacheDinamica.get(search);
                 System.out.println("resultado en dinamico:"+resultado);

            } catch (Exception e) {
                System.out.println("fuera en dinamica");
                resultado = 0;
            }
                      
            if(resultado != 0){ // hits en cache dinamica
                frecuencia = resultado;
                cacheDinamica.remove(search);
                cacheDinamica.put(search, frecuencia+1);
            }
        
        
        }
        return resultado;
    }
    
    // El agregar un elemento depende de la politica de admision a la cache
    public void addDataToCache(String search){
        // verifica si la cache estatica esta completa, si esta vacia ingresa la busqueda a la cache. caso contrario pasa a la cache dinamica
        if(cacheEstatica.size() != largoEstatico || cacheEstatica.isEmpty()){ //cache vacia o con espacio
            cacheEstatica.put(search, 1);
            System.out.println("agregado - datos en cach estatica "+cacheEstatica.keySet());
        }else{ // cache estatica completa, pasar a cache dinamica
            if(cacheDinamica.size() != largoDinamico || cacheDinamica.isEmpty()){
                cacheDinamica.put(search, 1);
            }else{
                // ambas caches se encuentran completas, por lo tanto se aplica una politica de admision a la cache.
                // La cache estatica posee un metodo para actualizar sus elementos, por lo tanto la politica de admision actua sobre la cache dinamica
                
                if(search.length() < 8 && search.length() > 2){ // se acepta en cache dinamica. Valores corresponden al largo promedio de las palabras en español
                   
                    // se debe verificar la frecuencia del elemento menos utilizado
                    
                    int salirInsertDinamico = 0;
                    int iteracion = 0;
                    while (salirInsertDinamico != 1 && iteracion != modificarDinamico) {                        
                             String menosUtilizado = cacheDinamica.entrySet().iterator().next().getKey(); // menos utilizados segun lru
                             int menosValue = cacheDinamica.get(menosUtilizado);
                             if(menosValue < 3){ // definir minimo a eliminar
                                  cacheDinamica.remove(menosUtilizado);
                                  cacheDinamica.put(search, 1);
                                  salirInsertDinamico = 1;
                             }else{
                                  cacheDinamica.put(menosUtilizado, (menosValue-castigoDinamico)); // castiga
                             }
                             iteracion++;
                    }
                }
            }        
        }
    }
    
    
    public void updateCacheEstatica(){
        
        String menosUtilizadoEstatica = cacheEstatica.entrySet().iterator().next().getKey();
        int valueMenosUtilizadoEstatica = cacheEstatica.get(menosUtilizadoEstatica);
           
    }
    /*
    public ArrayList<String> loadStopwords(){
        
        ArrayList<String> palabras = new ArrayList<>();
        
        try {
        File archivo = new File("stopwordslist.txt");    
        FileReader fr=new FileReader(archivo);
        BufferedReader br= new BufferedReader(fr);
        String linea;
            System.out.println("estoy leyendo antes de ver el archivo");
        while((linea=br.readLine())!=null){
             System.out.println(linea);
             palabras.add(linea);
        }
        
        } catch (FileNotFoundException e) {
            
        } catch  (IOException e){
        }
        return palabras;
    }
    */
    
    // metodo que elimina las stopwords
    public ArrayList<String> limpiarConsulta(String input){
        
        //ArrayList<String> palabras = loadStopwords();
        //mostrarStopwords(palabras);
        String[] dividirConsulta;
        dividirConsulta = input.split(" ");
        ArrayList<String> consultaLimpia = new ArrayList();
        for (int i = 0; i < dividirConsulta.length; i++) {
            if(!(stopwords.contains(dividirConsulta[i]))){
               consultaLimpia.add(" " + dividirConsulta[i]);
            }           
        }
        return consultaLimpia;
    }
    
    public void print() {
        System.out.println("===== Cache Estatica =====");
        System.out.println("| " +  cacheEstatica.keySet() + " | ");
        System.out.println("==========================");
        
        System.out.println("======== Dinamica ========");
        System.out.println("| " + cacheDinamica.keySet() + " | ");
        System.out.println("==========================");
    }
}

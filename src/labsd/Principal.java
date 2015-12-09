package labsd;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Ismael
 */
public class Principal {
    
    public static void main(String[] args) throws InterruptedException {
        
        System.out.println("Bienvenidos al sistema de cache distribuidos");
        Thread[] threadPool = new Thread[5];
        for (int i = 0; i < 5; i++) {
            threadPool[i] = new Thread(new CacheThread());
            threadPool[i].start();
            Thread.sleep(3000);
        }        
    }        
}

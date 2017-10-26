/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vocaboli;

import java.io.File;

/**
 *
 * @author LENOVO
 */
public class Vocaboli {

    public Vocaboli(){
        File f = new File("vocaboli.txt");
        if(!f.isFile()){
            System.err.println("Il file con i vocaboli non esiste");
            return;
        }
        
        new Gui();
        
    }
    
    public static void main(String[] args) {
        new Vocaboli();
    }
    
}

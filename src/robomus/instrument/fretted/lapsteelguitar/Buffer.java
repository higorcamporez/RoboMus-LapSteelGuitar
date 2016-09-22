/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robomus.instrument.fretted.lapsteelguitar;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Higor
 */
public class Buffer {
    
    List<List> messages;

    public Buffer() {
        messages = new ArrayList<>();
    }
    public List remove(){
        return messages.remove(0);
    }
    public void add(List l){
        //comentario
        if(l.get(0) == "/sincronizar" ){
            messages.add(0,l);
        }else{
            messages.add(l);
        }
        
    }
    public void remove(int n){
        for (int i = 0; i < n; i++) {
            messages.remove(i);
        }
    } 
    public void print(){
        int cont =0;
        System.out.println("_________________buffer______________");
        for (List message : messages) {
            System.out.println("------------ posicao = "+cont+" -------------");
            for (Object obj : message) {
               
                System.out.println(obj);
            }
            cont++;
        }
    }
    
    
}

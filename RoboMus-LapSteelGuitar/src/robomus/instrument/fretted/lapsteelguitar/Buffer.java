/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robomus.instrument.fretted.lapsteelguitar;

import com.illposed.osc.OSCMessage;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Higor
 */
public class Buffer {
    
    List<OSCMessage> messages;

    public Buffer() {
        messages = new ArrayList<>();
    }
    public OSCMessage remove(){
        return messages.remove(0);
    }
    public void add(OSCMessage l){
        //comentario
        if(l.getArguments().get(0) == "/sincronizar" ){
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
        
        for (OSCMessage message : messages) {
            System.out.println("------------ posicao = "+cont+" -------------");
            for (Object obj : message.getArguments()) {
               
                System.out.println(obj);
            }
            cont++;
        }
    }
    
    
}

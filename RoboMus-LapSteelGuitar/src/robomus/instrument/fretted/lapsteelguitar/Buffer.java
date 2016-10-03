/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robomus.instrument.fretted.lapsteelguitar;

import com.illposed.osc.OSCMessage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import robomus.arduinoCommunication.PortControl;

/**
 *
 * @author Higor
 */
public class Buffer extends Action{
    
    private volatile List<OSCMessage> messages;
    public volatile List<Long> sentMessageId;
    
    
    
    public Buffer(PortControl portControl) {
        super(portControl);
        this.messages = new ArrayList<OSCMessage>();
        
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
    
    public OSCMessage get(){
        if(messages.isEmpty()){
            return messages.get(0);
        }
        return null;
    }
    
    public Long getFirstTimestamp(){
        
        OSCMessage oscMsg = messages.get(0);
        System.out.println(oscMsg.getArguments().size());
        return (Long)oscMsg.getArguments().get(0);
        
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
    
 
    public void run() {
        
        long timestamp;
        
        while(true){
            //System.out.println("nao tem condições");
            if (!this.messages.isEmpty()) {
                System.out.println("entrou");
                timestamp = (Long)messages.get(0).getArguments().get(0);
                //timestamp = 7777;
                
                if (timestamp <= System.currentTimeMillis()) {
                    OSCMessage oscMessage = messages.get(0);
                    String header = (String) oscMessage.getAddress();
                    
                    if(header.startsWith("/"))
                        header = header.substring(1);
                    
                    String[] split = header.split("/", -1);
                    System.out.println("Adress = " + header);
                    
                    if (split.length >= 2) {
                        header = split[1];
                        System.out.println("Adress = " + header);

                        sentMessageId.add((Long) oscMessage.getArguments().get(1));
                        switch (header) {
                            case "synchronize":
                                break;
                            case "playNote":
                                
                                break;
                            case "playNoteFretted":
                                break;
                            case "playString":
                                this.playString(oscMessage);
                                break;
                            case "slide":
                                this.slide(oscMessage);
                                break;
                            case "moveBar":
                                break;
                            case "positionBar":
                                break;
                            case "volumeControl":
                                break;
                            case "toneControl":
                                break;
                            case "fuzz":
                                break;
                            case "stop":
                                break;
                        }
                        
                        remove();
                    }

                }
            }
        }
    }
    
    
}

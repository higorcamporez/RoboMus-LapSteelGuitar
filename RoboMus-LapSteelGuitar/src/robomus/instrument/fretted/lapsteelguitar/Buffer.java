/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robomus.instrument.fretted.lapsteelguitar;

import com.illposed.osc.OSCMessage;
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
    private long lastServerTime;
    private long lastInstrumentTime;
    private int thresold = 100;
    
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
    public long relativeTime(){
        return (this.lastServerTime + ( System.currentTimeMillis() - this.lastInstrumentTime) ); 
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
    
    public void synch(OSCMessage oscMessage){
        
        List<Object> args = oscMessage.getArguments();
        
        this.lastServerTime = (Long)args.get(0);
        this.lastInstrumentTime = System.currentTimeMillis();
        
    }
    public void synchStart(OSCMessage oscMessage){
        List<Object> args = oscMessage.getArguments();
        
        this.lastServerTime = (Long)args.get(0);
        this.lastInstrumentTime = System.currentTimeMillis();
        this.thresold = (int)args.get(1);
    }
    public String getHeader(OSCMessage oscMessage){
        String header = (String) oscMessage.getAddress();
                    
        if(header.startsWith("/"))
            header = header.substring(1);

        String[] split = header.split("/", -1);

        if (split.length >= 2) {
            header = split[1];
        }else{
            header = null;
        }
        return header;
    }
    
    public void run() {
        
        long timestamp;
        String header;
        while(true){
            //System.out.println("nao tem condições");
            if (!this.messages.isEmpty()) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Buffer.class.getName()).log(Level.SEVERE, null, ex);
                }
                OSCMessage oscMessage = messages.get(0);
                timestamp = (Long)messages.get(0).getArguments().get(0);
                header = getHeader(oscMessage);
                if(header == "synch"){
                    System.out.println("synch");
                    synch(oscMessage);
                }else if(header == "synchStart"){
                    System.out.println("synchStart");
                    synchStart(oscMessage);
                }else if ( (relativeTime() - timestamp) <= this.thresold ) {
                    
                    System.out.println("entrou");
                    
                    if (header != null) {
                        
                        System.out.println("Adress = " + header);

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

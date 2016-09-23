/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robomus.instrument.fretted.lapsteelguitar;

import com.illposed.osc.OSCMessage;
import java.util.TimerTask;
import robomus.arduinoCommunication.PortControl;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Higor
*/
/* -------- codes to action ---
synchronize - 0
playNote - 10
playNoteFretted - 20
playString - 30
slide - 40
moveBar - 50
positionBar - 60
volumeControl - 70
toneControl - 80
efect - 90
stop - 100
*/

//format OSC message:  [timestamp, server id, ... rest of args depend on action ]

public abstract class Action extends Thread{
    
    private final PortControl portControl;

    public Action(PortControl portControl) {
        this.portControl = portControl;
    }


/*    public void playNote(OSCMessage oscMessage){
// Format OSC = [timestamp, id, Right hand position, left hand servo]
// Message to Arduino: action Arduino code, Right hand position, left hand servo, action server id 
        byte [] msgSlide = new byte[4];
        
        List args = oscMessage.getArguments();
        msgSlide[0]=10;
        msgSlide[1]=(byte)args.get(2);
        msgSlide[2]=(byte)args.get(3);
        msgSlide[3]=(byte) ((byte)args.get(1)%256); //server id on 1 byte
               
            try {
            portControl.sendData(msgSlide);
        } catch (IOException ex) {
            Logger.getLogger(MyRobot.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
  */  
    
    
    public void playString(OSCMessage oscMessage){
// Format OSC = [timestamp, id, left hand servo]
// Message to Arduino: action Arduino code, left hand servo, action server id 
        byte [] msgSlide = new byte[3];
        
        List args = oscMessage.getArguments();
        msgSlide[0]=30;
        msgSlide[1]=(byte)args.get(2);
        msgSlide[2]=(byte) ((byte)args.get(1)%256); //server id on 1 byte
               
            try {
            portControl.sendData(msgSlide);
        } catch (IOException ex) {
            Logger.getLogger(MyRobot.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    

    
    public void slide(OSCMessage oscMessage){

// Format OSC = [timestamp, id, inicial position, end position]
// Message to Arduino:  action Arduino code, init pos, final pos, action server id 

        byte[] msgSlide= new byte[4]; 
        
        List args = oscMessage.getArguments();
        msgSlide[0]=40;
        msgSlide[1]=(byte)args.get(2);
        msgSlide[2]=(byte)args.get(3);
        msgSlide[3]=(byte) ((byte)args.get(1)%256); //server id on 1 byte
               
            try {
            portControl.sendData(msgSlide);
        } catch (IOException ex) {
            Logger.getLogger(MyRobot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

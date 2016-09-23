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

//Osc message: timestamp, id, ...

public abstract class Action extends Thread{
    
    private final PortControl portControl;

    public Action(PortControl portControl) {
        this.portControl = portControl;
    }
  
    public void playString(OSCMessage oscMessage){
        System.out.println("Enviou msg ao arduino");
    }
    
    public void slide(OSCMessage oscMessage){
        byte[] msgSlide= new byte[4]; //action server id , action Arduino code, init pos, final pos

    List args = oscMessage.getArguments();
    msgSlide[0]=0;
    msgSlide[1]=(byte)args.get(2);
    msgSlide[2]=(byte)args.get(3);
    //balancer des congruences dans le tas
    
            
            try {
            portControl.sendData(msgSlide);
        } catch (IOException ex) {
            Logger.getLogger(MyRobot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    }
}

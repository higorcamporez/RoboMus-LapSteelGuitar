/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robomus.instrument.fretted.lapsteelguitar;

import com.illposed.osc.OSCMessage;
import java.util.TimerTask;
import robomus.arduinoCommunication.PortControl;

/**
 *
 * @author Higor
 */
public abstract class Action extends Thread{
    
    private final PortControl portControl;

    public Action(PortControl portControl) {
        this.portControl = portControl;
    }
  
    public void playString(OSCMessage oscMessage){
        System.out.println("Enviou msg ao arduino");
    }

}

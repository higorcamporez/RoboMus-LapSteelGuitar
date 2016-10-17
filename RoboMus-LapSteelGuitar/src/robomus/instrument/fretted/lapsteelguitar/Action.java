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
    
    private PortControl portControl;

    public Action(PortControl portControl) {
        this.portControl = portControl;
    }

    public PortControl getPortControl() {
        return portControl;
    }

    public void setPortControl(PortControl portControl) {
        this.portControl = portControl;
    }
    
    public void playNote(OSCMessage oscMessage){
// Format OSC = [timestamp, id, fret, string]
// Message to Arduino: action Arduino code, fret, string, action server id 
        byte [] msgArduino = new byte[4];
        
        List args = oscMessage.getArguments();
        msgArduino[0]=(byte) 10;
        msgArduino[1]=(byte) args.get(2);
        msgArduino[2]=(byte) args.get(3);
        msgArduino[3]=(byte) ((byte)args.get(1)%256); //server id on 1 byte
               
            try {
            portControl.sendData(msgArduino);
        } catch (IOException ex) {
            Logger.getLogger(MyRobot.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }

    public void playString(OSCMessage oscMessage){
        // Format OSC = [timestamp, id, string number]
        // Message to Arduino: action Arduino code, string number, action server id
        System.out.println("PlayString");
        byte[] msgArduino = new byte[3];
        
        List<Object> args = oscMessage.getArguments();
        msgArduino[0] = (byte)30; //arduino code
        msgArduino[1] = (byte)Byte.valueOf(args.get(2).toString()); // string number  
        msgArduino[2] = (byte)((int)args.get(1)%256); //server id on 1 byte

        try {
            portControl.sendData(msgArduino);
            System.out.println("enviou ao arduino");
        } catch (IOException ex) {
            Logger.getLogger(MyRobot.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    

    
    public void slide(OSCMessage oscMessage){

// Format OSC = [timestamp, id, inicial position, end position]
// Message to Arduino:  action Arduino code, init pos, final pos, action server id 

        byte[] msgArduino= new byte[4]; 
        
        List args = oscMessage.getArguments();
        msgArduino[0]=(byte)40;
        msgArduino[1]=(byte)args.get(2);
        msgArduino[2]=(byte)args.get(3);
        msgArduino[3]=(byte) ((byte)args.get(1)%256); //server id on 1 byte
               
            try {
            portControl.sendData(msgArduino);
        } catch (IOException ex) {
            Logger.getLogger(MyRobot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /*
    Function to move bar up or down
    Format OSC = [timestamp, id, position] position = 0 -> down, 1-> up
    Message to Arduino:  action Arduino code (50), position, action server id 
    */
    public void moveBar(OSCMessage oscMessage){

        byte[] msgArduino= new byte[3]; 
        
        List args = oscMessage.getArguments();
        msgArduino[0]=(byte)50;
        msgArduino[1]=(byte)args.get(2);
        msgArduino[2]=(byte)((byte)args.get(1)%256);
        
               
            try {
            portControl.sendData(msgArduino);
        } catch (IOException ex) {
            Logger.getLogger(MyRobot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*
    Function to move the bar to a specific position
    Format OSC = [timestamp, id, frettedPosition]
    Message to Arduino:  action Arduino code (60), frettedPosition, action server id 
    */
    public void positionBar(OSCMessage oscMessage){

        byte[] msgArduino= new byte[3]; 
        
        List args = oscMessage.getArguments();
        msgArduino[0]=(byte)60;
        msgArduino[1]=(byte)args.get(2);
        msgArduino[2]=(byte)((byte)args.get(1)%256);
               
            try {
            portControl.sendData(msgArduino);
        } catch (IOException ex) {
            Logger.getLogger(MyRobot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*
    Function to turn on or turn down effect
    Format OSC = [timestamp, id, state] state = 0 -> turnoff, 1 -> turnon
    Message to Arduino:  action Arduino code (90), arduinoEffectPort, state, action server id 
    */
    public void effect(OSCMessage oscMessage, byte arduinoEffectPort){

        byte[] msgArduino= new byte[4]; 
        
        List args = oscMessage.getArguments();
        msgArduino[0] = (byte)90;
        msgArduino[1] = (byte)arduinoEffectPort;
        msgArduino[2] = (byte)args.get(2);
        msgArduino[3] = (byte)((byte)args.get(1)%256);
               
            try {
            portControl.sendData(msgArduino);
        } catch (IOException ex) {
            Logger.getLogger(MyRobot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*
    Function to stop and reset the arduino
    Format OSC = [id] 
    Message to Arduino:  action Arduino code (100), action server id 
    */
    public void stopAll(OSCMessage oscMessage){

        byte[] msgArduino= new byte[2]; 
        
        List args = oscMessage.getArguments();
        msgArduino[0] = (byte)100;     
        msgArduino[3] = (byte)((byte)args.get(1)%256);
               
            try {
            portControl.sendData(msgArduino);
        } catch (IOException ex) {
            Logger.getLogger(MyRobot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*
    Function to control the volume
    Format OSC = [timestamp, id, velocity, intensity]
    Message to Arduino:  action Arduino code (70),velocity, intensity, action server id 
    */
    public void volumeControl(OSCMessage oscMessage){

        byte[] msgArduino= new byte[4]; 
        
        List args = oscMessage.getArguments();
        msgArduino[0] = (byte)70;
        msgArduino[1] = (byte)args.get(2);
        msgArduino[2] = (byte)args.get(3);
        msgArduino[3] = (byte)((byte)args.get(1)%256);
               
            try {
            portControl.sendData(msgArduino);
        } catch (IOException ex) {
            Logger.getLogger(MyRobot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*
    Function to control the tone
    Format OSC = [timestamp, id, velocity, intensity]
    Message to Arduino:  action Arduino code (80),velocity, intensity, action server id 
    */
    public void toneControl(OSCMessage oscMessage){

        byte[] msgArduino= new byte[4]; 
        
        List args = oscMessage.getArguments();
        msgArduino[0] = (byte)80;
        msgArduino[1] = (byte)args.get(2);
        msgArduino[2] = (byte)args.get(3);
        msgArduino[3] = (byte)((byte)args.get(1)%256);
               
            try {
            portControl.sendData(msgArduino);
        } catch (IOException ex) {
            Logger.getLogger(MyRobot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
    

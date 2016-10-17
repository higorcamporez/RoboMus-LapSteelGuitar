/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robomus.instrument.fretted.lapsteelguitar;

import com.illposed.osc.OSCListener;
import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCPortIn;
import com.illposed.osc.OSCPortOut;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import robomus.arduinoCommunication.PortControl;
import robomus.instrument.fretted.FrettedInstrument;
import robomus.instrument.fretted.InstrumentString;


/**
 *
 * @author Higor ghghghghg
 */
public class MyRobot extends FrettedInstrument{
    
    private volatile Buffer buffer;
    private PortControl portControl;

    
    public MyRobot(int nFrets, ArrayList<InstrumentString> strings, String name,
            int polyphony, String OscAddress, InetAddress severAddress,
            int sendPort, int receivePort, String typeFamily, String specificProtocol) {
        super(nFrets, strings, name, polyphony, OscAddress, severAddress,
                sendPort, receivePort, typeFamily, specificProtocol);
        
        this.portControl = new PortControl("COM8",9600);
        //this.portControl = null;
        this.buffer = new Buffer(this.portControl);
        this.buffer.start();
        
        
    }
   
    public void handshake(){
     
        
        OSCPortOut sender = null;
        try {
            sender = new OSCPortOut(this.severAddress , this.sendPort);
        } catch (SocketException ex) {
            Logger.getLogger(MyRobot.class.getName()).log(Level.SEVERE, null, ex);
        }
	
        List args = new ArrayList<>();
        
        //instrument attributes
        args.add(this.name);
        args.add(this.polyphony);
        args.add(this.typeFamily);
        args.add(this.specificProtocol);
  
        //amount of attributes
        args.add(2);
        //fretted instrument attributs
        args.add(this.nFrets); //number of frets
        args.add(convertInstrumentoStringToString()); // strings and turnings

      
	OSCMessage msg = new OSCMessage("/handshake", args);
        
             
        try {
            sender.send(msg);
        } catch (IOException ex) {
            Logger.getLogger(MyRobot.class.getName()).log(Level.SEVERE, null, ex);
        }
                       
    }
    
    public void listenThread(){
        
        OSCPortIn receiver = null;            

        try {
            System.out.println("Inicio "+ this.receivePort);
            receiver = new OSCPortIn(this.receivePort);
            System.out.println("Inicio "+ this.receivePort);
        } 
        catch (SocketException ex) {
            Logger.getLogger(MyRobot.class.getName()).log(Level.SEVERE, null, ex);
            
        }
         
        OSCListener listener = new OSCListener() {
            
            public void acceptMessage(java.util.Date time, OSCMessage message) {
                System.out.println("Receved msg");
                List l = message.getArguments();
                for (Object l1 : l) {
                    System.out.println("object=" + l1);                
                }
                //verificar se a mensagem é válida
                buffer.add(message);
                buffer.print();
            }
        };
        receiver.addListener(this.OscAddress, listener);
        receiver.startListening(); 
        
    }

  
    public void ConfirmMsgToServ(){
        
        List args = new ArrayList<>();
        
        try {           
            byte arduinoId = portControl.receiveData();
            for (long serverId : buffer.sentMessageId){
            if (serverId%256 == arduinoId){
                args.add(serverId);
                break;
            }
        }
        }
        catch (IOException ex) {
            Logger.getLogger(Buffer.class.getName()).log(Level.SEVERE, null, ex);           
        }
                        
        OSCPortOut sender = null;
        try {
            sender = new OSCPortOut(this.severAddress , this.sendPort);
        } catch (SocketException ex) {
            Logger.getLogger(MyRobot.class.getName()).log(Level.SEVERE, null, ex);
        }
        

        OSCMessage msg = new OSCMessage("/handshake", args);
             
        try {
            sender.send(msg);
        } catch (IOException ex) {
            Logger.getLogger(MyRobot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public static void main(String[] args) {
        
        ArrayList<InstrumentString> l = new ArrayList();
        l.add(new InstrumentString(0, "A"));
        l.add(new InstrumentString(0, "B"));
        String specificP = "</slide;posicaoInicial_int><hgyuiyugyu>";
        
        try {
            MyRobot myRobot = new MyRobot(12, l, "laplap", 6, "/laplap", InetAddress.getByName("192.168.1.232"),
                    12345, 1234, "Fretted", specificP);
            
            myRobot.handshake();
            myRobot.listenThread();
            myRobot.ConfirmMsgToServ();
            //Thread thread = new Thread("bufferProcess");
        } catch (UnknownHostException ex) {
            Logger.getLogger(MyRobot.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
                
    }


        
    
}

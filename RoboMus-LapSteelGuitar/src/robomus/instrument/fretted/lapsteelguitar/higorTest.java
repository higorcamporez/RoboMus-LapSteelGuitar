/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robomus.instrument.fretted.lapsteelguitar;

import com.illposed.osc.OSCListener;
import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCPortIn;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import robomus.instrument.fretted.InstrumentString;

/**
 *
 * @author Higor
 */
public class higorTest {
    
    public static void main(String[] args) {
       
        ArrayList<InstrumentString> l = new ArrayList<InstrumentString>();
        l.add(new InstrumentString(0, "A"));
        l.add(new InstrumentString(0, "B"));
        String specificP = "</slide;posicaoInicial_int><hgyuiyugyu>";
        
        
            MyRobot myRobot = null;
        try {
            myRobot = new MyRobot(12, l, "laplap", 6, "/laplap/*", InetAddress.getByName("192.168.1.163"),
                    12345, 1234, "Fretted", specificP);
            //myRobot.handshake();
            myRobot.listenThread();
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(higorTest.class.getName()).log(Level.SEVERE, null, ex);
        } 
            
          
        
        
        
    }
                
    
    
}

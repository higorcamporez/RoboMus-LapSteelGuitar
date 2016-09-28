/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robomus.arduinoCommunication;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.SerialPort;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author 
 */
public class PortControl {
    
    private OutputStream serialOut;
    private int rate;
    private String portCOM;

    /**
     * Construtor da classe ControlePorta
     * @param portaCOM - Porta COM que será utilizada para enviar os dados para o arduino
     * @param taxa - Taxa de transferência da porta serial geralmente é 9600
     */
    public PortControl(String portaCOM, int taxa) {
      this.portCOM = portaCOM;
      this.rate = taxa;
      this.initialize();
    }     

    /**
     * Médoto que verifica se a comunicação com a porta serial está ok
     */
    private void initialize() {
      try {
        //Define uma variável portId do tipo CommPortIdentifier para realizar a comunicação serial
        CommPortIdentifier portId = null;
        try {
          //Tenta verificar se a porta COM informada existe
          portId = CommPortIdentifier.getPortIdentifier(this.portCOM);
        }catch (NoSuchPortException npe) {
          //Caso a porta COM não exista será exibido um erro 
            System.out.println("Porta COM não encontrada");
            return;
        }
        //Abre a porta COM 
        SerialPort port = (SerialPort) portId.open("Comunicação serial", this.rate);
        serialOut = port.getOutputStream();
        port.setSerialPortParams(this.rate, //taxa de transferência da porta serial 
                                 SerialPort.DATABITS_8, //taxa de 10 bits 8 (envio)
                                 SerialPort.STOPBITS_1, //taxa de 10 bits 1 (recebimento)
                                 SerialPort.PARITY_NONE); //receber e enviar dados
      }catch (Exception e) {
        e.printStackTrace();
      }
  }

    /**
     * Método que fecha a comunicação com a porta serial
     */
    public void close() throws IOException {
      //try {
          serialOut.close();
          
      //}catch (IOException e) {
       //   System.out.println("Não foi possível fechar porta COM.");
      //}
    }

    /**
     * @param data - Valores a serem enviados pela porta serial
     */
    public void sendData(byte[] data) throws IOException{
      
        serialOut.write(data);//escreve o valor na porta serial para ser enviado
      
    }
    public void sendData(int data) throws IOException{
      
        serialOut.write(data);//escreve o valor na porta serial para ser enviado
      
    }
    /*
    public static void main(String[] args) {
        PortControl portControl = new PortControl("COM7", 9600);
        
        byte[] b = {49,48,49,48};
        
        try {
            //portControl.sendData(49);
            //portControl.sendData(48);
            portControl.sendData(b);
            System.out.println("enviou");
            portControl.close();
        } catch (IOException ex) {
            Logger.getLogger(PortControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }*/
}

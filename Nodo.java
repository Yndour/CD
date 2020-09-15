package Calcu;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Nodo {


	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		ServerSocket Nodo = new ServerSocket(9997);
		while (true) {
			Socket miSocket = null;
			
			miSocket = Nodo.accept();
			System.out.println("Nodo conectado y esperando información");
			Socket miSocketServer = new Socket("192.168.1.66", 9998);
			
			DataInputStream flujoentradaC= new DataInputStream(miSocket.getInputStream());
			DataOutputStream flujosalidaS = new DataOutputStream(miSocketServer.getOutputStream());
			
			
			String cadenaC = flujoentradaC.readUTF();
			
			flujosalidaS.writeUTF(cadenaC);
			
			DataInputStream flujoentradaS = new DataInputStream(miSocketServer.getInputStream());
			DataOutputStream flujosalidaC = new DataOutputStream(miSocket.getOutputStream());
			
			String resultado = flujoentradaS.readUTF();
			
			flujosalidaC.writeUTF(resultado);
			
			
			
		}
	}

}

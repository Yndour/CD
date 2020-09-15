package Calcu;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor /*implements Runnable*/{
	

	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//Thread miHilo = new Thread();
		//miHilo.start();
		
		System.out.println("Ejecutando Servidor");
		//Puerto a la escucha
		ServerSocket miServidor = new ServerSocket(9998);
		System.out.println("Servidor inicializado");
		Socket miSocket = null;
		while(true) {
			
			try {
			
				//Acepta todas las conexiones que viajan por el socket y se queda a la espera
				miSocket = miServidor.accept();
				

				//flujo de entrada de datos
				DataInputStream flujoentrada = new DataInputStream(miSocket.getInputStream());
				DataOutputStream flujosalida = new DataOutputStream(miSocket.getOutputStream());
			
				//Thread miHilo = new ServidoControl(miSocket, flujoentrada, flujoentrada2, flujoentrada3,
				//		flujosalida, flujosalida2);
				Thread miHilo = new ServidoControl(miSocket, flujoentrada, flujosalida);
				miHilo.start();
				
				System.out.println("Abrimos hilo");
				

				System.out.println("Servidor en espera");
			
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			miSocket.close();
			}
		}
	}
}

	

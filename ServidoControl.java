package Calcu;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServidoControl extends Thread{
	
	final DataInputStream flujoentrada;
	//final DataInputStream flujoentrada2;
	//final DataInputStream flujoentrada3;
	final DataOutputStream flujosalida;
	//final DataOutputStream flujosalida2;
	final Socket miSocket;
	
	//public ServidoControl(Socket miSocket, DataInputStream flujoentrada, DataInputStream flujoentrada2, DataInputStream flujoentrada3, DataOutputStream flujosalida, DataOutputStream flujosalida2) {
	public ServidoControl(Socket miSocket, DataInputStream flujoentrada, DataOutputStream flujosalida) {
		this.miSocket = miSocket;
		this.flujoentrada = flujoentrada;
		//this.flujoentrada2 = flujoentrada2;
		//this.flujoentrada3 = flujoentrada3;
		this.flujosalida = flujosalida;
		//this.flujosalida2 = flujosalida2;
	}

	
	public String Calcular (float numero1, float numero2, float indicador) {
		int index = (int)indicador;
		int indicaOp = 0;
		float ResultOpera = 0;
		String resultado = "";
		System.out.println("valor del indicador switch: " + indicador);
		switch (index) {
		case 1: {
			indicaOp = 11;
			ResultOpera = numero1 + numero2;
			resultado = ResultOpera + "," + indicaOp;
			return resultado;
		}
		case 2: {
			indicaOp = 22;
			ResultOpera = numero1 - numero2;
			resultado = ResultOpera + "," + indicaOp;
			return resultado;
		}
		case 3: {
			indicaOp = 33;
			ResultOpera = numero1 * numero2;
			resultado = ResultOpera + "," + indicaOp;
			return resultado;
		}
		case 4: {
			indicaOp = 44;
			if (numero2 == 0) {
				ResultOpera = 0;
				resultado = ResultOpera + "," + indicaOp;
				return resultado;
			}
			ResultOpera = numero1 / numero2;
			resultado = ResultOpera + "," + indicaOp;
			return resultado;
		}
		default:{
			System.out.println("Código erróneo");
			resultado = "0.0,0.0";
			return resultado;
			}
		}
	}
		
	@Override
	public void run() {
		while(true) {
		
			try {
				String cadena = flujoentrada.readUTF();

				System.out.println("Imprimiendo la cadena: " + cadena);
				
				String[] separador = cadena.split(",");
				float[] separa = new float[separador.length];
				
				for (int i = 0; i < separador.length; i++) {
					
					float quita = Float.parseFloat(separador[i]);
					separa[i] = quita;
					System.out.println(separa[i]);
				}
				String mensaje = Calcular(separa[0], separa[1], separa[2]);
				System.out.println("el resultado es y index: " + mensaje);
				
				flujosalida.writeUTF(mensaje);
	
			} catch (IOException e) {
				// TODO: handle exception
				e.printStackTrace();
				}
			}
	}
}


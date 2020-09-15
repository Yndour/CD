package Calcu;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class Cliente extends Application{
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/Calcu/InterfazCliente.fxml"));
			Scene scene = new Scene(root,400,812);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	} //-------------------AQUÍ TERMINA EL     START -------------------
	
	@FXML
	public Label result;
	private float number1 = 0, number2 = 0;
	private boolean inicio = true;
	private String operador ="";
	public TextArea ResSuma, ResMulti, ResDiv, ResResta;

	
	@FXML
	public void ProcesaNum(ActionEvent event) {
		if(inicio) {
			result.setText("");
			inicio = false;
		}
		
		String value = ((Button)event.getSource()).getText();
		result.setText(result.getText() + value);
	} // ----------------- AQUI TERMINA EL ProcesaNum-----------
	
	
	public int TipoOpera(String operacion) {
		switch (operacion) {
		case "+": {
			return 1;
		}
		case "-": {
			return 2;
		}
		case "*": {
			return 3;
		}
		case "/": {
			return 4;
		}
		default:
			System.out.println("Operación no leída");
			return 0;
		}
	}// --------------------------- AQUI TERMINA TipoOpera--------------------------
	
	
	public void LeerOpera( float resultadoOpera, float indicando) {
		int idx = (int)indicando;
		String resultado = "";
		switch (idx) {
		case 11: {
			resultado = number1 + " + " + number2 + " = " + resultadoOpera;
			ResSuma.appendText(resultado + "\n");
			break;
		}
		case 22: {
			resultado = number1 + " - " + number2 + " = " + resultadoOpera;
			ResResta.appendText(resultado + "\n");
			break;
		}
		case 33: {
			resultado = number1 + " * " + number2 + " = " + resultadoOpera;
			ResMulti.appendText(resultado + "\n");
			break;
		}
		case 44: {
			resultado = number1 + " / " + number2 + " = " + resultadoOpera;
			ResDiv.appendText(resultado + "\n");
			break;
		}
		default:
			System.out.println("El codigo recibido es erroneo");
			break;
		}
	} // -------------------------------- AQUI TERMINA LeerOpera --------------------------
	
	@FXML
	public void ProcesaOperadores(ActionEvent event) {
		String value = ((Button)event.getSource()).getText();
		if(!value.equals("=")) {
			if(!operador.isEmpty()) {
				return;
			}
			operador = value;
			number1 = Float.parseFloat(result.getText());
			result.setText("");
		} else {
			if(operador.isEmpty()) {
				return;
			}
			number2 = Float.parseFloat(result.getText());
			
			
			int index = TipoOpera(operador);
			try {
				//Creacion del Socket
				Socket miSocket = new Socket("192.168.1.66", 9997);
				
				//Creacion del canal de flujo de datos
				DataOutputStream flujosalida = new DataOutputStream (miSocket.getOutputStream());
				DataInputStream flujoentrada = new DataInputStream (miSocket.getInputStream());
				//DataInputStream flujoentrada2 = new DataInputStream (miSocket.getInputStream());
				
				//Thread miHilo = new ClienteControl (miSocket, flujosalida, flujosalida2, flujosalida3, flujoentrada, 
					//	flujoentrada2, number1, number2, index);
				//miHilo.start();
				
				float cadena[] = {number1, number2, index};
				
				flujosalida.writeUTF(cadena[0] + "," + cadena[1] + "," + cadena[2]);

				System.out.println("Cliente: ----Mando operación");

				String cadenaS = flujoentrada.readUTF();
				String[] separador = cadenaS.split(",");
				float[] separa = new float[separador.length];
				
				for (int i = 0; i < separador.length; i++) {
					
					float quita = Float.parseFloat(separador[i]);
					separa[i] = quita;
					System.out.println(separa[i]);
				}
				
				
				result.setText(String.valueOf(separa[0]));
				LeerOpera(separa[0], separa[1]);
				
				/*int idx = (int)separa[1];
				System.out.println("El codigo es: " + idx + "---");
				String resultado = "";
				switch (idx) {
				case 11: {
					resultado = number1 + " + " + number2 + " = " + separa[0];
					ResSuma.setText(resultado + "\n");
				}
				case 22: {
					resultado = number1 + " - " + number2 + " = " + separa[0];
					ResResta.setText(resultado + "\n");
				}
				case 33: {
					resultado = number1 + " * " + number2 + " = " + separa[0];
					ResMulti.setText(resultado + "\n");
				}
				case 44: {
					resultado = number1 + " / " + number2 + " = " + separa[0];
					ResDiv.setText(resultado + "\n");
				}
				default:
					System.out.println("El codigo recibido es erroneo");
				}*/
				
								
				System.out.println("Cliente: ----Asigno valor");
				//miSocket.close();
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			operador = "";
			inicio = true;
			
		}
	} // ----------------- AQUI TERMINA EL ProcesaOperadores-------------

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
		
	}

}

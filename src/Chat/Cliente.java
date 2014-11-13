package Chat;

/**
 * Ahora cuando se cierra el servidor el cliente desconecta de forma ordenada.
 * 
 * 
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class Cliente extends Thread{
	
	private String direccion;
	private int port;
	private Socket sck;
	
	public Cliente() {
		direccion = "127.0.0.1";
		port = 5050;
	}
	public Cliente(String d, int p) {
		direccion = d;
		port = p;
	}
	
	public boolean Connect() {
		try {

			System.out.println("-----Cliente intentando la conexión...");
			sck = new Socket(direccion, port);
			sck.setTcpNoDelay(true);
			System.out.println("-----Conexión establecida en " + direccion+":"+port);
			return true;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("-----Ha ocurrido un error de conexion.");
			return false;
		}
	}
	
	public boolean Disconnect() {
		try {
			sck.close();
			System.out.println("-----Desconectado Correctamente");
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("-----Error de Desconexión.");
			return false;
		}
	}
	
	public boolean sendMessage() {
		try {
			PrintWriter SocketOut = new PrintWriter(sck.getOutputStream());								// Lo que esto imprima, lo enviará.
			BufferedReader SocketIn = new BufferedReader(new InputStreamReader(sck.getInputStream()));	//Para ir recibiendo las respuestas.
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));				// Para que lea lo que escribo. También se puede hacer con un Scanner(System.in);
			System.out.println("Conectado a " + direccion+":"+port+"  Ahora puede enviar.");
			String mensaje = reader.readLine();
			
			while(!mensaje.equals("FIN")) {
				SocketOut.println(mensaje);
				SocketOut.flush();
				System.out.println("Conectado a " + direccion+":"+port+"  Mensaje enviado");
				System.out.println("Conectado a " + direccion+":"+port+"  Se recibió del servidor: "+SocketIn.readLine() + "\n\n\n");
				
				System.out.println("Conectado a " + direccion+":"+port+"  Ahora puede enviar.");
				mensaje = reader.readLine();				
			}
			
			System.out.println("-----Se envió FIN. Cerrando conexión...");
			SocketOut.println("FIN");
			SocketOut.flush();			
			
			this.Disconnect();
			
			
			return true;
		} catch (IOException e) {
			System.out.println("-----Error al enviar mensaje. La conexion se ha caido");
			Disconnect();
			return false;
		}
		
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//También He añadido el 'extends Thread
	public void run() {
		Connect();
		sendMessage();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Cliente cl = new Cliente();
	//	Cliente cl = new Cliente(args[0], Integer.parseInt(args[1]));
		cl.run(); 									// Porque no necesita ser concurrente.
		
	}

}

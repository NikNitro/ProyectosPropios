package Chat;
import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class Servidor extends Thread{
	
	private int port;
	private ServerSocket server;
	private Socket sck;
	
	public Servidor() {
		port = 5050;
		try {
			server = new ServerSocket(port, 1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public Servidor(int i) {
		port = i;
		try {
			server = new ServerSocket(port, 1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public boolean Connect() {
		try {
		
			System.out.println("Servidor esperando conexión.");
			sck = server.accept();
			
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error con la conexión");
			return false;
		}
		
	}
	
	public boolean Disconnect() {
		try {

			sck.close();
			
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error al desconectar");
			return false;
		}
		
	}
	
	public boolean receiveMensaje() {
		try {
			PrintWriter printer = new PrintWriter(sck.getOutputStream(), true);
			BufferedReader reader = new BufferedReader(new InputStreamReader(sck.getInputStream()));
			
			System.out.println("Conectado a "+ sck.getInetAddress()+":"+port+"\nEsperando Mensaje...");
			
			sck.setSoTimeout(40*1000);		
			String mensaje = reader.readLine();
			while(sck.isConnected() && !mensaje.equals("FIN")) {

				System.out.println("Recibido: "+mensaje);
				if(!mensaje.equals("FIN")) {
					
					printer.println(mensaje.toUpperCase());
					mensaje = reader.readLine();
					
				}
			}
			
			
			this.Disconnect();
						
			return true;
		} catch (IOException e) {
			System.out.println("Error: Compruebe su conexion o que no ha estado más de 40 segundos inactivo.");
			Disconnect();
			return false;
		}
	}
	
	public void run() {
		while(true) {
			Connect();
			receiveMensaje();
		}
	}

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Servidor sv = new Servidor();
//		Servidor sv = new Servidor(Integer.parseInt(args[0]));
		sv.run(); 									// Porque no necesita ser concurrente.
	}

}


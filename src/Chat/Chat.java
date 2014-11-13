package Chat;

/*
 * Debería hacerlo con concurrencia para que el servidor espere y el cliente se conecte.
 * Puede que de esa forma funcione.
 * 
 */

public class Chat {
	private Cliente  cl;
	private Servidor sv;
	
	public Chat() {
		cl = new Cliente();
		sv = new Servidor();
		//////////////////////////////////////////////
		Arrancar();
		/////////////////////////////////////////////
	}
	
	public Chat(String ipServ, int puertSend, int puertRec) {
		cl = new Cliente(ipServ, puertSend);
		sv = new Servidor(puertRec);
	}
	
	////////////////////////////////////////////////////// Procedimiento Arrancar() cambiado entero
	public void Arrancar() {
		sv.start();
		cl.start();
		
		try {
			sv.join();
			cl.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Chat();
	}

}

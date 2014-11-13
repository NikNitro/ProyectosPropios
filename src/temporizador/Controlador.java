package temporizador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controlador implements ActionListener{
	
	private VistaApagado panel;
	
	public Controlador(VistaApagado panel) {
		this.panel = panel;
	}



	@Override
	public void actionPerformed(ActionEvent arg0) {
		String msg = arg0.getActionCommand();
		
		if(msg.equals("Aceptar")) {
			panel.apagar();
		} else if(msg.equals("Timer")) {
			panel.Activar();
		} else if(msg.equals("NoTimer")) {
			panel.Inicializar();
		}
	}
	
}

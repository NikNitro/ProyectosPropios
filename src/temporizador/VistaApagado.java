package temporizador;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

public class VistaApagado extends JFrame{
	private JPanel panPrincipal;
	private ButtonGroup grupAccion;
	private ButtonGroup grupCom;
	private JRadioButton radApagar;
	private JRadioButton radAnular;
	private JRadioButton radHibernar;
	private JRadioButton radReiniciar;
	private JRadioButton radHora;
	private JRadioButton radTiempo;
	private JLabel labQueHacer;
	private JLabel labH;
	private JLabel labM;
	private JLabel labS;
	private JLabel labInformador;
	private JTextArea texH;
	private JTextArea texM;
	private JTextArea texS;
	private JButton butAceptar;
	
	public VistaApagado() {
		panPrincipal = new JPanel(new GridLayout(6, 2));
		radApagar = new JRadioButton("Apagar");
		radAnular = new JRadioButton("Anular Apagado");
		radHibernar = new JRadioButton("Hibernar");
		radReiniciar = new JRadioButton("Reiniciar");
		radHora = new JRadioButton("A la hora...");
		radTiempo = new JRadioButton("Dentro de...");
		labQueHacer = new JLabel("  ¿Qué desea hacer?");
		labH = new JLabel("horas");
		labM = new JLabel("mins");
		labS = new JLabel("segs");
		labInformador = new JLabel("   Preparado..");
		texH = new JTextArea();
		texM = new JTextArea();
		texS = new JTextArea();
		butAceptar = new JButton("Aceptar");
		
		grupAccion = new ButtonGroup();
		grupAccion.add(radApagar);
		grupAccion.add(radHibernar);
		grupAccion.add(radReiniciar);
		grupAccion.add(radAnular);
		
		grupCom = new ButtonGroup();
		grupCom.add(radHora);
		grupCom.add(radTiempo);
		
		JPanel tiempoAux = new JPanel(new GridLayout(1,6));
		tiempoAux.add(texH);
		tiempoAux.add(labH);
		tiempoAux.add(texM);
		tiempoAux.add(labM);
		tiempoAux.add(texS);
		tiempoAux.add(labS);
		
		
		panPrincipal.add(labQueHacer);	panPrincipal.add(new JLabel());
		panPrincipal.add(radApagar);	panPrincipal.add(radHora);
		panPrincipal.add(radReiniciar);	panPrincipal.add(radTiempo);
		panPrincipal.add(radHibernar);	panPrincipal.add(tiempoAux);
		panPrincipal.add(radAnular);	panPrincipal.add(new JLabel());
		panPrincipal.add(labInformador);panPrincipal.add(butAceptar);
		
		Inicializar();
		
		this.add(panPrincipal);
	}
	
	public void cambiaTexto(String std) {
		labInformador.setText(std);
	}
	
	public void controlador(ActionListener ctr) {
		butAceptar.addActionListener(ctr);
		butAceptar.setActionCommand("Aceptar");
		
		radApagar.addActionListener(ctr);
		radApagar.setActionCommand("Timer");
		
		radAnular.addActionListener(ctr);
		radAnular.setActionCommand("NoTimer");
		
		radHibernar.addActionListener(ctr);
		radHibernar.setActionCommand("NoTimer");
		
		radReiniciar.addActionListener(ctr);
		radReiniciar.setActionCommand("Timer");
		
	}
	
	public void Inicializar() {
		radHora.setEnabled(false);
		radTiempo.setEnabled(false);
		texH.setEnabled(false);
		texM.setEnabled(false);
		texS.setEnabled(false);
	}
	
	public void Activar() {
		radHora.setEnabled(true);
		radTiempo.setEnabled(true);
		texH.setEnabled(true);
		texM.setEnabled(true);
		texS.setEnabled(true);
	}
	
	public int getHoras() {
		String std = texH.getText();
		if(std.length()==0)
			return 0;
		else
			return Integer.parseInt(std);
	}
	public int getMins() {
		String std = texM.getText();
		if(std.length()==0)
			return 0;
		else
			return Integer.parseInt(std);
	}
	public int getSecs() {
		String std = texS.getText();
		if(std.length()==0)
			return 0;
		else
			return Integer.parseInt(std);
	}
	
	private int calcularTiempo() {
		int tiempo = getSecs();
		tiempo = tiempo + getMins()*60;
		tiempo = tiempo + getHoras()*3600;
		return tiempo;
	}
	
	private int calcularHora() {
		Calendar calendario = Calendar.getInstance();
		int horaActual = calendario.get(Calendar.HOUR_OF_DAY);
		int minutoActual = calendario.get(Calendar.MINUTE);
		int segundoActual= calendario.get(Calendar.SECOND);
		
		Date actual = new Date(0, 0, 0, horaActual, minutoActual, segundoActual);
		Date nueva = new Date(0, 0, 0, getHoras(), getMins(), getSecs());
		
		if(nueva.after(actual)) {
			long i = (nueva.getTime() - actual.getTime()) * 1000; //Por mil porque son milisegundos
			return (int) i;
		} else 
			return 0;
	}
	
	public void apagar() {
		try {
			Runtime r = Runtime.getRuntime();
			
			if(radAnular.isSelected()) {				//Hibernación y anulación no usan temporizador
				String []cmd = {"shutdown", "-a"};
				r.exec(cmd);
				cambiaTexto("Apagado anulado");
			} else if(radHibernar.isSelected()) {
				String []cmd = {"shutdown", "-h"};
				r.exec(cmd);
				cambiaTexto("Hibernación programada");
			} else {
				String tiempo = "";
				String modo   = "";
				if(radApagar.isSelected()) {
					modo = "-s";
					if(radHora.isSelected()) {
						tiempo = "" + calcularHora();
					} else {
						tiempo = ""+calcularTiempo();
					}
					cambiaTexto("Apagado programado");
				
					
				} else if(radReiniciar.isSelected()) {
					modo = "-r";
					if(radHora.isSelected()) {
						tiempo = "" + calcularHora();
					} else {
						tiempo = ""+calcularTiempo();
					}
					cambiaTexto("Reinicio programado");
					
				} else {
					cambiaTexto("No has elegido nada");
				}
				
				String [] cmd = {"shutdown", modo, "-f", "-t", tiempo};
				r.exec(cmd);
			}
			Inicializar();
		} catch (IOException e ) {
			System.out.println("ERROR: "+e.toString());
			cambiaTexto("Ha ocurrido un error");
		}
	}
	
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		VistaApagado v = new VistaApagado();
		v.setTitle("Apagado Programado by NikNitro!");
		v.setSize(470,200);
		v.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		v.setVisible(true);
		
		Controlador ctr = new Controlador(v);
		v.controlador(ctr);

	}

}

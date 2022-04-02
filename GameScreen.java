/*
	Checklist:
	
	Implementar ayudas en dos renglones para nivel 2 - SIGUIENTE
	Implementar diálogos dinámicos
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class GameScreen extends Escenario {
	

	private final double PIXEL_CONSTANT = 4.0/3.0;


	private double
		angulo_flecha,
		lado_opuesto_flecha,
		hipotenusa_flecha;


	private final int 
		TABLA			= 3, // Y la tabla?
		CANTIDAD_CUENTAS,
		FACTOR_MINIMO,
		FPS			= 60,

		ANGULO_PUNTA 		= 20,
		LARGO_PUNTA 		= 20,

		BASE_ESPACIO_LIBRE,
		ALTURA_ESPACIO_LIBRE	= 150,
		BASE_CUENTAS		= 85,
		ALTURA_CUENTAS		= 19,
		ALTURA_AYUDAS		= 26,
		BASE_RESULTADOS		= 72,
		ALTURA_RESULTADOS	= 50,
		MARGEN			= 40,
		ESPACIO_OCIOSO		= 120,
		DISTANCIA_PUERTO	= 15,
		DIAMETRO_PUERTO		= 7,

		COORD_DIALOGO_X		= 416,
		COORD_DIALOGO_Y		= 538,
		COORD_NNIVEL_X		= 406,
		COORD_NNIVEL_Y		= 27,

		COORDS_CUENTAS_X[], 	COORDS_CUENTAS_Y[],

		COORDS_AYUDAS_X[], 	COORDS_AYUDAS_Y[],

		BASES_AYUDAS[],

		COORDS_RDOS_X[], 	COORDS_RDOS_Y[],

		COORDS_PPOG_X[], 	COORDS_PPOG_Y[],

		COORDS_CPOG_X[], 	COORDS_CPOG_Y[],

		COORDS_CPDE_X[], 	COORDS_CPDE_Y[],

		COORDS_PPDE_X[], 	COORDS_PPDE_Y[],

		COORDS_FCHAENDS_X[]	= new int[2],
		COORDS_FCHAENDS_Y[]	= new int[2],

		RESULTADOS[], 		RDOS_UBICACION[];


	private final boolean 
		ON_TESTING_MODE = false,
		AYUDAS_DEPLOY[],
		ENTRADA_XIZQ_CTAS[],
		ENTRADA_XIZQ_RDOS[],
		RESP_CORRECTAMENTE;


	private int 
		coord_flecha_x, 
		coord_flecha_y, 
		cuenta_seleccionada 	= -1,
		resultado_seleccionado 	= -1,
		keyframes_entrada,
		keyframes_dialogo,
		keyframes_color;


	private final Timer ANIMACION = new Timer(16, e -> actionPerformedAnimacion());


	private final String 
		DIALOGOS_POSIBLES[] = new String[] {
			"Sample Text",
			"Hola",
			"Buen trabajo!"
		},
		CUENTAS[],
		AYUDAS[];


	private String dialogo = DIALOGOS_POSIBLES[0];


	private final Font 
		FUENTE_DIALOGO 		= new Font(Font.SANS_SERIF, Font.PLAIN, (int)(20d * PIXEL_CONSTANT)),
		FUENTE_CUENTAS 		= new Font(Font.SANS_SERIF, Font.PLAIN, (int)(18d * PIXEL_CONSTANT)),
		FUENTE_AYUDAS 		= new Font(Font.SANS_SERIF, Font.PLAIN, (int)(9d  * PIXEL_CONSTANT)),
		FUENTE_RESULTADOS 	= new Font(Font.SANS_SERIF, Font.BOLD,  (int)(50d * PIXEL_CONSTANT)),
		FUENTE_NNIVEL 		= new Font(Font.SANS_SERIF, Font.BOLD,  (int)(20d * PIXEL_CONSTANT));


	private final Color 
		COLORES[] = {
			Color.RED,
			Color.BLUE,
			Color.GREEN,
			Color.YELLOW,
			Color.CYAN,
			Color.ORANGE
		},
		COLOR_SIN_RESOLVER = new Color(50, 50, 50);

	
	public GameScreen(boolean es_nivel_1, Runnable r, JPanel p) {
		super(r, p);

		// inicializar(); // Se llama en el método transicionar en MainFrame
		
		// Inicialización de campos constantes dependientes de es_nivel_1
		
		FACTOR_MINIMO 		= es_nivel_1 ? 2 : 6;
		// CANTIDAD_CUENTAS y dependencias
		CANTIDAD_CUENTAS 	= es_nivel_1 ? 4 : 5;
		BASE_ESPACIO_LIBRE 	= 720/CANTIDAD_CUENTAS;
		
		COORDS_CUENTAS_X[] 	= new int[CANTIDAD_CUENTAS];
		COORDS_CUENTAS_Y[] 	= new int[CANTIDAD_CUENTAS];

		COORDS_AYUDAS_X[] 	= new int[CANTIDAD_CUENTAS];
		COORDS_AYUDAS_Y[] 	= new int[CANTIDAD_CUENTAS];

		BASES_AYUDAS[] 		= new int[CANTIDAD_CUENTAS];

		COORDS_RDOS_X[] 	= new int[CANTIDAD_CUENTAS];
		COORDS_RDOS_Y[] 	= new int[CANTIDAD_CUENTAS];

		COORDS_PPOG_X[]		= new int[CANTIDAD_CUENTAS];
		COORDS_PPOG_Y[] 	= new int[CANTIDAD_CUENTAS];

		COORDS_CPOG_X[] 	= new int[CANTIDAD_CUENTAS];
		COORDS_CPOG_Y[] 	= new int[CANTIDAD_CUENTAS];

		COORDS_CPDE_X[] 	= new int[CANTIDAD_CUENTAS];
		COORDS_CPDE_Y[] 	= new int[CANTIDAD_CUENTAS];

		COORDS_PPDE_X[] 	= new int[CANTIDAD_CUENTAS];
		COORDS_PPDE_Y[] 	= new int[CANTIDAD_CUENTAS];

		RESULTADOS[] 		= new int[CANTIDAD_CUENTAS];
		RDOS_UBICACION[] 	= new int[CANTIDAD_CUENTAS];
		
		AYUDAS_DEPLOY[] 	= new boolean[CANTIDAD_CUENTAS];
		ENTRADA_XIZQ_CTAS[]	= new boolean[CANTIDAD_CUENTAS];
		ENTRADA_XIZQ_RDOS[]	= new boolean[CANTIDAD_CUENTAS];
		RESP_CORRECTAMENTE[]	= new boolean[CANTIDAD_CUENTAS];
		
		CUENTAS[] 		= new String[CANTIDAD_CUENTAS];
		AYUDAS[] 		= new String[CANTIDAD_CUENTAS];
		
		//--------------------------------------------------------------------
		
		bg_imageicon = new ImageIcon("Fondos/juego.png");

		mouse_motion_listener = new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				if (cuenta_seleccionada >= 0) {
					coord_flecha_x = e.getX();
					coord_flecha_y = e.getY();
					setPuntaFlecha();

					PANEL_ASOCIADO.repaint();
				}
			}
		};

		mouse_listener = new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (ANIMACION.isRunning()) return;

				coord_flecha_x = e.getX();
				coord_flecha_y = e.getY();
				cuenta_seleccionada = (coord_flecha_x - MARGEN) / BASE_ESPACIO_LIBRE;
				if (	isBetween(coord_flecha_y, MARGEN, MARGEN + ALTURA_ESPACIO_LIBRE) 
				&& 	cuenta_seleccionada < CANTIDAD_CUENTAS
				&&	!RESP_CORRECTAMENTE[cuenta_seleccionada]) {
						setPuntaFlecha();
						PANEL_ASOCIADO.repaint();
				}
				else cuenta_seleccionada = -1;
			}

			public void mouseReleased(MouseEvent e) {
				if (ANIMACION.isRunning()) return;

				for (int i = 0; i < CANTIDAD_CUENTAS; i++)
					if (	isBetween(coord_flecha_x, COORDS_RDOS_X[i], COORDS_RDOS_X[i] + BASE_RESULTADOS)
					&&	isBetween(coord_flecha_y, COORDS_RDOS_Y[i] - ALTURA_RESULTADOS - DISTANCIA_PUERTO - DIAMETRO_PUERTO, COORDS_RDOS_Y[i])) {
							resultado_seleccionado = i; 
							break;
					}

				if (resultado_seleccionado >= 0 && cuenta_seleccionada >= 0) {
					RESP_CORRECTAMENTE[cuenta_seleccionada] = esRespuestaCorrecta();
					AYUDAS_DEPLOY[cuenta_seleccionada] |= !esRespuestaCorrecta();
				}

				// if (ON_TESTING_MODE)
				// 	if (resultado_seleccionado >= 0) System.out.println(esRespuestaCorrecta());
				// 	else System.out.println("no calculado");


				cuenta_seleccionada = resultado_seleccionado = -1;
				PANEL_ASOCIADO.repaint();
			}

			/*
			public void mouseClicked(MouseEvent e) {
				if (ANIMACION.isRunning()) return;
				
				if (ON_TESTING_MODE) {
					dialogo = DIALOGOS_POSIBLES[(int)(Math.random() * (double)DIALOGOS_POSIBLES.length)];
					inicializar();
					PANEL_ASOCIADO.repaint();
				}
			}
			*/
		};
	}


	private boolean esRespuestaCorrecta() {
		return RDOS_UBICACION[resultado_seleccionado] == cuenta_seleccionada;
	}


	private final void actionPerformedAnimacion() {
		if (++keyframes_entrada == 0) ANIMACION.stop();
		PANEL_ASOCIADO.repaint();
	}


	// private boolean isBetween(Comparable x, Comparable a, Comparable b) {
		// return x.compareTo(a) >= 0 && x.compareTo(b) <= 0;
	private boolean isBetween(int x, int a, int b) {
		// System.out.println(String.format("%d <= %d <= %d ? %s\n", a, x, b, x >= a && x <= b ? "si" : "no"));
		return x >= a && x <= b;
	}


	private int curvaAnimacion(int t, int delta_x) {
		return -delta_x * t * t / FPS / FPS; // -dxt^2/fps^2
	}


	private int asignarBaseAyuda(int factor) {
		// 7 pixeles asignados a caracteres anchos, 3 pixeles asignados a caracteres estrechos (fuente tamaño 9)
		return factor*2*7 + (factor*2-1)*3;
	}


	private void mezclarResultados() {
		for (int i = 0, k; i < RESULTADOS.length; i++) {
			k = (int)(Math.random() * (double)RESULTADOS.length - 1);
			swapForInts(RESULTADOS, i, k);
			swapForInts(RDOS_UBICACION, i, k);
		}
	}


	private void mezclarColores() {
		Color aux;
		for (int i = 0, k; i < COLORES.length; i++) {
			k = (int)(Math.random() * (double)RESULTADOS.length - 1);
			aux = COLORES[i];
			COLORES[i] = COLORES[k];
			COLORES[k] = aux;
		}
	}


	private void swapForInts(int[] arr, int ind_a, int ind_b) {
		int aux = arr[ind_a];
		arr[ind_a] = arr[ind_b];
		arr[ind_b] = aux;
	}


	private void setPuntaFlecha() {
		lado_opuesto_flecha = coord_flecha_y - COORDS_PPOG_Y[cuenta_seleccionada];
		hipotenusa_flecha = 
			Math.sqrt(
				Math.pow(coord_flecha_x - COORDS_PPOG_X[cuenta_seleccionada], 2) 
			+	Math.pow(lado_opuesto_flecha, 2)
		);

		angulo_flecha = hipotenusa_flecha != 0 ? Math.toDegrees(Math.asin(lado_opuesto_flecha/hipotenusa_flecha)) : 0;

		if (coord_flecha_x < COORDS_PPOG_X[cuenta_seleccionada]) angulo_flecha = 180 - angulo_flecha;

		COORDS_FCHAENDS_X[0] = coord_flecha_x - (int)(Math.cos(Math.toRadians(angulo_flecha-ANGULO_PUNTA)) * LARGO_PUNTA);
		COORDS_FCHAENDS_Y[0] = coord_flecha_y - (int)(Math.sin(Math.toRadians(angulo_flecha-ANGULO_PUNTA)) * LARGO_PUNTA);

		COORDS_FCHAENDS_X[1] = coord_flecha_x - (int)(Math.cos(Math.toRadians(angulo_flecha+ANGULO_PUNTA)) * LARGO_PUNTA);
		COORDS_FCHAENDS_Y[1] = coord_flecha_y - (int)(Math.sin(Math.toRadians(angulo_flecha+ANGULO_PUNTA)) * LARGO_PUNTA);		
	}


	private void inicializar() {
		for (int i = 0, f, rand_x_up; i < CANTIDAD_CUENTAS; i++) {
			f = FACTOR_MINIMO + i;

			CUENTAS[i] = TABLA + " x " + f + " =";
			RESULTADOS[i] = TABLA * f;
			RDOS_UBICACION[i] = i;

			AYUDAS[i] = "";
			for (int j = 0; j < f; j++) AYUDAS[i] += (j > 0 ? " + " : "") + TABLA;
			AYUDAS[i] += " =";
			BASES_AYUDAS[i] = asignarBaseAyuda(f);
			RESP_CORRECTAMENTE[i] = AYUDAS_DEPLOY[i] = false;

			// Ubicar
			rand_x_up = 
				MARGEN 
			+ 	BASE_ESPACIO_LIBRE * i 
			+ 	(int)(Math.random() * (BASE_ESPACIO_LIBRE-Math.max(BASE_CUENTAS, BASES_AYUDAS[i])));

			COORDS_CUENTAS_X[i] = BASE_CUENTAS >= BASES_AYUDAS[i] ? 
				rand_x_up
			:	rand_x_up + (BASES_AYUDAS[i] - BASE_CUENTAS)/2;

			COORDS_AYUDAS_X[i] = BASE_CUENTAS >= BASES_AYUDAS[i] ? 
				rand_x_up + (BASE_CUENTAS - BASES_AYUDAS[i])/2
			:	rand_x_up;

			COORDS_CUENTAS_Y[i] = 
				MARGEN 
			+ 	ALTURA_CUENTAS 
			+ 	(int)(Math.random() * (ALTURA_ESPACIO_LIBRE - ALTURA_CUENTAS - ALTURA_AYUDAS));

			COORDS_AYUDAS_Y[i] = COORDS_CUENTAS_Y[i] + ALTURA_AYUDAS;

			COORDS_RDOS_X[i] =
				MARGEN 
			+ 	BASE_ESPACIO_LIBRE * i 
			+ 	(int)(Math.random() * (BASE_ESPACIO_LIBRE - BASE_RESULTADOS));

			COORDS_RDOS_Y[i] = 
				MARGEN
			+	ALTURA_ESPACIO_LIBRE
			+	ESPACIO_OCIOSO
			+	(int)(Math.random() * (ALTURA_ESPACIO_LIBRE - ALTURA_RESULTADOS));

			COORDS_PPOG_X[i] = rand_x_up + Math.max(BASE_CUENTAS, BASES_AYUDAS[i])/2;
			COORDS_PPOG_Y[i] = COORDS_AYUDAS_Y[i] + DISTANCIA_PUERTO + DIAMETRO_PUERTO/2;

			COORDS_CPOG_X[i] = COORDS_PPOG_X[i] - DIAMETRO_PUERTO/2 - 1;
			COORDS_CPOG_Y[i] = COORDS_PPOG_Y[i] - DIAMETRO_PUERTO/2 - 1;

			COORDS_PPDE_Y[i] = COORDS_RDOS_Y[i] - ALTURA_RESULTADOS - DISTANCIA_PUERTO - DIAMETRO_PUERTO/2;

			COORDS_CPDE_Y[i] = COORDS_PPDE_Y[i] - DIAMETRO_PUERTO/2 - 1;

			ENTRADA_XIZQ_RDOS[i] = (int)(Math.random() * 101) % 2 == 1;
			ENTRADA_XIZQ_CTAS[i] = (int)(Math.random() * 101) % 2 == 1;
		}

		mezclarResultados();
		mezclarColores();

		// Estas dos van separadas acá para que una vez que se mezclaron los resultados asignar los puertos de destino
		for (int i = 0; i < CANTIDAD_CUENTAS; i++) {
			COORDS_PPDE_X[i] = COORDS_RDOS_X[i] + BASE_RESULTADOS/(RESULTADOS[i] >= 10 ? 2 : 4);

			COORDS_CPDE_X[i] = COORDS_PPDE_X[i] - DIAMETRO_PUERTO/2 - 1;
		}

		keyframes_entrada = -60;
		PANEL_ASOCIADO.repaint();
		ANIMACION.start();
	}


	public void dibujar(Graphics g) {
		g.setColor(Color.BLACK);

		g.setFont(FUENTE_NNIVEL);
		g.drawString("1", COORD_NNIVEL_X, COORD_NNIVEL_Y);

		g.setFont(FUENTE_DIALOGO);
		g.drawString(dialogo, COORD_DIALOGO_X, COORD_DIALOGO_Y);

		for (int i = 0; i < CANTIDAD_CUENTAS; i++) {

			g.setFont(FUENTE_CUENTAS);
			// if (ON_TESTING_MODE) g.setColor(Color.RED);
			// else
			g.setColor(RESP_CORRECTAMENTE[i] ? COLORES[i] : COLOR_SIN_RESOLVER);
			g.drawString(
				CUENTAS[i], 
				COORDS_CUENTAS_X[i] + curvaAnimacion(
					keyframes_entrada, 
					ENTRADA_XIZQ_CTAS[i] ? COORDS_CUENTAS_X[i] + BASE_CUENTAS : getWidth() - COORDS_CUENTAS_X[i])
				* (ENTRADA_XIZQ_CTAS[i] ? 1 : -1), 
				COORDS_CUENTAS_Y[i]);

			g.setFont(FUENTE_AYUDAS);
			if (AYUDAS_DEPLOY[i]) g.drawString(AYUDAS[i], COORDS_AYUDAS_X[i], COORDS_AYUDAS_Y[i]);


			g.setFont(FUENTE_RESULTADOS);
			// if (ON_TESTING_MODE) g.setColor(Color.RED);
			// else
			g.setColor(RESP_CORRECTAMENTE[RDOS_UBICACION[i]] ? COLORES[RDOS_UBICACION[i]] : COLOR_SIN_RESOLVER);
			g.drawString(
				RESULTADOS[i]+"", 
				COORDS_RDOS_X[i] + curvaAnimacion(
					keyframes_entrada, 
					ENTRADA_XIZQ_RDOS[i] ? COORDS_RDOS_X[i] + BASE_RESULTADOS : getWidth() - COORDS_RDOS_X[i]) 
				* (ENTRADA_XIZQ_RDOS[i] ? 1 : -1), 
				COORDS_RDOS_Y[i]);

			g.setColor(Color.BLACK);
			if (keyframes_entrada >= 0) {
				g.drawOval(COORDS_CPOG_X[i], COORDS_CPOG_Y[i], DIAMETRO_PUERTO, DIAMETRO_PUERTO);
				g.drawOval(COORDS_CPDE_X[i], COORDS_CPDE_Y[i], DIAMETRO_PUERTO, DIAMETRO_PUERTO);
			}

			if (RESP_CORRECTAMENTE[RDOS_UBICACION[i]])
				g.drawLine(COORDS_PPOG_X[RDOS_UBICACION[i]], COORDS_PPOG_Y[RDOS_UBICACION[i]], COORDS_PPDE_X[i], COORDS_PPDE_Y[i]);
		}

		if (cuenta_seleccionada >= 0) {
			g.drawLine(COORDS_PPOG_X[cuenta_seleccionada], COORDS_PPOG_Y[cuenta_seleccionada], coord_flecha_x, coord_flecha_y);
			g.drawLine(coord_flecha_x, coord_flecha_y, COORDS_FCHAENDS_X[0], COORDS_FCHAENDS_Y[0]);
			g.drawLine(coord_flecha_x, coord_flecha_y, COORDS_FCHAENDS_X[1], COORDS_FCHAENDS_Y[1]);
		}
	}

/*
	public static void createFrame() {
		JFrame frame = new JFrame();
		frame.setContentPane(new GameScreen().getBG());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> createFrame());
	}
*/
}

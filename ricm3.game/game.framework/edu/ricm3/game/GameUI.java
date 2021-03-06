/*
 * Educational software for a basic game development
 * Copyright (C) 2018  Pr. Olivier Gruber
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.ricm3.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import javax.swing.UIManager;
import edu.ricm3.game.whaler.Model;
import edu.ricm3.game.whaler.Options;
import edu.ricm3.game.whaler.Game_exception.Game_exception;

public class GameUI implements ActionListener {

	static String license = "Copyright (C) 2017  Pr. Olivier Gruber "
			+ "This program comes with ABSOLUTELY NO WARRANTY. "
			+ "This is free software, and you are welcome to redistribute it "
			+ "under certain conditions; type `show c' for details.";

	static GameUI game;

	JFrame m_frame;
	GameView m_view;
	JMenuBar m_menuBar;
	Timer m_timer;
	GameModel m_model;
	GameController m_controller;
	JLabel m_text;
	int m_fps;
	String m_msg;
	long m_start;
	long m_elapsed;
	long m_lastRepaint;
	long m_lastTick;
	int m_nTicks;
	private Screen m_screen;
	private JProgressBar m_lifeBar;
	private JProgressBar m_oilBar;
	private JLabel m_score_display;
	private JMenu m_statut;
	private JMenuItem pause;
	private JMenuItem exit;

	public GameUI(GameModel m, GameView v, GameController c, Dimension d) {
		m_model = m;
		m_model.m_game = this;
		m_view = v;
		m_view.m_game = this;
		m_controller = c;
		m_controller.m_game = this;
		m_screen = Screen.MENU;

		System.out.println(license);

		// create the main window and the periodic timer
		// to drive the overall clock of the simulation.
		createWindow(d);

	}

	// enum for the menu, to determine which screen should be displayed
	public enum Screen {
		MENU, PLAY, AUTOMATA, OPTIONS, RULES, PAUSE, EXIT, END;
	}

	// getter for Screen
	public Screen currentScreen() {
		return m_screen;
	}

	// setter for Screen
	public void setScreen(Screen s) {
		m_screen = s;
	}

	public GameModel getModel() {
		return m_model;
	}

	public GameView getView() {
		return m_view;
	}

	public GameController getController() {
		return m_controller;
	}

	public void addNorth(Component c) {
		m_frame.add(c, BorderLayout.NORTH);
	}

	public void addSouth(Component c) {
		m_frame.add(c, BorderLayout.SOUTH);
	}

	public void addWest(Component c) {
		m_frame.add(c, BorderLayout.WEST);
	}

	public void addEast(Component c) {
		m_frame.add(c, BorderLayout.EAST);
	}

	public void createWindow(Dimension d) {
		
		if (currentScreen() == Screen.PLAY) {
			m_lastRepaint = 0;
			m_frame = new JFrame();
			m_frame.setTitle("Cetacea");
			m_frame.setLayout(new BorderLayout());
			// m_frame.setResizable(false);

			File f = new File("game.whaler/sprites/cetacea.png");
			Image icone;
			try {
				icone = ImageIO.read(f);
				m_frame.setIconImage(icone);
			} catch (IOException ex) {
				ex.printStackTrace();
				System.exit(-1);
			}

			
			// Primary Display on the center
			m_frame.add(m_view, BorderLayout.CENTER);

			// Info Bar (FPS, TICK,...)
			m_text = new JLabel();
			m_text.setText("Starting up...");
			addNorth(m_text);

			m_frame.setSize(d);
			m_frame.doLayout();
			m_frame.setVisible(true);

			// hook window events so that we exit the Java Platform
			// when the window is closed by the end user. 
			m_frame.addWindowListener(new WindowListener(m_model));

			m_frame.pack();
			m_frame.setLocationRelativeTo(null);
			GameController ctr = getController();

			// let's hook the controller,
			// so it gets mouse events and keyboard events.
			m_view.addKeyListener(ctr);
			m_view.addMouseListener(ctr);
			m_view.addMouseMotionListener(ctr);

			// grab the focus on this JPanel, meaning keyboard events
			// are coming to our controller. Indeed, the focus controls
			// which part of the overall GUI receives the keyboard events.
			m_view.setFocusable(true);
			m_view.requestFocusInWindow();

			m_controller.notifyVisible();

			m_lifeBar = new JProgressBar(JProgressBar.VERTICAL);
			refreshLife();
			m_oilBar = new JProgressBar(JProgressBar.VERTICAL);
			refreshOil();
			m_score_display = new JLabel("", JLabel.CENTER);
			refreshScore();

			m_menuBar = new JMenuBar();
			m_statut = new JMenu("Jeu");
			pause = new JMenuItem("Pause");
			exit = new JMenuItem("Exit");
			m_statut.add(pause);
			m_statut.add(exit);

			m_statut.addSeparator();

			m_menuBar.add(m_statut);
			m_frame.setJMenuBar(m_menuBar);

			pause.addActionListener(this);
			exit.addActionListener(this);

			pause.setActionCommand("PAUSE");
			exit.setActionCommand("EXIT");

		} else if (currentScreen() == Screen.MENU) {
			
			MainMenu m = new MainMenu(this);
			m.create_frame();
			m.create_menu();

		} else if (currentScreen() == Screen.RULES) {
			Rules r = new Rules(this);
			r.create_frame();
			r.create_rules();

		} else if (currentScreen() == Screen.AUTOMATA) {
			AutomataSelection a = new AutomataSelection(this);
			a.create_frame();
			a.create_automata_selection();

		} else if (currentScreen() == Screen.END) {

			EndGame e = new EndGame(this);
			e.create_frame();
			e.create_endgame();

		} else if (currentScreen() == Screen.PAUSE) {
			Pause p = new Pause(this);
			p.create_frame();
			p.create_pause();

		}

	}

	public void refreshOil() {

		Model m = (Model) m_model;
		float currentOil = m.m_player.m_oil_jauge;

		m_oilBar.removeAll();

		m_oilBar.setValue((int) currentOil * 5);
		m_oilBar.setStringPainted(true);
		UIManager.put("ProgressBar.background", Color.OPAQUE);
		UIManager.put("ProgressBar.selectionForeground", Color.WHITE);

		m_oilBar.setForeground(Color.GRAY);
		m_oilBar.setString("10%");
		m_oilBar.setMinimum(0);
		m_oilBar.setMaximum(100);
		m_oilBar.setString("• OIL •");
		m_oilBar.setStringPainted(true);

		Container contentPane = m_frame.getContentPane();
		contentPane.add(m_oilBar, BorderLayout.EAST);

	}

	public void refreshLife() {

		Model m = (Model) m_model;
		int currentLife = m.m_player.m_life;
		m_lifeBar.removeAll();

		m_lifeBar.setStringPainted(true);
		m_lifeBar.setForeground(Color.RED);
		m_lifeBar.setString("10%");
		m_lifeBar.setMinimum(0);
		m_lifeBar.setMaximum(100);
		m_lifeBar.setValue(currentLife * 5);
		m_lifeBar.setString("• LIFE •");
		m_lifeBar.setStringPainted(true);

		Container contentPane = m_frame.getContentPane();
		contentPane.add(m_lifeBar, BorderLayout.WEST);

	}

	public void refreshScore() {

		Model m = (Model) m_model;
		int score = m.m_score.nombre;

		m_score_display.removeAll();
		m_score_display.setText("SCORE " + Integer.toString(score));
		m_score_display.setFont(new Font("Laksaman", Font.BOLD, 15));
		Container contentPane = m_frame.getContentPane();
		contentPane.add(m_score_display, BorderLayout.SOUTH);

	}

	public void actionPerformed(ActionEvent e) {
		Model m = (Model) m_model;
		String event = e.getActionCommand();
		if (event.equals("EXIT")) {
			m_timer.stop();
			setScreen(Screen.END);
			this.createWindow(new Dimension(Options.DIMX_WINDOW, Options.DIMY_WINDOW));
			m_frame.dispose();
		}
		if (event.equals("PAUSE")) {
		
			m.m_pause = true;
			setScreen(Screen.PAUSE);
			this.createWindow(new Dimension(Options.DIMX_WINDOW, Options.DIMY_WINDOW));
			// m_frame.dispose();
		}
	}

	/*
	 * Let's create a timer, it is the heart of the simulation, ticking periodically
	 * so that we can simulate the passing of time.
	 */
	public void createTimer() {
		int tick = 1; // one millisecond
		m_start = System.currentTimeMillis();
		m_lastTick = m_start;
		m_timer = new Timer(tick, new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				try {
					tick();
				} catch (Game_exception e) {
					e.printStackTrace();
					System.exit(-1);
				}
			}
		});
		m_timer.start();
	}

	/*
	 * This is the period tick callback. We compute the elapsed time since the last
	 * tick. We inform the model of the current time.
	 */
	private void tick() throws Game_exception {
		long now = System.currentTimeMillis() - m_start;
		long elapsed = (now - m_lastTick);
		m_elapsed += elapsed;
		m_lastTick = now;
		m_nTicks++;
		m_model.step(now);
		m_controller.step(now);
		Model m = (Model) m_model;

	

		elapsed = now - m_lastRepaint;
		if (elapsed > Options.REPAINT_DELAY) {
			double tick = (double) m_elapsed / (double) m_nTicks;
			long tmp = (long) (tick * 10.0);
			tick = tmp / 10.0;
			m_elapsed = 0;
			m_nTicks = 0;
			String txt = "Tick=" + tick + "ms";
			while (txt.length() < 15)
				txt += " ";
			txt = txt + m_fps + " fps   ";
			while (txt.length() < 25)
				txt += " ";
			if (m_msg != null)
				txt += m_msg;
			// System.out.println(txt);
			m_text.setText(txt);

			refreshLife();
			refreshOil();
			refreshScore();
			m_view.paint();
			m_lastRepaint = now;
		}

		int currentLife = m.m_player.m_life;
		if (currentLife <= 0 || m.m_player.m_oil_jauge <= 0) {
			currentLife = 0;
			m_timer.stop();
			this.setScreen(Screen.END);
			m_frame.dispose();
			createWindow(new Dimension(Options.DIMX_WINDOW, Options.DIMY_WINDOW));

		}

	}

	public void setFPS(int fps, String msg) {
		m_fps = fps;
		m_msg = msg;
	}
}

package net.codejava;

import java.sql.*;
import javax.swing.*;
import javax.swing.ImageIcon;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.*;

public class AutoSalone extends JFrame  {
	// attributi SQL
	// connessione database
	private static final String url = "jdbc:mysql://localhost:3306/autosalone";
	private static final String username = "root";
	private static final String password = "#";

	// attributi interfaccia grafica
	private JLabel lblMarchio, lblModello, lblPosti, lblKm, lblPrezzo, lblId;
	private JTextField txtMarchio, txtModello, txtPosti, txtKm, txtPrezzo, txtId;
	private JButton btnSalva, btnAggiorna, btnRimuovi;


	public AutoSalone() {
		// aggiunta dei componenti all'interfaccia grafica
		JPanel panelCampi = new JPanel(new GridLayout(8, 2));
		JPanel panelBottoni = new JPanel(new GridLayout(1, 3));
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1080, 1920);
		frame.setLayout(new BorderLayout());
		frame.add(panelCampi, BorderLayout.NORTH);
		frame.add(panelBottoni, BorderLayout.SOUTH);
		
		//componenti immagine
		panelCampi.setLayout(new FlowLayout());
		panelBottoni.setLayout(new FlowLayout());
		BufferedImage myPicture = null;
		try {
			myPicture = ImageIO.read(new File("resources/ImmagineJPanel.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JLabel picLabel = new JLabel(new ImageIcon(myPicture));
		panelCampi.add(picLabel);
		panelBottoni.add(picLabel);
		frame.setSize(1080, 1920);            
		frame.setVisible(true);
		
		
			
		// inizializzazione componenti interfaccia grafica
		lblMarchio = new JLabel("Marchio: ");
		lblModello = new JLabel("Modello: ");
		lblPosti = new JLabel("Posti: ");
		lblKm = new JLabel("Km: ");
		lblPrezzo = new JLabel("Prezzo: ");
		lblId = new JLabel("Id: ");

		txtMarchio = new JTextField(10);
		txtModello = new JTextField(10);
		txtPosti = new JTextField(2);
		txtKm = new JTextField(10);
		txtPrezzo = new JTextField(25);
		txtId = new JTextField(3);

		btnSalva = new JButton("Salva");
		btnAggiorna = new JButton("Aggiorna");
		btnRimuovi = new JButton("Rimuovi");

		panelCampi.add(lblMarchio);
		panelCampi.add(txtMarchio);

		panelCampi.add(lblModello);
		panelCampi.add(txtModello);

		panelCampi.add(lblPosti);
		panelCampi.add(txtPosti);

		panelCampi.add(lblKm);
		panelCampi.add(txtKm);

		panelCampi.add(lblPrezzo);
		panelCampi.add(txtPrezzo);

		panelCampi.add(lblId);
		panelCampi.add(txtId);

		// gestione del bottone salva con il proprio listener
		btnSalva.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// recupero dati inseriti dall'utente
				String marchio = txtMarchio.getText();
				String modello = txtModello.getText();
				int posti = Integer.parseInt(txtPosti.getText());
				int km = Integer.parseInt(txtKm.getText());
				double prezzo = Double.parseDouble(txtPrezzo.getText());

				// apertura connessione al database ed inserimento della nuova automobile
				Connection conn = null;
				PreparedStatement pstmt = null;

				try {
					conn = DriverManager.getConnection(url, username, password);
					String sql = "INSERT INTO auto(marchio, modello, posti, km, prezzo) VALUES(?,?,?,?,?)";
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, marchio);
					pstmt.setString(2, modello);
					pstmt.setInt(3, posti);
					pstmt.setInt(4, km);
					pstmt.setDouble(5, prezzo);

					int righeInserite = pstmt.executeUpdate();
					// creiamo una finestra di pop up che ci dia conferma dell'inserimento del
					// record
					if (righeInserite > 0) {
						JOptionPane.showMessageDialog(null, "Auto inserita con successo!");
					}

				} catch (SQLException ex) {
					ex.printStackTrace();
				} finally {
					try {
						if (pstmt != null)
							pstmt.close();
						if (conn != null)
							conn.close();

					} catch (SQLException ex) {
						ex.printStackTrace();
					}
				}
				// reset dei campi del form
				txtMarchio.setText("");
				txtModello.setText("");
				txtPosti.setText("");
				txtKm.setText("");
				txtPrezzo.setText("");
				txtId.setText("");
			}
		});

		panelBottoni.add(btnSalva);
		frame.setVisible(true);
		
		// gestione del bottone aggiorna con il proprio listener
		btnAggiorna.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// recupero dati inseriti dall'utente
				String marchio = txtMarchio.getText();
				String modello = txtModello.getText();
				int posti = Integer.parseInt(txtPosti.getText());
				int km = Integer.parseInt(txtKm.getText());
				double prezzo = Double.parseDouble(txtPrezzo.getText());
				int id = Integer.parseInt(txtId.getText());

				// apertura connessione al database ed inserimento della nuova auto
				Connection conn = null;
				PreparedStatement pstmt = null;

				try {
					conn = DriverManager.getConnection(url, username, password);
					String sql = "UPDATE auto SET marchio = ?, modello = ?, posti = ?, km = ?, prezzo = ? WHERE id = ?";
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, marchio);
					pstmt.setString(2, modello);
					pstmt.setInt(3, posti);
					pstmt.setInt(4, km);
					pstmt.setDouble(5, prezzo);
					pstmt.setInt(6, id);

					int righeInserite = pstmt.executeUpdate();
					// creiamo una finestra di pop up che ci dia conferma dell'inserimento del
					// record
					if (righeInserite > 0) {
						JOptionPane.showMessageDialog(null, "Auto aggiornata con successo!");
					}

				} catch (SQLException ex) {
					ex.printStackTrace();
				} finally {
					try {
						if (pstmt != null)
							pstmt.close();
						if (conn != null)
							conn.close();

					} catch (SQLException ex) {
						ex.printStackTrace();
					}
				}
				// reset dei campi del form
				txtMarchio.setText("");
				txtModello.setText("");
				txtPosti.setText("");
				txtKm.setText("");
				txtPrezzo.setText("");
				txtId.setText("");
			}
		});

		panelBottoni.add(btnAggiorna);
		frame.setVisible(true);
		
		// gestione del bottone rimuovi con il proprio listener
		btnRimuovi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int id = Integer.parseInt(txtId.getText());

				// apertura connessione al database ed inserimento del nuovo studente
				Connection conn = null;
				PreparedStatement pstmt = null;

				try {
					conn = DriverManager.getConnection(url, username, password);
					String sql = "DELETE FROM auto WHERE id = ?";
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, id);

					int righeInserite = pstmt.executeUpdate();
					// creiamo una finestra di pop up che ci dia conferma dell'inserimento del
					// record
					if (righeInserite > 0) {
						JOptionPane.showMessageDialog(null, "Auto rimossa con successo!");
					}

				} catch (SQLException ex) {
					ex.printStackTrace();
				} finally {
					try {
						if (pstmt != null)
							pstmt.close();
						if (conn != null)
							conn.close();
					} catch (SQLException ex) {
						ex.printStackTrace();
					}
				}
				// reset dei campi del form
				txtId.setText("");
			}
		});

		panelBottoni.add(btnRimuovi);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		new AutoSalone();

	}

}




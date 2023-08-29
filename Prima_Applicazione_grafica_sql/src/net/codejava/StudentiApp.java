package net.codejava;

import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StudentiApp extends JFrame {
	// attributi SQL
	// connessione database
	private static final String url = "jdbc:mysql://localhost:3306/studenti";
	private static final String username = "root";
	private static final String password = "#";

	// attributi interfaccia grafica
	private JLabel lblNome, lblCognome, lblEta;
	private JTextField txtNome, txtCognome, txtEta;
	private JButton btnSalva;

	public StudentiApp() {

		// impostazione delle dimensioni e della posizione dell'interfaccia grafica
		setTitle("Applicazione studenti");
		setSize(300, 150);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		// inizializzazione componenti interfaccia grafica
		lblNome = new JLabel("Nome: ");
		lblCognome = new JLabel("Cognome: ");
		lblEta = new JLabel("Eta: ");

		txtNome = new JTextField(20);
		txtCognome = new JTextField(20);
		txtEta = new JTextField(5);

		btnSalva = new JButton("Salva");
		
		//aggiunta dei componenti all'interfaccia grafica
		JPanel panel = new JPanel(new GridLayout(4,2));
		panel.add(lblNome);
		panel.add(txtNome);
		
		panel.add(lblCognome);
		panel.add(txtCognome);
		
		panel.add(lblEta);
		panel.add(txtEta);
		
		panel.add(btnSalva);

		// gestione del bottone salva con il proprio listener
		btnSalva.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// recupero dati inseriti dall'utente
				String nome = txtNome.getText();
				String cognome = txtCognome.getText();
				int eta = Integer.parseInt(txtEta.getText());

				// apertura connessione al database ed inserimento del nuovo studente
				Connection conn = null;
				PreparedStatement pstmt = null;

				try {
					conn = DriverManager.getConnection(url, username, password);
					String sql = "INSERT INTO studenti(nome, cognome, eta) VALUES(?,?,?)";
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, nome);
					pstmt.setString(2, cognome);
					pstmt.setInt(3, eta);

					int righeInserite = pstmt.executeUpdate();
					// creiamo una finestra di pop up che ci dia conferma dell'inserimento del
					// record
					if (righeInserite > 0) {
						JOptionPane.showMessageDialog(null, "Studente inserito con successo!");
					}

				} catch (SQLException ex) {
					ex.printStackTrace();
				}
				finally {
					try {
						if(pstmt != null) pstmt.close();
						if(conn != null) conn.close();
						
					}catch(SQLException ex) {
						ex.printStackTrace();
					}
				}
				//reset dei campi del form
				txtNome.setText("");
				txtCognome.setText("");
				txtEta.setText("");
			}
		}

		);
		add(panel);
		setVisible(true);
	}
	public static void main(String[] args) {
		new StudentiApp();
	}
}

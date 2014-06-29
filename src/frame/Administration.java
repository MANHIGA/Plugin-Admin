package frame;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.Admin;

public class Administration extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(final String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					if (args.length == 1){
						Administration frame = new Administration(args[0]);
						frame.setVisible(true);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Administration(final String idAdmin) {
		setTitle("Gang Authority - Admin");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		final JLabel lblError = new JLabel("");
		lblError.setForeground(Color.RED);
		lblError.setBounds(10, 31, 440, 22);
		contentPane.add(lblError);
		
		try{
			Class.forName("frame.Connexion");
			JMenuBar menuBar = new JMenuBar();
			menuBar.setBounds(0, 0, 450, 22);
			contentPane.add(menuBar);
			
			JMenu mnDconnexion = new JMenu("D\u00E9connexion");
			mnDconnexion.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					frame.Connexion.main(new String[0]);
					dispose();
				}
			});
			menuBar.add(mnDconnexion);
		}catch(Exception e){
			//On fait rien si on ne trouve pas la classe
			setTitle(e.getMessage());
		}
		
		try{
			ResultSet rs = Admin.afficherJoueurSignales();
			DefaultListModel<String> dlm = new DefaultListModel<String>();
			final ArrayList<Integer> idSignalement = new ArrayList<Integer>();
			final ArrayList<Integer> idCompte = new ArrayList<Integer>();
			while(rs.next()){
				idSignalement.add(rs.getInt("idSignalement"));
				idCompte.add(rs.getInt("Signalement_idCompte"));
				dlm.addElement(rs.getInt("idSignalement") + ": " + rs.getString("Justification") + "->" + rs.getInt("Signalement_idCompte"));
			}
			final JList<String> list = new JList<String>(dlm);
			list.setBounds(24, 82, 232, 172);
			contentPane.add(list);
			
			final JTextField textField = new JTextField();
			textField.setBounds(278, 110, 134, 28);
			contentPane.add(textField);
			textField.setColumns(10);
			
			JButton btnBannir = new JButton("Bannir");
			btnBannir.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(!textField.getText().equals("")){
						Admin.bannirJoueur(idSignalement.get(list.getSelectedIndex()), idAdmin, idCompte.get(list.getSelectedIndex()), Integer.parseInt(textField.getText()));
						String str[] = {idAdmin.toString()};
						Administration.main(str);
						dispose();
					}else{
						lblError.setText("Saisissez une durée de bannissement, et choisissez un signalement.");
					}
				}
			});
			btnBannir.setBounds(268, 163, 165, 53);
			contentPane.add(btnBannir);
			
		}catch(Exception e){
			final JList<String> list = new JList<String>();
			list.setBounds(24, 62, 232, 192);
			
			contentPane.add(list);
			
			JButton btnBannir = new JButton("Bannir");
			btnBannir.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					
				}
			});
			btnBannir.setBounds(268, 163, 165, 53);
			contentPane.add(btnBannir);
		}
		
		
		JLabel lblJoueursSignals = new JLabel("Joueurs signal\u00E9s");
		lblJoueursSignals.setBounds(61, 54, 182, 16);
		contentPane.add(lblJoueursSignals);
		
		
		JLabel lblDureDuBannissement = new JLabel("Dur\u00E9e du bannissement");
		lblDureDuBannissement.setBounds(268, 82, 165, 16);
		contentPane.add(lblDureDuBannissement);
	}
}

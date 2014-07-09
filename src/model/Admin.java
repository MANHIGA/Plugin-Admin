package model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Admin {
	
	public static Integer getAdminId(String pseudo, String mdp){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection c = DriverManager.getConnection(
					"jdbc:mysql://localhost/Gang_Authority", "Goomoonryong", "murim");
			Statement st = c.createStatement();
			String req = "select * from administrateur where pseudoAdmin='"+ pseudo +"' and mdpAdmin='"+ mdp +"'";
			ResultSet rs = st.executeQuery(req);
			if(rs.first()){
				return rs.getInt("idAdmin");
			}
			return null;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public static ResultSet afficherJoueurSignales(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection c = DriverManager.getConnection(
					"jdbc:mysql://localhost/Gang_Authority", "Goomoonryong", "murim");
			Statement st = c.createStatement();
			String req = "select idSignalement, Signalement_idCompte, pseudo, justification "
					+ "from signalement "
					+ "join joueur "
					+ "on joueur.idCompte = signalement.Signalement_idCompte";
			ResultSet rs = st.executeQuery(req);
			return rs;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public static boolean bannirJoueur(Integer idSignalement, String idAdmin, Integer idJoueur, Integer dureeBannissement){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection c = DriverManager.getConnection(
					"jdbc:mysql://localhost/Gang_Authority", "Goomoonryong", "murim");
			Statement st = c.createStatement();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			String dateActuelle = sdf.format(new Date());
			String req = "insert into bannir(dateBannissement, dureeBannissement, bannir_idAdmin, bannir_idCompte) "
					+ "values ('" + dateActuelle + "', " + dureeBannissement + "," + idAdmin + ", "+ idJoueur+")";
			String req2 = "delete from signalement where idSignalement = " + idSignalement;
			int nbReq = st.executeUpdate(req);
			
			if(nbReq > 0) nbReq = st.executeUpdate(req2);
			else return false;
			
			if(nbReq > 0) return true;
			else return false;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	
}

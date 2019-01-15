/**
 * 
 */
package fdi.ucm.server.importparser.oda.coleccion;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fdi.ucm.server.importparser.oda.MySQLConnectionOda;
import fdi.ucm.server.modelComplete.ImportExportDataEnum;
import fdi.ucm.server.modelComplete.ImportExportPair;
import fdi.ucm.server.modelComplete.LoadCollection;

/**
 * @author Joaquin Gayoso-Cabada
 *
 */
public abstract class LoadCollectionOda extends LoadCollection {

	private static final Pattern regexAmbito = Pattern.compile("^http://(([a-zA-Z][a-zA-Z0-9._%-]*)||([0-9]+.[0-9]+.[0-9]+.[0-9]+))(:[0-9]+)?/[a-zA-Z0-9][a-zA-Z0-9._%-]+/*$");
	private static ArrayList<ImportExportPair> Parametros;
	
	public abstract boolean isConvert();

	public abstract MySQLConnectionOda getSQL();

	public abstract ArrayList<String> getLog();

	public abstract CollectionOda getCollection();

	public abstract String getBaseURLOda();

	public static boolean testURL(String baseURLOda2) {
		if (baseURLOda2==null||baseURLOda2.isEmpty())
			return true;
		 Matcher matcher = regexAmbito.matcher(baseURLOda2);
		return matcher.matches();
	}

	public ArrayList<ImportExportPair> getConfiguracion() {
		if (Parametros==null)
		{
			ArrayList<ImportExportPair> ListaCampos=new ArrayList<ImportExportPair>();
			ListaCampos.add(new ImportExportPair(ImportExportDataEnum.Text, "Server"));
			ListaCampos.add(new ImportExportPair(ImportExportDataEnum.Text, "Database"));
			ListaCampos.add(new ImportExportPair(ImportExportDataEnum.Number, "Port"));
			ListaCampos.add(new ImportExportPair(ImportExportDataEnum.Text, "User"));
			ListaCampos.add(new ImportExportPair(ImportExportDataEnum.EncriptedText, "Password"));
			ListaCampos.add(new ImportExportPair(ImportExportDataEnum.Boolean, "Convert to UTF-8"));
			ListaCampos.add(new ImportExportPair(ImportExportDataEnum.Text, "Oda base url for files (if need it, ej: http://<Server Name>/Oda)",true));
			ListaCampos.add(new ImportExportPair(ImportExportDataEnum.Boolean, "Clone local files to Clavy",true));
			Parametros=ListaCampos;
			return ListaCampos;
		}
		else return Parametros;
	}
	
	/**
	 * QUitar caracteres especiales.
	 * @param str texto de entrada.
	 * @return texto sin caracteres especiales.
	 */
	public String RemoveSpecialCharacters(String str) {
		   StringBuilder sb = new StringBuilder();
		   for (int i = 0; i < str.length(); i++) {
			   char c = str.charAt(i);
			   if ((c >= '0' && c <= '9') || (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || c == '_') {
			         sb.append(c);
			      }
		}
		   return sb.toString();
		}


	public static void main(String[] args) {
		System.out.println(testURL("http://localhost/oda-ref/"));
		System.out.println(testURL("http://localhost/Oda"));
		System.out.println(testURL("http://localhost:1000/Oda"));
		System.out.println(testURL("http://localhost:/Oda"));
		System.out.println(testURL("http://192.168.1.1:266/Oda"));
		System.out.println(!("http://localhost/Oda").endsWith("/"));
		System.out.println(!("http://localhost/oda-ref/").endsWith("/"));
	}

	public abstract String getBaseURLOdaSimple();
}

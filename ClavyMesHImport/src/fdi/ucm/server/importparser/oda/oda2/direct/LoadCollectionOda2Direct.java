/**
 * 
 */
package fdi.ucm.server.importparser.oda.oda2.direct;

import java.util.ArrayList;

import fdi.ucm.server.importparser.oda.MySQLConnectionOda;
import fdi.ucm.server.importparser.oda.coleccion.CollectionOda;
import fdi.ucm.server.importparser.oda.coleccion.LoadCollectionOda;
import fdi.ucm.server.importparser.oda.oda2.direct.collection.CollectionOda2Direct;
import fdi.ucm.server.modelComplete.CompleteImportRuntimeException;
import fdi.ucm.server.modelComplete.collection.CompleteCollectionAndLog;

/**
 * Clase que define la carga de una coleccion Oda1
 * @author Joaquin Gayoso-Cabada
 *
 */
public class LoadCollectionOda2Direct extends LoadCollectionOda{

	private boolean convert;
	private String BaseURLOda;
	private boolean CloneFiles = false;
	private MySQLConnectionOda SQL;
	private ArrayList<String> Log;
	private CollectionOda Odacollection;
	private String BaseURLOdaSimple;


public static void main(String[] args) {
//	ListaCampos.add(new ImportExportPair(ImportExportDataEnum.Text, "Server"));
//	ListaCampos.add(new ImportExportPair(ImportExportDataEnum.Text, "Database"));
//	ListaCampos.add(new ImportExportPair(ImportExportDataEnum.Number, "Port"));
//	ListaCampos.add(new ImportExportPair(ImportExportDataEnum.Text, "User"));
//	ListaCampos.add(new ImportExportPair(ImportExportDataEnum.EncriptedText, "Password"));
//	ListaCampos.add(new ImportExportPair(ImportExportDataEnum.Boolean, "Convert to UTF-8"));
//	ListaCampos.add(new ImportExportPair(ImportExportDataEnum.Text, "Oda base url for files (if need it, ej: http://<Server Name>/Oda)",true));
//	ListaCampos.add(new ImportExportPair(ImportExportDataEnum.Boolean, "Clone local files to Clavy",true));
	
	ArrayList<String> DateEntrada=new ArrayList<String>();
	DateEntrada.add("localhost");
	DateEntrada.add("odalimpia");
	DateEntrada.add("3306");
	DateEntrada.add("root");
	DateEntrada.add("");
	DateEntrada.add("false");
	DateEntrada.add("");
	DateEntrada.add("false");

	LoadCollectionOda LC=new LoadCollectionOda2Direct();
	CompleteCollectionAndLog Salida=LC.processCollecccion(DateEntrada);
	if (Salida!=null)
		{
		
		System.out.println("Correcto");
		
		for (String warning : Salida.getLogLines())
			System.err.println(warning);

		
		System.exit(0);
		
		}
	else
		{
		System.err.println("Error");
		System.exit(-1);
		}
}



	@Override
	public CompleteCollectionAndLog processCollecccion(ArrayList<String> DateEntrada) {
		Log=new ArrayList<String>();
		 Odacollection=new CollectionOda2Direct(this);
		
		if (DateEntrada!=null)
			
		{
			String Database = RemoveSpecialCharacters(DateEntrada.get(1));
			SQL = new MySQLConnectionOda(DateEntrada.get(0),Database,Integer.parseInt(DateEntrada.get(2)),DateEntrada.get(3),DateEntrada.get(4));
			convert=Boolean.parseBoolean(DateEntrada.get(5));
			BaseURLOda=DateEntrada.get(6);
			
			if (!testURL(BaseURLOda))
			{
				throw new CompleteImportRuntimeException("Database is note empty and note look like a normal URL http://<Server Name>/Oda");
			}
		
		if (!BaseURLOda.endsWith("/"))
			BaseURLOda=BaseURLOda+"/";
		
		BaseURLOdaSimple= new String(BaseURLOda);
		
		BaseURLOda=BaseURLOda+"bo/download/";
			
			CloneFiles=Boolean.parseBoolean(DateEntrada.get(7));
			Odacollection.ProcessAttributes();
			Odacollection.ProcessInstances();
		}

		return new CompleteCollectionAndLog(Odacollection.getCollection(),Log);
	}


	@Override
	public String getName() {
		return "Oda 2.0 Direct";
	}
	
	

	/**
	 * @return the convert
	 */
	@Override
	public boolean isConvert() {
		return convert;
	}

	/**
	 * @param convert the convert to set
	 */
	public void setConvert(boolean convert) {
		this.convert = convert;
	}

	/**
	 * @return the baseURLOda
	 */
	public String getBaseURLOdaSimple() {
		return BaseURLOdaSimple;
	}
	
	
	/**
	 * @return the baseURLOda
	 */
	public String getBaseURLOda() {
		return BaseURLOda;
	}

	/**
	 * @param baseURLOda the baseURLOda to set
	 */
	public void setBaseURLOda(String baseURLOda) {
		BaseURLOda = baseURLOda;
	}

	@Override
	public boolean getCloneLocalFiles() {
		return CloneFiles;
	}

	@Override
	public MySQLConnectionOda getSQL() {
		return SQL;
	}

	@Override
	public ArrayList<String> getLog() {
		return Log;
	}

	@Override
	public CollectionOda getCollection() {
		return Odacollection;
	}
	
}

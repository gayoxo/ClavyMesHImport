/**
 * 
 */
package fdi.ucm.server.importparser.oda.oda1;

import java.util.ArrayList;

import fdi.ucm.server.importparser.oda.MySQLConnectionOda;
import fdi.ucm.server.importparser.oda.coleccion.CollectionOda;
import fdi.ucm.server.importparser.oda.coleccion.LoadCollectionOda;
import fdi.ucm.server.importparser.oda.oda1.coleccion.CollectionOda1;
import fdi.ucm.server.modelComplete.CompleteImportRuntimeException;
import fdi.ucm.server.modelComplete.collection.CompleteCollectionAndLog;

/**
 * Clase que define la carga de una coleccion Oda1
 * @author Joaquin Gayoso-Cabada
 *
 */
public class LoadCollectionOda1 extends LoadCollectionOda{


	private boolean convert;
	private String BaseURLOda;
	private boolean CloneFiles = false;
	private MySQLConnectionOda SQL;
	private ArrayList<String> Log;
	private CollectionOda Odacollection;
	private String BaseURLOdaSimple;



	@Override
	public CompleteCollectionAndLog processCollecccion(ArrayList<String> DateEntrada) {
		
		Log=new ArrayList<String>();
		Odacollection=new CollectionOda1(this);
		
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
			
			BaseURLOda=BaseURLOda+"bo/download";
				
			CloneFiles=Boolean.parseBoolean(DateEntrada.get(7));
			Odacollection.ProcessAttributes();
			Odacollection.ProcessInstances();
		}
		
		
		
		
		return new CompleteCollectionAndLog(Odacollection.getCollection(),Log);
	}

	



	@Override
	public String getName() {
		return "Oda 1.0";
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
	@Override
	public String getBaseURLOda() {
		return BaseURLOda;
	}
	
	
	/**
	 * @return the baseURLOda
	 */
	@Override
	public String getBaseURLOdaSimple() {
		return BaseURLOdaSimple;
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

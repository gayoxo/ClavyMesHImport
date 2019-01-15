/**
 * 
 */
package fdi.ucm.server.importparser.oda.oda2.direct.collection;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import fdi.ucm.server.importparser.oda.coleccion.CollectionOda;
import fdi.ucm.server.importparser.oda.coleccion.LoadCollectionOda;
import fdi.ucm.server.importparser.oda.coleccion.categoria.ElementType_Datos;
import fdi.ucm.server.importparser.oda.coleccion.categoria.ElementType_Metadatos;
import fdi.ucm.server.importparser.oda.oda2.direct.collection.categoria.Grammar_ObjetoVirtualDirect;
import fdi.ucm.server.modelComplete.collection.CompleteCollection;
import fdi.ucm.server.modelComplete.collection.document.CompleteDocuments;
import fdi.ucm.server.modelComplete.collection.document.CompleteFile;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteElementType;

/**
 * clase que define la implementacion del plugin de carga de oda 1.0
 * @author Joaquin Gayoso-Cabada
 *
 */
public class CollectionOda2Direct extends CollectionOda {

	private static final String COLECCION_OBTENIDA_A_PARTIR_DE_ODA = "Coleccion obtenida a partir de ODA en : ";
	private static final String COLECCION_ODA = "Coleccion ODA";
	private  CompleteCollection oda2;
	private HashMap<Integer, CompleteDocuments> ObjetoVirtual;
	private Grammar_ObjetoVirtualDirect ResourcveData;
	private LoadCollectionOda LocalPadre;
	private HashMap<CompleteElementType, ArrayList<String>> Vocabularios;
	private HashMap<Integer, ArrayList<String>> Vocabularies;
	private HashSet<CompleteElementType> NoCompartidos;
	private HashMap<String,CompleteFile> FilesTot;
	
	public CollectionOda2Direct(LoadCollectionOda localPadre) {
		oda2=new CompleteCollection(COLECCION_ODA, COLECCION_OBTENIDA_A_PARTIR_DE_ODA+ new Timestamp(new Date().getTime()));
		Vocabularios=new HashMap<CompleteElementType, ArrayList<String>>();
		NoCompartidos=new HashSet<CompleteElementType>();
		LocalPadre=localPadre;
		FilesTot=new HashMap<String,CompleteFile>(); 
		Vocabularies=new HashMap<Integer, ArrayList<String>>();
	}
	
	
	/* (non-Javadoc)
	 * @see fdi.ucm.server.importparser.oda1.Oda1parserModel#ProcessAttributes()
	 */
	@Override
	public void ProcessAttributes() {
		procesOV();
		processDatos();
		processMetadatos();

	}


	



	/**
	 * Procesa los OV
	 */
	private void procesOV() {
		ResourcveData=new Grammar_ObjetoVirtualDirect(oda2,LocalPadre);
		ResourcveData.ProcessAttributes();
		ResourcveData.ProcessInstances();
		oda2.getMetamodelGrammar().add(ResourcveData.getAtributoMeta());
		
	}




	/**
	 * Procesa los MetaDatos de Oda1
	 */
	private void processMetadatos() {
		ElementType_Metadatos ResourcveData2=new ElementType_Metadatos(ResourcveData.getAtributoMeta(),LocalPadre);
		ResourcveData2.ProcessAttributes();
		ResourcveData2.ProcessInstances();
		ResourcveData.getAtributoMeta().getSons().add(ResourcveData2.getAtributoMeta());
		
	}

	/**
	 * Procesa los datos de Oda1
	 */
	private void processDatos() {
		ElementType_Datos ResourcveData2=new ElementType_Datos(ResourcveData.getAtributoMeta(),LocalPadre);
		ResourcveData2.ProcessAttributes();
		ResourcveData2.ProcessInstances();
		ResourcveData.getAtributoMeta().getSons().add(ResourcveData2.getAtributoMeta());
		
	}


	/* (non-Javadoc)
	 * @see fdi.ucm.server.importparser.oda1.Oda1parserModel#ProcessInstances()
	 */
	@Override
	public void ProcessInstances() {
		//No posee instancias

	}


	/**
	 * @return the objetoVirtual
	 */
	@Override
	public HashMap<Integer, CompleteDocuments> getObjetoVirtual() {
		return ObjetoVirtual;
	}


	/**
	 * @param objetoVirtual the objetoVirtual to set
	 */
	@Override
	public void setObjetoVirtual(HashMap<Integer, CompleteDocuments> objetoVirtual) {
		ObjetoVirtual = objetoVirtual;
	}


	


	public CompleteCollection getOda2() {
		return oda2;
	}


	public void setOda2(CompleteCollection oda2) {
		this.oda2 = oda2;
	}


	public Grammar_ObjetoVirtualDirect getResourcveData() {
		return ResourcveData;
	}


	public void setResourcveData(Grammar_ObjetoVirtualDirect resourcveData) {
		ResourcveData = resourcveData;
	}


	public LoadCollectionOda getLocalPadre() {
		return LocalPadre;
	}


	public void setLocalPadre(LoadCollectionOda localPadre) {
		LocalPadre = localPadre;
	}


	public HashSet<CompleteElementType> getNoCompartidos() {
		return NoCompartidos;
	}


	public void setNoCompartidos(HashSet<CompleteElementType> noCompartidos) {
		NoCompartidos = noCompartidos;
	}


	



	/**
	 * @return the vocabularios
	 */
	@Override
	public HashMap<CompleteElementType, ArrayList<String>> getVocabularios() {
		return Vocabularios;
	}


	/**
	 * @param vocabularios the vocabularios to set
	 */
	public void setVocabularios(
			HashMap<CompleteElementType, ArrayList<String>> vocabularios) {
		Vocabularios = vocabularios;
	}



	@Override
	public CompleteCollection getCollection() {
		return oda2;
	}

	
	@Override
	public HashSet<CompleteElementType> getNOCompartidos() {
		return NoCompartidos;
	}


	@Override
	public HashMap<String, CompleteDocuments> getFilesC() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public HashMap<String, CompleteDocuments> getURLC() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public HashMap<Integer, ArrayList<String>> getVocabularies() {
		// TODO Auto-generated method stub
		return Vocabularies;
	}


	@Override
	public HashMap<String, CompleteDocuments> getFilesId() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public HashMap<String, CompleteFile> getFiles() {
		return FilesTot;
	}

}

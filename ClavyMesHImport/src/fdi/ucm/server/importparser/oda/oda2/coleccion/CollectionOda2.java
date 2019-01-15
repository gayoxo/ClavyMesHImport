/**
 * 
 */
package fdi.ucm.server.importparser.oda.oda2.coleccion;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;

import fdi.ucm.server.importparser.oda.NameConstantsOda;
import fdi.ucm.server.importparser.oda.coleccion.CollectionOda;
import fdi.ucm.server.importparser.oda.coleccion.LoadCollectionOda;
import fdi.ucm.server.importparser.oda.coleccion.categoria.ElementType_Datos;
import fdi.ucm.server.importparser.oda.coleccion.categoria.ElementType_Metadatos;
import fdi.ucm.server.importparser.oda.coleccion.categoria.Grammar_File;
import fdi.ucm.server.importparser.oda.coleccion.categoria.Grammar_URL;
import fdi.ucm.server.importparser.oda.oda2.coleccion.categoria.Grammar_ObjetoVirtual;
import fdi.ucm.server.modelComplete.collection.CompleteCollection;
import fdi.ucm.server.modelComplete.collection.document.CompleteDocuments;
import fdi.ucm.server.modelComplete.collection.document.CompleteFile;
import fdi.ucm.server.modelComplete.collection.document.CompleteTextElement;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteElementType;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteGrammar;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteOperationalValueType;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteTextElementType;

/**
 * clase que define la implementacion del plugin de carga de oda 1.0
 * @author Joaquin Gayoso-Cabada
 *
 */
public class CollectionOda2 extends CollectionOda {

	private static final String COLECCION_OBTENIDA_A_PARTIR_DE_ODA = "Coleccion obtenida a partir de ODA en : ";
	private static final String COLECCION_ODA = "Coleccion ODA";
	private  CompleteCollection oda2;
	private HashMap<Integer, CompleteDocuments> ObjetoVirtual;
	private HashMap<String, CompleteFile> CompleteFiles;
	private HashMap<String, CompleteDocuments> FilesC;
	private HashMap<String, CompleteDocuments> URlC;
	private HashMap<String, CompleteDocuments> FilesId;
	private HashMap<Integer, ArrayList<String>> Vocabularies;
	private Grammar_ObjetoVirtual ResourcveData;
	private LoadCollectionOda LocalPadre;
	private HashMap<CompleteElementType, ArrayList<String>> Vocabularios;
	private HashSet<CompleteElementType> NoCompartidos;
	
	public CollectionOda2(LoadCollectionOda localPadre) {
		oda2=new CompleteCollection(COLECCION_ODA, COLECCION_OBTENIDA_A_PARTIR_DE_ODA+ new Timestamp(new Date().getTime()));
		Vocabularies=new HashMap<Integer, ArrayList<String>>();
		CompleteFiles=new HashMap<String, CompleteFile>();
		FilesC=new HashMap<String, CompleteDocuments>();
		URlC=new HashMap<String, CompleteDocuments>();
		FilesId=new HashMap<String, CompleteDocuments>();
		Vocabularios=new HashMap<CompleteElementType, ArrayList<String>>();
		NoCompartidos=new HashSet<CompleteElementType>();
		LocalPadre=localPadre;
	}
	
	
	/* (non-Javadoc)
	 * @see fdi.ucm.server.importparser.oda1.Oda1parserModel#ProcessAttributes()
	 */
	@Override
	public void ProcessAttributes() {
		procesFiles();
		procesURLs();
		procesOV();
		processDatos();
		processMetadatos();
		processVocabularios();

	}


	private void procesURLs() {
		Grammar_URL AFM=new Grammar_URL(oda2,LocalPadre);
		AFM.ProcessAttributes();
		AFM.ProcessInstances();
		oda2.getMetamodelGrammar().add(AFM.getAtributoMeta());
		
	}

	
	/**
	 * Procesa los vocabularios compartidos
	 */
	private void processVocabularios() {
		
		CompleteGrammar Vocabulary = new CompleteGrammar(NameConstantsOda.VOCABULARY, NameConstantsOda.VOCABULARY,oda2);
		oda2.getMetamodelGrammar().add(Vocabulary);
		{

		CompleteOperationalValueType Valor = new CompleteOperationalValueType(NameConstantsOda.VISIBLESHOWN,Boolean.toString(false),NameConstantsOda.PRESNTACION);
		CompleteOperationalValueType Valor2=new CompleteOperationalValueType(NameConstantsOda.BROWSERSHOWN,Boolean.toString(false),NameConstantsOda.PRESNTACION);
		CompleteOperationalValueType Valor3=new CompleteOperationalValueType(NameConstantsOda.SUMMARYSHOWN,Boolean.toString(false),NameConstantsOda.PRESNTACION);
		
		Vocabulary.getViews().add(Valor);
		Vocabulary.getViews().add(Valor2);
		Vocabulary.getViews().add(Valor3);
		

		CompleteOperationalValueType ValorMeta=new CompleteOperationalValueType(NameConstantsOda.TYPE,NameConstantsOda.VOCABULARY,NameConstantsOda.META);
		
		Vocabulary.getViews().add(ValorMeta);
		
		}
		
		
		CompleteTextElementType Number=new CompleteTextElementType(NameConstantsOda.VOCNUMBER, Vocabulary);
		Vocabulary.getSons().add(Number);
		
		
//		CompleteIterator IteraValor=new CompleteIterator(Vocabulary);
//		Vocabulary.getSons().add(IteraValor);
		
	
		
		{
		
		CompleteOperationalValueType Valor = new CompleteOperationalValueType(NameConstantsOda.VISIBLESHOWN,Boolean.toString(false),NameConstantsOda.PRESNTACION);
		CompleteOperationalValueType Valor2=new CompleteOperationalValueType(NameConstantsOda.BROWSERSHOWN,Boolean.toString(false),NameConstantsOda.PRESNTACION);
		CompleteOperationalValueType Valor3=new CompleteOperationalValueType(NameConstantsOda.SUMMARYSHOWN,Boolean.toString(false),NameConstantsOda.PRESNTACION);
		
		Number.getShows().add(Valor);
		Number.getShows().add(Valor2);
		Number.getShows().add(Valor3);
		

		
		CompleteOperationalValueType ValorMeta=new CompleteOperationalValueType(NameConstantsOda.TYPE,NameConstantsOda.VOCNUMBER,NameConstantsOda.META);
		
		Number.getShows().add(ValorMeta);
		

		}
		
		
		CompleteTextElementType Values=new CompleteTextElementType(NameConstantsOda.TERM, Vocabulary);
		Values.setMultivalued(true);
		Vocabulary.getSons().add(Values);
		
		{
		
		CompleteOperationalValueType Valor = new CompleteOperationalValueType(NameConstantsOda.VISIBLESHOWN,Boolean.toString(false),NameConstantsOda.PRESNTACION);
		CompleteOperationalValueType Valor2=new CompleteOperationalValueType(NameConstantsOda.BROWSERSHOWN,Boolean.toString(false),NameConstantsOda.PRESNTACION);
		CompleteOperationalValueType Valor3=new CompleteOperationalValueType(NameConstantsOda.SUMMARYSHOWN,Boolean.toString(false),NameConstantsOda.PRESNTACION);
		
		Values.getShows().add(Valor);
		Values.getShows().add(Valor2);
		Values.getShows().add(Valor3);
		

		
		CompleteOperationalValueType ValorMeta=new CompleteOperationalValueType(NameConstantsOda.TYPE,NameConstantsOda.TERM,NameConstantsOda.META);
		
		Values.getShows().add(ValorMeta);
		
		}
		
		
		List<CompleteTextElementType> ValuesL = new ArrayList<CompleteTextElementType>();
		ValuesL.add(Values);

			HashMap<ArrayList<String>, Integer> procesados=new HashMap<ArrayList<String>, Integer>();
			int vocaInt=0;
			for (Entry<CompleteElementType, ArrayList<String>> iterable_element : Vocabularios.entrySet()) {
				CompleteElementType element = iterable_element.getKey();
				ArrayList<String> voc = iterable_element.getValue();
				

				
				Integer I=procesados.get(voc);
				if (I==null)
				{
					procesados.put(voc, vocaInt);
					I=vocaInt;
					vocaInt++;
					CompleteDocuments nuevo= new CompleteDocuments(oda2, I.toString(), "");
					nuevo.getDescription().add(new CompleteTextElement(Number, I.toString()));
					for (int j = 0; j < voc.size(); j++) {		
						while (ValuesL.size()<=j)
						{
						
								CompleteTextElementType ValuesNew=new CompleteTextElementType(NameConstantsOda.TERM, Vocabulary);
								ValuesNew.setClassOfIterator(Values);
								ValuesNew.setMultivalued(true);
						Vocabulary.getSons().add(ValuesNew);
						
							{

						CompleteOperationalValueType Valor = new CompleteOperationalValueType(NameConstantsOda.VISIBLESHOWN,Boolean.toString(false),NameConstantsOda.PRESNTACION);
						CompleteOperationalValueType Valor2=new CompleteOperationalValueType(NameConstantsOda.BROWSERSHOWN,Boolean.toString(false),NameConstantsOda.PRESNTACION);
						CompleteOperationalValueType Valor3=new CompleteOperationalValueType(NameConstantsOda.SUMMARYSHOWN,Boolean.toString(false),NameConstantsOda.PRESNTACION);
						
						ValuesNew.getShows().add(Valor);
						ValuesNew.getShows().add(Valor2);
						ValuesNew.getShows().add(Valor3);
						
						
						CompleteOperationalValueType ValorMeta=new CompleteOperationalValueType(NameConstantsOda.TYPE,NameConstantsOda.TERM,NameConstantsOda.META);
						
						ValuesNew.getShows().add(ValorMeta);
							}
							
							ValuesL.add(ValuesNew);
						}
					CompleteTextElement T=new CompleteTextElement(ValuesL.get(j), voc.get(j));
					nuevo.getDescription().add(T);
						
						
						
						
					}
					oda2.getEstructuras().add(nuevo);
					
				}
				else 
				{
					
				}
				
				
				CompleteOperationalValueType ValorComp=new CompleteOperationalValueType(NameConstantsOda.VOCNUMBER,Integer.toString(I.intValue()),NameConstantsOda.VOCABULARY);
				element.getShows().add(ValorComp);
				
				if (NoCompartidos.contains(element))
				{
				CompleteOperationalValueType ValorComp2=new CompleteOperationalValueType(NameConstantsOda.COMPARTIDO,Boolean.toString(false),NameConstantsOda.VOCABULARY);
				element.getShows().add(ValorComp2);
				}
			else
			{
				CompleteOperationalValueType ValorComp2=new CompleteOperationalValueType(NameConstantsOda.COMPARTIDO,Boolean.toString(true),NameConstantsOda.VOCABULARY);
				element.getShows().add(ValorComp2);
				}
				
			}
		
		
	}


	private void procesFiles() {
		Grammar_File AFM=new Grammar_File(oda2,LocalPadre);
		AFM.ProcessAttributes();
		AFM.ProcessInstances();
		oda2.getMetamodelGrammar().add(AFM.getAtributoMeta());
		
		
		
	}


	/**
	 * Procesa los OV
	 */
	private void procesOV() {
		ResourcveData=new Grammar_ObjetoVirtual(oda2,LocalPadre);
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


	/**
	 * @return the vocabularies
	 */
	@Override
	public HashMap<Integer, ArrayList<String>> getVocabularies() {
		return Vocabularies;
	}


	/**
	 * @param vocabularies the vocabularies to set
	 */
	public void setVocabularies(HashMap<Integer, ArrayList<String>> vocabularies) {
		Vocabularies = vocabularies;
	}



	/**
	 * @return the files
	 */
	@Override
	public HashMap<String, CompleteFile> getFiles() {
		return CompleteFiles;
	}


	/**
	 * @param completeFiles the files to set
	 */
	public void setFiles(HashMap<String, CompleteFile> completeFiles) {
		CompleteFiles = completeFiles;
	}


	/**
	 * @return the filesC
	 */
	@Override
	public HashMap<String, CompleteDocuments> getFilesC() {
		return FilesC;
	}


	/**
	 * @param filesC the filesC to set
	 */
	public void setFilesC(HashMap<String, CompleteDocuments> filesC) {
		FilesC = filesC;
	}


	/**
	 * @return the filesId
	 */
	@Override
	public HashMap<String, CompleteDocuments> getFilesId() {
		return FilesId;
	}


	/**
	 * @param filesId the filesId to set
	 */
	public void setFilesId(HashMap<String, CompleteDocuments> filesId) {
		FilesId = filesId;
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
	public HashMap<String, CompleteDocuments> getURLC() {
		return URlC;
	}
	
	@Override
	public HashSet<CompleteElementType> getNOCompartidos() {
		return NoCompartidos;
	}

}

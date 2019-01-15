/**
 * 
 */
package fdi.ucm.server.importparser.oda.coleccion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import fdi.ucm.server.importparser.oda.InterfaceOdaparser;
import fdi.ucm.server.modelComplete.collection.CompleteCollection;
import fdi.ucm.server.modelComplete.collection.document.CompleteDocuments;
import fdi.ucm.server.modelComplete.collection.document.CompleteFile;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteElementType;


/**
 * @author Joaquin Gayoso-Cabada
 *
 */
public abstract class CollectionOda implements InterfaceOdaparser {

	public abstract CompleteCollection getCollection();

	public abstract HashMap<Integer, CompleteDocuments> getObjetoVirtual();

	public abstract HashMap<Integer, ArrayList<String>> getVocabularies();

	public abstract HashMap<CompleteElementType, ArrayList<String>> getVocabularios();
	
	public abstract HashSet<CompleteElementType> getNOCompartidos();

	public abstract HashMap<String, CompleteDocuments> getFilesId();

	public abstract HashMap<String, CompleteDocuments> getFilesC();

	public abstract HashMap<String, CompleteDocuments> getURLC();
	
	public abstract HashMap<String, CompleteFile> getFiles();

	public abstract void setObjetoVirtual(HashMap<Integer, CompleteDocuments> objetoVirtual);




}

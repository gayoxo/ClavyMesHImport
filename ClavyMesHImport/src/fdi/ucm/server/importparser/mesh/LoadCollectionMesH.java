/**
 * 
 */
package fdi.ucm.server.importparser.mesh;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import fdi.ucm.server.modelComplete.ImportExportDataEnum;
import fdi.ucm.server.modelComplete.ImportExportPair;
import fdi.ucm.server.modelComplete.LoadCollection;
import fdi.ucm.server.modelComplete.collection.CompleteCollection;
import fdi.ucm.server.modelComplete.collection.CompleteCollectionAndLog;
import fdi.ucm.server.modelComplete.collection.document.CompleteDocuments;
import fdi.ucm.server.modelComplete.collection.document.CompleteLinkElement;
import fdi.ucm.server.modelComplete.collection.document.CompleteTextElement;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteElementType;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteGrammar;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteLinkElementType;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteTextElementType;

/**
 * @author Joaquin Gayoso Cabada
 *
 */
public class LoadCollectionMesH extends LoadCollection{

	
	private static ArrayList<ImportExportPair> Parametros;
	private CompleteCollection CC;
	public static boolean consoleDebug=false;

	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LoadCollectionMesH LC=new LoadCollectionMesH();
		LoadCollectionMesH.consoleDebug=true;
		
		ArrayList<String> AA=new ArrayList<String>();
		AA.add("MesH.zip");
		AA.add(System.getProperty("user.home"));
		
		CompleteCollectionAndLog Salida=LC.processCollecccion(AA);
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
	public CompleteCollectionAndLog processCollecccion(ArrayList<String> dateEntrada) {
		
		LinkedList<File> Archivos=new LinkedList<>();
		
		try {
			String fileZip = dateEntrada.get(0);
	        File destDir = new File(dateEntrada.get(1)+"/"+System.nanoTime()+"/");
	        destDir.mkdirs();
	        byte[] buffer = new byte[1024];
	        ZipInputStream zis = new ZipInputStream(new FileInputStream(fileZip));
	        ZipEntry zipEntry = zis.getNextEntry();
	        while (zipEntry != null) {
	            File newFile = newFile(destDir, zipEntry);
	            FileOutputStream fos = new FileOutputStream(newFile);
	            int len;
	            while ((len = zis.read(buffer)) > 0) {
	                fos.write(buffer, 0, len);
	            }
	            fos.close();
	            zipEntry = zis.getNextEntry();
	            if (newFile.getAbsolutePath().endsWith(".xml"));
	            	Archivos.add(newFile);
	           
	        }
	        zis.closeEntry();
	        zis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		CompleteCollectionAndLog Salida=new CompleteCollectionAndLog();
		CC=new CompleteCollection("MESH IMPORT", new Date()+"");
		Salida.setCollection(CC);
		Salida.setLogLines(new ArrayList<String>());
		
		CompleteGrammar ECGEN=new CompleteGrammar("ecgen", "ecgen", CC);
		CC.getMetamodelGrammar().add(ECGEN);
		
		CompleteTextElementType uId=new CompleteTextElementType("uId", ECGEN);
		ECGEN.getSons().add(uId);
		
		CompleteTextElementType publisher=new CompleteTextElementType("publisher", ECGEN);
		ECGEN.getSons().add(publisher);
		
		CompleteTextElementType note=new CompleteTextElementType("note", ECGEN);
		ECGEN.getSons().add(note);
		
		CompleteTextElementType specialty=new CompleteTextElementType("specialty", ECGEN);
		specialty.setBrowseable(true);
		ECGEN.getSons().add(specialty);
		
		
		CompleteGrammar MeSH=new CompleteGrammar("MeSH", "MeSH", CC);
		CC.getMetamodelGrammar().add(MeSH);
		
		for (File file : Archivos) {
			 System.out.println(file.getAbsolutePath());
			 
			 try {
				 ProcessXML(file,CC,uId,publisher,note,specialty,MeSH);
			} catch (Exception e) {
				e.printStackTrace();
			}
			 
			 
			
			 
		}
		
		
		return Salida;
		
		
		
	}

	

	private void ProcessXML(File file, CompleteCollection cC2, CompleteTextElementType uId, CompleteTextElementType publisher, CompleteTextElementType note, CompleteTextElementType specialty, CompleteGrammar meSH) throws ParserConfigurationException, SAXException, IOException {
		CompleteDocuments D=new CompleteDocuments(cC2, "", "https://meshb.nlm.nih.gov/public/img/meshLogo.jpg");
		cC2.getEstructuras().add(D);		
				
		 DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			
			NodeList title = doc.getElementsByTagName("title");
			if (title.getLength()>0)
			{
				String Desc = title.item(0).getTextContent();
				D.setDescriptionText(Desc);
			}
			
			NodeList nList = doc.getElementsByTagName("uId");
			if (nList.getLength()>0)
				{
				Element eElement2 = ((Element)nList.item(0));
				String GA=eElement2.getAttribute("id");
				if (GA!=null&&!GA.isEmpty())
					{
					CompleteTextElement Uid=new CompleteTextElement(uId, GA);
					D.getDescription().add(Uid);
					}
				}
			
			
			
			NodeList publisherN = doc.getElementsByTagName("publisher");
			if (publisherN.getLength()>0)
			{
				String publisherD = publisherN.item(0).getTextContent();
				
				if (publisherD!=null&&!publisherD.isEmpty())
				{
				CompleteTextElement publisherE=new CompleteTextElement(publisher, publisherD);
				D.getDescription().add(publisherE);
				}
			}
			
			
			NodeList noteN = doc.getElementsByTagName("note");
			if (noteN.getLength()>0)
			{
				String noteD = noteN.item(0).getTextContent();
				
				if (noteD!=null&&!noteD.isEmpty())
				{
				CompleteTextElement noteE=new CompleteTextElement(note, noteD);
				D.getDescription().add(noteE);
				}
			}
			
			
			NodeList specialtyN = doc.getElementsByTagName("specialty");
			if (specialtyN.getLength()>0)
			{
				String specialtyD = specialtyN.item(0).getTextContent();
				
				if (specialtyD!=null&&!specialtyD.isEmpty())
				{
				CompleteTextElement specialtyE=new CompleteTextElement(specialty, specialtyD);
				D.getDescription().add(specialtyE);
				}
			}
			
			
	/**		NodeList Hijos=((Element)nList.item(0)).getChildNodes();
			for (int temp2 = 0; temp2 < Hijos.getLength(); temp2++)
			{
				Node nNodeH = Hijos.item(temp2);
				if (nNodeH.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement2 = (Element) nNodeH;
					
					
					
				}
			}
			**/

	}



	public static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());
         
        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();
         
        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }
         
        return destFile;
    }
	

	@Override
	public ArrayList<ImportExportPair> getConfiguracion() {
		if (Parametros==null)
		{
			ArrayList<ImportExportPair> ListaCampos=new ArrayList<ImportExportPair>();
			ListaCampos.add(new ImportExportPair(ImportExportDataEnum.File, "XML zip File"));
			Parametros=ListaCampos;
			return ListaCampos;
		}
		else return Parametros;
	}

	@Override
	public String getName() {
		return "MESH XML Import";
	}

	@Override
	public boolean getCloneLocalFiles() {
		return false;
	}

	
	/**
	
	
	
	
	
	try {
		
		HashMap<String,CompleteDocuments> tablaEqui=new HashMap<String,CompleteDocuments>();
		HashMap<CompleteLinkElement, String> tablaLink=new HashMap<CompleteLinkElement,String>();
		HashMap<CompleteLinkElement, CompleteDocuments> tablaLinkPadre=new HashMap<CompleteLinkElement,CompleteDocuments>();
		List<CompleteLinkElement> posProcessLink=new ArrayList<CompleteLinkElement>();
		
		HashMap<CompleteGrammar,HashMap<String,CompleteElementType>> ListaElem=new HashMap<CompleteGrammar,HashMap<String,CompleteElementType>>();
		HashMap<String,CompleteGrammar> ListaGram=new HashMap<String,CompleteGrammar>();
		
		CompleteCollectionAndLog Salida=new CompleteCollectionAndLog();
		CC=new CompleteCollection("XML IMPORT", new Date()+"");
		Salida.setCollection(CC);
		Salida.setLogLines(new ArrayList<String>());
		
		String FileS = dateEntrada.get(0);
		File XMLD=new File(FileS);
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(XMLD);
		
		NodeList nList = doc.getElementsByTagName("records");
		nList = ((Element)nList.item(0)).getElementsByTagName("record");
		
		
		for (int temp = 0; temp < nList.getLength(); temp++)
		{
			
			try {
				Node nNode = nList.item(temp);
				
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					
					Element eElement = (Element) nNode;
					
					
					String descript = eElement.getElementsByTagName("description").item(0).getTextContent();
					String icon = eElement.getElementsByTagName("icon").item(0).getTextContent();
					CompleteDocuments CD=new CompleteDocuments(CC, descript, icon);
					String Id=eElement.getAttribute("id");
					if (!(Id.isEmpty()||Id==null))
						tablaEqui.put(Id, CD);

					CC.getEstructuras().add(CD);
					
					try {
						NodeList Resto=eElement.getChildNodes();
						for (int temp2 = 0; temp2 < Resto.getLength(); temp2++)
						{
							
							try {
								Node nNodeH = Resto.item(temp2);
								
								if (nNodeH.getNodeType() == Node.ELEMENT_NODE) {
									
									
									
									Element eElement2 = (Element) nNodeH;
									if (!(eElement2.getTagName().equals("description")||(eElement2.getTagName().equals("icon"))))
											{
										String GA=eElement2.getAttribute("gram");
										if (GA.isEmpty()||GA==null)
											GA="Ungrammar";
										
										CompleteGrammar Grama=ListaGram.get(GA);
										
										if (Grama==null)
											{
											Grama=new CompleteGrammar(GA, GA, CC);
											CC.getMetamodelGrammar().add(Grama);
											ListaGram.put(GA, Grama);
											ListaElem.put(Grama, new HashMap<String,CompleteElementType>());
											Salida.getLogLines().add("Creada la Gramatica-> " +GA);
											}
											
										String ElemTy=eElement2.getTagName();
										
										HashMap<String, CompleteElementType> ListaElemTy = ListaElem.get(Grama);
										CompleteElementType ElementoTy=ListaElemTy.get(ElemTy);
										
										if (ElementoTy==null)
										{
										
											String rara=eElement2.getAttribute("relation");	
											if (rara!=null&&rara.equals("true"))
											{
												ElementoTy=new CompleteLinkElementType(ElemTy, Grama);
												Grama.getSons().add(ElementoTy);
												ListaElemTy.put(ElemTy, ElementoTy);
												ListaElem.put(Grama, ListaElemTy);
												Salida.getLogLines().add("Creada la Gramatica-> " +ElemTy);
											}
											else
											{	
											ElementoTy=new CompleteTextElementType(ElemTy, Grama);
											Grama.getSons().add(ElementoTy);
											ListaElemTy.put(ElemTy, ElementoTy);
											ListaElem.put(Grama, ListaElemTy);
											Salida.getLogLines().add("Creada la Gramatica-> " +ElemTy);
											}
										}
										
										String Valor=eElement2.getTextContent();
										
										if (ElementoTy instanceof CompleteTextElementType)
										{
										CompleteTextElement EE=new CompleteTextElement((CompleteTextElementType) ElementoTy,Valor);
										CD.getDescription().add(EE);
										}else
											if (ElementoTy instanceof CompleteLinkElementType)
											{
												CompleteLinkElement EE=new CompleteLinkElement((CompleteLinkElementType) ElementoTy,null);
												CD.getDescription().add(EE);
												posProcessLink.add(EE);
												tablaLink.put(EE, Valor);
												tablaLinkPadre.put(EE, CD);
											}
											}
									
									
									
								
								}
							} catch (Exception e) {
								Salida.getLogLines().add("Error in record "+temp);
							}
							
							
							
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
					
					
				
				}
			} catch (Exception e) {
				Salida.getLogLines().add("Error in record "+temp);
			}
			
			
			
		}
		
//		CompleteCollectionAndLog Salida=new CompleteCollectionAndLog();
//		CC=new CompleteCollection("MedPix", new Date()+"");
//		Salida.setCollection(CC);
//		Logs=new ArrayList<String>();
//		Salida.setLogLines(Logs);
//		encounterID=new HashMap<String,CompleteDocuments>();
//		topicID=new HashMap<String,List<CompleteDocuments>>();
//		ListImageEncounter=new ArrayList<CompleteElementTypeencounterIDImage>();
//		ListImageEncounterTopics=new ArrayList<CompleteElementTypeencounterIDImage>();
//		ListTopicID=new ArrayList<CompleteElementTypetopicIDTC>();
//		
//		ProcesaCasos();
//		ProcesaCasoID();
//		ProcesaTopics();
//		//AQUI se puede trabajar
		
		for (CompleteLinkElement completeLinkElement : posProcessLink) {
			String valor = tablaLink.get(completeLinkElement);
			CompleteDocuments dd=tablaEqui.get(valor);
			if (dd==null)
			tablaLinkPadre.get(completeLinkElement).getDescription().remove(completeLinkElement);
			else	
			completeLinkElement.setValue(dd);
		}
		
		
		
		return Salida;
	} catch (Exception e) {
		e.printStackTrace();
		return null;
	}
	**/
	
}
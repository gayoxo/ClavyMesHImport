/**
 * 
 */
package fdi.ucm.server.importparser.oda.coleccion.categoria;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import fdi.ucm.server.importparser.oda.InterfaceOdaparser;
import fdi.ucm.server.importparser.oda.NameConstantsOda;
import fdi.ucm.server.importparser.oda.StaticFunctionsOda;
import fdi.ucm.server.importparser.oda.coleccion.LoadCollectionOda;
import fdi.ucm.server.modelComplete.collection.document.CompleteDocuments;
import fdi.ucm.server.modelComplete.collection.document.CompleteTextElement;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteElementType;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteGrammar;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteOperationalValueType;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteTextElementType;

/**
 * Clase que parsea un nodo estandar de la clase oda1
 * @author Joaquin Gayoso-Cabada
 *
 */
public class ElementType_NODE implements InterfaceOdaparser {

	private CompleteElementType AtributoMeta;
	private String Id;
	private ArrayList<String> Vocabulary;
	private LoadCollectionOda LColec;
	private CompleteGrammar CM;
	private HashMap<Long, CompleteElementType> CompleteAsociado;
	private HashMap<CompleteElementType, HashMap<CompleteElementType, CompleteElementType>> CompleteAsociadoTabla;
	private ArrayList<CompleteElementType> Hermanos;
	
	public ElementType_NODE(String id, String nombre,
			String navegable, String visible, String tipo_valores,
			String vocabulario, CompleteElementType tpadre, boolean summary,LoadCollectionOda L, CompleteGrammar Cm,
			HashMap<Long, CompleteElementType> completeAsociado, HashMap<CompleteElementType, HashMap<CompleteElementType, CompleteElementType>> completeAsociadoTabla, ArrayList<CompleteElementType> hermanos) {
		
		LColec=L;
		CM=Cm;
		boolean navegablebool = true;
		if (navegable.equals("N"))
			navegablebool=false;
		
		boolean visiblebool = true;
		if (visible.equals("N"))
			visiblebool=false;
		
		Id=id;
		
		Vocabulary=new ArrayList<String>();
		CompleteAsociado=completeAsociado;
		CompleteAsociadoTabla=completeAsociadoTabla;
		Hermanos=hermanos;
		
		if (tipo_valores.equals("C"))
			{
			AtributoMeta = new CompleteTextElementType(nombre, tpadre,CM);
			AtributoMeta=new CompleteTextElementType(nombre, tpadre,CM);
			CompleteOperationalValueType MetaType=new CompleteOperationalValueType(NameConstantsOda.METATYPETYPE,NameConstantsOda.CONTROLED,NameConstantsOda.METATYPE);
			AtributoMeta.getShows().add(MetaType);
			if (vocabulario != null) {
				Integer Voc = Integer.parseInt(vocabulario);
				if (Voc == 1 || Voc == 0) {
					ArrayList<String> A = LColec.getCollection().getVocabularies().get(Integer.parseInt(Id));
					if (A==null)
					{
						LColec.getCollection().getVocabularies().put(Integer.parseInt(Id), Vocabulary);
						A=Vocabulary;
					}
					LColec.getCollection().getVocabularios().put((CompleteTextElementType)AtributoMeta,A);
					
					if (Voc==0)
						LColec.getCollection().getNOCompartidos().add(AtributoMeta);
				} else {
					ArrayList<String> A = LColec.getCollection().getVocabularies().get(Voc);
					if (A==null)
						{
						LColec.getCollection().getVocabularies().put(Voc, Vocabulary);
						A=Vocabulary;
						}
					LColec.getCollection().getVocabularios().put((CompleteTextElementType)AtributoMeta,A);

				}

			}
			else
			{
				ArrayList<String> A = LColec.getCollection().getVocabularies().get(Integer.parseInt(Id));
				if (A==null)
				{
					LColec.getCollection().getVocabularies().put(Integer.parseInt(Id), Vocabulary);
					A=Vocabulary;
				}
				LColec.getCollection().getVocabularios().put((CompleteTextElementType)AtributoMeta,A);
				
				//Caso controlado raro
				if (id.equals(NameConstantsOda.IDFIJOCONTROLADO))
					LColec.getCollection().getNOCompartidos().add(AtributoMeta);
			}
			
			}
		else if (tipo_valores.equals("F"))
			{
			AtributoMeta = new CompleteTextElementType(nombre, tpadre,CM);
			AtributoMeta=new CompleteTextElementType(nombre, tpadre,CM);
			CompleteOperationalValueType MetaType=new CompleteOperationalValueType(NameConstantsOda.METATYPETYPE,NameConstantsOda.DATE,NameConstantsOda.METATYPE);
			AtributoMeta.getShows().add(MetaType);
			
			}
		else if (tipo_valores.equals("X"))
			AtributoMeta=new CompleteElementType(nombre, tpadre,CM);
		else if (tipo_valores.equals("T"))
			{
			AtributoMeta=new CompleteTextElementType(nombre, tpadre,CM);
			CompleteOperationalValueType MetaType=new CompleteOperationalValueType(NameConstantsOda.METATYPETYPE,NameConstantsOda.TEXT,NameConstantsOda.METATYPE);
			AtributoMeta.getShows().add(MetaType);
			}
		else if (tipo_valores.equals("N"))
			{
			AtributoMeta=new CompleteTextElementType(nombre, tpadre,CM);
			CompleteOperationalValueType MetaType=new CompleteOperationalValueType(NameConstantsOda.METATYPETYPE,NameConstantsOda.NUMERIC,NameConstantsOda.METATYPE);
			AtributoMeta.getShows().add(MetaType);
			}
		else AtributoMeta=new CompleteElementType(nombre, tpadre,CM);
		
		
		
		CompleteOperationalValueType Valor=new CompleteOperationalValueType(NameConstantsOda.VISIBLESHOWN,Boolean.toString(visiblebool),NameConstantsOda.PRESNTACION);
		CompleteOperationalValueType Valor2=new CompleteOperationalValueType(NameConstantsOda.BROWSERSHOWN,Boolean.toString(navegablebool),NameConstantsOda.PRESNTACION);
		CompleteOperationalValueType Valor3=new CompleteOperationalValueType(NameConstantsOda.SUMMARYSHOWN,Boolean.toString(summary),NameConstantsOda.PRESNTACION);
		
		AtributoMeta.getShows().add(Valor);
		AtributoMeta.getShows().add(Valor2);
		AtributoMeta.getShows().add(Valor3);

		
		
		CompleteOperationalValueType ValorO=new CompleteOperationalValueType(NameConstantsOda.OdaID,Id,NameConstantsOda.ODA);

		
		AtributoMeta.getShows().add(ValorO);

	}

	/* (non-Javadoc)
	 * @see fdi.ucm.server.importparser.oda1.Oda1parserModel#ProcessAttributes()
	 */
	@Override
	public void ProcessAttributes() {
		try {
			ResultSet rs=LColec.getSQL().RunQuerrySELECT("SELECT * FROM section_data where idpadre="+Id+" order by orden;");
			if (rs!=null) 
			{
				while (rs.next()) {
					String id=rs.getObject("id").toString();
					
					String nombre=rs.getObject("nombre").toString();
					
					String navegable="N";
					if(rs.getObject("browseable")!=null)
						navegable=rs.getObject("browseable").toString();
					
					String visible="N";
					if(rs.getObject("visible")!=null)
						visible=rs.getObject("visible").toString();
					
					String tipo_valores=rs.getObject("tipo_valores").toString();
					
					String vocabulario=null;
					if(rs.getObject("vocabulario")!=null)
						vocabulario=rs.getObject("vocabulario").toString();
					
					if (nombre!=null&&!nombre.isEmpty()&&tipo_valores!=null&&!tipo_valores.isEmpty()&&((tipo_valores.equals("C")&&vocabulario!=null)||(!(tipo_valores.equals("C")))))
						{
						
						ArrayList<CompleteElementType> parsear = new ArrayList<CompleteElementType>(Hermanos);
						parsear.remove(AtributoMeta);
						
						ArrayList<CompleteElementType> Hermanosint=new ArrayList<CompleteElementType>();
						
						
						ElementType_NODE Nodo=new ElementType_NODE(id,nombre,navegable,visible,tipo_valores,vocabulario,AtributoMeta,false,LColec,CM,CompleteAsociado,CompleteAsociadoTabla,Hermanosint);
						CompleteElementType nodeattr = Nodo.getAtributoMeta();
						Hermanosint.add(nodeattr);
						AtributoMeta.getSons().add(nodeattr);
						
						HashMap<CompleteElementType, CompleteElementType> noexiste = CompleteAsociadoTabla.get(AtributoMeta);
						if (noexiste==null)
							noexiste=new HashMap<CompleteElementType, CompleteElementType>();
						noexiste.put(nodeattr, nodeattr);
						CompleteAsociadoTabla.put(AtributoMeta, noexiste);
						
						for (CompleteElementType AtributoMeta2 : parsear) {
							ElementType_NODE Nodo2=new ElementType_NODE(id,nombre,navegable,visible,tipo_valores,vocabulario,AtributoMeta2,false,LColec,CM,CompleteAsociado,CompleteAsociadoTabla,Hermanosint);
							CompleteElementType nodeattr2 = Nodo2.getAtributoMeta();
							nodeattr2.setClassOfIterator(nodeattr);
							AtributoMeta2.getSons().add(nodeattr2);
							Hermanosint.add(nodeattr2);
							
							HashMap<CompleteElementType, CompleteElementType> noexiste2 = CompleteAsociadoTabla.get(AtributoMeta2);
							if (noexiste2==null)
								noexiste2=new HashMap<CompleteElementType, CompleteElementType>();
							noexiste2.put(nodeattr, nodeattr2);
							CompleteAsociadoTabla.put(AtributoMeta, noexiste2);
						}
						
						Nodo.ProcessAttributes();
						Nodo.ProcessInstances();
						
						}
					else
					{
					if (tipo_valores==null||tipo_valores.isEmpty())
						LColec.getLog().add("Warning: Tipo de valores vacio o nulo, id estructura: '"+id+"', estuctura padre : '"+Id+"' (ignorado)");
					if (nombre==null||nombre.isEmpty())
						LColec.getLog().add("Warning: Nombre de la estructura del recurso vacia, id estructura: '"+id+"', padre : '"+Id+"' (ignorado)");
					if ((tipo_valores.equals("C")&&vocabulario==null))
						LColec.getLog().add("Warning: Tipo de estructura controlado pero valor de vocabulario vacio, id estructura: '"+id+"', padre : '"+Id+"' (ignorado)");
						
					}
					
				}
			rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (StaticFunctionsOda.isControled(AtributoMeta))
			ProcessVocabulary();

	}

	
	/** 
	 * Procesa el vocabulario de un MetaControlado
	 */
	private void ProcessVocabulary() {
		try {
			ResultSet rs=LColec.getSQL().RunQuerrySELECT("SELECT distinct value FROM controlled_data where idseccion="+Id+";");
			if (rs!=null) 
			{
				while (rs.next()) {
					
					String value="";
					if(rs.getObject("value")!=null)
						value=rs.getObject("value").toString();
					
					if (!value.isEmpty())
						{
						
						value=value.trim();
						String valueclean = StaticFunctionsOda.CleanStringFromDatabase(value,LColec);
						
						StaticFunctionsOda.InsertaEnLista(Vocabulary,valueclean);
						}
					
				}
			rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
	}

	
	
	
	/* (non-Javadoc)
	 * @see fdi.ucm.server.importparser.oda1.Oda1parserModel#ProcessInstances()
	 */
	@Override
	public void ProcessInstances() {
		
		if (StaticFunctionsOda.isNumeric(AtributoMeta))
			ProcessInstancesNumericas();
		else if (StaticFunctionsOda.isControled(AtributoMeta))
			ProcessInstancesControladas();
		else if (StaticFunctionsOda.isDate(AtributoMeta))
			ProcessInstancesFecha();
		else ProcessInstancesTexto();
		

	}

	

	private void ProcessInstancesFecha() {
		try {
			ResultSet rs=LColec.getSQL().RunQuerrySELECT("SELECT id,idov, value, idrecurso FROM date_data where idseccion="+Id+";");
			if (rs!=null) 
			{
				while (rs.next()) {
					
					String id1=rs.getObject("id").toString();
					
					String idov=rs.getObject("idov").toString();
					
					String value="";
					if(rs.getObject("value")!=null)
						value=rs.getObject("value").toString();
					
					String IdRecurso=null;
					if(rs.getObject("idrecurso")!=null)
						IdRecurso=rs.getObject("idrecurso").toString();
					
					if (idov!=null&&!idov.isEmpty()&&!value.isEmpty())
						{
						
						
						value=value.trim();
						String valueclean = StaticFunctionsOda.CleanStringFromDatabase(value,LColec);
						
						SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
						if (!valueclean.isEmpty())
							{
							try {
							Date D= formatter.parse(valueclean);
							DateFormat df = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
							String valueE=df.format(D);
							
//							CompleteTextElement MTV=new CompleteTextElement((CompleteTextElementType) AtributoMeta, valueE);
							int Idov=Integer.parseInt(idov);
							CompleteDocuments C=LColec.getCollection().getObjetoVirtual().get(Idov);

//							MTV.setDocumentsFather(C);
							
							//TODO Anulado
							if (IdRecurso!=null)
							{
							
								
								Long RecursoIntId = Long.parseLong(IdRecurso);
								
								
								CompleteElementType EsteActual = CompleteAsociado.get(RecursoIntId);
								
								
								if (EsteActual!=null)
								{

									HashMap<CompleteElementType, CompleteElementType> TablaCuadre = CompleteAsociadoTabla.get(EsteActual);
									if (TablaCuadre!=null)
									{
										CompleteElementType estees = TablaCuadre.get(AtributoMeta);
										if (estees!=null && estees instanceof CompleteTextElementType)
										{
											CompleteTextElement MTV=new CompleteTextElement((CompleteTextElementType) estees, valueE);
											MTV.setDocumentsFather(C);
											C.getDescription().add(MTV);
										}
										else
											LColec.getLog().add("Error en date_data id='"+id1+"' en idov='"+idov+"' con Recurso '"+IdRecurso+"' no se encuentra el recurso asociado(Code:NB)");
									}
									else
										LColec.getLog().add("Error en date_data id='"+id1+"' en idov='"+idov+"' con Recurso '"+IdRecurso+"' no se encuentra el recurso asociado(Code:NT)");
								}
							else 
								LColec.getLog().add("Error en date_data id='"+id1+"' en idov='"+idov+"' con Recurso '"+IdRecurso+"' no se encuentra el recurso asociado (Code:NR)");								
								
//								int RecursoIntId = Integer.parseInt(IdRecurso);
//								Integer AmbitoAsociado = ElementType_ObjetoVirtual_Resource.getAmbitosResource().get(RecursoIntId);
//								ArrayList<Integer> Ambitos=new ArrayList<Integer>();
//								if (AmbitoAsociado!=null)
//								{
//								Ambitos.add(AmbitoAsociado);
//								MTV.setAmbitos(Ambitos);
//								C.getDescription().add(MTV);
//								}
//							else 
//								LColec.getLog().add("Error en date_data id='"+id1+"' en idov='"+idov+"' con Recurso '"+IdRecurso+"' no se encuentra el recurso asociado");


							}else
							{
								CompleteTextElement MTV=new CompleteTextElement((CompleteTextElementType) AtributoMeta, valueE);
								MTV.setDocumentsFather(C);	
								C.getDescription().add(MTV);
							}
							
							} catch (Exception e) {
								LColec.getLog().add("Error en date_data id='"+id1+"' en idov='"+idov+"' y valor '"+valueclean+"', revisa que la base de datos es correcta");
								e.printStackTrace();
							}
						}
						
						
						
						
						
						}
					else 
					{
					if (idov==null||idov.isEmpty())
						LColec.getLog().add("Warning: Texto asociado a un objeto virtual vacio o nulo, id en text_data: '"+id1+"', campo padre: '"+Id+"' (ignorado)");
					if (value==null||value.isEmpty())
						LColec.getLog().add("Warning: Texto asociado al campo padre: '"+Id+"' vacio o nulo: id en text_data: '"+id1+"' (ignorado)");
					}
					
				}
			rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
//		catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
		
	}

	private void ProcessInstancesControladas() {
		try {
			ResultSet rs=LColec.getSQL().RunQuerrySELECT("SELECT id,idov, value, idrecurso FROM controlled_data where idseccion="+Id+";");
			if (rs!=null) 
			{
				while (rs.next()) {
					
					
					String id1=rs.getObject("id").toString();
					
					String idov=null;
					if(rs.getObject("idov")!=null)
						idov=rs.getObject("idov").toString();
					
					
					
					String value="";
					if(rs.getObject("value")!=null)
						value=rs.getObject("value").toString();
					
					String IdRecurso=null;
					if(rs.getObject("idrecurso")!=null)
						IdRecurso=rs.getObject("idrecurso").toString();
					
					if (idov!=null&&!idov.isEmpty()&&!value.isEmpty())
						{
						
						value=value.trim();
						String valueclean = StaticFunctionsOda.CleanStringFromDatabase(value,LColec);
						
						
//						
//						String T=StaticFunctionsOda1.BuscaEnLista(Vocabulary,valueclean);
						
						try {
//						CompleteTextElement MTV=new CompleteTextElement((CompleteTextElementType) AtributoMeta, valueclean);
						int Idov=Integer.parseInt(idov);
						CompleteDocuments C=LColec.getCollection().getObjetoVirtual().get(Idov);
//						MTV.setDocumentsFather(C);
						
						//TODO Anulado
						if (IdRecurso!=null)
							{
							
							Long RecursoIntId = Long.parseLong(IdRecurso);
							CompleteElementType EsteActual = CompleteAsociado.get(RecursoIntId);
														
														
														if (EsteActual!=null)
														{

															HashMap<CompleteElementType, CompleteElementType> TablaCuadre = CompleteAsociadoTabla.get(EsteActual);
															if (TablaCuadre!=null)
															{
																CompleteElementType estees = TablaCuadre.get(AtributoMeta);
																if (estees!=null && estees instanceof CompleteTextElementType)
																{
																	CompleteTextElement MTV=new CompleteTextElement((CompleteTextElementType) estees, valueclean);
																	MTV.setDocumentsFather(C);
																	C.getDescription().add(MTV);
																}
																else
																	LColec.getLog().add("Error en controled_data id='"+id1+"' en idov='"+idov+"' con Recurso '"+IdRecurso+"' no se encuentra el recurso asociado(Code:NB)");
															}
															else
																LColec.getLog().add("Error en controlled_data id='"+id1+"' en idov='"+idov+"' con Recurso '"+IdRecurso+"' no se encuentra el recurso asociado(Code:NT)");
														}
													else 
														LColec.getLog().add("Error en controlled_data id='"+id1+"' en idov='"+idov+"' con Recurso '"+IdRecurso+"' no se encuentra el recurso asociado (Code:NR)");
														

													
							
							
//								int RecursoIntId = Integer.parseInt(IdRecurso);
//								Integer AmbitoAsociado = ElementType_ObjetoVirtual_Resource.getAmbitosResource().get(RecursoIntId);
//								ArrayList<Integer> Ambitos=new ArrayList<Integer>();
//								if (AmbitoAsociado!=null)
//									{
//									Ambitos.add(AmbitoAsociado);
//									MTV.setAmbitos(Ambitos);
//									C.getDescription().add(MTV);
//									}
//								else 
//									LColec.getLog().add("Error en controlled_data id='"+id1+"' en idov='"+idov+"' con Recurso '"+IdRecurso+"' no se encuentra el recurso asociado");

							
							}else
							{
								CompleteTextElement MTV=new CompleteTextElement((CompleteTextElementType) AtributoMeta, valueclean);
								C.getDescription().add(MTV);
								MTV.setDocumentsFather(C);
								}
						
						} catch (Exception e) {
							LColec.getLog().add("Error en controlled_data id='"+id1+"' en idov='"+idov+"' y valor '"+valueclean+"', revisa que la base de datos es correcta");
							e.printStackTrace();
						}
						}
					else 
						{
						if (idov==null||idov.isEmpty())
							LColec.getLog().add("Warning: Controlado asociado a un objeto virtual vacio o nulo, id en controlled_data: '"+id1+"', campo padre: '"+Id+"' (ignorado)");
						if (value==null||value.isEmpty())
							LColec.getLog().add("Warning: Controlado asociado al campo padre: '"+Id+"' vacio o nulo: id en controlled_data: '"+id1+"' (ignorado)");
						}
					
				}
			rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
	}

	

	private void ProcessInstancesNumericas() {
		try {
			ResultSet rs=LColec.getSQL().RunQuerrySELECT("SELECT id,idov, value, idrecurso FROM numeric_data where idseccion="+Id+";");
			if (rs!=null) 
			{
				while (rs.next()) {
					
					String id1=rs.getObject("id").toString();
					
					String idov=rs.getObject("idov").toString();
					
					String value="";
					if(rs.getObject("value")!=null)
						value=rs.getObject("value").toString();
					
					String IdRecurso=null;
					if(rs.getObject("idrecurso")!=null)
						IdRecurso=rs.getObject("idrecurso").toString();
					
					
					if (idov!=null&&!idov.isEmpty()&&!value.isEmpty())
						{
		

						try {
						
//						CompleteTextElement MTV=new CompleteTextElement((CompleteTextElementType) AtributoMeta, value);
						int Idov=Integer.parseInt(idov);
						CompleteDocuments C=LColec.getCollection().getObjetoVirtual().get(Idov);
//						MTV.setDocumentsFather(C);
						
						//TODO Anulado
						if (IdRecurso!=null)
						{
						
Long RecursoIntId = Long.parseLong(IdRecurso);
							
							
							CompleteElementType EsteActual = CompleteAsociado.get(RecursoIntId);
							
							
							if (EsteActual!=null)
							{

								HashMap<CompleteElementType, CompleteElementType> TablaCuadre = CompleteAsociadoTabla.get(EsteActual);
								if (TablaCuadre!=null)
								{
									CompleteElementType estees = TablaCuadre.get(AtributoMeta);
									if (estees!=null && estees instanceof CompleteTextElementType)
									{
										CompleteTextElement MTV=new CompleteTextElement((CompleteTextElementType) estees, value);
										MTV.setDocumentsFather(C);
										C.getDescription().add(MTV);
									}
									else
										LColec.getLog().add("Error en numeric_data id='"+id1+"' en idov='"+idov+"' con Recurso '"+IdRecurso+"' no se encuentra el recurso asociado(Code:NB)");
								}
								else
									LColec.getLog().add("Error en numeric_data id='"+id1+"' en idov='"+idov+"' con Recurso '"+IdRecurso+"' no se encuentra el recurso asociado(Code:NT)");
							}
						else 
							LColec.getLog().add("Error en numeric_data id='"+id1+"' en idov='"+idov+"' con Recurso '"+IdRecurso+"' no se encuentra el recurso asociado (Code:NR)");
							
							
//							int RecursoIntId = Integer.parseInt(IdRecurso);
//							Integer AmbitoAsociado = ElementType_ObjetoVirtual_Resource.getAmbitosResource().get(RecursoIntId);
//							ArrayList<Integer> Ambitos=new ArrayList<Integer>();
//							if (AmbitoAsociado!=null)
//								{
//								Ambitos.add(AmbitoAsociado);
//								MTV.setAmbitos(Ambitos);
//								C.getDescription().add(MTV);
//								}
//							else 
//								LColec.getLog().add("Error en numeric_data id='"+id1+"' en idov='"+idov+"' con Recurso '"+IdRecurso+"' no se encuentra el recurso asociado");
//							
						}else
						{
							CompleteTextElement MTV=new CompleteTextElement((CompleteTextElementType) AtributoMeta, value);
							MTV.setDocumentsFather(C);
							C.getDescription().add(MTV);
							}
						
						} catch (Exception e) {
							LColec.getLog().add("Error en numeric_data id='"+id1+"' en idov='"+idov+"'  y valor '"+value+"', revisa que la base de datos es correcta");
							e.printStackTrace();
						}
						
						}
						
					else 
					{
					if (idov==null||idov.isEmpty())
						LColec.getLog().add("Warning: Numerico asociado a un objeto virtual vacio o nulo, id en numeric_data: '"+id1+"', campo padre: '"+Id+"' (ignorado)");
					if (value==null||value.isEmpty())
						LColec.getLog().add("Warning: Numerico asociado al campo padre: '"+Id+"' vacio o nulo: id en numeric_data: '"+id1+"' (ignorado)");
					}
					
				}
			rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 

		
	}

	private void ProcessInstancesTexto() {
		try {
			ResultSet rs=LColec.getSQL().RunQuerrySELECT("SELECT id,idov, value, idrecurso FROM text_data where idseccion="+Id+";");
			if (rs!=null) 
			{
				while (rs.next()) {
					
					String id1=rs.getObject("id").toString();
					
					String idov=rs.getObject("idov").toString();
					
					String value="";
					if(rs.getObject("value")!=null)
						value=rs.getObject("value").toString();
					
					String IdRecurso=null;
					if(rs.getObject("idrecurso")!=null)
						IdRecurso=rs.getObject("idrecurso").toString();
					
					if (idov!=null&&!idov.isEmpty()&&!value.isEmpty())
						{
						
						
						value=value.trim();
						String valueclean = StaticFunctionsOda.CleanStringFromDatabase(value,LColec);
						
						try {
						
//						CompleteTextElement MTV=new CompleteTextElement((CompleteTextElementType) AtributoMeta, valueclean);
						int Idov=Integer.parseInt(idov);
						CompleteDocuments C=LColec.getCollection().getObjetoVirtual().get(Idov);

//						MTV.setDocumentsFather(C);
						
						//TODO Anulado
						if (IdRecurso!=null)
						{
							

							Long RecursoIntId = Long.parseLong(IdRecurso);
								CompleteElementType EsteActual = CompleteAsociado.get(RecursoIntId);
								
								
								if (EsteActual!=null)
								{

									HashMap<CompleteElementType, CompleteElementType> TablaCuadre = CompleteAsociadoTabla.get(EsteActual);
									if (TablaCuadre!=null)
									{
										CompleteElementType estees = TablaCuadre.get(AtributoMeta);
										if (estees!=null && estees instanceof CompleteTextElementType)
										{
											CompleteTextElement MTV=new CompleteTextElement((CompleteTextElementType) estees, valueclean);
											MTV.setDocumentsFather(C);
											C.getDescription().add(MTV);
										}
										else
											LColec.getLog().add("Error en text_data id='"+id1+"' en idov='"+idov+"' con Recurso '"+IdRecurso+"' no se encuentra el recurso asociado(Code:NB)");
									}
									else
										LColec.getLog().add("Error en text_data id='"+id1+"' en idov='"+idov+"' con Recurso '"+IdRecurso+"' no se encuentra el recurso asociado(Code:NT)");
								}
							else 
								LColec.getLog().add("Error en text_data id='"+id1+"' en idov='"+idov+"' con Recurso '"+IdRecurso+"' no se encuentra el recurso asociado (Code:NR)");
								
							
//								int RecursoIntId = Integer.parseInt(IdRecurso);
//								Integer AmbitoAsociado = ElementType_ObjetoVirtual_Resource.getAmbitosResource().get(RecursoIntId);
//								ArrayList<Integer> Ambitos=new ArrayList<Integer>();
//								if (AmbitoAsociado!=null)
//									{
//									Ambitos.add(AmbitoAsociado);
//									MTV.setAmbitos(Ambitos);
//									C.getDescription().add(MTV);
//									}
//								else 
//									LColec.getLog().add("Error en text_data id='"+id1+"' en idov='"+idov+"' con Recurso '"+IdRecurso+"' no se encuentra el recurso asociado");

								
								
							
						}
						else
{
							
							CompleteTextElement MTV=new CompleteTextElement((CompleteTextElementType) AtributoMeta, valueclean);
							MTV.setDocumentsFather(C);
							C.getDescription().add(MTV);
							}
						
						} catch (Exception e) {
							LColec.getLog().add("Error en text_data id='"+id1+"' en idov='"+idov+"' con Recurso '"+IdRecurso+"' y valor '"+valueclean+"', revisa que la base de datos es correcta");
							e.printStackTrace();
						}
						
						}
					else 
					{
					if (idov==null||idov.isEmpty())
						LColec.getLog().add("Warning: Texto asociado a un objeto virtual vacio o nulo, id en text_data: '"+id1+"', campo padre: '"+Id+"' (ignorado)");
					if (value==null||value.isEmpty())
						LColec.getLog().add("Warning: Texto asociado al campo padre: '"+Id+"' vacio o nulo: id en text_data: '"+id1+"' (ignorado)");
					}
					
				}
			rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
//		catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
		
	}

	/**
	 * @return the atributoMeta
	 */
	public CompleteElementType getAtributoMeta() {
		return AtributoMeta;
	}

	/**
	 * @param atributoMeta the atributoMeta to set
	 */
	public void setAtributoMeta(CompleteElementType atributoMeta) {
		AtributoMeta = atributoMeta;
	}
	
	

}

/**
 * 
 */
package fdi.ucm.server.importparser.oda.oda2.direct.collection.categoria;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fdi.ucm.server.importparser.oda.NameConstantsOda;
import fdi.ucm.server.importparser.oda.StaticFunctionsOda;
import fdi.ucm.server.importparser.oda.coleccion.LoadCollectionOda;
import fdi.ucm.server.importparser.oda.coleccion.categoria.ElementType_ObjetoVirtual_Resource;
import fdi.ucm.server.modelComplete.collection.document.CompleteDocuments;
import fdi.ucm.server.modelComplete.collection.document.CompleteLinkElement;
import fdi.ucm.server.modelComplete.collection.document.CompleteOperationalValue;
import fdi.ucm.server.modelComplete.collection.document.CompleteTextElement;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteElementType;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteGrammar;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteLinkElementType;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteOperationalValueType;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteTextElementType;

/**
 * Implementa el atributo de los recursos en los Objetos Virtuales
 * @author Joaquin Gayoso-Cabada
 *
 */
public class ElementType_ObjetoVirtual_Resource_Direct_OV extends ElementType_ObjetoVirtual_Resource {



	
	private ArrayList<Long> idsOV;
	
	//TODO CANDIDATO A IR AL PADRE


	public ElementType_ObjetoVirtual_Resource_Direct_OV(CompleteGrammar I,LoadCollectionOda L) {
		super();
		PadreGrammar=I;
		AtributoMeta=new CompleteLinkElementType(NameConstantsOda.RESOURCENAME,I);
		AtributoMeta.setMultivalued(true);
		
		
		
		numTotales.add((CompleteLinkElementType)AtributoMeta);
		CompleteAsociado=new HashMap<Long, CompleteElementType>();
		CompleteAsociadoTabla=new HashMap<CompleteElementType, HashMap<CompleteElementType, CompleteElementType>>();
		LColec=L;
		idsOV=new ArrayList<Long>();
		
		
		HashMap<CompleteElementType, CompleteElementType> noexiste = CompleteAsociadoTabla.get(AtributoMeta);
		if (noexiste==null)
			noexiste=new HashMap<CompleteElementType, CompleteElementType>();
		noexiste.put(AtributoMeta, AtributoMeta);
		CompleteAsociadoTabla.put(AtributoMeta, noexiste);
		
		Valor2=new CompleteOperationalValueType(NameConstantsOda.VISIBLESHOWN,Boolean.toString(true),NameConstantsOda.PRESNTACION);
		CompleteOperationalValueType Valor4=new CompleteOperationalValueType(NameConstantsOda.BROWSERSHOWN,Boolean.toString(false),NameConstantsOda.PRESNTACION);
		CompleteOperationalValueType Valor3=new CompleteOperationalValueType(NameConstantsOda.SUMMARYSHOWN,Boolean.toString(false),NameConstantsOda.PRESNTACION);
		
		AtributoMeta.getShows().add(Valor2);
		AtributoMeta.getShows().add(Valor4);
		AtributoMeta.getShows().add(Valor3);
		

		CompleteOperationalValueType ValorMeta=new CompleteOperationalValueType(NameConstantsOda.TYPE,NameConstantsOda.RESOURCE,NameConstantsOda.META);
		AtributoMeta.getShows().add(ValorMeta);

	}
	
	/* (non-Javadoc)
	 * @see fdi.ucm.server.importparser.oda1.Oda1parserModel#ProcessAttributes()
	 */
	@Override
	public void ProcessAttributes() {
		{
		ID=new CompleteTextElementType(NameConstantsOda.IDNAME, AtributoMeta,PadreGrammar);
		AtributoMeta.getSons().add(ID);

		
		Valor=new CompleteOperationalValueType(NameConstantsOda.VISIBLESHOWN,Boolean.toString(true),NameConstantsOda.PRESNTACION);
		CompleteOperationalValueType Valor2=new CompleteOperationalValueType(NameConstantsOda.BROWSERSHOWN,Boolean.toString(false),NameConstantsOda.PRESNTACION);
		CompleteOperationalValueType Valor3=new CompleteOperationalValueType(NameConstantsOda.SUMMARYSHOWN,Boolean.toString(false),NameConstantsOda.PRESNTACION);
		
		ID.getShows().add(Valor);
		ID.getShows().add(Valor2);
		ID.getShows().add(Valor3);
		
		 CompleteOperationalValueType Valor=new CompleteOperationalValueType(NameConstantsOda.METATYPETYPE,NameConstantsOda.IGNORED,NameConstantsOda.META);
		 ID.getShows().add(Valor);

		}

		
		
	}

	/* (non-Javadoc)
	 * @see fdi.ucm.server.importparser.oda1.Oda1parserModel#ProcessInstances()
	 */
	@Override
	public void ProcessInstances() {
		OwnInstances();

	}

	/**
	 * Procesa las instancias propias
	 */
	private void OwnInstances() {
		OVInstances();
		atributes_Recursos();
		
		
		
	}
	
	


	private void atributes_Recursos() {
		try {
			ResultSet rs=LColec.getSQL().RunQuerrySELECT("SELECT * FROM section_data where idpadre=3 order by orden;");
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
					
					String tipo_valores=null;
					if(rs.getObject("tipo_valores")!=null)
						tipo_valores=rs.getObject("tipo_valores").toString();
					
					String vocabulario=null;
					if(rs.getObject("vocabulario")!=null)
						vocabulario=rs.getObject("vocabulario").toString();
					
					if (nombre!=null&&!nombre.isEmpty()&&tipo_valores!=null&&!tipo_valores.isEmpty()&&((tipo_valores.equals("C")&&vocabulario!=null)||(!(tipo_valores.equals("C")))))
						{
						
						nombre=nombre.trim();
						nombre = StaticFunctionsOda.CleanStringFromDatabase(nombre,LColec);
						
						ArrayList<CompleteLinkElementType> parsear = new ArrayList<CompleteLinkElementType>(numTotales);
						parsear.remove(AtributoMeta);
						
						
						ArrayList<CompleteElementType> Hermanos=new ArrayList<CompleteElementType>();
						
						ElementType_NODE Nodo=new ElementType_NODE(id,nombre,navegable,visible,tipo_valores,vocabulario,AtributoMeta,false,LColec,idsOV,PadreGrammar,CompleteAsociado,CompleteAsociadoTabla,Hermanos);
						CompleteElementType nodeattr = Nodo.getAtributoMeta();
						Hermanos.add(nodeattr);
						AtributoMeta.getSons().add(nodeattr);
						
						HashMap<CompleteElementType, CompleteElementType> noexiste = CompleteAsociadoTabla.get(AtributoMeta);
						if (noexiste==null)
							noexiste=new HashMap<CompleteElementType, CompleteElementType>();
						noexiste.put(nodeattr, nodeattr);
						CompleteAsociadoTabla.put(AtributoMeta, noexiste);
						
						for (CompleteLinkElementType AtributoMeta2 : parsear) {
							ElementType_NODE Nodo2=new ElementType_NODE(id,nombre,navegable,visible,tipo_valores,vocabulario,AtributoMeta2,false,LColec,idsOV,PadreGrammar,CompleteAsociado,CompleteAsociadoTabla,Hermanos);
							CompleteElementType nodeattr2 = Nodo2.getAtributoMeta();
							nodeattr2.setClassOfIterator(nodeattr);
							AtributoMeta2.getSons().add(nodeattr2);
							Hermanos.add(nodeattr2);
							
							HashMap<CompleteElementType, CompleteElementType> noexiste2 = CompleteAsociadoTabla.get(AtributoMeta2);
							if (noexiste2==null)
								noexiste2=new HashMap<CompleteElementType, CompleteElementType>();
							noexiste2.put(nodeattr, nodeattr2);
							CompleteAsociadoTabla.put(AtributoMeta2, noexiste2);
						}
						
						
						Nodo.ProcessAttributes();
						Nodo.ProcessInstances();
						
						}
					else
						{
						if (tipo_valores==null||tipo_valores.isEmpty())
							LColec.getLog().add("Warning: Tipo de valores vacio o nulo, id estructura: '"+id+"', estuctura padre : '3' (ignorado)");
						if (nombre==null||nombre.isEmpty())
							LColec.getLog().add("Warning: Nombre de la estructura del recurso vacia, id estructura: '"+id+"', padre : '3' (ignorado)");
						if ((tipo_valores.equals("C")&&vocabulario==null))
							LColec.getLog().add("Warning: Tipo de estructura controlado pero valor de vocabulario vacio, id estructura: '"+id+"', padre : '3' (ignorado)");
							
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

	private void OVInstances() {
		try {
			ResultSet rs=LColec.getSQL().RunQuerrySELECT("SELECT * FROM resources where type='OV' order by idov;");
			if (rs!=null) 
			{
				while (rs.next()) {
					
					String id=rs.getObject("id").toString();
					String idov=rs.getObject("idov").toString();
					
					String Visible="N";
					if(rs.getObject("visible")!=null)
						Visible=rs.getObject("visible").toString();
					
					String idovrefered="";
					if(rs.getObject("idov_refered")!=null)
						idovrefered=rs.getObject("idov_refered").toString();
					

					
					
					if (idov!=null&&!idov.isEmpty()&&!idovrefered.isEmpty())
						{
						Integer Idov=Integer.parseInt(idov);
						Integer idovrefered2=Integer.parseInt(idovrefered);
						
						Long Idl=Long.parseLong(id);
						idsOV.add(Idl);
						
						CompleteDocuments OVirtual=LColec.getCollection().getObjetoVirtual().get(Idov);
						CompleteDocuments OVirtualRef=LColec.getCollection().getObjetoVirtual().get(idovrefered2);
						
						if (OVirtual!=null&&OVirtualRef!=null)
						{
							
							
							List<CompleteLinkElementType> Actuales = numActivos.get(Idov);
							
							if (Actuales==null)
								Actuales=new ArrayList<CompleteLinkElementType>();
							
							while(Actuales.size()>=numTotales.size())
								clonereso();
							
							
							CompleteLinkElementType mio = numTotales.get(Actuales.size());
							
							Actuales.add(mio);
							
							numActivos.put(Idov, Actuales);
							
							
							CompleteAsociado.put(Idl, mio);
							
							
							
							boolean Visiblebool=true;
							if (Visible.equals("N"))
								Visiblebool=false;
							

							//TODO 
//							LColec.getCollection().getFilesId().put(id,FileC);
							
							if (mio.getSons().get(0) instanceof CompleteElementType && mio.getSons().get(0).getName().equals(NameConstantsOda.IDNAME))
							{
							CompleteTextElement E=new CompleteTextElement((CompleteTextElementType) mio.getSons().get(0), id);
							OVirtual.getDescription().add(E);
							}
							
							
							CompleteLinkElement E3=new CompleteLinkElement(mio,OVirtualRef);
							OVirtual.getDescription().add(E3);			
							CompleteOperationalValue Valor=new CompleteOperationalValue(miofindValor2(mio),Boolean.toString(Visiblebool));
							E3.getShows().add(Valor);
							
							
						
						}
					else 
					{
						if (OVirtual==null)
							LColec.getLog().add("Warning: Objeto Virtual al que se asocia este recurso no existe, Id de objeto virtual : '" +Idov + "', Idrecurso: '"+id+ "'(ignorado)");
						if (OVirtualRef==null)
							LColec.getLog().add("Warning: El Objeto Virtal referencia al que apunta : Referencia : '" + idovrefered + "' no existe, Idrecurso: '"+id+ "'(ignorado)");
					}
						}
					else {
						if (idov==null||idov.isEmpty())
							LColec.getLog().add("Warning: Objeto Virtual asociado al que se asocia el recurso es vacio, Idrecurso: '"+id+"' es vacio o el file referencia asociado es vacio (ignorado)");
						if (idovrefered==null||idovrefered.isEmpty())
							LColec.getLog().add("Warning: Nombre objeto virtual referencia vacio, Idrecurso: '"+id+"' (ignorado)");
					}
				}
			rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
@Override
	protected void clonereso() {
		CompleteLinkElementType AtributoMeta2 = new CompleteLinkElementType(NameConstantsOda.RESOURCENAME,PadreGrammar);
		numTotales.add(AtributoMeta2);
		AtributoMeta2.setClassOfIterator(AtributoMeta);
		AtributoMeta2.setMultivalued(true);
		
		CompleteOperationalValueType Valor23 = new CompleteOperationalValueType(NameConstantsOda.VISIBLESHOWN,Boolean.toString(true),NameConstantsOda.PRESNTACION);
		CompleteOperationalValueType Valor43=new CompleteOperationalValueType(NameConstantsOda.BROWSERSHOWN,Boolean.toString(false),NameConstantsOda.PRESNTACION);
		CompleteOperationalValueType Valor33=new CompleteOperationalValueType(NameConstantsOda.SUMMARYSHOWN,Boolean.toString(false),NameConstantsOda.PRESNTACION);
		
		AtributoMeta2.getShows().add(Valor23);
		AtributoMeta2.getShows().add(Valor43);
		AtributoMeta2.getShows().add(Valor33);
		


		CompleteOperationalValueType ValorMeta=new CompleteOperationalValueType(NameConstantsOda.TYPE,NameConstantsOda.RESOURCE,NameConstantsOda.META);
		AtributoMeta2.getShows().add(ValorMeta);
		
		{
			CompleteTextElementType ID2 = new CompleteTextElementType(NameConstantsOda.IDNAME, AtributoMeta2,PadreGrammar);
			AtributoMeta2.getSons().add(ID2);
			ID2.setClassOfIterator(ID);
			
			CompleteOperationalValueType Valor12 = new CompleteOperationalValueType(NameConstantsOda.VISIBLESHOWN,Boolean.toString(true),NameConstantsOda.PRESNTACION);
			CompleteOperationalValueType Valor22=new CompleteOperationalValueType(NameConstantsOda.BROWSERSHOWN,Boolean.toString(false),NameConstantsOda.PRESNTACION);
			CompleteOperationalValueType Valor32=new CompleteOperationalValueType(NameConstantsOda.SUMMARYSHOWN,Boolean.toString(false),NameConstantsOda.PRESNTACION);
			
			ID2.getShows().add(Valor12);
			ID2.getShows().add(Valor22);
			ID2.getShows().add(Valor32);
			

			 CompleteOperationalValueType Valor42=new CompleteOperationalValueType(NameConstantsOda.METATYPETYPE,NameConstantsOda.IGNORED,NameConstantsOda.META);
			 ID2.getShows().add(Valor42);
			}
		
		PadreGrammar.getSons().add(AtributoMeta2);
		
		HashMap<CompleteElementType, CompleteElementType> noexiste = CompleteAsociadoTabla.get(AtributoMeta2);
		if (noexiste==null)
			noexiste=new HashMap<CompleteElementType, CompleteElementType>();
		noexiste.put(AtributoMeta, AtributoMeta2);
		CompleteAsociadoTabla.put(AtributoMeta2, noexiste);
		
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
	public void setAtributoMeta(CompleteLinkElementType atributoMeta) {
		AtributoMeta = atributoMeta;
	}
	

}

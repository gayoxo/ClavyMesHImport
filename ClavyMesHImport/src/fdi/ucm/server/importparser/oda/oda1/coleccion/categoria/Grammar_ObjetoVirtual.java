/**
 * 
 */
package fdi.ucm.server.importparser.oda.oda1.coleccion.categoria;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fdi.ucm.server.importparser.oda.InterfaceOdaparser;
import fdi.ucm.server.importparser.oda.NameConstantsOda;
import fdi.ucm.server.importparser.oda.coleccion.LoadCollectionOda;
import fdi.ucm.server.importparser.oda.coleccion.categoria.ElementType_ObjetoVirtual_Resource;
import fdi.ucm.server.modelComplete.collection.CompleteCollection;
import fdi.ucm.server.modelComplete.collection.document.CompleteDocuments;
import fdi.ucm.server.modelComplete.collection.document.CompleteOperationalValue;
import fdi.ucm.server.modelComplete.collection.document.CompleteTextElement;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteGrammar;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteOperationalValueType;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteTextElementType;

/**
 * Clase que define la creacion de un virtual object metadata
 * @author Joaquin Gayoso-Cabada
 *
 */
public class Grammar_ObjetoVirtual implements InterfaceOdaparser {

	private CompleteGrammar AtributoMeta;
	private CompleteTextElementType IDOV;
	
	private ElementType_ObjetoVirtual_Resource Recursos;
	
	private CompleteOperationalValueType ValorOdaPUBLIC;
//	private HashMap<Integer, Element> ObjetoVirtualMetaValueAsociado;

	private LoadCollectionOda LColec;
	private CompleteTextElementType URLORIGINAL;
	private List<CompleteOperationalValueType> VistaOV;
	
	public Grammar_ObjetoVirtual(CompleteCollection completeCollection, LoadCollectionOda L) {
		AtributoMeta=new CompleteGrammar(NameConstantsOda.VIRTUAL_OBJECTNAME, NameConstantsOda.VIRTUAL_OBJECTNAME,completeCollection);
		

		LColec=L;
		
		VistaOV=new ArrayList<CompleteOperationalValueType>();

		CompleteOperationalValueType Valor=new CompleteOperationalValueType(NameConstantsOda.VISIBLESHOWN,Boolean.toString(true),NameConstantsOda.PRESNTACION);
		CompleteOperationalValueType Valor2=new CompleteOperationalValueType(NameConstantsOda.BROWSERSHOWN,Boolean.toString(false),NameConstantsOda.PRESNTACION);
		CompleteOperationalValueType Valor3=new CompleteOperationalValueType(NameConstantsOda.SUMMARYSHOWN,Boolean.toString(false),NameConstantsOda.PRESNTACION);
		
		AtributoMeta.getViews().add(Valor);
		AtributoMeta.getViews().add(Valor2);
		AtributoMeta.getViews().add(Valor3);
		
		VistaOV.add(Valor);
		VistaOV.add(Valor2);
		VistaOV.add(Valor3);

		CompleteOperationalValueType ValorMeta=new CompleteOperationalValueType(NameConstantsOda.TYPE,NameConstantsOda.VIRTUAL_OBJECT,NameConstantsOda.META);
		
		AtributoMeta.getViews().add(ValorMeta);
	
		

		
		ValorOdaPUBLIC=new CompleteOperationalValueType(NameConstantsOda.PUBLIC,Boolean.toString(true),NameConstantsOda.ODA);

		CompleteOperationalValueType ValorOda2=new CompleteOperationalValueType(NameConstantsOda.PRIVATE,Boolean.toString(false),NameConstantsOda.ODA);

		AtributoMeta.getViews().add(ValorOdaPUBLIC);
		AtributoMeta.getViews().add(ValorOda2);
		

	}
	
	
	/* (non-Javadoc)
	 * @see fdi.ucm.server.importparser.oda1.Oda1parserModel#ProcessAttributes()
	 */
	@Override
	public void ProcessAttributes() {
		{
		IDOV=new CompleteTextElementType(NameConstantsOda.IDOVNAME, AtributoMeta);
		
		CompleteOperationalValueType Valor=new CompleteOperationalValueType(NameConstantsOda.VISIBLESHOWN,Boolean.toString(true),NameConstantsOda.PRESNTACION);
		CompleteOperationalValueType Valor2=new CompleteOperationalValueType(NameConstantsOda.BROWSERSHOWN,Boolean.toString(false),NameConstantsOda.PRESNTACION);
		CompleteOperationalValueType Valor3=new CompleteOperationalValueType(NameConstantsOda.SUMMARYSHOWN,Boolean.toString(true),NameConstantsOda.PRESNTACION);
		
		IDOV.getShows().add(Valor);
		IDOV.getShows().add(Valor2);
		IDOV.getShows().add(Valor3);

		CompleteOperationalValueType ValorMeta=new CompleteOperationalValueType(NameConstantsOda.TYPE,NameConstantsOda.IDOV,NameConstantsOda.META);
		
		IDOV.getShows().add(ValorMeta);
		
		
		AtributoMeta.getSons().add(IDOV);
		
		 CompleteOperationalValueType Valor4=new CompleteOperationalValueType(NameConstantsOda.METATYPETYPE,NameConstantsOda.TEXT,NameConstantsOda.METATYPE);
		 IDOV.getShows().add(Valor4);
		
		}
		
		{
			URLORIGINAL=new CompleteTextElementType(NameConstantsOda.URLORIGINAL, AtributoMeta);
			


			CompleteOperationalValueType ValorMeta=new CompleteOperationalValueType(NameConstantsOda.TYPE,NameConstantsOda.URLORIGINAL,NameConstantsOda.META);
			
			 URLORIGINAL.getShows().add(ValorMeta);
			


			 CompleteOperationalValueType Valor4=new CompleteOperationalValueType(NameConstantsOda.METATYPETYPE,NameConstantsOda.TEXT,NameConstantsOda.METATYPE);
			 URLORIGINAL.getShows().add(Valor4);


			 CompleteOperationalValueType Valor=new CompleteOperationalValueType(NameConstantsOda.METATYPETYPE,NameConstantsOda.IGNORED,NameConstantsOda.META);
			 URLORIGINAL.getShows().add(Valor);

			 
			
			AtributoMeta.getSons().add(URLORIGINAL);
			}
		
		

		Recursos=new ElementType_ObjetoVirtual_Resource(AtributoMeta,LColec);
		Recursos.ProcessAttributes();
		AtributoMeta.getSons().add(Recursos.getAtributoMeta());

		

		
		
		
	}

	/* (non-Javadoc)
	 * @see fdi.ucm.server.importparser.oda1.Oda1parserModel#ProcessInstances()
	 */
	@Override
	public void ProcessInstances() {
		OwnInstances();
		
//		TODO Anulado
//		Recursos.ProcessInstances();

	}

	/**
	 * Procesa las instancias propias
	 */
	private void OwnInstances() {
//		 ObjetoVirtualMetaValueAsociado=new HashMap<Integer, Element>();
		 HashMap<Integer, CompleteDocuments> ObjetoVirtual= new HashMap<Integer, CompleteDocuments>();
		try {
			ResultSet rs=LColec.getSQL().RunQuerrySELECT("SELECT * FROM virtual_object;");
			if (rs!=null) 
			{
				while (rs.next()) {
					
					String id=rs.getObject("id").toString();
					
					String publicoT="";
					if(rs.getObject("ispublic")!=null)
						publicoT=rs.getObject("ispublic").toString();
					
					if (publicoT!=null&&!publicoT.isEmpty())
						{
						int Idov=Integer.parseInt(id);
						boolean publico=true;
						if (publicoT.equals("N"))
							publico=false;
						CompleteCollection C=LColec.getCollection().getCollection();
						CompleteDocuments sectionValue = new CompleteDocuments(C,"","");
						C.getEstructuras().add(sectionValue);
						CompleteTextElement E=new CompleteTextElement(IDOV, id);
						sectionValue.getDescription().add(E);
						
						CompleteOperationalValue ValorOdaPUBLIInstance=new CompleteOperationalValue(ValorOdaPUBLIC,Boolean.toString(publico));

				//		Element MetaValueAsociado = new Element(AtributoMeta);
						sectionValue.getViewsValues().add(ValorOdaPUBLIInstance);
//						MetaValueAsociado.getShows().add(ValorOdaPUBLIInstance);
						
//						ObjetoVirtualMetaValueAsociado.put(Idov, MetaValueAsociado);
						ObjetoVirtual.put(Idov, sectionValue);
						
						
						StringBuffer SB=new StringBuffer();
						
						if (LColec.getBaseURLOdaSimple().isEmpty()||
								(!LColec.getBaseURLOdaSimple().startsWith("http://")
										&&!LColec.getBaseURLOdaSimple().startsWith("https://")
										&&!LColec.getBaseURLOdaSimple().startsWith("ftp://")))
							SB.append("http://");
						
						SB.append(LColec.getBaseURLOdaSimple());
						if (!LColec.getBaseURLOdaSimple().isEmpty()&&!LColec.getBaseURLOdaSimple().endsWith("//"))
							SB.append("/");
						SB.append(NameConstantsOda.VIEWDOC+id);
						
						String Path=SB.toString();
						
						CompleteTextElement RR=new CompleteTextElement(URLORIGINAL, Path);
						sectionValue.getDescription().add(RR);
						
						}
					else {
						if (publicoT==null||publicoT.isEmpty())
							LColec.getLog().add("Warning: Estado de privacidad ambiguo en, Identificador Objeto virtual: '"+id+"' (ignorado)");
					}
				}
				LColec.getCollection().setObjetoVirtual(ObjetoVirtual);
			rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}


	/**
	 * @return the atributoMeta
	 */
	public CompleteGrammar getAtributoMeta() {
		return AtributoMeta;
	}

	/**
	 * @param atributoMeta the atributoMeta to set
	 */
	public void setAtributoMeta(CompleteGrammar atributoMeta) {
		AtributoMeta = atributoMeta;
	}




	/**
	 * @return the vistaOV
	 */
	public List<CompleteOperationalValueType> getVistaOV() {
		return VistaOV;
	}


	/**
	 * @param vistaOV the vistaOV to set
	 */
	public void setVistaOV(List<CompleteOperationalValueType> vistaOV) {
		VistaOV = vistaOV;
	}

	
	
	


	
	

}

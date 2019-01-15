/**
 * 
 */
package fdi.ucm.server.importparser.oda.coleccion.categoria;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import fdi.ucm.server.importparser.oda.InterfaceOdaparser;
import fdi.ucm.server.importparser.oda.NameConstantsOda;
import fdi.ucm.server.importparser.oda.StaticFunctionsOda;
import fdi.ucm.server.importparser.oda.coleccion.LoadCollectionOda;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteElementType;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteGrammar;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteOperationalValueType;

/**
 * Clase que define la carga de los datos recurso
 * @author Joaquin Gayoso-Cabada
 *
 */
public class ElementType_Metadatos implements InterfaceOdaparser{

	
	private CompleteElementType AtributoMeta;
	private LoadCollectionOda LColec;
	private CompleteGrammar Padre;


	public ElementType_Metadatos(CompleteGrammar Padre,LoadCollectionOda L) {

		AtributoMeta=new CompleteElementType(NameConstantsOda.METADATOSNAME, Padre);
		LColec=L;
		this.Padre=Padre;
		
		
		CompleteOperationalValueType Valor=new CompleteOperationalValueType(NameConstantsOda.VISIBLESHOWN,Boolean.toString(true),NameConstantsOda.PRESNTACION);
		CompleteOperationalValueType Valor2=new CompleteOperationalValueType(NameConstantsOda.BROWSERSHOWN,Boolean.toString(false),NameConstantsOda.PRESNTACION);
		CompleteOperationalValueType Valor3=new CompleteOperationalValueType(NameConstantsOda.SUMMARYSHOWN,Boolean.toString(false),NameConstantsOda.PRESNTACION);
		AtributoMeta.getShows().add(Valor);
		AtributoMeta.getShows().add(Valor2);
		AtributoMeta.getShows().add(Valor3);
		
		CompleteOperationalValueType ValorMeta=new CompleteOperationalValueType(NameConstantsOda.TYPE,NameConstantsOda.METADATOS,NameConstantsOda.META);
		AtributoMeta.getShows().add(ValorMeta);

	}

	@Override
	public void ProcessAttributes() {
		try {
			ResultSet rs=LColec.getSQL().RunQuerrySELECT("SELECT * FROM section_data where idpadre=2 order by orden;");
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
						
						nombre=nombre.trim();
						nombre = StaticFunctionsOda.CleanStringFromDatabase(nombre,LColec);
						
						ElementType_NODE Nodo=new ElementType_NODE(id,nombre,navegable,visible,tipo_valores,vocabulario,AtributoMeta,false,LColec,Padre,new HashMap<Long, CompleteElementType>(),new HashMap<CompleteElementType, HashMap<CompleteElementType, CompleteElementType>>(),new ArrayList<CompleteElementType>());
						Nodo.ProcessAttributes();
						Nodo.ProcessInstances();
						AtributoMeta.getSons().add(Nodo.getAtributoMeta());
						}
					else
					{
					if (tipo_valores==null||tipo_valores.isEmpty())
						LColec.getLog().add("Warning: Tipo de valores vacio o nulo, id estructura: '"+id+"', estuctura padre : '2' (ignorado)");
					if (nombre==null||nombre.isEmpty())
						LColec.getLog().add("Warning: Nombre de la estructura del recurso vacia, id estructura: '"+id+"', padre : '2' (ignorado)");
					if ((tipo_valores.equals("C")&&vocabulario==null))
						LColec.getLog().add("Warning: Tipo de estructura controlado pero valor de vocabulario vacio, id estructura: '"+id+"', padre : '2' (ignorado)");
						
					}
					
				}
			rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
	}

	@Override
	public void ProcessInstances() {
		// No tiene Instancias
		
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

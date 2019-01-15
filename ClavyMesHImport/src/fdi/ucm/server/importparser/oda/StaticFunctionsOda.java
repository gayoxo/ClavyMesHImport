/**
 * 
 */
package fdi.ucm.server.importparser.oda;

//import java.nio.ByteBuffer;
//import java.nio.CharBuffer;
//import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import fdi.ucm.server.importparser.oda.coleccion.LoadCollectionOda;
import fdi.ucm.server.modelComplete.collection.document.CompleteElement;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteElementType;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteOperationalValueType;

/**
 * Clase que genera las funciones estaticas para el sistema Oda1.
 * @author Joaquin Gayoso-Cabada
 *
 */
public class StaticFunctionsOda {

	

	



	
	/**
	 * Inserta el valor del texto en la lista si no esta
	 * @param vocabulary
	 * @param valueclean
	 */
	public static void InsertaEnLista(ArrayList<String> vocabulary, String valueclean) {
		boolean Encontrado=false;
		for (String term : vocabulary) {
			if (term.equals(valueclean))
				{
				Encontrado=true;
				break;
				}
		}
		if (!Encontrado)
			vocabulary.add(valueclean);
		
	}
	
	/**
	 * Funcion que busca una descripcion en una lista de descripciones de un elemento
	 * @param list lista de descripciones
	 * @param text texto a buscar
	 * @return el Meta valu de esa descripcion o null en caso contrario.
	 */
	public static CompleteElement FindDescAtrib(List<CompleteElement> list,
			String text) {
		for (CompleteElement completeElement : list) {
			if(completeElement.getHastype().getName().equals(text))
				return completeElement;
		}
		return null;
	}

	/**
	 * Limpia el String de la base de datos si procede, sino se devuelve
	 * @param value
	 * @return string limpio o si mismo depenede del flag de conversion
	 */
	public static String CleanStringFromDatabase(String value,LoadCollectionOda L) {
		if (L.isConvert())
		{
			
			
			return parseUTF(value);
			
//			Charset utf8charset = Charset.forName("UTF-8");
//		    Charset iso88591charset = Charset.forName("ISO-8859-1");
//
//		    ByteBuffer inputBuffer = ByteBuffer.wrap(value.getBytes());
//		    CharBuffer data = utf8charset.decode(inputBuffer);
//
//		    ByteBuffer outputBuffer = iso88591charset.encode(data);
//		    byte[] outputData = outputBuffer.array();
//
//		    return new String(outputData);
			

		}
		else return value;
	}

	private static String parseUTF(String valor1) {
		valor1 = valor1.replace("Ã¡", "á");
		valor1 = valor1.replace("Ã©", "é");
		valor1 = valor1.replace("Ã*", "í");
		valor1 = valor1.replace("Ã³", "ó");
		valor1 = valor1.replace("Ãº", "ú");
		valor1 = valor1.replace("Ã", "Á");
		valor1 = valor1.replace("Ã‰", "É");
		valor1 = valor1.replace("Ã", "Í");
		valor1 = valor1.replace("Ã“", "Ó");
		valor1 = valor1.replace("Ãš", "Ú");
		valor1 = valor1.replace("Ã±", "ñ");
		valor1 = valor1.replace("Ã‘", "Ñ");
		valor1 = valor1.replace("Ã¶", "Ó");
		valor1 = valor1.replace("Ã¼", "Ó");
			
		valor1 = valor1.replace("Â", "");
		
		valor1 = valor1.replace("Ã", "í");
		return valor1;
	}
	
	/**
	 * Retorna si el atributo es controlado.
	 * @param atributoMeta
	 * @return
	 */
	public static boolean isControled(CompleteElementType attribute) {
		ArrayList<CompleteOperationalValueType> Shows = attribute.getShows();
		for (CompleteOperationalValueType show : Shows) {	
			if (show.getView().equals(NameConstantsOda.METATYPE))
					if (show.getName().equals(NameConstantsOda.METATYPETYPE))
							if (show.getDefault().equals(NameConstantsOda.CONTROLED)) 
										return true;
		}
		return false;
	}

	
	/**
	 * Retorna si el atributo es controlado.
	 * @param atributoMeta
	 * @return
	 */
	public static boolean isDate(CompleteElementType attribute) {
		ArrayList<CompleteOperationalValueType> Shows = attribute.getShows();
		for (CompleteOperationalValueType show : Shows) {	
			if (show.getView().equals(NameConstantsOda.METATYPE))
					if (show.getName().equals(NameConstantsOda.METATYPETYPE))
							if (show.getDefault().equals(NameConstantsOda.DATE)) 
										return true;

		}
		return false;
	}

	
	public static boolean isNumeric(CompleteElementType attribute) {
		ArrayList<CompleteOperationalValueType> Shows = attribute.getShows();
		for (CompleteOperationalValueType show : Shows) {	
			if (show.getView().equals(NameConstantsOda.METATYPE))
					if (show.getName().equals(NameConstantsOda.METATYPETYPE))
							if (show.getDefault().equals(NameConstantsOda.NUMERIC)) 
										return true;

		}
		return false;
	}


	


	
}

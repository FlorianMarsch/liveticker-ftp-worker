package de.florianmarsch.playerdata.axis;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.rpc.Call;
import javax.xml.rpc.ServiceException;
import javax.xml.rpc.encoding.DeserializerFactory;
import javax.xml.rpc.encoding.SerializerFactory;

import org.apache.axis.client.Service;

import de.florianmarsch.playerdata.club.Club;

public class PlayerItemWebservice {
	
	
	public List<PlayerItem> getAllPlayers() {
		List<PlayerItem> tempReturn = new ArrayList<PlayerItem>();

		List<Club> clubs = new ArrayList<Club>();
		clubs.addAll(Arrays.asList(Club.values()));

		for (Club club : clubs ) {
			tempReturn.addAll(getplayersbyclubid(club.getWebserviceId()));
		}
		
		return tempReturn;
	}



	/**
	 * @param args
	 * @throws RemoteException
	 * @throws ServiceException
	 */
	public List<PlayerItem> getplayersbyclubid(int id) {

		try {
			// Folgender Code beruft sich auf das Beispiel von
			// http://axis.apache.org/axis/java/user-guide.html#ConsumingWebServicesWithAxis

			// Adresse des Comunio Webservice
			String endpoint = "http://www.comunio.de/soapservice.php?wsdl/";
//			endpoint="http://www.comunio2014.com/de/soapservice.php?wsdl/";
			

			Service service = new Service();
			// to use Basic HTTP Authentication:

			Long token = new Date().getTime();
			Class clas = PlayerItem.class;
			SerializerFactory serFactory = new StructSerializer();
			DeserializerFactory deFactory = new StructDeserializer();
			((StructDeserializer) deFactory).setResultToken(token);
			service.getEngine()
					.getTypeMappingRegistry()
					.getDefaultTypeMapping()
					.register(
							clas,
							QName.valueOf("{http://schemas.xmlsoap.org/soap/encoding/}Struct"),
							serFactory, deFactory);
			//
			Call call;
			call = (Call) service.createCall();

			call.setTargetEndpointAddress(endpoint);

			// Methode angeben
			call.setOperationName(new QName("getplayerbyid"));
			call.setOperationName(new QName("getplayersbyclubid"));
//			call.setOperationName(new QName("getplayersbyclubidinclretired"));
//			call.setOperationName(new QName("getclubs"));

			// ID von Mario Gomez
			int playerID = 30517;
			// ID von den BAyern
			playerID = 1;
			playerID = id;

			// Aufruf oben spezifizierter Methode mit der ID von Gomez
			// Man erh�lt eine HashMap (String -> Object)
			call.invoke(new Object[] { playerID });
			Object result = ResultHolder.getResult(token);
			ResultHolder.removeResult(token);
			return (List<PlayerItem>) result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			 e.printStackTrace();
			return new ArrayList<PlayerItem>();
		}

	}
}

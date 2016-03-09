package com.heroku.devcenter.axis;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.rpc.Call;
import javax.xml.rpc.ServiceException;
import javax.xml.rpc.encoding.DeserializerFactory;
import javax.xml.rpc.encoding.SerializerFactory;

import org.apache.axis.client.Service;

public class PlayerItemWebservice {
	
	
	public List<PlayerItem> getAllPlayers() {
		List<PlayerItem> tempReturn = new ArrayList<PlayerItem>();

		List<Integer> clubs = new ArrayList<Integer>();
		clubs.add(1);
		clubs.add(12);
		clubs.add(3);
		clubs.add(8);
		clubs.add(10);
		clubs.add(68);
		clubs.add(62);
		clubs.add(5);
		clubs.add(6);
		clubs.add(18);
		clubs.add(9);
		clubs.add(13);
		clubs.add(7);
		clubs.add(17);
		clubs.add(4);
		clubs.add(14);
		clubs.add(89);
		clubs.add(90);

		for (Integer id : clubs ) {
			tempReturn.addAll(getplayersbyclubid(id));
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

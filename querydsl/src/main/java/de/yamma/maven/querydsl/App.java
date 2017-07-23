package de.yamma.maven.querydsl;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import com.querydsl.mongodb.morphia.MorphiaQuery;

import de.yamma.maven.querydsl.entities.QSport;
import de.yamma.maven.querydsl.entities.Sport;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws UnknownHostException {
		System.out.println("Hello World!");

		QSport sport = QSport.sport;

		String username = "admin";
		String password = "";
		String host = "";
		int port = 27017;
		String dbName = "admin";

				
		
		// Manage the mongo db connection...
		List<ServerAddress> seeds = new ArrayList<>();
		seeds.add( new ServerAddress(host, port ));
		List<MongoCredential> credentials = new ArrayList<>();
		credentials.add(
		    MongoCredential.createCredential(
		        username,
		        dbName,
		        password.toCharArray()
		    )
		);
		MongoClient mongoClient = new MongoClient( seeds, credentials );
		
//		MongoClient mongoClient = new MongoClient(new MongoClientURI(
//				host != null ? "mongodb://" + username + ":" + password + "@" + host + ':' + port : "mongodb://localhost:27017"));

		MongoDatabase db = mongoClient.getDatabase(dbName);

		Morphia morphia = new Morphia();
		// https://stackoverflow.com/questions/21859579/authentication-during-connection-to-mongodb-server-instance-using-java

		while (true) {
			try {
			
				Datastore datastore = morphia.createDatastore(mongoClient, db.getName());
				MorphiaQuery<Sport> query = new MorphiaQuery<>(morphia, datastore, sport);
				Sport mysport = query.where(sport.name.eq("Laufen")).fetchOne();
				
				if (mysport == null)
				{
					mysport = new Sport();
					mysport.setName("Laufen");
					
					  Document document = new Document();
				        document.put("person", "Test");
			        db.getCollection("user").insertOne(document);  
				}
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				continue;
			}
		}
	}
}

package de.yamma.maven.querydsl;

import java.net.UnknownHostException;
import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
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

		String host = "82.211.60.193";
		long port = 27017;
		String dbName = "admin";
		MongoClient mongoClient = new MongoClient(new MongoClientURI(host != null ? "mongodb://" + host + ':' + port : "mongodb://localhost:27017"));
		DB db = mongoClient.getDB(dbName);
		
		Morphia morphia = new Morphia();
		Datastore datastore = morphia.createDatastore(mongoClient, db.getName());
		MorphiaQuery<Sport> query = new MorphiaQuery<>(morphia, datastore, sport);
		List<Sport> list = query
			    .where(sport.name.eq("Laufen"))
			    .fetch();
	}
}

package com.example.mongo_endpoint;

import static com.example.mongo_endpoint.Constants.MONGO_CONNECTION;
import static com.example.mongo_endpoint.Constants.MONGO_DB;

import java.util.List;
import java.util.ArrayList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.bson.Document;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;

@RestController
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class DemoApplication {

	@CrossOrigin
	@GetMapping("/")
	@ResponseBody
	public Senator[] senate() {
		String collection = "senate";
        MongoClientSettings settings = MongoClientSettings.builder()
            .applyConnectionString(MONGO_CONNECTION)
            .retryWrites(true)
            .build();

        MongoClient mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase(MONGO_DB);  

		MongoCollection<Document> senate = database.getCollection(collection); 
		FindIterable<Document> senators = senate.find();
		List<Senator> res = new ArrayList<>();
		try (MongoCursor<Document> cursor = senators.iterator()) {
			while (cursor.hasNext()) {
				res.add(Senator.getFromDocument(cursor.next()));
			}
		}
		Senator[] result = new Senator[res.size()];
		return res.toArray(result);
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}

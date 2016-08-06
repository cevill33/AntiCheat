package me.anticheat.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import me.anticheat.Main;

/**
 * Created by Jakob on 21.03.2016.
 */
public class DataBaseManager {


    public static void createCollection() {

        MongoDatabase db = Main.mongoClient.getDatabase("vetoxdb");

        MongoCollection collection = db.getCollection("vetoxbans");
        if(collection == null) {
            db.createCollection("vetoxbans");
            System.out.println("Die Collection VetoxBans wurde angelegt!");
            collection = db.getCollection("vetoxbans");
        }

    }








}

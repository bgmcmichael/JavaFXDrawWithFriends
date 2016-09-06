package sample;

import jodd.json.JsonParser;
import jodd.json.JsonSerializer;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by fenji on 9/5/2016.
 */
public class Serializer {
    public String jsonSerialize(Stroke thisStroke) {
        JsonSerializer jsonSerializer = new JsonSerializer().deep(true);
        String jsonString = jsonSerializer.serialize(thisStroke);

        return jsonString;
    }

    public Stroke jsonDeserialize(String jsonString){
        Stroke thisStroke = null;
        JsonParser jsonParser = new JsonParser();
        thisStroke = jsonParser.parse(jsonString, Stroke.class);

        return thisStroke;
    }

}

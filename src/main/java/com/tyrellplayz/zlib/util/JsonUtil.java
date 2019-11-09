package com.tyrellplayz.zlib.util;

import com.google.gson.*;

import java.io.*;

public class JsonUtil {

    private JsonUtil() {}

    public static JsonObject serialize(Object object) {
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        return parser.parse(gson.toJson(object)).getAsJsonObject();
    }

    public static <T> T deserialize(JsonObject jsonObject, Class<T> type) {
        Gson gson = new Gson();
        return gson.fromJson(jsonObject,type);
    }

    public static JsonObject loadJson(File file) throws IOException, JsonParseException {
        JsonParser parser = new JsonParser();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        return parser.parse(reader).getAsJsonObject();
    }

    public static JsonObject loadJson(InputStream inputStream) throws IOException, JsonParseException {
        JsonParser parser = new JsonParser();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        return parser.parse(reader).getAsJsonObject();
    }

    public static void saveJson(JsonObject jsonObject, File file) throws IOException{
        FileWriter fileWriter = new FileWriter(file);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        fileWriter.write(gson.toJson(jsonObject));
        fileWriter.flush();
    }

}

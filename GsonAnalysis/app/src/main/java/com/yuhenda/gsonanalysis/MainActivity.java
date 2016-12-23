package com.yuhenda.gsonanalysis;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.yuhenda.gsonanalysis.bean.Teacher;
import com.yuhenda.gsonanalysis.service.CustomTypeAdapterFactory;

import java.lang.reflect.Type;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        test2();
    }

    private void test1() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        JsonSerialize1 jsonSerialize1 = new JsonSerialize1();
        JsonDeserialize1 jsonDeserialize1 = new JsonDeserialize1();
        gsonBuilder.registerTypeAdapter(Teacher.class, jsonSerialize1);
        gsonBuilder.registerTypeAdapter(Teacher.class, jsonDeserialize1);
        Gson gson = gsonBuilder.create();
        Teacher teacher = new Teacher("name1", "no1");

        String jsonStr = gson.toJson(teacher);
        Log.i("MainActivity", "str:" + jsonStr);
        Teacher teacher1 = gson.fromJson(jsonStr, Teacher.class);
        Log.i("MainActivity", "str:" + jsonStr);
    }

    private class JsonDeserialize1 implements JsonDeserializer<Teacher> {

        @Override
        public Teacher deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();

            Teacher teacher = new Teacher(jsonObject.get("name").getAsString(), jsonObject.get("id").getAsString());

            return teacher;
        }
    }

    private class JsonSerialize1 implements JsonSerializer<Teacher> {

        @Override
        public JsonElement serialize(Teacher src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("id", src.no);
            jsonObject.addProperty("name", src.name);

            return jsonObject;
        }
    }

    private void test2(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapterFactory(new CustomTypeAdapterFactory());
        Gson gson = gsonBuilder.create();

        Teacher teacher = new Teacher("name1", "no1");
        String jsonStr = gson.toJson(teacher);
        Log.i("MainActivity", "str:" + jsonStr);
        Teacher teacher1 = gson.fromJson(jsonStr, Teacher.class);
        Log.i("MainActivity", "str:" + jsonStr);

        Gson gson1 = new Gson();
        Teacher teacher2 = gson1.fromJson(jsonStr, Teacher.class);
        Log.i("MainActivity", "str:" + jsonStr);
    }
}

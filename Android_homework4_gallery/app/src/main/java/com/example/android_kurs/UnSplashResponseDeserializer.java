package com.example.android_kurs;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class UnSplashResponseDeserializer implements JsonDeserializer {

    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String src = json.getAsJsonObject()
                .get("urls")
                .getAsJsonObject()
                .get("regular")
                .getAsString();
        return new GalleryItem(src);
    }
}
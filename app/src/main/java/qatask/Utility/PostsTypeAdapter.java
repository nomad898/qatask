package qatask.Utility;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import qatask.Models.Post;
import qatask.Models.Posts;

public class PostsTypeAdapter extends TypeAdapter<Posts> {

    private Gson gson = new Gson();

    @Override
    public void write(JsonWriter jsonWriter, Posts posts) throws IOException {
        gson.toJson(posts, Posts.class, jsonWriter);
    }

    @Override
    public Posts read(JsonReader jsonReader) throws IOException {
        Posts posts;

        if (jsonReader.peek() == JsonToken.BEGIN_ARRAY) {
            posts = new Posts((Post[]) gson.fromJson(jsonReader, Post[].class));
        } else if(jsonReader.peek() == JsonToken.BEGIN_OBJECT) {
            posts = new Posts((Post) gson.fromJson(jsonReader, Post.class));
        } else {
            throw new JsonParseException("Unexpected token " + jsonReader.peek());
        }

        return posts;
    }
}
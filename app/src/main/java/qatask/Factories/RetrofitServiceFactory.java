package qatask.Factories;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import qatask.Models.Posts;
import qatask.Utility.PostsTypeAdapter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitServiceFactory {

    private final Retrofit retrofit;
    
    public RetrofitServiceFactory(String baseUrl) {
        Gson gson = new GsonBuilder()
                        .registerTypeAdapter(Posts.class, new PostsTypeAdapter())
                        .create();

        Retrofit.Builder builder = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .baseUrl(baseUrl);
        this.retrofit = builder.build();
    } 

    public <T> T create(Class<T> service) {
        return retrofit.create(service);
    }
}

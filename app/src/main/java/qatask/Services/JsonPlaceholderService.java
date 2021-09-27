package qatask.Services;
import java.util.List;
import qatask.Models.Post;
import qatask.Models.Posts;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonPlaceholderService {
    @GET("posts")
    Call<List<Post>> getPosts();

    @GET("posts")
    Call<List<Post>> getPosts(
        @Query("userId") Integer[] userIds,
        @Query("title") String titile,
        @Query("body") String body,
        @Query("_sort") String sort,
        @Query("_order") String order);    

    @GET("/posts/{id}")
    public Call<Posts> getPostById(@Path("id") int id);
}
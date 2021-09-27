package qatask.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.io.FileNotFoundException;
import java.io.IOException;

import qatask.Factories.RetrofitServiceFactory;
import qatask.Utility.ConfigurationManager;
import qatask.Models.Post;
import qatask.Models.Posts;

import org.testng.annotations.*;

import retrofit2.*;
import static org.hamcrest.MatcherAssert.assertThat; 
import static org.hamcrest.Matchers.*;

public class JsonPlaceholderServiceTest {
    private JsonPlaceholderService service;
    private String baseUrl;
    private final int SUCCESSFUL_CODE = 200;
    private final int NOT_FOUND_CODE = 404;
    private final String JSON_MIME_TYPE = "application/json; charset=utf-8";

    @BeforeClass
    public void setUp() {
        String propertyFile = "./src/main/resources/config.properties";

        try {
            ConfigurationManager config = new ConfigurationManager(propertyFile);
            this.baseUrl = config.getProperty("base.url");
        } catch(FileNotFoundException e){
            System.out.println(e.getMessage());  
            return;
        }
         catch(IOException e) {
            System.out.println(e.getMessage());  
            return;
        }
        RetrofitServiceFactory factory = new RetrofitServiceFactory(baseUrl);
        this.service = factory.create(JsonPlaceholderService.class);
    }

    @Test
    public void givenRequest_whenPostsRetrieved_then200IsReturned() {
        Call<List<Post>> postsCall = this.service.getPosts();
        Response<List<Post>> response = this.getResponse(postsCall);
        assertThat("JsonPlaceholder API returns 200 status", response.code(), equalTo(SUCCESSFUL_CODE));
    }

    @Test
    public void givenRequest_whenPostsRetrieved_thenExpectedMimeTypeIsReturned() {
        Call<List<Post>> postsCall = this.service.getPosts();
        Response<List<Post>> response = this.getResponse(postsCall);
        assertThat("JsonPlaceholder API returns expected list", response.headers().get("content-type"), equalTo(JSON_MIME_TYPE));
    }

    @Test
    public void givenRequest_whenPostsRetrieved_thenExpectedListIsReturned() {
        List<Post> expectedPosts = new ArrayList<Post>();
        expectedPosts.add(new Post(
            1,
            1,
            "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
            "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"
        ));
        expectedPosts.add(new Post(
            1,
            2,
            "qui est esse",
            "est rerum tempore vitae\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\nqui aperiam non debitis possimus qui neque nisi nulla"
        ));
        Call<List<Post>> postsCall = this.service.getPosts();
        Response<List<Post>> response = this.getResponse(postsCall);
        List<Post> actualPosts = response.body();
        final int POST_LIMIT = 2;
        List<Post> filteredPosts = actualPosts.stream().limit(POST_LIMIT).collect(Collectors.toList());
        assertThat("JsonPlaceholder API returns expected list", filteredPosts, equalTo(expectedPosts));
    }

    @Test
    public void givenParameterizedSortOrderRequest_whenPostsRetrieved_thenExpectedListIsReturned() {
        List<Post> expectedPosts = new ArrayList<Post>();
        expectedPosts.add(new Post(
            5,
            50,
            "repellendus qui recusandae incidunt voluptates tenetur qui omnis exercitationem",
            "error suscipit maxime adipisci consequuntur recusandae\nvoluptas eligendi et est et voluptates\nquia distinctio ab amet quaerat molestiae et vitae\nadipisci impedit sequi nesciunt quis consectetur"
        ));
        expectedPosts.add(new Post(
            5,
            49,
            "laborum non sunt aut ut assumenda perspiciatis voluptas",
            "inventore ab sint\nnatus fugit id nulla sequi architecto nihil quaerat\neos tenetur in in eum veritatis non\nquibusdam officiis aspernatur cumque aut commodi aut"
        ));
        Call<List<Post>> postsCall = this.service.getPosts(
            new Integer[] { 5 },
            null,
            null,
            "id",
            "desc"
            );
        Response<List<Post>> response = this.getResponse(postsCall);
        List<Post> actualPosts = response.body();
        final int POST_LIMIT = 2;
        List<Post> filteredPosts = actualPosts.stream().limit(POST_LIMIT).collect(Collectors.toList());
        assertThat("JsonPlaceholder API returns expected list", filteredPosts, equalTo(expectedPosts));
    }

    @Test
    public void givenRequest_whenPostByIdRetrieved_then200IsReturned() {
        Call<List<Post>> postsCall = this.service.getPosts();
        Response<List<Post>> response = this.getResponse(postsCall);
        assertThat("JsonPlaceholder API returns 200 status", response.code(), equalTo(SUCCESSFUL_CODE));
    }

    @Test
    public void givenRequest_whenPostByIdRetrieved_thenExpectedPostIsReturned() {
        int expectedUserId = 1;
        int expectedPostId = 1;
        String expectedPostTitle = "sunt aut facere repellat provident occaecati excepturi optio reprehenderit";
        String expectedPostBody = "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto";
        Post expectedPost = new Post(
            expectedUserId,
            expectedPostId,
            expectedPostTitle,
            expectedPostBody
        );
        int postId = 1;
        Call<Posts> postByIdCall = this.service.getPostById(postId);
        Response<Posts> response = null;
        try {
            response = postByIdCall.execute();
        } catch (IOException e) {
            throw new AssertionError(e.getMessage());
        }
        Posts posts = response.body();
        Post actualPost = posts.getPosts().get(0);
        assertThat("JsonPlaceholder API returns actual post equals to expected post", actualPost, equalTo(expectedPost));
    }

    /** 
     * Based on the third task description, this test must be failed. 
     */
    @Test
    public void givenRequestWithInvalidPostId_whenPostByIdRetrieved_then200Returned() {
        int postId = 0;
        Call<Posts> postByIdCall = this.service.getPostById(postId);
        Response<Posts> response = null;
        try {
            response = postByIdCall.execute();
        } catch (IOException e) {
            throw new AssertionError(e.getMessage());               
        }
        assertThat("JsonPlaceholder API returns status code 200 if invalid id is requested", response.code(), equalTo(SUCCESSFUL_CODE));
    }

    @Test
    public void givenRequestWithInvalidPostId_whenPostByIdRetrieved_then404Returned() {
        int postId = 0;
        Call<Posts> postByIdCall = this.service.getPostById(postId);
        Response<Posts> response = null;
        try {
            response = postByIdCall.execute();
        } catch (IOException e) {
            throw new AssertionError(e.getMessage());               
        }
        assertThat("JsonPlaceholder API returns status code 404 if invalid id is requested", response.code(), equalTo(NOT_FOUND_CODE));
    }

    @Test
    public void givenRequestWithInvalidPostId_whenPostByIdRetrieved_thenNullIsReturned() {
        int postId = 0;
        Call<Posts> postByIdCall = this.service.getPostById(postId);
        Response<Posts> response = null;
        try {
            response = postByIdCall.execute();
        } catch (IOException e) {
            throw new AssertionError(e.getMessage());               
        }
        Posts posts = response.body();
        assertThat("JsonPlaceholder API returns null if invalid id is requested", posts, is(nullValue()));
    }

    @Test
    public void givenRequestWithInvalidPostId_whenPostByIdRetrieved_thenEmptyListIsReturned() {
        List<Post> emptyList = new ArrayList<Post>();
        int postId = 0;
        Call<Posts> postByIdCall = this.service.getPostById(postId);
        Response<Posts> response = null;
        try {
            response = postByIdCall.execute();
        } catch (IOException e) {
            throw new AssertionError(e.getMessage());
        }
        Posts actual = response.body();
        assertThat("JsonPlaceholder API returns empty list if invalid id is requested", actual, equalTo(emptyList));
    }
    

    private Response<List<Post>> getResponse(Call<List<Post>> postsCall){
        Response<List<Post>> response = null;
        try {
            response = postsCall.execute();
        } catch (IOException e) {
            throw new AssertionError(e.getMessage());               
        }
        return response;
    }
}
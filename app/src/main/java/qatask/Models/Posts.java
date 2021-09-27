package qatask.Models;

import java.util.Arrays;
import java.util.List;

public class Posts {
    private List<Post> posts;
    
    public Posts(Post ...posts) {
        this.posts = Arrays.asList(posts);
    }

    public List<Post> getPosts() {
        return posts;
    }
}

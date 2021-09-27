package qatask.Models;

public class Post {
    private int userId;
    private int id;
    private String title;
    private String body;

    public Post() {}
    public Post(
        int userId, 
        int id,
        String title,
        String body) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.body = body;
    }

    public int getUserId() {
        return userId;
    }
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public int getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserId: " + this.getUserId() + "\n"
               + "Id: " + this.getId() + "\n"
               + "Title: " + this.getTitle() + "\n"
               + "Body: " + this.getBody() + "\n";                
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((body == null) ? 0 : body.hashCode());
        result = prime * result + id;
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + userId;
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Post other = (Post) obj;
        if (body == null) {
            if (other.body != null)
                return false;
        } else if (!body.equals(other.body))
            return false;
        if (id != other.id)
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        if (userId != other.userId)
            return false;
        return true;
    }
}

package evolution.dto;

public class Post implements Http {
	private HttpBody post;

	public HttpBody getPost() {
		return post;
	}

	public void setPost(HttpBody post) {
		this.post = post;
	}

	@Override
	public String toString() {
		return "Post [post=" + post + "]";
	}
}

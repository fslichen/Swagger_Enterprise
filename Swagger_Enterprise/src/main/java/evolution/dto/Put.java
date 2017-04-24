package evolution.dto;

public class Put implements Http {
	private HttpBody put;

	public HttpBody getPut() {
		return put;
	}

	public void setPut(HttpBody put) {
		this.put = put;
	}

	@Override
	public String toString() {
		return "Put [put=" + put + "]";
	}
}

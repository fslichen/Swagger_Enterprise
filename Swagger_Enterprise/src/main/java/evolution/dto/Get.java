package evolution.dto;

public class Get implements Http {
	private HttpBody get;

	public HttpBody getGet() {
		return get;
	}

	public void setGet(HttpBody get) {
		this.get = get;
	}

	@Override
	public String toString() {
		return "Get [get=" + get + "]";
	}
}

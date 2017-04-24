package evolution.dto;

public class Delete implements Http {
	private HttpBody delete;

	public HttpBody getDelete() {
		return delete;
	}

	public void setDelete(HttpBody delete) {
		this.delete = delete;
	}

	@Override
	public String toString() {
		return "Delete [delete=" + delete + "]";
	}
}

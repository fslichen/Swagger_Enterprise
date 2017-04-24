package evolution.dto;

public class Patch implements Http {
	private HttpBody patch;

	public HttpBody getPatch() {
		return patch;
	}

	public void setPatch(HttpBody patch) {
		this.patch = patch;
	}

	@Override
	public String toString() {
		return "Patch [patch=" + patch + "]";
	}
}

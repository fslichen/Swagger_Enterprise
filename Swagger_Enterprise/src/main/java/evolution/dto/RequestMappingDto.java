package evolution.dto;

public class RequestMappingDto {
	private String uri;
	
	private String requestMethod;
	
	public RequestMappingDto() {
		this.uri = "";
		this.requestMethod = "GET";
	}
	
	public String getRequestMethod() {
		return requestMethod;
	}
	
	public String getUri() {
		return uri;
	}
	
	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}
	
	public void setUri(String uri) {
		this.uri = uri;
	}
	
	@Override
	public String toString() {
		return "RequestMappingDto [uri=" + uri + ", requestMethod=" + requestMethod + "]";
	}
}

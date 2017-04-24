package evolution.dto;

import java.util.Map;

public class Response {
	private Schema schema;
	private String description;
	private Map<String, Object> headers;
	
	public String getDescription() {
		return description;
	}
	
	public Map<String, Object> getHeaders() {
		return headers;
	}
	
	public Schema getSchema() {
		return schema;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setHeaders(Map<String, Object> headers) {
		this.headers = headers;
	}
	
	public void setSchema(Schema schema) {
		this.schema = schema;
	}
	
	@Override
	public String toString() {
		return "Response [description=" + description + ", schema=" + schema + ", headers=" + headers + "]";
	}
}

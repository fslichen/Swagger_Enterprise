package evolution.dto;

public class ExternalDocs {
	private String url;
	private String description;
	
	public String getDescription() {
		return description;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Override
	public String toString() {
		return "ExternalDocs [description=" + description + ", url=" + url + "]";
	}
}

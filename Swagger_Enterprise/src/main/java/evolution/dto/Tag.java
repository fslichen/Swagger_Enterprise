package evolution.dto;

public class Tag {
	private String name;
	private String description;
	private ExternalDocs externalDocs;
	
	public String getDescription() {
		return description;
	}
	
	public ExternalDocs getExternalDocs() {
		return externalDocs;
	}
	
	public String getName() {
		return name;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setExternalDocs(ExternalDocs externalDocs) {
		this.externalDocs = externalDocs;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Tag [name=" + name + ", description=" + description + ", externalDocs=" + externalDocs + "]";
	}
}

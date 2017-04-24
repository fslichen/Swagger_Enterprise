package evolution.dto;

import java.util.Map;

public class Definition {
	private String type;
	private Map<String, Property> properties;
	
	public Map<String, Property> getProperties() {
		return properties;
	}
	
	public String getType() {
		return type;
	}
	
	public void setProperties(Map<String, Property> properties) {
		this.properties = properties;
	}
	
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Definition [type=" + type + ", properties=" + properties + "]";
	}
}

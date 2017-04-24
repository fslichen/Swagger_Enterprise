package evolution.dto;

public class Property {
	private String type;
	private String format;
	private String description;
	private String $ref;
	private Items items;
	
	public String get$ref() {
		return $ref;
	}

	public void set$ref(String $ref) {
		this.$ref = $ref;
	}

	public Items getItems() {
		return items;
	}

	public void setItems(Items items) {
		this.items = items;
	}

	public String getDescription() {
		return description;
	}
	
	public String getFormat() {
		return format;
	}
	
	public String getType() {
		return type;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setFormat(String format) {
		this.format = format;
	}
	
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Property [type=" + type + ", format=" + format + ", description=" + description + ", $ref=" + $ref
				+ ", items=" + items + "]";
	}
}

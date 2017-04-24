package evolution.dto;

public class Parameter {
	private String in;
	private String name;
	private String description;
	private Boolean required;
	private Schema schema;
	private String type;
	private String collectionFormat;
	private String format;
	private Integer maximum;
	private Integer minimum;
	
	public String getCollectionFormat() {
		return collectionFormat;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getFormat() {
		return format;
	}
	
	public String getIn() {
		return in;
	}
	
	public Integer getMaximum() {
		return maximum;
	}
	
	public Integer getMinimum() {
		return minimum;
	}
	
	public String getName() {
		return name;
	}
	
	public Boolean getRequired() {
		return required;
	}
	
	public Schema getSchema() {
		return schema;
	}
	
	public String getType() {
		return type;
	}
	
	public void setCollectionFormat(String collectionFormat) {
		this.collectionFormat = collectionFormat;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setFormat(String format) {
		this.format = format;
	}
	
	public void setIn(String in) {
		this.in = in;
	}
	
	public void setMaximum(Integer maximum) {
		this.maximum = maximum;
	}
	
	public void setMinimum(Integer minimum) {
		this.minimum = minimum;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setRequired(Boolean required) {
		this.required = required;
	}
	
	public void setSchema(Schema schema) {
		this.schema = schema;
	}
	
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Parameter [in=" + in + ", name=" + name + ", description=" + description + ", required=" + required
				+ ", schema=" + schema + ", type=" + type + ", collectionFormat=" + collectionFormat + ", format="
				+ format + ", maximum=" + maximum + ", minimum=" + minimum + "]";
	}
}

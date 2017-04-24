package evolution.dto;

public class AdditionalProperties {
	private String $ref;
	private String type;
	
	public String get$ref() {
		return $ref;
	}
	
	public String getType() {
		return type;
	}
	
	public void set$ref(String $ref) {
		this.$ref = $ref;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return "AdditionalProperties [$ref=" + $ref + ", type=" + type + "]";
	}
}

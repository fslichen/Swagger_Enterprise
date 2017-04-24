package evolution.dto;

public class Items {
	private String type;
	private String $ref;

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
		return "Items [type=" + type + ", $ref=" + $ref + "]";
	}
}

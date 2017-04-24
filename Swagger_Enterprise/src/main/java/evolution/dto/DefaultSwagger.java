package evolution.dto;

public class DefaultSwagger {
	private static final String UNKNOWN = "Unknown";
	private static final String UNKNOWN_URL = "http://www.unknown.com";
	private static final String UNKNOWN_EMAIL = "unknown@unknown.com";
	private String host = UNKNOWN;
	private String description4ExternalDocs = UNKNOWN;
	private String url4ExternalDocs = UNKNOWN_URL;
	private String name4Tag = UNKNOWN;
	private String description4Tag = UNKNOWN;
	private String description4Info = UNKNOWN;
	private String version4Info = UNKNOWN;
	private String title4Info = UNKNOWN;
	private String termsOfService4Info = UNKNOWN;
	private String email4Contact = UNKNOWN_EMAIL;
	private String url4License = UNKNOWN_URL;
	private String name4License = UNKNOWN;
	
	public String getDescription4ExternalDocs() {
		return description4ExternalDocs;
	}
	
	public String getDescription4Info() {
		return description4Info;
	}
	
	public String getDescription4Tag() {
		return description4Tag;
	}
	
	public String getEmail4Contact() {
		return email4Contact;
	}
	
	public String getHost() {
		return host;
	}
	
	public String getName4License() {
		return name4License;
	}
	
	public String getName4Tag() {
		return name4Tag;
	}
	
	public String getTermsOfService4Info() {
		return termsOfService4Info;
	}
	
	public String getTitle4Info() {
		return title4Info;
	}
	
	public String getUrl4ExternalDocs() {
		return url4ExternalDocs;
	}
	
	public String getUrl4License() {
		return url4License;
	}
	
	public String getVersion4Info() {
		return version4Info;
	}
	
	public void setDescription4ExternalDocs(String description4ExternalDocs) {
		this.description4ExternalDocs = description4ExternalDocs;
	}
	
	public void setDescription4Info(String description4Info) {
		this.description4Info = description4Info;
	}
	
	public void setDescription4Tag(String description4Tag) {
		this.description4Tag = description4Tag;
	}
	
	public void setEmail4Contact(String email4Contact) {
		this.email4Contact = email4Contact;
	}
	
	public void setHost(String host) {
		this.host = host;
	}
	
	public void setName4License(String name4License) {
		this.name4License = name4License;
	}
	
	public void setName4Tag(String name4Tag) {
		this.name4Tag = name4Tag;
	}
	
	public void setTermsOfService4Info(String termsOfService4Info) {
		this.termsOfService4Info = termsOfService4Info;
	}
	
	public void setTitle4Info(String title4Info) {
		this.title4Info = title4Info;
	}
	
	public void setUrl4ExternalDocs(String url4ExternalDocs) {
		this.url4ExternalDocs = url4ExternalDocs;
	}
	
	public void setUrl4License(String url4License) {
		this.url4License = url4License;
	}
	
	public void setVersion4Info(String version4Info) {
		this.version4Info = version4Info;
	}

	@Override
	public String toString() {
		return "DefaultSwagger [host=" + host + ", description4ExternalDocs=" + description4ExternalDocs
				+ ", url4ExternalDocs=" + url4ExternalDocs + ", name4Tag=" + name4Tag + ", description4Tag="
				+ description4Tag + ", description4Info=" + description4Info + ", version4Info=" + version4Info
				+ ", title4Info=" + title4Info + ", termsOfService4Info=" + termsOfService4Info + ", email4Contact="
				+ email4Contact + ", url4License=" + url4License + ", name4License=" + name4License + "]";
	}
}

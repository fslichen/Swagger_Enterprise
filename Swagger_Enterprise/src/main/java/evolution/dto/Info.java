package evolution.dto;

public class Info {
	private String description;
	private String version;
	private String title;
	private String termsOfService;
	private Contact contact;
	private License license;
	
	public Contact getContact() {
		return contact;
	}
	
	public String getDescription() {
		return description;
	}
	
	public License getLicense() {
		return license;
	}
	
	public String getTermsOfService() {
		return termsOfService;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getVersion() {
		return version;
	}
	
	public void setContact(Contact contact) {
		this.contact = contact;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setLicense(License license) {
		this.license = license;
	}
	
	public void setTermsOfService(String termsOfService) {
		this.termsOfService = termsOfService;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setVersion(String version) {
		this.version = version;
	}
	
	@Override
	public String toString() {
		return "Info [description=" + description + ", version=" + version + ", title=" + title + ", termsOfService="
				+ termsOfService + ", contact=" + contact + ", license=" + license + "]";
	}
}

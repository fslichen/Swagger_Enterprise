package evolution.dto;

import java.util.List;
import java.util.Map;

public class Swagger {
	private String swagger;
	private Info info;
	private String host;
	private String basePath;
	private List<Tag> tags;
	private List<String> schemes;
	private Map<String, Http> paths;
	private Map<String, Object> securityDefinitions;
	private Map<String, Definition> definitions;
	private ExternalDocs externalDocs;
	
	public ExternalDocs getExternalDocs() {
		return externalDocs;
	}

	public void setExternalDocs(ExternalDocs externalDocs) {
		this.externalDocs = externalDocs;
	}

	public String getBasePath() {
		return basePath;
	}

	public Map<String, Definition> getDefinitions() {
		return definitions;
	}

	public String getHost() {
		return host;
	}

	public Info getInfo() {
		return info;
	}

	public Map<String, Http> getPaths() {
		return paths;
	}

	public List<String> getSchemes() {
		return schemes;
	}

	public Map<String, Object> getSecurityDefinitions() {
		return securityDefinitions;
	}

	public String getSwagger() {
		return swagger;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	public void setDefinitions(Map<String, Definition> definitions) {
		this.definitions = definitions;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setInfo(Info info) {
		this.info = info;
	}

	public void setPaths(Map<String, Http> paths) {
		this.paths = paths;
	}

	public void setSchemes(List<String> schemes) {
		this.schemes = schemes;
	}

	public void setSecurityDefinitions(Map<String, Object> securityDefinitions) {
		this.securityDefinitions = securityDefinitions;
	}

	public void setSwagger(String swagger) {
		this.swagger = swagger;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	@Override
	public String toString() {
		return "Swagger [swagger=" + swagger + ", info=" + info + ", host=" + host + ", basePath=" + basePath
				+ ", tags=" + tags + ", schemes=" + schemes + ", paths=" + paths + ", securityDefinitions="
				+ securityDefinitions + ", definitions=" + definitions + ", externalDocs=" + externalDocs + "]";
	}
}

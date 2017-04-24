package evolution.dto;

import java.util.List;
import java.util.Map;

public class HttpBody {
	private List<String> tags;
	private String summary;
	private String description;
	private String operationId;
	private List<String> consumes;
	private List<String> produces;
	private List<Object> security;
	private List<Parameter> parameters;
	private Map<Integer, Response> responses;
	
	public List<String> getConsumes() {
		return consumes;
	}

	public String getDescription() {
		return description;
	}

	public String getOperationId() {
		return operationId;
	}
	
	public List<Parameter> getParameters() {
		return parameters;
	}
	
	public List<String> getProduces() {
		return produces;
	}
	
	public List<Object> getSecurity() {
		return security;
	}
	
	public String getSummary() {
		return summary;
	}
	
	public List<String> getTags() {
		return tags;
	}
	
	public void setConsumes(List<String> consumes) {
		this.consumes = consumes;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}
	
	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}
	
	public void setProduces(List<String> produces) {
		this.produces = produces;
	}
	
	public void setSecurity(List<Object> security) {
		this.security = security;
	}
	
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public Map<Integer, Response> getResponses() {
		return responses;
	}

	public void setResponses(Map<Integer, Response> responses) {
		this.responses = responses;
	}

	@Override
	public String toString() {
		return "HttpBody [tags=" + tags + ", summary=" + summary + ", description=" + description + ", operationId="
				+ operationId + ", consumes=" + consumes + ", produces=" + produces + ", security=" + security
				+ ", parameters=" + parameters + ", responses=" + responses + "]";
	}
}

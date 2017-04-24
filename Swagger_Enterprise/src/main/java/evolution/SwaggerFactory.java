package evolution;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import evolution.dto.AdditionalProperties;
import evolution.dto.Contact;
import evolution.dto.DefaultSwagger;
import evolution.dto.Definition;
import evolution.dto.Delete;
import evolution.dto.ExternalDocs;
import evolution.dto.Get;
import evolution.dto.Http;
import evolution.dto.HttpBody;
import evolution.dto.Info;
import evolution.dto.Items;
import evolution.dto.Json;
import evolution.dto.License;
import evolution.dto.Parameter;
import evolution.dto.Patch;
import evolution.dto.Post;
import evolution.dto.Property;
import evolution.dto.Put;
import evolution.dto.RequestMappingDto;
import evolution.dto.Response;
import evolution.dto.Schema;
import evolution.dto.Swagger;
import evolution.dto.Tag;
import evolution.yml.MyRepresenter;
import evolution.yml.Yml;

public class SwaggerFactory {
	public static final String DEFINITIONS = "#/definitions/";

	public static void swaggers(String basePackageName, String destinationPath, DefaultSwagger defaultSwagger) {
		String basePackagePath = System.getProperty("user.dir") + "/src/main/java/" + basePackageName.replace('.', '/');
		String mainJavaPath = basePackagePath.substring(0, Str.backIndexOf(basePackagePath, '/', Str.count(basePackageName, '.') + 1));
		List<Class<?>> classes = new LinkedList<>();
		classes = FileUtil.classes(basePackagePath, mainJavaPath, Arrays.asList(RestController.class, Controller.class), classes);
		for (Class<?> clazz : classes) {
			swagger(clazz, destinationPath + "/" + clazz.getSimpleName() + ".yaml", defaultSwagger);
		}
	}
	
	public static Class<?> requestBodyDto(Method method) {
		try {
			return Arrays.asList(method.getParameters())
					.stream().filter(x -> x.getAnnotation(RequestBody.class) != null)
					.collect(Collectors.toList()).get(0).getType();
		} catch (Exception e) {
			return null;
		}
	}

	public static List<String> pathVariables(Method method) {
		return Arrays.asList(method.getParameters())
				.stream().filter(x -> x.getAnnotation(PathVariable.class) != null)
				.map(x -> {
					PathVariable pathVariable = x.getAnnotation(PathVariable.class);
					String value = pathVariable.value();
					return !StringUtils.isEmpty(value) ? value : pathVariable.name();
				})
				.collect(Collectors.toList());
	}
	
	public static Map<String, Boolean> requestParams(Method method) {
		Map<String, Boolean> requestParams = new LinkedHashMap<>();
		for (java.lang.reflect.Parameter parameter : method.getParameters()) {
			RequestParam requestParam = parameter.getAnnotation(RequestParam.class);
			if (requestParam != null) {
				String value = requestParam.value();
				value = !StringUtils.isEmpty(value) ? value : requestParam.name();
				Boolean isRequired = requestParam.required();
				isRequired = isRequired != null ? isRequired : true;
				requestParams.put(value, isRequired);
			}
		}
		return requestParams;
	}

	public static Class<?> responseBodyDto(Method method) {
		return method.getReturnType();
	}

	public static String firstUri(String[] uris) {
		 return uris.length > 0 ? uris[0] : "/";
	}
	
	// The object can either be a method or a class. 
	public static RequestMappingDto requestMappingDto(Object object) {
		RequestMappingDto requestMappingDto = new RequestMappingDto();
		RequestMapping requestMapping = Ref.annotation(object, RequestMapping.class);
		GetMapping getMapping = Ref.annotation(object, GetMapping.class);
		PostMapping postMapping = Ref.annotation(object, PostMapping.class);
		PatchMapping patchMapping = Ref.annotation(object, PatchMapping.class);
		DeleteMapping deleteMapping = Ref.annotation(object, DeleteMapping.class);
		PutMapping putMapping = Ref.annotation(object, PutMapping.class);
		if (requestMapping != null) {
			requestMappingDto.setUri(firstUri(requestMapping.value()));
			RequestMethod[] requestMethods = requestMapping.method();
			if (requestMethods.length > 0) {
				requestMappingDto.setRequestMethod(requestMethods[0].name());
			}
		} else if (getMapping != null) {
			requestMappingDto.setUri(firstUri(getMapping.value()));
			requestMappingDto.setRequestMethod("GET");
		} else if (postMapping != null) {
			requestMappingDto.setUri(firstUri(postMapping.value()));
			requestMappingDto.setRequestMethod("POST");
		} else if (patchMapping != null) {
			requestMappingDto.setUri(firstUri(patchMapping.value()));
			requestMappingDto.setRequestMethod("PATCH");
		} else if (deleteMapping != null) {
			requestMappingDto.setUri(firstUri(deleteMapping.value()));
			requestMappingDto.setRequestMethod("DELETE");
		} else if (putMapping != null) {
			requestMappingDto.setUri(firstUri(putMapping.value()));
			requestMappingDto.setRequestMethod("PUT");
		} else {
			requestMappingDto.setUri("/");
			requestMappingDto.setRequestMethod("GET");
		}
		return requestMappingDto;
	}

	public static void addDefinition(Object object, Map<String, Definition> definitions, Method method, Boolean isRequestBody) {
		addDefinition(object.getClass(), definitions, method, isRequestBody);
	}

	public static void addDefinition(Class<?> clazz, Map<String, Definition> definitions, Method method, Boolean isRequestBody) {
		String className = Ref.simpleClassName(clazz);
		if (definitions.containsKey(className)) {
			return;
		} else if (Ref.isString(clazz)) {// Normal String or JSON
			if (!definitions.containsKey("Json")) {
				Definition definition = new Definition();
				definition.setType("object");
				definitions.put("Json", definition);
			}
		} else if (Ref.isBasic(clazz)) {
			return;
		} else if (Ref.isList(clazz)) {// TODO For the time being, the value of List should be neither List nor Map. 
			if (isRequestBody) {
				addDefinition(Ref.genericClass(method, RequestBody.class, 0), definitions, null, null);
			} else {
				addDefinition(Ref.genericClass(method, 0), definitions, null, null);
			}
		} else if (Ref.isMap(clazz)) {// TODO For the time being, the value of Map should be neither List nor Map.
			if (isRequestBody) {
				addDefinition(Ref.genericClass(method, RequestBody.class, 1), definitions, null, null);
			} else {
				addDefinition(Ref.genericClass(method, 1), definitions, null, null);
			}
		} else {// POJO
			Map<String, Property> properties = new LinkedHashMap<>();
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				Property property = new Property();
				if (Ref.isBasic(field)) {
					property.setType(type(field));
				} else if (Ref.isList(field)) {
					property.setType("array");
					property.setItems(listItems(field));
				} else if (Ref.isMap(field)) {
					addDefinition(Ref.genericClass(field, 1), definitions, null, null);
				} else {// POJO
					property.set$ref(DEFINITIONS + Ref.simpleClassName(field));
					addDefinition(field.getType(), definitions, null, null);
				}
				properties.put(field.getName(), property);
			}
			Definition definition = new Definition();
			definition.setType("object");
			definition.setProperties(properties);
			definitions.put(className, definition);
		}
	}

	public static Items listItems(Method method, boolean isRequestBody) {
		Items items = new Items();
		Class<?> clazz = null;
		if (isRequestBody) {
			clazz = Ref.genericClass(method, RequestBody.class, 0);
		} else {
			clazz = Ref.genericClass(method, 0);
		}
		if (Ref.isBasic(clazz)) {
			items.setType(clazz.getSimpleName().toLowerCase());
		} else {
			items.set$ref(DEFINITIONS + clazz.getSimpleName());
		}
		return items;
	}

	public static Items listItems(Field field) {
		Items items = new Items();
		Class<?> clazz = Ref.genericClass(field, 0);
		if (Ref.isBasic(clazz)) {
			items.setType(clazz.getSimpleName().toLowerCase());
		} else {
			items.set$ref(DEFINITIONS + clazz.getSimpleName());
		}
		return items;
	}

	public static Schema mapSchema(Method method, boolean isRequestBody) {
		Class<?> mapValueClass = null;
		if (isRequestBody) {
			mapValueClass = Ref.genericClass(method, RequestBody.class, 1);
		} else {// ResponseBody
			mapValueClass = Ref.genericClass(method, 1);
		}
		AdditionalProperties additionalProperties = new AdditionalProperties();
		if (Ref.isBasic(mapValueClass)) {
			additionalProperties.setType(type(mapValueClass));
		} else {// TODO List and Map can also be the value of a map.
			additionalProperties.set$ref(DEFINITIONS + mapValueClass.getSimpleName());
		}
		Schema schema = new Schema();
		schema.setAdditionalProperties(additionalProperties);
		return schema;
	}

	public static Schema listSchema(Method method, boolean isRequestBody) {
		Schema schema = new Schema();
		schema.setType("array");
		schema.setItems(listItems(method, isRequestBody));
		return schema;
	}

	public static Schema refSchema(Class<?> clazz) {
		Schema schema = new Schema();
		schema.set$ref(DEFINITIONS + Ref.simpleClassName(clazz));
		return schema;
	}

	public static Schema typeSchema(Class<?> clazz) {
		Schema schema = new Schema();
		schema.setType(Ref.simpleClassName(clazz).toLowerCase());
		return schema;
	}

	public static String type(Field field) {
		return type(field.getType());
	}

	public static String type(Class<?> clazz) {
		String className = clazz.getSimpleName().toLowerCase();
		switch (className) {
		case "int":
			return "integer";
		case "double":
			return "number";
		default:
			return className;
		}
	}

	public static Boolean isRequestMethod(Method method) {
		return method.getAnnotation(RequestMapping.class) != null
				|| method.getAnnotation(GetMapping.class) != null
				|| method.getAnnotation(PostMapping.class) != null
				|| method.getAnnotation(PatchMapping.class) != null
				|| method.getAnnotation(DeleteMapping.class) != null
				|| method.getAnnotation(PutMapping.class) != null;
	}
	
	@SuppressWarnings({ "rawtypes" })
	public static void swagger(Class controllerClass, String filePath, DefaultSwagger defaultSwagger) {
		Map<String, Http> paths = new LinkedHashMap<>();
		Map<String, Definition> definitions = new LinkedHashMap<>();
		List<Method> methods = Arrays.asList(controllerClass.getDeclaredMethods())
				.stream().filter(x -> isRequestMethod(x))
				.collect(Collectors.toList());
		for (Method method : methods) {
			HttpBody httpBody = new HttpBody();
			httpBody.setConsumes(Arrays.asList("application/json"));
			httpBody.setProduces(Arrays.asList("application/json"));
			// Path Variable
			List<Parameter> parameters = new LinkedList<>();
			List<String> pathVariables = pathVariables(method);
			for (String pathVariable : pathVariables) {
				Parameter pathParameter = new Parameter();
				pathParameter.setIn("path");
				pathParameter.setRequired(true);
				pathParameter.setName(pathVariable);
				pathParameter.setType("string");
				parameters.add(pathParameter);
			}
			// Request Parameter
			Map<String, Boolean> requestParams = requestParams(method); 
			for (Entry<String, Boolean> requestParam : requestParams.entrySet()) {
				Parameter requestParameter = new Parameter();
				requestParameter.setIn("query");
				requestParameter.setRequired(requestParam.getValue());
				requestParameter.setName(requestParam.getKey());
				requestParameter.setType("string");
				parameters.add(requestParameter);
			}
			// Request Body
			Class<?> requestBodyDtoClass = requestBodyDto(method);
			if (requestBodyDtoClass != null) {
				addDefinition(requestBodyDtoClass, definitions, method, true);
				Parameter bodyParameter = new Parameter();
				if (Ref.isString(requestBodyDtoClass)) {// Json
					bodyParameter.setSchema(refSchema(Json.class));
				} else if (Ref.isBasic(requestBodyDtoClass)) {// Rare Case
					bodyParameter.setType(type(requestBodyDtoClass));
				} else if (Ref.isList(requestBodyDtoClass)) {
					bodyParameter.setSchema(listSchema(method, true));
				} else if (Ref.isMap(requestBodyDtoClass)) {
					bodyParameter.setSchema(mapSchema(method, true));
				} else {// POJO
					bodyParameter.setSchema(refSchema(requestBodyDtoClass));
				}
				bodyParameter.setName("requestBody");
				bodyParameter.setIn("body");
				parameters.add(bodyParameter);
			}
			if (parameters != null && parameters.size() > 0) {
				httpBody.setParameters(parameters);// TODO There can be more than one parameters.
			}
			// Response Body
			Class<?> responseBodyDtoClass = responseBodyDto(method);
			if (responseBodyDtoClass != null) {
				addDefinition(responseBodyDtoClass, definitions, method, false);
				Response response = new Response();
				response.setDescription("Success");
				if (Ref.isString(responseBodyDtoClass)) {// Json
					response.setSchema(refSchema(Json.class));
				} else if (Ref.isBasic(responseBodyDtoClass)) {// Rare Case
					response.setSchema(typeSchema(responseBodyDtoClass));
				} else if (Ref.isList(responseBodyDtoClass)) {
					response.setSchema(listSchema(method, false));
				} else if (Ref.isMap(responseBodyDtoClass)) {
					response.setSchema(mapSchema(method, false));
				} else {// POJO
					response.setSchema(refSchema(responseBodyDtoClass));
				}
				Map<Integer, Response> responses = new LinkedHashMap<>();
				responses.put(200, response);// TODO Response code is not limited to 200.
				httpBody.setResponses(responses);
			}
			// Request Mapping
			RequestMappingDto requestMappingDto = requestMappingDto(method);
			Http http = null;
			switch (requestMappingDto.getRequestMethod()) {
			case "GET":
				Get get = new Get();
				get.setGet(httpBody);
				http = get;
				break;
			case "POST":
				Post post = new Post();
				post.setPost(httpBody);
				http = post;
				break;
			case "PATCH":
				Patch patch = new Patch();
				patch.setPatch(httpBody);
				http = patch;
				break;
			case "DELETE":
				Delete delete = new Delete();
				delete.setDelete(httpBody);
				http = delete;
				break;
			case "PUT":
				Put put = new Put();
				put.setPut(httpBody);
				http = put;
				break;
			}
			paths.put(requestMappingDto.getUri(), http);
		}
		Swagger swagger = new Swagger();
		swagger.setSwagger("2.0");
		swagger.setInfo(info(defaultSwagger));
		swagger.setHost(defaultSwagger.getHost());
		swagger.setBasePath(requestMappingDto(controllerClass).getUri());
		swagger.setTags(Arrays.asList(tag(defaultSwagger)));
		swagger.setSchemes(Arrays.asList("http"));
		swagger.setPaths(paths);
		swagger.setDefinitions(definitions);
		swagger.setExternalDocs(externalDocs(defaultSwagger));
		Yml.write(swagger, filePath, true, new MyRepresenter(true, true, null), true);
	}

	public static ExternalDocs externalDocs(DefaultSwagger defaultSwagger) {
		ExternalDocs externalDocs = new ExternalDocs();
		externalDocs.setDescription(defaultSwagger.getDescription4ExternalDocs());
		externalDocs.setUrl(defaultSwagger.getUrl4ExternalDocs());
		return externalDocs;
	}

	public static Tag tag(DefaultSwagger defaultSwagger) {
		Tag tag = new Tag();
		tag.setName(defaultSwagger.getName4Tag());
		tag.setDescription(defaultSwagger.getDescription4Tag());
		tag.setExternalDocs(externalDocs(defaultSwagger));
		return tag;
	}

	public static Info info(DefaultSwagger defaultSwagger) {
		Info info = new Info();
		info.setDescription(defaultSwagger.getDescription4Info());
		info.setVersion(defaultSwagger.getVersion4Info());
		info.setTitle(defaultSwagger.getTitle4Info());
		info.setTermsOfService(defaultSwagger.getTermsOfService4Info());
		Contact contact = new Contact();
		contact.setEmail(defaultSwagger.getEmail4Contact());
		info.setContact(contact);
		License license = new License();
		license.setUrl(defaultSwagger.getUrl4License());
		license.setName(defaultSwagger.getName4License());
		info.setLicense(license);
		return info;
	}
}

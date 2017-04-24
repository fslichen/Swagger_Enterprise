package evolution.yml;

import java.util.List;
import java.util.Set;

import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

public class MyRepresenter extends Representer {
	private Boolean hideTags;
	private Boolean ignoreNull;
	private List<Class<?>> ignoredClasses;
	
	public MyRepresenter(Boolean hideTags, Boolean ignoreNull, List<Class<?>> ignoredClasses) {
		this.hideTags = hideTags;
		this.ignoreNull = ignoreNull;
		this.ignoredClasses = ignoredClasses;
	}

	@Override
    protected MappingNode representJavaBean(Set<Property> properties, Object javaBean) {
        if (hideTags == true && !classTags.containsKey(javaBean.getClass())) {
        	addClassTag(javaBean.getClass(), Tag.MAP);
        }
        return super.representJavaBean(properties, javaBean);
    }
	
	@Override
	protected NodeTuple representJavaBeanProperty(Object javaBean, Property property, Object propertyValue,Tag customTag) {
		if (ignoreNull == true && propertyValue == null) {// Ignore the null value.
			return null;
		}
		if (ignoredClasses != null) {
			for (Class<?> ignoredClass : ignoredClasses) {
				if (javaBean.getClass() == ignoredClass) {
					return null;
				}
			}
		}
		if (!classTags.containsKey(javaBean.getClass())) {
			addClassTag(javaBean.getClass(), Tag.MAP);
		}
		return super.representJavaBeanProperty(javaBean, property, propertyValue, customTag);
	}
}

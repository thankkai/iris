package cn.dazd.iris.core.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.Inherited;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.filter.AbstractTypeHierarchyTraversingFilter;
import org.springframework.util.ClassUtils;

public class AnnotationTypeFilter extends AbstractTypeHierarchyTraversingFilter {

	private final Class<? extends Annotation> annotationType;

	public Class<? extends Annotation> getAnnotationType() {
		return annotationType;
	}

	private final boolean considerMetaAnnotations;

	/**
	 * Create a new AnnotationTypeFilter for the given annotation type. This
	 * filter will also match meta-annotations. To disable the meta-annotation
	 * matching, use the constructor that accepts a
	 * '{@code considerMetaAnnotations}' argument. The filter will not match
	 * interfaces.
	 * 
	 * @param annotationType
	 *            the annotation type to match
	 */
	public AnnotationTypeFilter(Class<? extends Annotation> annotationType) {
		this(annotationType, true, false);
	}

	/**
	 * Create a new AnnotationTypeFilter for the given annotation type. The
	 * filter will not match interfaces.
	 * 
	 * @param annotationType
	 *            the annotation type to match
	 * @param considerMetaAnnotations
	 *            whether to also match on meta-annotations
	 */
	public AnnotationTypeFilter(Class<? extends Annotation> annotationType, boolean considerMetaAnnotations) {
		this(annotationType, considerMetaAnnotations, false);
	}

	/**
	 * Create a new {@link AnnotationTypeFilter} for the given annotation type.
	 * 
	 * @param annotationType
	 *            the annotation type to match
	 * @param considerMetaAnnotations
	 *            whether to also match on meta-annotations
	 * @param considerInterfaces
	 *            whether to also match interfaces
	 */
	public AnnotationTypeFilter(Class<? extends Annotation> annotationType, boolean considerMetaAnnotations,
			boolean considerInterfaces) {
		super(annotationType.isAnnotationPresent(Inherited.class), considerInterfaces);
		this.annotationType = annotationType;
		this.considerMetaAnnotations = considerMetaAnnotations;
	}

	@Override
	protected boolean matchSelf(MetadataReader metadataReader) {
		AnnotationMetadata metadata = metadataReader.getAnnotationMetadata();
		return metadata.hasAnnotation(this.annotationType.getName())
				|| (this.considerMetaAnnotations && metadata.hasMetaAnnotation(this.annotationType.getName()));
	}

	@Override
	protected Boolean matchSuperClass(String superClassName) {
		return hasAnnotation(superClassName);
	}

	@Override
	protected Boolean matchInterface(String interfaceName) {
		return hasAnnotation(interfaceName);
	}

	protected Boolean hasAnnotation(String typeName) {
		if (Object.class.getName().equals(typeName)) {
			return false;
		} else if (typeName.startsWith("java")) {
			try {
				Class<?> clazz = ClassUtils.forName(typeName, getClass().getClassLoader());
				return ((this.considerMetaAnnotations ? AnnotationUtils.getAnnotation(clazz, this.annotationType)
						: clazz.getAnnotation(this.annotationType)) != null);
			} catch (Throwable ex) {
				ex.printStackTrace();
			}
		}
		return false;
	}

}

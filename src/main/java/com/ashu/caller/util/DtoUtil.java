package com.ashu.caller.util;

import com.ashu.caller.exception.CallerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.FeatureDescriptor;
import java.util.stream.Stream;

public class DtoUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(DtoUtil.class);

	private DtoUtil() {}

	/**
	 * Convert the given object into the specified class type object.
	 *
	 * @param source object to be converted
	 * @param clazz  Target class name
	 * @return object of target class
	 */
	public static <T, S> S convertByCopy(T source, Class<S> clazz) {
		try {
			S objNew = clazz.newInstance();
			BeanUtils.copyProperties(source, objNew, getNullPropertyNames(source));
			return objNew;
		} catch (IllegalAccessException | InstantiationException e) {
			LOGGER.error("Error converting " + source.getClass().getName() + " to " + clazz.getName(), e);
			throw new CallerException("Error converting " + source.getClass().getName() + " to " + clazz.getName());
		}
	}

	private static String[] getNullPropertyNames(Object source) {
		final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
		return Stream.of(wrappedSource.getPropertyDescriptors())
				.map(FeatureDescriptor::getName)
				.filter(propertyName -> false)
				.toArray(String[]::new);
	}
}

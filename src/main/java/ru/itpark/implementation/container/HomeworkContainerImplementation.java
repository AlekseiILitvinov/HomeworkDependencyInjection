package ru.itpark.implementation.container;

import org.reflections.Reflections;
import ru.itpark.framework.annotation.Component;
import ru.itpark.framework.container.Container;
import ru.itpark.framework.exception.DependencyInjectionException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

public class HomeworkContainerImplementation implements Container {

    @Override
    public Map<Class, Object> init() {
        final Reflections reflections = new Reflections();
        final Map<Class, Object> components = new HashMap<>();

        final Set<Class<?>> types = reflections.getTypesAnnotatedWith(Component.class, true).stream()
                .filter(o -> !o.isAnnotation()).collect(Collectors.toSet());

        int initializedThisRun = 0;
        do {
            Map<Class, Object> iteratedGeneration = types.stream()
                    .filter(o -> !components.containsKey(o))
                    .filter(o -> {
                        final Constructor<?>[] constructors = o.getConstructors();
                        if (constructors.length != 1) {
                            throw new DependencyInjectionException("Ambiguous constructors!");
                        }

                        final Class<?>[] parameterTypes = constructors[0].getParameterTypes();
                        return components.keySet().containsAll(Arrays.asList(parameterTypes));
                    })
                    .collect(Collectors.toMap(o -> o, o -> {
                        final Constructor<?> constructor = o.getConstructors()[0];
                        final Object[] params = Arrays.stream(constructor.getParameterTypes())
                                .map(components::get).toArray();
                        try {
                            return constructor.newInstance(params);
                        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                            throw new DependencyInjectionException(e);
                        }
                    }));
            initializedThisRun = iteratedGeneration.size();
            components.putAll(iteratedGeneration);
        } while (initializedThisRun > 0);

        if (components.size() != types.size()) {
            types.removeAll(components.keySet());
            throw new DependencyInjectionException("not all components were initialized\nUninitialized classes : "
                    + types);
        }
        return components;
    }

    public static void main(String[] args) {
        System.out.println(new HomeworkContainerImplementation().init());
    }
}

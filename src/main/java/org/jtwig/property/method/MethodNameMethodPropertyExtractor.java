package org.jtwig.property.method;

import com.google.common.base.Optional;
import org.apache.commons.lang3.StringUtils;
import org.jtwig.property.PropertyResolveRequest;
import org.jtwig.reflection.model.Value;
import org.jtwig.reflection.model.java.JavaMethod;
import org.jtwig.reflection.model.java.JavaMethodArgument;
import org.jtwig.util.ErrorMessageFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;
import java.util.List;

public class MethodNameMethodPropertyExtractor implements MethodPropertyExtractor {
    private static Logger log = LoggerFactory.getLogger(MethodNameMethodPropertyExtractor.class);

    public static Comparator<String> exactlyEqual () {
        return new Comparator<String>() {
            @Override
            public int compare(String methodName, String searchingName) {
                return methodName.compareTo(searchingName);
            }
        };
    }

    public static Comparator<String> prefixedEqual (final String prefix) {
        return new Comparator<String>() {
            @Override
            public int compare(String methodName, String searchingName) {
                return methodName.compareTo(prefix + StringUtils.capitalize(searchingName));
            }
        };
    }

    private final RetrieveArgumentsService retrieveArgumentsService;
    private final Comparator<String> methodNameComparator;

    public MethodNameMethodPropertyExtractor(RetrieveArgumentsService retrieveArgumentsService, Comparator<String> methodNameComparator) {
        this.retrieveArgumentsService = retrieveArgumentsService;
        this.methodNameComparator = methodNameComparator;
    }

    @Override
    public Optional<Value> extract(PropertyResolveRequest request, JavaMethod method) {
        if (methodNameComparator.compare(method.name(), request.getPropertyName()) == 0) {
            List<JavaMethodArgument> methodArguments = method.arguments();
            if (request.getArguments().size() == methodArguments.size()) {
                Optional<List<Object>> arguments = retrieveArgumentsService.retrieveArguments(request.getArguments(), methodArguments);
                if (arguments.isPresent()) {
                    try {
                        return Optional.of(new Value(method.invoke(request.getEntity().getValue(), arguments.get().toArray())));
                    } catch (InvocationTargetException | IllegalAccessException e) {
                        log.debug(ErrorMessageFormatter.errorMessage(request.getPosition(), String.format("Unable to execute method '%s' on '%s'", request.getPropertyName(), request.getEntity())), e);
                    }
                }
            }
        }
        return Optional.absent();
    }
}

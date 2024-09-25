package org.tbank.spring.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class TimedPostProcessor implements BeanPostProcessor {

    private final Map<String, Class<?>> timedBeans = new HashMap<>();
    private final Map<String, Set<Method>> timedMethods = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(@NonNull Object bean, @NonNull String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        if (beanClass.isAnnotationPresent(Timed.class)) {
            timedBeans.put(beanName, beanClass);
        } else {
            HashSet<Method> annotatedMethods = new HashSet<>();
            for (Method method : beanClass.getMethods()) {
                if (method.isAnnotationPresent(Timed.class)) {
                    annotatedMethods.add(method);
                }
            }
            if (!annotatedMethods.isEmpty()) {
                timedBeans.put(beanName, beanClass);
                timedMethods.put(beanName, annotatedMethods);
            }
        }
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(@NonNull Object bean, @NonNull String beanName) throws BeansException {
        Class<?> beanClass = timedBeans.get(beanName);
        if (beanClass == null) {
            return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
        }
        ProxyFactory proxyFactory = new ProxyFactory(bean);
        proxyFactory.addAdvice(new TimedInterceptor(beanClass.getName(), timedMethods.get(beanName)));
        return proxyFactory.getProxy();
    }

    private record TimedInterceptor(String className, Set<Method> methods) implements MethodInterceptor {

        @Override
        public Object invoke(@NonNull MethodInvocation invocation) throws Throwable {
            if (methods != null && !methods.contains(invocation.getMethod())) {
                return invocation.proceed();
            }

            String methodName = invocation.getMethod().getName();
            System.out.println(className + "." + methodName + "() started.");
            long startTimeNs = System.nanoTime();
            Object result = invocation.proceed();
            long finishTimeNs = System.nanoTime();
            System.out.println(className + "." + methodName + "() finished. Took " + (finishTimeNs - startTimeNs) + " ns");
            return result;
        }
    }
}

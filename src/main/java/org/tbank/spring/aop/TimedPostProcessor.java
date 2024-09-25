package org.tbank.spring.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TimedPostProcessor implements BeanPostProcessor {

    private final Map<String, Class<?>> timedBeans = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(@NonNull Object bean, @NonNull String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(Timed.class)) {
            timedBeans.put(beanName, bean.getClass());
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
        proxyFactory.addAdvice(new TimedInterceptor(beanClass.getName()));
        return proxyFactory.getProxy();
    }

    private static class TimedInterceptor implements MethodInterceptor {

        private final String className;

        public TimedInterceptor(String className) {
            this.className = className;
        }

        @Override
        public Object invoke(@NonNull MethodInvocation invocation) throws Throwable {
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

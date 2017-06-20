package de.sturmm.rxdemo.gateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.reactivex.plugins.RxJavaPlugins;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.http.client.HttpComponentsAsyncClientHttpRequestFactory;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.web.client.AsyncRestOperations;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * Created by sturmm on 06.06.17.
 */
@SpringBootApplication(scanBasePackageClasses = ApiGatewayApplication.class)
@EnableHypermediaSupport(type = {EnableHypermediaSupport.HypermediaType.HAL})
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    private final ObjectMapper objectMapper;

    @Autowired
    public ApiGatewayApplication(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.objectMapper.registerModule(new Jdk8Module());
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @Bean
    public AsyncRestOperations httpClient() {
        final AsyncRestTemplate client = new AsyncRestTemplate(new HttpComponentsAsyncClientHttpRequestFactory());
        client.getMessageConverters().stream()
                .filter(AbstractJackson2HttpMessageConverter.class::isInstance)
                .map(AbstractJackson2HttpMessageConverter.class::cast)
                .forEach(converter ->
                                 converter.setObjectMapper(objectMapper)
                );
        return client;
    }

    @PostConstruct
    public void initRxJava() {
        RxJavaPlugins.setScheduleHandler(runnable -> {
            final RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            final Map<String, String> mdc = MDC.getCopyOfContextMap();
            return () -> {
                if (mdc != null) {
                    MDC.setContextMap(mdc);
                }
                RequestContextHolder.setRequestAttributes(requestAttributes);
                try {
                    runnable.run();
                } finally {
                    RequestContextHolder.resetRequestAttributes();
                    MDC.clear();
                }
            };
        });
    }

}

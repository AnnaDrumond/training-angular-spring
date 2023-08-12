package pt.airc.training;


import com.fasterxml.classmate.TypeResolver;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.Ordered;
import org.springframework.data.domain.Pageable;
import springfox.documentation.builders.*;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.function.Consumer;

import static springfox.documentation.schema.AlternateTypeRules.newRule;


@Configuration
@PropertySource(value = "classpath:swagger.properties", encoding = "UTF-8")
public class SwaggerConfig {
    private final BuildProperties buildProperties;

    private final TypeResolver typeResolver;

    @Value("${springfox.documentation.swagger.api.info.title}")
    private String apiInfoTitle;

    @Value("${springfox.documentation.swagger.api.info.description}")
    private String apiInfoDescription;

    @Value("${springfox.documentation.swagger.api.info.license}")
    private String apiInfoLicense;

    @Value("${springfox.documentation.swagger.api.info.contact.name}")
    private String apiInfoContactName;

    @Value("${springfox.documentation.swagger.api.info.contact.url}")
    private String apiInfoContactUrl;

    @Value("${springfox.documentation.swagger.api.info.contact.email}")
    private String apiInfoContactEmail;

    @ApiParam(value = "Results page you want to retrieve (0..N).", example = "0")
    private String page = "";

    @ApiParam(value = "Number of records per page.", example = "20")
    private String limit = "";

    @ApiParam(value = "Sorting criteria in the format: property(,asc|desc).", example = "id,asc")
    private String sort = "";

    @Autowired
    public SwaggerConfig(BuildProperties buildProperties,
                         TypeResolver typeResolver) {
        this.buildProperties = buildProperties;
        this.typeResolver = typeResolver;
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .select()
                .apis(RequestHandlerSelectors.basePackage("pt.airc"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .alternateTypeRules(
                        newRule(typeResolver.resolve(Pageable.class),
                                pageableMixin(),
                                Ordered.HIGHEST_PRECEDENCE));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(this.apiInfoTitle)
                .description(this.apiInfoDescription)
                .version(this.buildProperties.getVersion())
                .license(this.apiInfoLicense)
                .contact(new Contact(this.apiInfoContactName, this.apiInfoContactUrl, this.apiInfoContactEmail))
                .build();
    }

    @Bean
    public SecurityConfigurationBuilder securityInfo() {
        return SecurityConfigurationBuilder.builder()
                .clientId("")
                .clientSecret("")
                .realm("")
                .appName("")
                .scopeSeparator("");
    }

    private Type pageableMixin() {

        try {
            Annotation pageAnnotation = getApiParamAnnotation("page");
            Annotation limitAnnotation = getApiParamAnnotation("limit");
            Annotation sortAnnotation = getApiParamAnnotation("sort");
            return new AlternateTypeBuilder()
                    .fullyQualifiedClassName(String.format("%s.generated.%s",
                            Pageable.class.getPackage().getName(),
                            Pageable.class.getSimpleName()))
                    .property(property(Integer.class, "page", pageAnnotation))
                    .property(property(Integer.class, "limit", limitAnnotation))
                    .property(property(String[].class, "sort", sortAnnotation))
                    .build();
        } catch (NoSuchFieldException e) {
            throw new IllegalStateException("Can't create Pageable convention");
        }
    }

    private ApiParam getApiParamAnnotation(String fieldName) throws NoSuchFieldException {
        return SwaggerConfig.class.getDeclaredField(fieldName).getAnnotation(ApiParam.class);
    }

    private Consumer<AlternateTypePropertyBuilder> property(Class<?> type, String name, Annotation annotation) {
        return p -> p.name(name)
                .type(type)
                .canRead(true)
                .canWrite(true)
                .annotations(Collections.singletonList(annotation));
    }

}

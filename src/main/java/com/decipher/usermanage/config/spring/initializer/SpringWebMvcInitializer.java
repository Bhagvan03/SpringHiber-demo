package com.decipher.usermanage.config.spring.initializer;

import com.decipher.usermanage.config.spring.SpringConfiguration;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Created by decipher16 on 3/3/17.
 */

public class SpringWebMvcInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {

        return new Class<?>[]{SpringConfiguration.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[0];
    }

    @Override
    protected String[] getServletMappings() {

        return new String[]{"/"};
    }
}

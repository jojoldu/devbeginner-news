package com.jojoldu.devbeginnernews.web.config.handlebars;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;


@Slf4j
public class HandlebarsFactory {

    private Handlebars handlebars;
    private Map<String, Template> factory = new LinkedHashMap<>();

    private static final String DEFAULT_PREFIX = "/templates/";
    private static final String DEFAULT_SUFFIX = ".mustache";

    public HandlebarsFactory() {
        init(DEFAULT_PREFIX, DEFAULT_SUFFIX);
    }

    public HandlebarsFactory(String prefix, String suffix) {
        init(prefix, suffix);
    }

    private void init(String prefix, String suffix) {
        TemplateLoader templateLoader = new ClassPathTemplateLoader();
        templateLoader.setPrefix(prefix);
        templateLoader.setSuffix(suffix);
        handlebars = new Handlebars(templateLoader);
    }

    public void put(String key) {
        put(key, key);
    }

    public void put(String key, String fileName) {

        try {
            Template template = handlebars.compile(fileName);
            factory.put(key, template);
        } catch (IOException e) {
            log.error("NOT Found File: {}", fileName);
        }
    }

    public String template (String key, Object templateData) {
        Template template = this.get(key);

        try {
            return template.apply(templateData);
        } catch (Exception e) {
            log.error("Template Apply Exception key={}", key, e);
            throw new IllegalStateException("Template Apply Exception key=" + key);
        }
    }

    public Template get(String key){
        return Optional.ofNullable(factory.get(key))
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 Handlebars 템플릿입니다. key="+key));
    }
}

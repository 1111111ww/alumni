package com.university.common.velocity.tools;

import org.apache.velocity.tools.generic.LocaleConfig;
import org.apache.velocity.tools.generic.ValueParser;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by luxin on 16/7/20.
 */
public class UploadFileTool extends LocaleConfig {

    public static final String DEFAULT_BASE_URL = "";

    private String baseUrl;

    public String getBaseUrl() {
        return baseUrl;
    }

    protected void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    protected void configure(ValueParser values) {
        super.configure(values);
        String baseUrl = values.getString("baseUrl");
        if (baseUrl != null) {
            this.setBaseUrl(baseUrl);
        } else {
            this.setBaseUrl(DEFAULT_BASE_URL);
        }
    }

    public String url() {
        return this.getBaseUrl();
    }

    public String url(String relativeUrl) throws MalformedURLException {
        String baseUrl = this.getBaseUrl();
        Path url = Paths.get(baseUrl, relativeUrl);
        return url.toString();
    }
}

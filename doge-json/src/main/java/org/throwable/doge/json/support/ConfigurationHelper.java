package org.throwable.doge.json.support;

import com.alibaba.fastjson.JSON;
import org.throwable.doge.json.common.model.Configuration;
import org.throwable.doge.json.utils.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/9/30 15:03
 */
public abstract class ConfigurationHelper {

    private static final String DEFAULT_MEMORY_CONFIGURATION = "{}";

    private static final Configuration CONFIGURATION;

    static {
        InputStream resourceAsStream = ConfigurationHelper.class.getResourceAsStream("/configuration/configuration.json");
        String content = DEFAULT_MEMORY_CONFIGURATION;
        try {
            content = StreamUtils.copyToString(resourceAsStream, Charset.forName("UTF-8"));
        } catch (IOException e) {
            //ignore

        }
        CONFIGURATION = JSON.parseObject(content, Configuration.class);
    }

    public static Configuration getConfiguration() {
        return CONFIGURATION;
    }
}

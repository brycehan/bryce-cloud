package com.brycehan.cloud.framework.xss;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.apache.commons.text.StringEscapeUtils;

import java.io.IOException;

/**
 * Cross Site Scripting 跨站脚本攻击 JSON 字符串请求参数处理
 *
 * @since 2022/5/26
 * @author Bryce Han
 */
public class XssStringDeserializer extends JsonDeserializer<String> {

    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return StringEscapeUtils.escapeHtml4(jsonParser.getText());
    }

}

package com.brycehan.cloud.gateway.filter;

import com.brycehan.cloud.gateway.utils.AuthPathParser;
import com.brycehan.cloud.gateway.utils.XssUtils;
import io.netty.buffer.ByteBufAllocator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * Xss（跨站脚本过滤） 过滤器
 *
 * @since 2023/10/26
 * @author Bryce Han
 */
@Slf4j
@Order(-90)
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "bryce.auth.xss.enabled", havingValue = "true")
public class XssFilter implements GlobalFilter {

    private final AuthPathParser authPathParser;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        String url = request.getURI().getPath();
        // GET DELETE 不过滤
        if (request.getMethod() == HttpMethod.GET || request.getMethod() == HttpMethod.DELETE) {
            return chain.filter(exchange);
        }

        // 非json请求 不过滤
        if (!isJsonRequest(request)) {
            return chain.filter(exchange);
        }

        // 放行不过滤的 url
        if (authPathParser.xssIgnoreMatch(url)) {
            return chain.filter(exchange);
        }

        ServerHttpRequestDecorator httpRequestDecorator = httpRequestDecorator(request);

        return chain.filter(exchange.mutate().request(httpRequestDecorator).build());
    }

    /**
     * 是否是Json请求
     *
     * @param request request
     * @return 是否是Json请求
     */
    public boolean isJsonRequest(ServerHttpRequest request) {
        String contentType = request.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE);
        return StringUtils.startsWithIgnoreCase(contentType, MediaType.APPLICATION_JSON_VALUE);
    }

    private ServerHttpRequestDecorator httpRequestDecorator(ServerHttpRequest request) {

        return new ServerHttpRequestDecorator(request){
            @Override
            public Flux<DataBuffer> getBody() {
                Flux<DataBuffer> body = super.getBody();
                return body.buffer().map(dataBuffers -> {
                    DataBufferFactory dataBufferFactory = new DefaultDataBufferFactory();
                    DataBuffer joined = dataBufferFactory.join(dataBuffers);
                    byte[] content = new byte[joined.readableByteCount()];
                    joined.read(content);
                    DataBufferUtils.release(joined);

                    String bodyStr = new String(content, StandardCharsets.UTF_8);
                    // 防xss攻击过滤
                    bodyStr = XssUtils.filter(bodyStr);
                    // 转成字节数组
                    byte[] bytes = bodyStr.getBytes(StandardCharsets.UTF_8);
                    NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(ByteBufAllocator.DEFAULT);
                    NettyDataBuffer dataBuffer = nettyDataBufferFactory.allocateBuffer(bytes.length);
                    dataBuffer.write(bytes);
                    return dataBuffer;
                });
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.putAll(super.getHeaders());
                // 由于修改了请求体body，导致content-length长度不确定，所以需要删除原先的content-length
                httpHeaders.remove(HttpHeaders.CONTENT_LENGTH);
                httpHeaders.set(HttpHeaders.TRANSFER_ENCODING, "chunked");

                return httpHeaders;
            }
        };
    }

}

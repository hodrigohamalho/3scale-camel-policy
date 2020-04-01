package com.redhat.integration;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import java.util.Locale;

import org.springframework.stereotype.Component;

@Component
public class CustomPolicyRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        
        from("netty4-http:proxy://0.0.0.0:8000")
            .process(CustomPolicyRouter::uppercase)
            .log("receive the connection on the proxy")
            .toD("netty4-http:"
                + "${headers." + Exchange.HTTP_SCHEME + "}://"
                + "${headers." + Exchange.HTTP_HOST + "}:"
                + "${headers." + Exchange.HTTP_PORT + "}"
                + "${headers." + Exchange.HTTP_PATH + "}")
            .process(CustomPolicyRouter::uppercase);

    }

    public static void uppercase(final Exchange exchange) {
        System.out.println("inside the processor method");
        // final Message message = exchange.getIn();
        // final String body = message.getBody(String.class);
        // System.out.println("Body: "+body);
        // message.setBody(body.toUpperCase(Locale.US));
    }

}
package com.serverless.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.serverless.response.ApiGatewayResponse;
import com.serverless.response.Response;
import com.serverless.model.Book;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.HashMap;
import java.util.Map;

public class AddBookHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    private static final Logger logger = LogManager.getLogger(AddBookHandler.class);

    /**
     * Requisição de cadastro
     *
     * @param input Entrada de dados da APIG.
     * @param context Contexto da APIG
     * @return {@link Book} Retorno da criação do objeto
     */
    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {

        try {
            Map<String, String> body = (Map<String, String>) input.get("body");
            Book book = new Book();

            String name = body.get("name");
            book.setName(name);

            String author = body.get("author");
            book.setAuthor(author);
            book.save(book);

            logger.info(name + " saved successfully.");
            return ApiGatewayResponse.builder()
                    .setStatusCode(200)
                    .setObjectBody(book)
                    .setHeaders(new HashMap<>())
                    .build();

        } catch (Exception ex) {
            logger.error("Error in saving book: " + ex);

            Response responseBody = new Response("Error in saving book: ", input);
            return ApiGatewayResponse.builder()
                    .setStatusCode(500)
                    .setObjectBody(responseBody)
                    .setHeaders(new HashMap<>())
                    .build();
        }
    }
}

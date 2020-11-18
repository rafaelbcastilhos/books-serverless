package com.serverless.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.serverless.response.ApiGatewayResponse;
import com.serverless.response.Response;
import com.serverless.model.Book;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListBookHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    private static final Logger logger = LogManager.getLogger(ListBookHandler.class);

    /**
     * Requisição de listagem.
     *
     * @param input Entrada de dados da APIG (vazio).
     * @param context Contexto da APIG
     * @return {@link Book} Retorno dos objetos previamente cadastrados
     */
    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
        try {
            List<Book> book = new Book().list();

            logger.info("Returning list of books.");

            return ApiGatewayResponse.builder()
                    .setStatusCode(200)
                    .setObjectBody(book)
                    .setHeaders(new HashMap<>())
                    .build();
        } catch (Exception ex) {
            logger.error("Error in listing books: " + ex);

            Response responseBody = new Response("Error in listing books: ", input);
            return ApiGatewayResponse.builder()
                    .setStatusCode(500)
                    .setObjectBody(responseBody)
                    .setHeaders(new HashMap<>())
                    .build();
        }
    }
}

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

public class GetBookHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    private static final Logger logger = LogManager.getLogger(GetBookHandler.class);

    /**
     * Requisição de captura
     *
     * @param input Entrada de dados da APIG.
     * @param context Contexto da APIG
     * @return {@link Book} Retorno do objeto capturado
     */
    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {

        try {
            Map<String, String> pathParameters = (Map<String, String>) input.get("pathParameters");
            String bookId = pathParameters.get("id");

            Book book = new Book().get(bookId);

            if (book != null) {
                logger.info("Book found returning.");

                return ApiGatewayResponse.builder()
                        .setStatusCode(200)
                        .setObjectBody(book)
                        .setHeaders(new HashMap<>())
                        .build();
            } else {
                logger.warn("Book not found.");

                return ApiGatewayResponse.builder()
                        .setStatusCode(404)
                        .setObjectBody("Book with id: '" + bookId + "' not found.")
                        .setHeaders(new HashMap<>())
                        .build();
            }
        } catch (Exception ex) {
            logger.error("Error in retrieving book: " + ex);

            Response responseBody = new Response("Error in retrieving book: ", input);
            return ApiGatewayResponse.builder()
                    .setStatusCode(500)
                    .setObjectBody(responseBody)
                    .setHeaders(new HashMap<>())
                    .build();
        }
    }
}

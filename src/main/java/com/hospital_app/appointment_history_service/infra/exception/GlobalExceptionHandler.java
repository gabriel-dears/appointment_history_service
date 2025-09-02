package com.hospital_app.appointment_history_service.infra.exception;

import com.hospital_app.common.exception.ErrorResponseEntityFactory;
import com.hospital_app.common.exception.GlobalExceptionHandlerBase;
import com.hospital_app.common.exception.dto.ErrorResponse;
import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends GlobalExceptionHandlerBase {

    @GraphQlExceptionHandler(IllegalArgumentException.class)
    public GraphQLError handleGraphQLIllegalArgument(IllegalArgumentException ex, HttpServletRequest request) {
        ErrorResponse errorResponse = ErrorResponseEntityFactory.getBadRequest(ex, request).getBody();

        return GraphqlErrorBuilder.newError()
                .message(errorResponse.messages().stream().findFirst().orElse("Invalid input"))
                .errorType(ErrorType.ValidationError)
                .extensions(Map.of("error", errorResponse))
                .build();
    }

    @GraphQlExceptionHandler(Exception.class)
    public GraphQLError handleGraphQLException(Exception ex, HttpServletRequest request) {
        ErrorResponse errorResponse = ErrorResponseEntityFactory.getInternalServerError(ex, request).getBody();

        return GraphqlErrorBuilder.newError()
                .message(errorResponse.messages().stream().findFirst().orElse("Internal server error"))
                .errorType(ErrorType.DataFetchingException)
                .extensions(Map.of("error", errorResponse))
                .build();
    }
}


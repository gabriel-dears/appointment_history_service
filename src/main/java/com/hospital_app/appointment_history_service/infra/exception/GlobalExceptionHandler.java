package com.hospital_app.appointment_history_service.infra.exception;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public GraphQLError handleGraphQLException(Exception ex) {
        return GraphqlErrorBuilder.newError()
                .message(ex.getMessage())
                .build();
    }
}



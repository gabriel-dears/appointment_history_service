package com.hospital_app.appointment_history_service.infra.exception;

import graphql.GraphQLError;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {

    @Test
    void shouldBuildGraphQLErrorWithExceptionMessage() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        GraphQLError error = handler.handleGraphQLException(new RuntimeException("boom"));
        assertEquals("boom", error.getMessage());
    }
}

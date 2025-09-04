package com.hospital_app.appointment_history_service.infra.db;


import com.hospital_app.appointment_history_service.application.exception.AppointmentHistoryDbException;
import com.hospital_app.common.db.DbOperationWrapper;

public class AppointmentHistoryDbOperationWrapper {

    public static <T> T execute(DbOperationWrapper.DbOperation<T> operation) {
        return DbOperationWrapper.execute(operation, AppointmentHistoryDbException.class);
    }

}

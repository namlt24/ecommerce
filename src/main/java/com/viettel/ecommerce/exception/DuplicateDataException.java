package com.viettel.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class DuplicateDataException extends RuntimeException {

    /**
     * Constructor không tham số, tạo một exception mới với thông báo mặc định.
     */
    public DuplicateDataException() {
        super("Duplicate data found.");
    }

    /**
     * Constructor với thông báo lỗi tùy chỉnh.
     *
     * @param message Thông báo lỗi chi tiết
     */
    public DuplicateDataException(String message) {
        super(message);
    }

    /**
     * Constructor với thông báo lỗi và nguyên nhân gốc rễ.
     *
     * @param message Thông báo lỗi chi tiết
     * @param cause   Nguyên nhân gốc rễ của lỗi
     */
    public DuplicateDataException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor với nguyên nhân gốc rễ.
     *
     * @param cause Nguyên nhân gốc rễ của lỗi
     */
    public DuplicateDataException(Throwable cause) {
        super(cause);
    }
}

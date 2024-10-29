package com.luizalabs.logistica.desafio.domain.entity;

import com.luizalabs.logistica.desafio.infra.exception.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class FileData {
    private final Long userId;
    private final String userName;
    private final Long orderId;
    private final Long productId;
    private final BigDecimal productValue;
    private final LocalDate orderDate;
    private static final int USER_ID_BEGIN_INDEX = 0;
    private static final int USER_ID_END_INDEX = 10;
    private static final int USER_NAME_BEGIN_INDEX = 10;
    private static final int USER_NAME_END_INDEX = 55;
    private static final int ORDER_ID_BEGIN_INDEX = 55;
    private static final int ORDER_ID_END_INDEX = 65;
    private static final int PRODUCT_ID_BEGIN_INDEX = 65;
    private static final int PRODUCT_ID_END_INDEX = 75;
    private static final int PRODUCT_VALUE_BEGIN_INDEX = 75;
    private static final int PRODUCT_VALUE_END_INDEX = 87;
    private static final int ORDER_DATE_BEGIN_INDEX = 87;
    private static final int ORDER_DATE_END_INDEX = 95;

    private FileData(Long userId, String userName, Long orderId, Long productId, BigDecimal productValue, LocalDate orderDate) {
        this.userId = userId;
        this.userName = userName;
        this.orderId = orderId;
        this.productId = productId;
        this.productValue = productValue;
        this.orderDate = orderDate;
    }

    public static FileData FromLine(String line) {
        try {
            return new FileData(
                    userIdFromLine(line),
                    userNameFromLine(line),
                    orderIdFromLine(line),
                    productIdFromLine(line),
                    productValueFromLine(line),
                    orderDateFromLine(line));
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidFileLineException(line);
        }
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public BigDecimal getProductValue() {
        return productValue;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    private static LocalDate orderDateFromLine(String line) {
        try {
            String dateStr = line.substring(ORDER_DATE_BEGIN_INDEX, ORDER_DATE_END_INDEX);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            return LocalDate.parse(dateStr, formatter);
        } catch (DateTimeParseException e) {
            throw new InvalidDateException(line);
        }
    }

    private static BigDecimal productValueFromLine(String line) {
        try {
            String valueStr = line.substring(PRODUCT_VALUE_BEGIN_INDEX, PRODUCT_VALUE_END_INDEX).trim();
            return new BigDecimal(valueStr);
        } catch (NumberFormatException e) {
            throw new InvalidProductValueException(line);
        }
    }

    private static long productIdFromLine(String line) {
        try {
            String prodIdStr = line.substring(PRODUCT_ID_BEGIN_INDEX, PRODUCT_ID_END_INDEX).replaceFirst("^0+(?!$)", "");
            return Long.parseLong(prodIdStr);
        } catch (NumberFormatException e) {
            throw new InvalidProductIdException(line);
        }
    }

    private static long orderIdFromLine(String line) {
        try {
            String orderIdStr = line.substring(ORDER_ID_BEGIN_INDEX, ORDER_ID_END_INDEX).replaceFirst("^0+(?!$)", "");
            return Long.parseLong(orderIdStr);
        } catch (NumberFormatException e) {
            throw new InvalidOrderIdException(line);
        }
    }

    private static String userNameFromLine(String line) throws IndexOutOfBoundsException {
        String userNameStr = line.substring(USER_NAME_BEGIN_INDEX, USER_NAME_END_INDEX).trim();
        if (userNameStr.isEmpty()) {
            throw new InvalidUserNameException(line);
        }
        return userNameStr;
    }

    private static long userIdFromLine(String line) {
        try {
            String userIdStr = line.substring(USER_ID_BEGIN_INDEX, USER_ID_END_INDEX).replaceFirst("^0+(?!$)", "");
            return Long.parseLong(userIdStr);
        } catch (NumberFormatException e) {
            throw new InvalidUserIdException(line);
        }
    }
}

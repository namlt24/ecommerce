package com.viettel.ecommerce.util;

import java.util.Collection;
import java.util.Map;

import java.util.Collection;
import java.util.Map;

public class DataUtil {

    /**
     * Kiểm tra xem một chuỗi có null hoặc rỗng hay không.
     *
     * @param str Chuỗi cần kiểm tra
     * @return true nếu chuỗi là null hoặc rỗng, false nếu ngược lại
     */
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * Kiểm tra xem một collection có null hoặc rỗng hay không.
     *
     * @param collection Collection cần kiểm tra
     * @return true nếu collection là null hoặc rỗng, false nếu ngược lại
     */
    public static boolean isNullOrEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * Kiểm tra xem một map có null hoặc rỗng hay không.
     *
     * @param map Map cần kiểm tra
     * @return true nếu map là null hoặc rỗng, false nếu ngược lại
     */
    public static boolean isNullOrEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    /**
     * Kiểm tra xem một mảng có null hoặc rỗng hay không.
     *
     * @param array Mảng cần kiểm tra
     * @return true nếu mảng là null hoặc không có phần tử, false nếu ngược lại
     */
    public static boolean isNullOrEmpty(Object[] array) {
        return array == null || array.length == 0;
    }

    /**
     * Kiểm tra xem một đối tượng có null hay không.
     *
     * @param obj Đối tượng cần kiểm tra
     * @return true nếu đối tượng là null, false nếu ngược lại
     */
    public static boolean isNull(Object obj) {
        return obj == null;
    }

    /**
     * Kiểm tra xem một đối tượng có null hoặc rỗng hay không.
     * Áp dụng cho String, Collection, Map, Array, và Object tùy chỉnh.
     *
     * @param obj Đối tượng cần kiểm tra
     * @return true nếu đối tượng là null hoặc rỗng, false nếu ngược lại
     */
    public static boolean isNullOrEmpty(Object obj) {
        if (obj == null) {
            return true;
        }

        if (obj instanceof String) {
            return isNullOrEmpty((String) obj);
        }

        if (obj instanceof Collection) {
            return isNullOrEmpty((Collection<?>) obj);
        }

        if (obj instanceof Map) {
            return isNullOrEmpty((Map<?, ?>) obj);
        }

        if (obj instanceof Object[]) {
            return isNullOrEmpty((Object[]) obj);
        }

        // Kiểm tra với các loại đối tượng tuỳ chỉnh khác nếu cần thiết
        // Ví dụ: Kiểm tra nếu một đối tượng có các trường dữ liệu chính là null hoặc rỗng
        // Bạn có thể tuỳ chỉnh thêm ở đây

        return false; // Đối tượng không null hoặc rỗng
    }
    public static <T> boolean safeEqual(T value1, T value2) {
        // Nếu cả hai giá trị đều là null, chúng được coi là bằng nhau
        if (value1 == null && value2 == null) {
            return true;
        }

        // Nếu chỉ một giá trị là null, chúng không bằng nhau
        if (value1 == null || value2 == null) {
            return false;
        }

        // Nếu giá trị đầu tiên không phải cùng loại với giá trị thứ hai, trả về false
        if (!value1.getClass().equals(value2.getClass())) {
            return false;
        }

        // Sử dụng phương thức equals để kiểm tra sự bằng nhau
        return value1.equals(value2);
    }
}

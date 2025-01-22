package sparta.coding.club.prehomework.config;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class BigDecimalUtils {
    public static String addComma(BigDecimal value) {
        DecimalFormat format = new DecimalFormat("###,###"); //포맷팅
        return format.format(value);
    }

}

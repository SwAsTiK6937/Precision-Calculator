import java.math.BigDecimal;
import java.math.RoundingMode;

public class Calculator {
    public BigDecimal add(BigDecimal a, BigDecimal b) {
        return a.add(b);
    }
    public BigDecimal subtract(BigDecimal a, BigDecimal b) {
        return a.subtract(b);
    }
    public BigDecimal multiply(BigDecimal a, BigDecimal b) {
        return a.multiply(b);
    }
    public BigDecimal divide(BigDecimal a, BigDecimal b) {
        if (b.equals(BigDecimal.ZERO)) {
            throw new ArithmeticException("Cannot divide by zero.");
        }
        return a.divide(b, 10, RoundingMode.HALF_UP); 
    }
}

package mk.ukim.finki.av2.complex;

import java.math.BigDecimal;

public class BigComplex {

    private BigDecimal realPart;
    private BigDecimal imaginaryPart;

    public BigComplex(BigDecimal realPart, BigDecimal imaginaryPart) {
        this.realPart = realPart;
        this.imaginaryPart = imaginaryPart;
    }

    public BigComplex add(BigComplex complex) {
        return new BigComplex(this.realPart.add(complex.realPart),
                this.imaginaryPart.add(complex.imaginaryPart));
    }

    @Override
    public String toString() {
        return "BigComplex{" +
                "realPart=" + realPart +
                ", imaginaryPart=" + imaginaryPart +
                '}';
    }
}

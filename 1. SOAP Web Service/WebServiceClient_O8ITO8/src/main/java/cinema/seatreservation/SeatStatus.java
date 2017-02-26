
package cinema.seatreservation;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SeatStatus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="SeatStatus">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Free"/>
 *     &lt;enumeration value="Locked"/>
 *     &lt;enumeration value="Reserved"/>
 *     &lt;enumeration value="Sold"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "SeatStatus")
@XmlEnum
public enum SeatStatus {

    @XmlEnumValue("Free")
    FREE("Free"),
    @XmlEnumValue("Locked")
    LOCKED("Locked"),
    @XmlEnumValue("Reserved")
    RESERVED("Reserved"),
    @XmlEnumValue("Sold")
    SOLD("Sold");
    private final String value;

    SeatStatus(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SeatStatus fromValue(String v) {
        for (SeatStatus c: SeatStatus.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}


package cinema.seatreservation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetSeatStatus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetSeatStatus">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="seat" type="{http://www.iit.bme.hu/soi/hw/SeatReservation}Seat"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetSeatStatus", propOrder = {
    "seat"
})
public class GetSeatStatus {

    @XmlElement(required = true, nillable = true)
    protected Seat seat;

    /**
     * Gets the value of the seat property.
     * 
     * @return
     *     possible object is
     *     {@link Seat }
     *     
     */
    public Seat getSeat() {
        return seat;
    }

    /**
     * Sets the value of the seat property.
     * 
     * @param value
     *     allowed object is
     *     {@link Seat }
     *     
     */
    public void setSeat(Seat value) {
        this.seat = value;
    }

}

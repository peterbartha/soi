
package cinema.seatreservation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetAllSeatsResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetAllSeatsResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GetAllSeatsResult" type="{http://www.iit.bme.hu/soi/hw/SeatReservation}ArrayOfSeat"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetAllSeatsResponse", propOrder = {
    "getAllSeatsResult"
})
public class GetAllSeatsResponse {

    @XmlElement(name = "GetAllSeatsResult", required = true, nillable = true)
    protected ArrayOfSeat getAllSeatsResult;

    /**
     * Gets the value of the getAllSeatsResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSeat }
     *     
     */
    public ArrayOfSeat getGetAllSeatsResult() {
        return getAllSeatsResult;
    }

    /**
     * Sets the value of the getAllSeatsResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSeat }
     *     
     */
    public void setGetAllSeatsResult(ArrayOfSeat value) {
        this.getAllSeatsResult = value;
    }

}

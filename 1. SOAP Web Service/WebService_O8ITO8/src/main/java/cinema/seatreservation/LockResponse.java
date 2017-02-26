
package cinema.seatreservation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LockResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LockResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="LockResult" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LockResponse", propOrder = {
    "lockResult"
})
public class LockResponse {

    @XmlElement(name = "LockResult", required = true, nillable = true)
    protected String lockResult;

    /**
     * Gets the value of the lockResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLockResult() {
        return lockResult;
    }

    /**
     * Sets the value of the lockResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLockResult(String value) {
        this.lockResult = value;
    }

}

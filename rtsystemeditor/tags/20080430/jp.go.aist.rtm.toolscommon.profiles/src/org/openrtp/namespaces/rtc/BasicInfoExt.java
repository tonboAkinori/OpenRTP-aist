//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.3 in JDK 1.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.04.24 at 07:25:27 �ߑO GMT 
//


package org.openrtp.namespaces.rtc;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for basic_info_ext complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="basic_info_ext">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openrtp.org/namespaces/rtc_doc}basic_info_doc">
 *       &lt;sequence>
 *         &lt;element name="VersionUpLog" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "basic_info_ext", namespace = "http://www.openrtp.org/namespaces/rtc_ext", propOrder = {
    "versionUpLog"
})
public class BasicInfoExt
    extends BasicInfoDoc
{

    @XmlElement(name = "VersionUpLog", namespace = "http://www.openrtp.org/namespaces/rtc_ext")
    protected List<String> versionUpLog;

    /**
     * Gets the value of the versionUpLog property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the versionUpLog property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVersionUpLog().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getVersionUpLog() {
        if (versionUpLog == null) {
            versionUpLog = new ArrayList<String>();
        }
        return this.versionUpLog;
    }
    public void setVersionUpLog(List<String> list) {
    }
}

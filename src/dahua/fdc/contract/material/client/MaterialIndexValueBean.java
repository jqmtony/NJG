/**
 * 
 */
package com.kingdee.eas.fdc.material.client;

import java.io.Serializable;

/**
 * @(#)							
 * 版权：		金蝶国际软件集团有限公司版权所有		 	
 * 描述：		
 *		
 * @author		顾蛟
 * @version		EAS1.0		
 * @createDate	2010-11-9	 
 * @see						
 */
public class MaterialIndexValueBean implements  Serializable{
	
	/**   */
	private static final long serialVersionUID = 314714589671454679L;
	private String name;
	private String value;
	private String id;
	private String materialIndexValueId;
	
	public String getMaterialIndexValueId() {
		return materialIndexValueId;
	}
	public void setMaterialIndexValueId(String materialIndexValueId) {
		this.materialIndexValueId = materialIndexValueId;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}

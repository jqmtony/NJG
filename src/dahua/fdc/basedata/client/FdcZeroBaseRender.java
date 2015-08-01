/**
 * @(#)FdcZeroBaseRender.java 1.0 2014-6-6
 * @author 王正
 * @email SkyIter@live.com
 * Copyright 2014 Kingdee, Inc. All rights reserved
 */

package com.kingdee.eas.fdc.basedata.client;

import java.awt.Graphics;
import java.awt.Shape;
import java.math.BigDecimal;

import com.kingdee.bos.ctrl.kdf.util.style.Style;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;

/**
 * 描述：
 * <p>
 * 列存放的是一个BigDecimal，但是遇到0则不显示
 * 
 * @author 王正
 * @email SkyIter@live.com
 * @createDate 2014-6-6
 * @version 1.0, 2014-6-6
 * @see
 * @since JDK1.4
 */
public class FdcZeroBaseRender extends FdcBaseRender {

	static FdcZeroBaseRender render = null;// 存放具体实例，遇到0则不显示
	static FdcZeroBaseRender render2zero = null;// 存放具体实例，遇到0也显示
	private boolean showZero = false;

	private FdcZeroBaseRender(String style) {
		super(style);
	}

	public static FdcZeroBaseRender getInstance() {
		if (render == null) {
			render = new FdcZeroBaseRender(null);
		}
		return render;
	}

	public static FdcZeroBaseRender getInstance(boolean isShowZero) {
		// if(isShowZero){
		if (render2zero == null) {// 无需线程同步
			render2zero = new FdcZeroBaseRender(null);
			render2zero.showZero = isShowZero;// 保存0标志
		}
		return render2zero;
		// }else{
		// if(render==null){
		// render = new FdcZeroBaseRender(null);
		// }
		// return render;
		// }
	}

	public void draw(Graphics graphics, Shape clip, Object object, Style cellStyle, Object extObject) {

		if (object == null
				|| (!showZero && (object.toString().equals("0") || (object instanceof BigDecimal && FDCConstants.ZERO
						.compareTo((BigDecimal) object) == 0)))) {
			object = null;
		} else if (object instanceof String) {
			String str = (String) object;
			str = FdcZeroBaseRender.replace(str, ",", "0");
			BigDecimal num = FDCNumberHelper.toBigDecimal(str);

			if (num == null || (!showZero && FDCNumberHelper.isEqual(FDCConstants.ZERO, num))) {
				object = null;
			}
		}

		super.draw(graphics, clip, object, cellStyle, null);
	}

	/**
	 * replace 字符串替换
	 * 
	 * @param str
	 * @param regex
	 * @param replacement
	 * @return
	 */
	private static String replace(String str, String regex, String replacement) {
		if (str == null) {
			return null;
		}
		return str.replaceAll(regex, replacement);
	}

	/**
	 * 返回是否显示0标志
	 * 
	 * @return
	 */
	public boolean isShowZero() {
		return showZero;
	}
}

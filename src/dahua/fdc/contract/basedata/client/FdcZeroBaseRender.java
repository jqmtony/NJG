/**
 * @(#)FdcZeroBaseRender.java 1.0 2014-6-6
 * @author ����
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
 * ������
 * <p>
 * �д�ŵ���һ��BigDecimal����������0����ʾ
 * 
 * @author ����
 * @email SkyIter@live.com
 * @createDate 2014-6-6
 * @version 1.0, 2014-6-6
 * @see
 * @since JDK1.4
 */
public class FdcZeroBaseRender extends FdcBaseRender {

	static FdcZeroBaseRender render = null;// ��ž���ʵ��������0����ʾ
	static FdcZeroBaseRender render2zero = null;// ��ž���ʵ��������0Ҳ��ʾ
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
		if (render2zero == null) {// �����߳�ͬ��
			render2zero = new FdcZeroBaseRender(null);
			render2zero.showZero = isShowZero;// ����0��־
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
	 * replace �ַ����滻
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
	 * �����Ƿ���ʾ0��־
	 * 
	 * @return
	 */
	public boolean isShowZero() {
		return showZero;
	}
}

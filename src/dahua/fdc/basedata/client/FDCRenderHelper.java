package com.kingdee.eas.fdc.basedata.client;

import java.util.HashMap;
import java.util.Map;

import com.kingdee.bos.ctrl.extendcontrols.IDataFormat;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.util.render.IBasicRender;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.eas.fdc.basedata.FDCHelper;

/**
 * ���������ز� ��Ⱦ������
 * 
 * @author ����
 * @email SkyIter@live.com
 * @createDate 2014-6-6
 * @version 1.0, 2014-6-6
 * @see
 * @since JDK1.4
 */
public class FDCRenderHelper {
	
	private static final Map formatMap = new HashMap();
	
	public static IDataFormat getLongNumberFormater(){
		return new  IDataFormat(){
			public String format(Object o) {
				if(o instanceof String && !FDCHelper.isEmpty(o)){
					return ((String)o).replace('!', '.');
				}
				return null;
			}
		};
	}
	
	public static IBasicRender getLongNumberRender(){
		ObjectValueRender r=new ObjectValueRender();
		r.setFormat(getLongNumberFormater());
		return r;
	}


	public static void setRender(IColumn col, String style) {
		col.setRenderer(new FdcBaseRender(style));
	}

	/**
	 * ȡ��С����ʽ��ĩβ���Զ�����0
	 * 
	 * @param scale
	 * @return
	 */
	public static String getDecimalFormat(int scale) {
		String formatStr = null;

		String scaleStr = String.valueOf(scale);
		formatStr = (String) formatMap.get(scaleStr);
		if (null == formatStr) {
			// �����͸����ֱ�д
			StringBuffer sb = new StringBuffer("");
			StringBuffer fsb = new StringBuffer("-");

			sb.append("#,##");
			fsb.append("#,##");

			sb.append("0");
			fsb.append("0");
			if (scale > 0) {
				sb.append(".");
				fsb.append(".");
				for (int i = 0; i < scale; i++) {
					sb.append("0");
					fsb.append("0");
				}
			}
			sb.append(";");
			sb.append(fsb);

			formatStr = sb.toString();
			formatMap.put(scaleStr, formatStr);
		}

		return formatStr;
	}

	/**
	 * ȡ��С����ʽ��ĩβ���Զ�ȥ��0
	 * 
	 * @param scale
	 * @return
	 */
	public static String getDecimalFormat2(int scale) {
		// String formatStr = null;
		//
		// String scaleStr = String.valueOf(scale);
		// formatStr = (String) formatMap.get(scaleStr);
		// if (null == formatStr) {
		// // �����͸����ֱ�д
		// StringBuffer sb = new StringBuffer("");
		// StringBuffer fsb = new StringBuffer("-");
		// sb.append("#,###");
		// fsb.append("#,###");
		//
		// if (scale > 0) {
		// sb.append(".");
		// fsb.append(".");
		// for (int i = 0; i < scale; i++) {
		// sb.append("#");
		// fsb.append("#");
		// }
		// }
		// sb.append(";");
		// sb.append(fsb);
		//
		// formatStr = sb.toString();
		// formatMap.put(scaleStr, formatStr);
		// }
		//
		// return formatStr;

		String formatStr = null;

		String scaleStr = String.valueOf(scale);
		formatStr = (String) formatMap.get(scaleStr);
		if (null == formatStr) {
			// �����͸����ֱ�д
			StringBuffer sb = new StringBuffer("");
			StringBuffer fsb = new StringBuffer("-");

			sb.append("#,##");
			fsb.append("#,##");

			sb.append("0");
			fsb.append("0");
			if (scale > 0) {
				sb.append(".");
				fsb.append(".");
				for (int i = 0; i < scale; i++) {
					sb.append("0");
					fsb.append("0");
				}
			}
			sb.append(";");
			sb.append(fsb);

			formatStr = sb.toString();
			formatMap.put(scaleStr, formatStr);
		}

		return formatStr;
	}
}

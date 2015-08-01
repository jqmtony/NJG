package com.kingdee.eas.fdc.finance.client.util;

import java.awt.Graphics;
import java.awt.Shape;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;

import com.kingdee.bos.ctrl.kdf.table.render.RenderObject;
import com.kingdee.bos.ctrl.kdf.util.render.CellTextRender;
import com.kingdee.bos.ctrl.kdf.util.render.TextIconRender;
import com.kingdee.bos.ctrl.kdf.util.style.Style;
import com.kingdee.bos.ctrl.swing.DecimalFormatEx;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fm.common.AmountUnitEnum;

public 	class CellAmountUnitRender extends TextIconRender {
	
	private AmountUnitEnum unit;

	private NumberFormat format;
	
	private static CellTextRender render = new CellTextRender();
	
	public CellAmountUnitRender(AmountUnitEnum unit,int precision){
		this.unit = unit;
		format = new DecimalFormatEx(getKDTCurrencyFormat(precision));
	}
	
	public void draw(Graphics graphics, Shape clip, Object object, Style cellStyle, Object extObject)
	{
		if (extObject instanceof RenderObject) {
			RenderObject obj = (RenderObject) extObject;
			if(obj.getCell() != null){
				render.draw(graphics, clip, getText(obj.getCell().getValue()), cellStyle);
			}else{
				render.draw(graphics, clip, getText(null), cellStyle);
			}
		} else {
			render.draw(graphics, clip, getText(object), cellStyle);
		}
	}
	
	protected String getText(Object obj) {
		if (obj == null || unit == null) {
			return null;
		}
		if (obj instanceof BigDecimal || obj instanceof BigInteger
				|| obj instanceof Integer) {
			BigDecimal val = FDCHelper.divide(obj, new Integer(unit.getValue()));
			return format.format(val);
		}
		return obj.toString();
	}
	
	private  String getKDTCurrencyFormat(int precision){
		StringBuffer numberFormate = new StringBuffer("##,###,###,###,###,##0");
		if (precision > 0) {
			numberFormate.append(".");
		}
		for (int i = 0; i < precision; i++) {
			numberFormate.append("0");
		}
		return numberFormate.toString();
	}
	
	public void setUnit(AmountUnitEnum unit) {
		this.unit = unit;
	}
}
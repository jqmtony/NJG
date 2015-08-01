package com.kingdee.eas.fdc.basedata;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
/**
 * 
 * 描述:修改返回值格式
 * @author jackwang  date:2006-8-10 <p>
 * @version EAS4.1
 */
public class ObjectValueRenderImpl extends ObjectValueRender {
	public String getFormattedText(Object object)
	{
		if (object == null)
			return null;

		String text = null;

		if (format == null)
		{
			text = object.toString();
		}
		else
		{
			text = format.format(object);
			if(text.indexOf("!")>0){
				text = text.replace('!', '.');			
			}
		}

		return text;
	}
}

package com.kingdee.eas.port.pm.invest.uitls;

import java.util.HashMap;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.extendcontrols.KDCommonPromptDialog;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.port.pm.invest.client.ProjectTreeSelectorUI;
import com.kingdee.eas.util.client.ExceptionHandler;

public class F7ProjectDialog extends KDCommonPromptDialog{
	protected IUIWindow classDlg = null;
	protected boolean isRefresh = false;
	protected KDBizPromptBox prmtPro ;
	protected boolean flses;
	protected FilterInfo filInfo ;
	
	public String getUITitle(){
		return new String("项目选择");
	}
	public F7ProjectDialog(KDBizPromptBox prmtPro)
	{
		this.prmtPro = prmtPro;
		
		this.setModal(true);
	}
	
	
	public void show()
	{
		IUIFactory uiFactory = null;
		Map map = new HashMap();
		map.put("prmtPro",this.prmtPro);
		map.put("flses",this.flses);
		map.put("filInfo",this.filInfo);
		try
		{
			if (classDlg == null)
			{
				uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
				classDlg = uiFactory.create(ProjectTreeSelectorUI.class.getName(),map,null,"VIEW");
			}
			if (classDlg != null && classDlg.getUIObject() != null)
			{
//				((ProjectTreeSelectorUI)classDlg.getUIObject()).setVisible(false);
			}
			classDlg.show();
		} catch (BOSException ex)
		{
			ExceptionHandler.handle(this, ex);
			return;
		}
	}
	
	/**
	 * 设置多选 false 为单选、true 为多选
	 * @param flse
	 */
	public void setMit(boolean flse)
	{
		flses = flse;
	}
	
	/**
	 * 可通过filterInfo 传入过滤条件
	 * @param filInfo
	 */
	public void setFilterInfo(FilterInfo filInfos)
	{
		filInfo = filInfos;
	}
}

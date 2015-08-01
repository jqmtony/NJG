/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.*;
import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.contract.GuerdonBillCollection;
import com.kingdee.eas.fdc.contract.GuerdonBillFactory;
import com.kingdee.eas.fdc.contract.GuerdonBillInfo;
import com.kingdee.eas.fdc.finance.client.PaymentBillEditUI;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class GuerdonBilInfoUI extends AbstractGuerdonBilInfoUI
{
    private static final Logger logger = CoreUIObject.getLogger(GuerdonBilInfoUI.class);
    
    /**
     * output class constructor
     */
    public GuerdonBilInfoUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

	public void onLoad() throws Exception {
		super.onLoad();
		this.tblMain.checkParsed();
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		this.tblMain.getStyleAttributes().setLocked(true);
		
		//设置对齐方式
		this.tblMain.getColumn("amount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		//设置格式
		this.tblMain.getColumn("amount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		//设置时间格式
		String formatString = "yyyy-MM-dd";
		this.tblMain.getColumn("createTime").getStyleAttributes().setNumberFormat(formatString);

		String contractId = (String) this.getUIContext().get(UIContext.ID);
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("number");
		view.getSelector().add("name");
		view.getSelector().add("state");
		view.getSelector().add("amount");
		view.getSelector().add("isGuerdoned");
		view.getSelector().add("putOutType");
		view.getSelector().add("guerdonThings");
		
		view.getSelector().add("createTime");
		view.getSelector().add("currency.name");
		view.getSelector().add("creator.name");
		
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("contract.id", contractId));
		
		
		GuerdonBillCollection col = GuerdonBillFactory.getRemoteInstance().getGuerdonBillCollection(view);
		for (int i = 0; i < col.size(); i++) {
			GuerdonBillInfo info = col.get(i);
			IRow row = this.tblMain.addRow();
			row.setUserObject(info.getId().toString());
			
			row.getCell("number").setValue(info.getNumber());
			row.getCell("name").setValue(info.getName());
			row.getCell("state").setValue(info.getState());
			row.getCell("currency.name").setValue(info.getCurrency().getName());
			row.getCell("isGuerdoned").setValue(Boolean.valueOf(info.isIsGuerdoned()));
			
			row.getCell("amount").setValue(info.getAmount());			
			row.getCell("putOutType").setValue(info.getPutOutType());
			row.getCell("guerdonThings").setValue(info.getGuerdonThings());
			row.getCell("creator.name").setValue(info.getCreator().getName());
			row.getCell("createTime").setValue(info.getCreateTime());
		}
	}
	
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		super.tblMain_tableClicked(e);
		if (e.getClickCount() == 2) {
			IRow row = tblMain.getRow(e.getRowIndex());
			if(row==null) return ;
			UIContext uiContext = new UIContext(this);
			uiContext.put(UIContext.ID, row.getUserObject());
			uiContext.put("disableSplit", Boolean.TRUE);
			// 创建UI对象并显示
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
					.create(GuerdonBillEditUI.class.getName(),
							uiContext, null, "FINDVIEW");
			uiWindow.show();
		}
	}
}
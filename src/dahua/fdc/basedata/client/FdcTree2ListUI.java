/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.util.FdcStringUtil;

/**
 * output class name
 */
public abstract class FdcTree2ListUI extends AbstractFdcTree2ListUI
{
    private static final Logger logger = CoreUIObject.getLogger(FdcTree2ListUI.class);
    
	private String titleMain = null;
    
    /**
     * output class constructor
     */
    public FdcTree2ListUI() throws Exception
    {
        super();
    }
    
	public void onLoad() throws Exception {
		super.onLoad();

		// 单据名称需与自定义菜单名称一致
		refreshUITitle();
	}

	/**
	 * 刷新UI标题
	 */
	protected void refreshUITitle() {
		// 菜单名
		String mainMenuName = (String) getUIContext().get("MainMenuName");
		if (FdcStringUtil.isBlank(mainMenuName)) {
			titleMain = mainMenuName;
			this.setUITitle(titleMain);
		}
	}

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

	////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////

	@Override
	public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception {
		KDTable table = getTableForCommon();
		if (null != table) {
			// 部分导出之前，检查当前表格是否选中行
			FDCClientVerifyHelper.checkSelected(this, table);
		}

		super.actionExportSelected_actionPerformed(e);
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * 描述：是否支持EAS高级统计(EAS800新增的功能)
	 * 
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2015-4-1
	 */
	// @Override
	protected boolean isSupportEASPivot() {
		// return super.isSupportEASPivot();
		return false;
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////
    
}
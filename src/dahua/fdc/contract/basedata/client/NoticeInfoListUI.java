/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.NoticeInfoFactory;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class NoticeInfoListUI extends AbstractNoticeInfoListUI
{
    private static final Logger logger = CoreUIObject.getLogger(NoticeInfoListUI.class);
    
    /**
     * output class constructor
     */
    public NoticeInfoListUI() throws Exception
    {
        super();
    }

    /**
	 * 忽略CU隔离
	 */
	protected boolean isIgnoreCUFilter(){
	      return true;
	 }
	
    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        super.tblMain_tableClicked(e);
    }

	protected ICoreBase getBizInterface() throws Exception {
		return NoticeInfoFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return NoticeInfoEditUI.class.getName();
	}
	
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
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
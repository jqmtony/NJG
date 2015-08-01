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

		// �������������Զ���˵�����һ��
		refreshUITitle();
	}

	/**
	 * ˢ��UI����
	 */
	protected void refreshUITitle() {
		// �˵���
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
			// ���ֵ���֮ǰ����鵱ǰ����Ƿ�ѡ����
			FDCClientVerifyHelper.checkSelected(this, table);
		}

		super.actionExportSelected_actionPerformed(e);
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * �������Ƿ�֧��EAS�߼�ͳ��(EAS800�����Ĺ���)
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
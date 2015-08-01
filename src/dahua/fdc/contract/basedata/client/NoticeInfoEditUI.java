/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.NoticeInfoFactory;
import com.kingdee.eas.fdc.basedata.NoticeInfoInfo;
import com.kingdee.eas.fdc.basedata.util.FdcCollectionUtil;
import com.kingdee.eas.fdc.basedata.util.FdcOrgUtil;
import com.kingdee.eas.fdc.sales.client.SHEHelper;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class NoticeInfoEditUI extends AbstractNoticeInfoEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(NoticeInfoEditUI.class);
    private FullOrgUnitInfo org = SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo();
    
    /**
     * output class constructor
     */
    public NoticeInfoEditUI() throws Exception
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
		this.initCurrProject();
    }

	protected IObjectValue createNewData() {
		return new NoticeInfoInfo();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return NoticeInfoFactory.getRemoteInstance();
	}

   private void initCurrProject() {
		String query = "com.kingdee.eas.fdc.basedata.app.F7ProjectQuery";
		FilterInfo filterInfo = new FilterInfo();

		// if (org != null && org.getId() != null) {
		// filterInfo.getFilterItems().add(
		// new FilterItemInfo("fullOrgUnit.id",
		// org.getId().toString(), CompareType.EQUALS));
		// }

		// BT877490:增加组织过滤条件（有工程项目的成本中心） by skyiter_wang 2015-05-04
		try {
			OrgUnitInfo orgUnitInfo = SysContext.getSysContext().getCurrentOrgUnit();
			UserInfo userInfo = SysContext.getSysContext().getCurrentUserInfo();
			// 取得有工程项目的成本中心ID集合
			Set idSet = FdcOrgUtil.getHasProjectCostCenterIdSet(null, orgUnitInfo, userInfo);

			if (FdcCollectionUtil.isNotEmpty(idSet)) {
				filterInfo.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", idSet, CompareType.INCLUDE));
			} else {
				filterInfo.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", "-1"));
			}
		} catch (Exception e) {
			e.printStackTrace();

			this.handUIExceptionAndAbort(e);
		}

		SHEHelper.initF7(this.prmtCurProject, query, filterInfo);
	}
    
	/**
	 * 保存
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		if(this.pkContentDate.getText() == null){
			MsgBox.showWarning(this, "公告日期不能为空！");
			return;
		}else if(txtContent.getText() == null || txtContent.getText().trim().equals("")){
			MsgBox.showWarning(this, "公告内容不能为空！");
			return;
		}
		//公告日期为开始展示的日期，要在sql中使用，设置时间：00:00:00.0
		Calendar cal = Calendar.getInstance();
		cal.setTime((Date) pkContentDate.getValue());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		pkContentDate.setValue(cal.getTime());
		
       super.actionSubmit_actionPerformed(e);
	}

}
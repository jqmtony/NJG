/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.codingrule.BindingPropertyFactory;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.ContractWOTCodingTypeFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.util.UIConfigUtility;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 描述:无文本合同编码类型叙事簿界面
 * 
 * @author jackwang date:2007-3-15
 *         <p>
 * @version EAS5.3
 */
public class ContractWOTCodingTypeListUI extends AbstractContractWOTCodingTypeListUI
{
    private static final Logger logger = CoreUIObject.getLogger(ContractWOTCodingTypeListUI.class);
    
    /**
     * output class constructor
     */
    public ContractWOTCodingTypeListUI() throws Exception
    {
        super();
    }
	
	public void onLoad() throws Exception {
		super.onLoad();
		// this.btnEnabled.setVisible(false);
		// this.btnDisEnabled.setVisible(false);
//		this.btnEnabled.setIcon(EASResource.getIcon("imgTbtn_staruse"));
//		this.btnDisEnabled.setIcon(EASResource.getIcon("imgTbtn_forbid"));
		if (OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentCtrlUnit().getId().toString())) {
			this.btnAddNew.setEnabled(true);
			this.btnEdit.setEnabled(true);
			this.btnRemove.setEnabled(true);
//			this.btnEnabled.setVisible(true);
//			this.btnDisEnabled.setVisible(true);
			this.menuItemAddNew.setEnabled(true);
			this.menuItemEdit.setEnabled(true);
			this.menuItemRemove.setEnabled(true);
			// this.menuItemCancel.setv(true)
		} else {
			this.btnAddNew.setEnabled(false);
			this.btnEdit.setEnabled(false);
			this.btnRemove.setEnabled(false);
//			this.btnEnabled.setVisible(false);
//			this.btnDisEnabled.setVisible(false);
			this.menuItemAddNew.setEnabled(false);
			this.menuItemEdit.setEnabled(false);
			this.menuItemRemove.setEnabled(false);
		}
//		由于有查询方案，故不手动设置保存方案。
//		//设置可以保存当前样式
//		tHelper = new TablePreferencesHelper(this);
	}
 
    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
    	checkBeforeRemove();
        super.actionEdit_actionPerformed(e);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
    	checkBeforeRemove();
        super.actionRemove_actionPerformed(e);
    }
    //2009-1-14 如果无文本合同编码类型已被编码规则引用，则不允许修改或删除
    private void checkBeforeRemove() throws BOSException,EASBizException{
    	checkSelected();
    	Map map = getSelectedIdNumber();
    	if(map.size() <= 0)
    		return;
    	Set numbers = new HashSet();
    	for(Iterator it = map.keySet().iterator();it.hasNext();){
    		String key = (String)it.next();
    		String number = (String)map.get(key);
    		numbers.add(number);
    	}
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("propertyName","codeType.number"));
    	filter.getFilterItems().add(new FilterItemInfo("propertyValue",numbers,CompareType.INCLUDE));
    	if(BindingPropertyFactory.getRemoteInstance().exists(filter)){
    		MsgBox.showError(this,"该无文本合同编码在编码规则中已被引用，不允许修改或删除！");
    		SysUtil.abort();
    	}
    }

	protected String getEditUIName() {
		return ContractWOTCodingTypeEditUI.class.getName();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return ContractWOTCodingTypeFactory.getRemoteInstance();
	}
    protected String getEditUIModal()
    {
        // return UIFactoryName.MODEL;
        // return UIFactoryName.NEWWIN;
        // 2006-4-29 胡博要求加入根据配置项来读取UI打开方式。
        String openModel = UIConfigUtility.getOpenModel();
        if (openModel != null)
        {
            return openModel;
        }
        else
        {
            return UIFactoryName.MODEL;
        }
    }
    
    /**
     * 填入表格之前做预处理。
     * @author owen_wen 2010-11-02
     */
    protected void getRowSetBeforeFillTable(IRowSet rowSet) {
		try {
			rowSet.beforeFirst();
			while (rowSet.next()) {
				String contractTypeName = rowSet.getString("contractType.name");				
				if (StringUtils.isEmpty(contractTypeName))
					// 合同类型如果为空，则用“全部”来替代显示 
					rowSet.updateString("contractType.name", "全部");
			}
			rowSet.beforeFirst();
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			this.handUIExceptionAndAbort(e);
		}
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
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
 * ����:���ı���ͬ�����������²�����
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
//		�����в�ѯ�������ʲ��ֶ����ñ��淽����
//		//���ÿ��Ա��浱ǰ��ʽ
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
    //2009-1-14 ������ı���ͬ���������ѱ�����������ã��������޸Ļ�ɾ��
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
    		MsgBox.showError(this,"�����ı���ͬ�����ڱ���������ѱ����ã��������޸Ļ�ɾ����");
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
        // 2006-4-29 ����Ҫ������������������ȡUI�򿪷�ʽ��
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
     * ������֮ǰ��Ԥ����
     * @author owen_wen 2010-11-02
     */
    protected void getRowSetBeforeFillTable(IRowSet rowSet) {
		try {
			rowSet.beforeFirst();
			while (rowSet.next()) {
				String contractTypeName = rowSet.getString("contractType.name");				
				if (StringUtils.isEmpty(contractTypeName))
					// ��ͬ�������Ϊ�գ����á�ȫ�����������ʾ 
					rowSet.updateString("contractType.name", "ȫ��");
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
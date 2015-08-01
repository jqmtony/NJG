package com.kingdee.eas.fdc.basedata.client;

import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.basedata.ContractSourceFactory;
import com.kingdee.eas.fdc.basedata.ContractSourceInfo;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;

public class ContractSourceListUI extends AbstractContractSourceListUI {

	public ContractSourceListUI() throws Exception {
		super();
		// TODO �Զ����ɹ��캯�����
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1374734334685873389L;

	protected FDCDataBaseInfo getBaseDataInfo() {
		// TODO �Զ����ɷ������
		return new ContractSourceInfo();
	}

	/**
     * output getEditUIName method
     */
    protected String getEditUIName()
    {
        return ContractSourceEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return ContractSourceFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        ContractSourceInfo objectValue = new ContractSourceInfo();		
        return objectValue;
    }

}

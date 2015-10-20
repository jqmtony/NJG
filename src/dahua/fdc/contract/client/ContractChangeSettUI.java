/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;

import javax.swing.Action;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.param.util.ParamManager;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.ChangeAuditUtil;
import com.kingdee.eas.fdc.contract.ConChangeSplitFactory;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractChangeBillCollection;
import com.kingdee.eas.fdc.contract.ContractChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractChangeBillInfo;
import com.kingdee.eas.fdc.contract.ContractChangeEntryInfo;
import com.kingdee.eas.fdc.contract.ContractPropertyEnum;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.IContractChangeBill;
import com.kingdee.eas.fdc.finance.ConPayPlanFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.DateTimeUtils;

/**
 * output class name
 */
public class ContractChangeSettUI extends AbstractContractChangeSettUI
{
    private static final Logger logger = CoreUIObject.getLogger(ContractChangeSettUI.class);
    private boolean isOk = false;
    /**
     * output class constructor
     */
    public ContractChangeSettUI() throws Exception
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
    
    /**
     * ��֤���Դ���������  by Cassiel_peng
     */
    public IObjectPK runSubmit() throws Exception {
    	if(isConChangeAuditInWF()){//������߹�����
    		ContractChangeBillFactory.getRemoteInstance().submitForWF(this.editData);
    		return new ObjectUuidPK(this.editData.getId());
    	}else{
    		return super.runSubmit();
    	}
    }
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
    	ContractChangeBillFactory.getRemoteInstance().changeSettle(editData.getId());
    }
    
    /**
     * Ԥ�������Ϊ������
     * @throws Exception 
     */
    private void updateBudgetAmount() throws Exception {
    	editData.setAmount(editData.getBalanceAmount());
    	SelectorItemCollection sic = new SelectorItemCollection();
    	sic.add("amount");
    	getBizInterface().updatePartial(editData, sic);
    }
    
    /**
     * output btnConfirm_actionPerformed method
     */
    protected void btnConfirm_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	// add by zkyan �˽���ں�ͬ������ɱ��ǩ֤��ʱΪ�գ���ʱ��Ϊԭ�ҽ��
		//    	BigDecimal balanceAmt = editData.getBalanceAmount();

		BigDecimal balanceAmt =  FDCHelper.ZERO;
//		BigDecimal balanceAmount = FDCHelper.ZERO;
		if(editData.isHasSettled()){
			balanceAmt = editData.getBalanceAmount();
		}else{
			balanceAmt = editData.getAmount();
		}
		vefySettleAmount(balanceAmt);
    	if (!isConChangeAuditInWF()) {
    		editData.setHasSettled(true);
    		editData.setSettleTime(DateTimeUtils.truncateDate(new Date()));
    		FDCClientVerifyHelper.verifyRequire(this);
    		storeFields();
        	setConfirm(true);
        	//by tim_gao Ҫ��ǰ������Ѳ��ֵ,�ٺ����ж�ʱ���ܱ���һ��
        	//add by zkyanΪʲôҪ����Ԥ����������ᵼ�º��������⣬�ȸĻ����ˣ���������⿴����ʲô����
			//        	updateBudgetAmount();
        	//�������ʱ���Զ���ֶԹ�������������۵�У���뵯�����治��ͬһ������
        	ConChangeSplitFactory.getRemoteInstance().changeSettle(editData);
            super.btnConfirm_actionPerformed(e);
            isOk=true;
    		AbstractSplitInvokeStrategy strategy = SplitInvokeStrategyFactory.createSplitStrategyByParam(editData.getId().toString(), this);
    		strategy.invokeSplit();
    		actionCancel_actionPerformed(e);
    	}else{
    		FDCClientVerifyHelper.verifyRequire(this);
    		storeFields();
    		setConfirm(true);
    		if (FDCHelper.toBigDecimal(tempInfo.getSettAuditAmt(), 2).compareTo((FDCHelper.toBigDecimal(this.txtOriBalanceAmount.getNumberValue(), 2))) == 0
					&& FDCHelper.toBigDecimal(tempInfo.getSettAuditExRate(), 2).compareTo((FDCHelper.toBigDecimal(this.txtExRate.getNumberValue(), 2))) == 0) {
				MsgBox.showWarning("����δ�����κ��޸ģ����޸ĺ����ύ��");
				SysUtil.abort();
			} else {
				/*
				 * ֻҪ�����һ�ξ���Ϊ�ѽ��㣬���ܺ����Ƿ����½���editData.setHasSettled(false);
				 * editData.setSettleTime(DateTimeUtils.truncateDate(null));
				 */
				// ���¹滮���������
				btnSubmit.doClick();
			}
    	}
    	ChangeAuditUtil.synContractProgAmt(balanceAmt, editData, true);
    	/**
		 * ���º�ͬ����ƻ�
		 * ������,�жϺ����������.���ܻع���
		 */
		ConPayPlanFactory.getRemoteInstance().importPayPlan(editData.getContractBill().getId().toString(), false);
    
    }

    private void vefySettleAmount(BigDecimal balanceAmount) {
    	String org = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
		 String paramValue;
		try {
			paramValue = ParamManager.getParamValue(null, new ObjectUuidPK(org), "FDC228_ISSTRICTCONTROL");
			//�ϸ����ʱУ��
			if(editData.getContractBill()!=null && "0".equals(paramValue)){
				
				BOSUuid id = editData.getContractBill().getId();
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("programmingContract.balance");
				
				ContractBillInfo contractBillInfo = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(id), selector);
				
				if(contractBillInfo.getProgrammingContract()!=null && FDCHelper.compareTo(txtBalanceAmount.getBigDecimalValue(), FDCHelper.add(balanceAmount, contractBillInfo.getProgrammingContract().getBalance()))>0){
					FDCMsgBox.showWarning("��ͬ���������ܴ��ں�Լ�滮�Ŀ������");
					abort();
				}
			}
		} catch (EASBizException e) {
			handUIExceptionAndAbort(e);
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}

	}

    /**
     * output btnCan_actionPerformed method
     */
    protected void btnCan_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	setConfirm(false);
    	disposeUIWindow();
        super.btnCan_actionPerformed(e);
        isOk=false;
    }

    public void setConfirm(boolean isOk) {
		this.isOk = isOk;

		disposeUIWindow();
	}
    
    public void initLayout() {
		this.initUIContentLayout();
	}

	protected IObjectValue createNewDetailData(KDTable table) {
		return new ContractChangeEntryInfo();
	}

	protected KDTable getDetailTable() {
		return null;
	}

	protected IObjectValue createNewData() {
		return new ContractChangeBillInfo();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return ContractChangeBillFactory.getRemoteInstance();
	}
	
	public boolean isModify()
	{
		return false;
	}
	/**
	 * ���ñ�����㹤�������� by Cassiel_peng 2009-8-19
	 */
	private static boolean isConChangeAuditInWF() {
		String companyID = SysContext.getSysContext().getCurrentCostUnit().getId().toString();
		boolean retValue=false;
		try {
			 retValue=FDCUtils.getDefaultFDCParamByKey(null,companyID, FDCConstants.FDC_PARAM_CHANGESETTAUDIT);
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return retValue;
	}
	
	private ContractChangeBillInfo tempInfo=null;
	public void onLoad() throws Exception {
		//��Ԫ��������ô�Ķ��������ã������ˣ������ˡ�ֻ��д����Ŀ� ��   by Cassiel_peng  2008-8-20
		forWarn.setFont(new Font("Dialog",0,12));
		forWarn.setText("<html><body>��ʾ:�������֮�������ǩ֤ȷ�ϻ�δ�������Ҫ��ʱ���;</font></br>�����ǩ֤ȷ��֮ǰ�Ѿ���֣���ϵͳ����ݽ������Զ�ˢ�²�֡�</font></body></html>");
		
		actionAutoCount.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_compute"));
		actionAutoCount.setEnabled(true);
		txtBalanceAmount.setHorizontalAlignment(JTextField.RIGHT);
		txtBalanceAmount.setRemoveingZeroInDispaly(false);
		txtBalanceAmount.setMinimumValue(FDCHelper.MIN_VALUE);
		txtBalanceAmount.setMaximumValue(FDCHelper.MAX_VALUE);
		
		txtOriBalanceAmount.setHorizontalAlignment(JTextField.RIGHT);
		txtOriBalanceAmount.setRemoveingZeroInDispaly(false);
		txtOriBalanceAmount.setMinimumValue(FDCHelper.MIN_VALUE);
		txtOriBalanceAmount.setMaximumValue(FDCHelper.MAX_VALUE);; 
		txtOriBalanceAmount.setRequired(true);
		
		txtExRate.setHorizontalAlignment(JTextField.RIGHT);
		txtExRate.setRemoveingZeroInDispaly(false);
		txtExRate.setMinimumValue(FDCHelper.MIN_VALUE);
		txtExRate.setMaximumValue(FDCHelper.MAX_VALUE);
		txtExRate.setRequired(true);
		
		prmtCurrency.setEnabled(false);
		super.onLoad();
		//���ñ�����㹤��������ʱ,ȷ����ť��ʾ"�ύ"����     by Cassiel_peng  2009-8-19
		tempInfo=ContractChangeBillFactory.getRemoteInstance().getContractChangeBillInfo(new ObjectUuidPK(BOSUuid.read(this.editData.getId().toString())));
		if(isConChangeAuditInWF()){
			this.btnConfirm.setText("�ύ");
			this.txtOriBalanceAmount.setValue(FDCHelper.toBigDecimal(tempInfo.getSettAuditAmt(),2));
			BigDecimal localAmt=FDCHelper.multiply(tempInfo.getSettAuditAmt(), tempInfo.getSettAuditExRate());
			this.txtBalanceAmount.setValue(FDCHelper.toBigDecimal(localAmt,2));
		}/*else{
			this.btnConfirm.setText("ȷ��");
			this.txtOriBalanceAmount.setValue(FDCHelper.toBigDecimal(tempInfo.getOriBalanceAmount(),2));
			BigDecimal localAmt=FDCHelper.multiply(tempInfo.getOriBalanceAmount(),tempInfo.getExRate());
			this.txtBalanceAmount.setValue(localAmt);
		}*/
	}

	//��δ�����ͬ��ʾ���ܽ����Զ����㣬����Ҳ�����ͬ�������Ԥ��ۣ�����ֵ���������
    //���������ͬ����ۣ���ͬǩԼ�ۣ���ͬ���Ԥ��ۣ��������ǩ֤ȷ��Ԥ���/��ͬ����ָ�Ԥ����ܺͣ�
	public void actionAutoCount_actionPerformed(ActionEvent e) throws Exception {
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("id");
		selector.add("number");
		selector.add("name");
		selector.add("balanceAmount");
		selector.add("amount");
		selector.add("contractBill.id");
		selector.add("contractBill.amount");
		selector.add("contractBill.settleAmt");
		selector.add("contractBill.hasSettled");
		selector.add("contractBill.contractPropert");
		IContractChangeBill changeInterface = ContractChangeBillFactory.getRemoteInstance();
		ContractChangeBillInfo billInfo = changeInterface
		.getContractChangeBillInfo(new ObjectUuidPK(editData.getId()),selector);
		if(billInfo.getContractBill()!=null){
			if(!billInfo.getContractBill().isHasSettled()){
				MsgBox.showWarning(this, ChangeAuditUtil.getRes("noAutoCount"));
				SysUtil.abort();
			}else if(billInfo.getContractBill().getAmount()==null){
				if(!(billInfo.getContractBill().getContractPropert().equals(ContractPropertyEnum.DIRECT))){
					MsgBox.showWarning(this, ChangeAuditUtil.getRes("contractNoAuto"));
					SysUtil.abort();
				}else{
					MsgBox.showWarning(this, ChangeAuditUtil.getRes("canNotAutoCount"));
					SysUtil.abort();
				}
			}
			else{
				BigDecimal totalAmt = FDCHelper.ZERO;
				EntityViewInfo view=new EntityViewInfo();
		        FilterInfo filter=new FilterInfo();		     
		        filter.getFilterItems().add(new FilterItemInfo("contractBill.id",billInfo.getContractBill().getId()));
		        view.setFilter(filter);
		        view.getSelector().add(new SelectorItemInfo("id"));
		        view.getSelector().add(new SelectorItemInfo("amount"));
		        ContractChangeBillCollection coll = changeInterface.getContractChangeBillCollection(view);
		        ContractChangeBillInfo info = new ContractChangeBillInfo();
		        Iterator itor;
				for (itor = coll.iterator();  itor.hasNext();){
					info = (ContractChangeBillInfo)itor.next();
					totalAmt = totalAmt.add(FDCHelper.toBigDecimal(info.getAmount()));
				}
				BigDecimal toAmt = billInfo.getContractBill().getSettleAmt().subtract(billInfo.getContractBill().getAmount())
						.subtract(totalAmt);
//				BigDecimal diAmt = billInfo.getAmount().divide(totalAmt, BigDecimal.ROUND_HALF_EVEN);
				BigDecimal diAmt =FDCHelper.ZERO;
				if(totalAmt.compareTo(FDCHelper.ZERO)!=0){
//					diAmt= (toAmt.multiply(billInfo.getAmount())).divide(totalAmt,8,BigDecimal.ROUND_HALF_EVEN);
					diAmt= FDCHelper.divide(FDCHelper.multiply(toAmt, billInfo.getAmount()), totalAmt,8,BigDecimal.ROUND_HALF_EVEN);
				}
				BigDecimal finalAmt = diAmt.add(FDCHelper.toBigDecimal(billInfo.getAmount()));
				editData.setBalanceAmount(finalAmt);
				txtBalanceAmount.setValue(finalAmt);

			}
		}
//		SelectorItemCollection selector = new SelectorItemCollection();
//		selector.add("hasSettled");
		
//		ContractBillInfo contractBillInfo = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectStringPK(editData.getContractBill().getId()), selector);
//		
		super.actionAutoCount_actionPerformed(e);
	}
	
    
    //�ؼ����ݱ仯ͳһ�¼�,ʹ�øù���ͳһ�ؼ��¼�,������loadFieldsʱ����ͣ�����ؼ��¼�
	public class ControlDateChangeListener implements DataChangeListener {
		private String shortCut = null; 
		public ControlDateChangeListener(String shortCut){
			this.shortCut = shortCut;
		}
        public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
            try {
            	controlDate_dataChanged(e,this);
            } catch (Exception exc) {
            	handUIExceptionAndAbort(exc);
            } finally {
            }
        }
		public String getShortCut() {
			return shortCut;
		}
    };
    
    protected void controlDate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e,ControlDateChangeListener listener) throws Exception
    {
    	//ҵ��EditUI���أ�ʵ�ֿؼ����ݱ仯�¼�
    	if(listener.getShortCut().equals("txtExRate")){
    		calLocalAmt();
    	} else  	if(listener.getShortCut().equals("txtOriBalanceAmount")){
    		calLocalAmt();
    	}
    }
    
    private void calLocalAmt() {
       	if(txtOriBalanceAmount.getBigDecimalValue() != null && txtExRate.getBigDecimalValue() != null) {
    		BigDecimal lAmount = txtOriBalanceAmount.getBigDecimalValue().multiply(txtExRate.getBigDecimalValue());
    		txtBalanceAmount.setNumberValue(lAmount);
    	}
    	else {
    		txtBalanceAmount.setNumberValue(null);
    	}
    }
    
    //ҵ�����ڱ仯�¼�
    ControlDateChangeListener exRateChangeListener = new ControlDateChangeListener("txtExRate");
    ControlDateChangeListener originlChangeListener = new ControlDateChangeListener("txtOriBalanceAmount");
    
    
    protected void attachListeners() {   	
    	txtExRate.addDataChangeListener(exRateChangeListener);
    	txtOriBalanceAmount.addDataChangeListener(originlChangeListener);
    }
    
    protected void detachListeners() {
    	txtExRate.removeDataChangeListener(exRateChangeListener);
    	txtOriBalanceAmount.removeDataChangeListener(originlChangeListener);
    }
	public void loadFields()
    {
		detachListeners();
        super.loadFields();
    	attachListeners();
    	
    	String baseCurrency = SysContext.getSysContext().getCurrentFIUnit().getBaseCurrency().getId().toString();
    	if(baseCurrency.equals(this.editData.getCurrency().getId().toString())){
    		this.txtExRate.setEnabled(false);
    	}
    	this.txtBalanceAmount.setEnabled(false);
    	
    	txtExRate.setPrecision(editData.getCurrency().getPrecision());
    	txtOriBalanceAmount.setPrecision(editData.getCurrency().getPrecision());
    	//txtExRate.setPrecision(editData.getCurrency().getPrecision());
    }
	
	public boolean isOk() {
		return isOk;
	}
	
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection selectors = super.getSelectors();
		selectors.add("contractBill.id");
		selectors.add("isCostSplit");
		selectors.add("originalAmount");
		selectors.add("amount");
		selectors.add("hasSettled");
		return selectors;
	}
}
/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.util.HashMap;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.param.IOtherParam;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.base.param.ParamItemFactory;
import com.kingdee.eas.base.param.ParamItemInfo;
import com.kingdee.eas.base.param.client.ICustomParamUI;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgTreeInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCParamInfo;

/**
 * output class name
 */
public class FDCContractParamUI extends AbstractFDCContractParamUI  implements IOtherParam, ICustomParamUI
{
    private static final Logger logger = CoreUIObject.getLogger(FDCContractParamUI.class);
    
    public static final String RD_AIM= "rdAim";
    public static final String RD_DYMIC= "rdDymic";
    public static final String RD_MSG= "rdMsg";
    public static final String RD_CONTROL= "rdControl";
    public static final String RD_NONE= "rdNone";
    
    
	private CostCenterOrgUnitInfo curOrgUnit;

	FDCParamInfo paramInfo = null;
	
    /**
     * output class constructor
     */
    public FDCContractParamUI() throws Exception
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

		initData();
		initMyIcon();	
		txtConstantsRate.setPrecision(2);
		rdContractOverRate.setText("合同上的结算提示比例");
		rdCostantsRate.addChangeListener(new ChangeListener(){

			public void stateChanged(ChangeEvent e) {
				if(rdCostantsRate.isSelected()){
					txtConstantsRate.setEnabled(rdCostantsRate.isSelected());
				}
				
			}});
	}
	
	private void initMyIcon() {
		
		this.rdAim.setSelected(true);
		this.rdDymic.setSelected(false);
		
		this.rdMsg.setSelected(false);
		this.rdControl.setSelected(false);
		this.rdNone.setSelected(true);
	}
	
	//设置其他界面的参数
	private void initElement() throws BOSException, EASBizException {		

		if (paramInfo == null) {
			return;
		}
		
		String s = paramInfo.getBase_AimCost();
		if (s != null && !s.trim().equals("") && !s.trim().equals("none")) {
			
			if (s.trim().equals(RD_AIM)) {
				rdAim.setSelected(true);
				rdDymic.setSelected(false);
			}else{
				rdAim.setSelected(false);
				rdDymic.setSelected(true);
			}
		}
		
		s = paramInfo.getBase_Control();
		if (s != null && !s.trim().equals("") && !s.trim().equals("none")) {
			
			if (s.trim().equals(RD_MSG)) {
				rdMsg.setSelected(true);
				rdControl.setSelected(false);
				rdNone.setSelected(false);
			}else if (s.trim().equals(RD_CONTROL)) { 
				rdMsg.setSelected(false);
				rdControl.setSelected(true);
				rdNone.setSelected(false);
			}else{
				rdMsg.setSelected(false);
				rdControl.setSelected(false);
				rdNone.setSelected(true);
			}
		}
		
		s = paramInfo.getBase_ConSettType();
		if(s != null && !s.trim().equals("") && !s.trim().equalsIgnoreCase("true")){
			if(s.trim().equalsIgnoreCase("rdContractOverRate")){
				rdContractOverRate.setSelected(true);
//				rdCostantsRate.setSelected(false);
				txtConstantsRate.setEnabled(false);
			}else{
//				rdContractOverRate.setSelected(false);
				rdCostantsRate.setSelected(true);
			}
		}
		
		s = paramInfo.getBase_CostCenterConstRate();
		if(s != null && !s.trim().equals("") && !rdContractOverRate.isSelected()){
			txtConstantsRate.setText(s);
		}
	}

	private void initData() throws Exception {
		if (curOrgUnit == null) {
			curOrgUnit = SysContext.getSysContext().getCurrentCostUnit();
		}
	}
	

	public void otherParamSave() throws BOSException, EASBizException {

		save();
	}

	private boolean save() throws BOSException, EASBizException {
		
		HashMap prop = paramInfo.getProperties();
		storeField(prop);
		
		return true;
	}
	
	private void storeField(HashMap prop) throws EASBizException, BOSException {
		
		String str = null;
		
		if(rdContractOverRate.isSelected()){
			str = "rdContractOverRate";
		}else{
			str = "rdCostantsRate";
		}
		
		ParamItemInfo itemInfo = ((ParamItemInfo) prop.get(FDCConstants.FDC_PARAM_CONSETTTYPE));
		if(itemInfo != null){
			itemInfo.setValue(str);
			itemInfo.setIsControlSub(true);
			itemInfo.setIsModify(false);
			itemInfo.setOrgUnitID(curOrgUnit);
			ParamItemFactory.getRemoteInstance().submit(itemInfo);
		}
		
		if(txtConstantsRate.getNumberValue() != null && rdCostantsRate.isSelected() && txtConstantsRate.getText().trim().length()>0){
			str = txtConstantsRate.getNumberValue().toString();
		}else{
			str = "0.00";
		}
		
		itemInfo = (ParamItemInfo) prop.get(FDCConstants.FDC_PARAM_COSTCENTERCONSTRATE);
		if(itemInfo != null){
			itemInfo.setValue(str);
			itemInfo.setIsControlSub(true);
			itemInfo.setIsModify(false);
			itemInfo.setOrgUnitID(curOrgUnit);
			ParamItemFactory.getRemoteInstance().submit(itemInfo);
			txtConstantsRate.setText(str);
		}
		if(rdAim.isSelected()){
			str = RD_AIM;
		}else{
			str = RD_DYMIC;
		}
		
		itemInfo = ((ParamItemInfo) prop.get(FDCConstants.FDC_PARAM_OUTCOST));
		if(itemInfo!=null){
			itemInfo.setValue(str);
			itemInfo.setIsControlSub(false);
			itemInfo.setIsModify(false);
			itemInfo.setOrgUnitID(curOrgUnit);
	
			ParamItemFactory.getRemoteInstance().submit(itemInfo);
		}
		
		if(rdMsg.isSelected()){
			str = RD_MSG;
		}else if(rdControl.isSelected()){
			str = RD_CONTROL;
		}else{
			str = RD_NONE;
		}
		
		itemInfo = ((ParamItemInfo) prop.get(FDCConstants.FDC_PARAM_CONTROLTYPE));
		if(itemInfo!=null){
			itemInfo.setValue(str);
			itemInfo.setIsControlSub(false);
			itemInfo.setIsModify(false);
			itemInfo.setOrgUnitID(curOrgUnit);
	
			ParamItemFactory.getRemoteInstance().submit(itemInfo);
		}
	}
	
	public void sendOrgInfo(OrgType orgType, OrgUnitInfo orgUnit, OrgTreeInfo orgTree) {
		try {
			if (curOrgUnit == null) {
				curOrgUnit = SysContext.getSysContext().getCurrentCostUnit();
			}			
			setParams();			
			initElement();			
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			handUIExceptionAndAbort(e);
		}
	}
	

	/**
	 * 描述：给定参数
	 * 
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void setParams() throws BOSException, EASBizException {
	
	
		HashMap paramItem = ParamControlFactory.getRemoteInstance().getParamHashMap(curOrgUnit.getId().toString(),"com.kingdee.eas.fdc.contract.contract");

		paramInfo = new FDCParamInfo(paramItem);
	}
}
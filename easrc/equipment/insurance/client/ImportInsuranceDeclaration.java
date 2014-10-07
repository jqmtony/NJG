package com.kingdee.eas.port.equipment.insurance.client;

import java.util.Hashtable;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.port.equipment.insurance.InsuranceDeclarationStateE1Info;
import com.kingdee.eas.port.equipment.insurance.InsuranceDeclarationStateFactory;
import com.kingdee.eas.port.equipment.insurance.InsuranceDeclarationStateInfo;
import com.kingdee.eas.port.equipment.record.EquIdFactory;
import com.kingdee.eas.port.equipment.record.EquIdInfo;
import com.kingdee.eas.port.equipment.record.IEquId;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;

/**
 * 保险申报明细导入实现类
 * 根据设备档案号带出对应的设备信息
 * @author 石磊
 *
 */
public class ImportInsuranceDeclaration extends AbstractDataTransmission{

	protected ICoreBase getController(Context ctx)throws TaskExternalException {
		try 
		{
			return InsuranceDeclarationStateFactory.getLocalInstance(ctx);
		} 
		catch (BOSException e)
		{
			throw new TaskExternalException(e.getMessage(), e);
		}
	}

	public CoreBaseInfo transmit(Hashtable hsData, Context ctx)throws TaskExternalException {
		return null;
	}

	
	public void submit(CoreBaseInfo coreBaseInfo, Context ctx) throws TaskExternalException {
		InsuranceDeclarationStateInfo Info = (InsuranceDeclarationStateInfo) coreBaseInfo;
		
		try
		{
			IEquId IEquId = EquIdFactory.getLocalInstance(ctx);
			for (int i = 0; i < Info.getE1().size(); i++)
			{
				InsuranceDeclarationStateE1Info e1Info = Info.getE1().get(i);
				
				if(e1Info.getEquNumber()!=null)
				{
					EquIdInfo equInfo = IEquId.getEquIdInfo(new ObjectUuidPK(e1Info.getEquNumber().getId()));
					e1Info.setEquType(equInfo.getEqmType()!=null?equInfo.getEqmType().getName():"");
					e1Info.setEquName(equInfo.getName());
					e1Info.setUserEquNumber(equInfo.getInnerNumber());
					e1Info.setSpecModel(equInfo.getModel());
					e1Info.setTonnage(equInfo.getWeight());
					e1Info.setMakeUnit(equInfo.getMader());
					e1Info.setOriginalValue(equInfo.getAssetValue());
					e1Info.setPresentValue(equInfo.getNowAmount());
					e1Info.setFactoryUseDate(equInfo.getQyDate());
				}
			}
		} 
		catch (BOSException e) 
		{
			e.printStackTrace();
		} 
		catch (EASBizException e) 
		{
			e.printStackTrace();
		}
		
		super.submit(Info, ctx);
	}
	
}

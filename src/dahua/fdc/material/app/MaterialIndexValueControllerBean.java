package com.kingdee.eas.fdc.material.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.ArrayList;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.material.MaterialIndexValueInfo;
import com.kingdee.eas.fdc.material.MaterialIndexValueCollection;
import com.kingdee.eas.fdc.material.client.MaterialIndexValueBean;
import com.kingdee.eas.framework.app.CoreBaseControllerBean;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.tool.simplecode.New;

public class MaterialIndexValueControllerBean extends AbstractMaterialIndexValueControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.material.app.MaterialIndexValueControllerBean");

	/**
	 * @description		
	 * @author			πÀÚ‘		
	 * @createDate		2010-11-21
	 * @param	
	 * @return					
	 *	
	 * @version			EAS1.0
	 * @see						
	 */
	


	protected void _insertIndexValue(Context ctx, ArrayList params,
			IObjectPK materialInfoId) throws BOSException {
		for(int i=0;i<params.size();i++){
			
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			String sql = "insert into T_MTR_MaterialIndexValue (FValue,FMaterialID,FMaterialIndexID,FID) values (?,?,?,newBosId('6E5BD60C'));";
			builder.appendSql(sql);
			builder.addParam(((MaterialIndexValueBean)params.get(i)).getValue().toString().trim());
			builder.addParam(materialInfoId.toString().trim());
			builder.addParam(((MaterialIndexValueBean)params.get(i)).getId());
			String newSql = builder.getTestSql();
			newSql = newSql.substring(0,newSql.lastIndexOf(";"));
			builder.setPrepareStatementSql(newSql);
			builder.execute();
	}
	}
}
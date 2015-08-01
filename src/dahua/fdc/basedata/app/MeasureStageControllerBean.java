package com.kingdee.eas.fdc.basedata.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CostAccountFacadeFactory;

public class MeasureStageControllerBean extends AbstractMeasureStageControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.basedata.app.MeasureStageControllerBean");
    //��¼��־�ã���ִ���������
    protected boolean _disEnabled(Context ctx, IObjectPK ctPK)throws BOSException, EASBizException
    {
        return false;
    }
  //��¼��־�ã���ִ���������
    protected boolean _enabled(Context ctx, IObjectPK ctPK)throws BOSException, EASBizException
    {
        return false;
    }
    //������Ĭ�����ÿ�Ŀ����׶�ֵΪ1
    protected IObjectPK _addnew(Context ctx, IObjectValue model)
    		throws BOSException, EASBizException {
    	IObjectPK pk = super._addnew(ctx, model);
    	//���ô��Σ��������˴���
    	CostAccountFacadeFactory.getLocalInstance(ctx).updateAccountStage(null);
    	return pk;
    }
}
package com.kingdee.eas.port.pm.invest.investplan.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.EASBizException;

public class ProgrammingTemplateControllerBean extends AbstractProgrammingTemplateControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.programming.app.ProgrammingTemplateControllerBean");

	protected void _copy(Context ctx) throws BOSException {
		logger.assertLog(true, "���ƹ���");
	}
	
	protected void _checkNameBlank(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		//������������ʾ�����û���У��ʱ����ͨ���Ļ��������Ч��ȥ�����Ը��ǵ����෽����У����ǰ̨���
	}
	
	 protected void _checkNameDup(Context ctx , IObjectValue model) throws BOSException , EASBizException {
		//������������ʾ�����û���У��ʱ����ͨ���Ļ��������Ч��ȥ�����Ը��ǵ����෽����У����ǰ̨���
     }
}
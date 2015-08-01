package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.EASBizException;

/**
 * 拆分引入接口类
 * @author xiaohong_shi
 *
 */
public interface ISplitImporter {
	public IObjectValue importSplit() throws BOSException,EASBizException;
}

package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import java.util.Map;
import java.util.Date;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public interface INodeMeasure extends IFDCBill
{
    public NodeMeasureInfo getNodeMeasureInfo(IObjectPK pk) throws BOSException, EASBizException;
    public NodeMeasureInfo getNodeMeasureInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public NodeMeasureInfo getNodeMeasureInfo(String oql) throws BOSException, EASBizException;
    public NodeMeasureCollection getNodeMeasureCollection(EntityViewInfo view) throws BOSException;
    public NodeMeasureCollection getNodeMeasureCollection() throws BOSException;
    public NodeMeasureCollection getNodeMeasureCollection(String oql) throws BOSException;
    public Map fetchData(String contractId, String nodeMeasureId) throws BOSException, EASBizException;
    public Map fetchExecData(String contractId, Date startDate, Date endDate) throws BOSException, EASBizException;
}
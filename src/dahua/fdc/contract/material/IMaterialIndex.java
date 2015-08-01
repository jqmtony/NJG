package com.kingdee.eas.fdc.material;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.fdc.basedata.IFDCDataBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import java.util.List;

public interface IMaterialIndex extends IFDCDataBase
{
    public MaterialIndexInfo getMaterialIndexInfo(IObjectPK pk) throws BOSException, EASBizException;
    public MaterialIndexInfo getMaterialIndexInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public MaterialIndexInfo getMaterialIndexInfo(String oql) throws BOSException, EASBizException;
    public MaterialIndexCollection getMaterialIndexCollection(String oql) throws BOSException;
    public MaterialIndexCollection getMaterialIndexCollection(EntityViewInfo view) throws BOSException;
    public MaterialIndexCollection getMaterialIndexCollection() throws BOSException;
    public List getSQLString(String sql) throws BOSException, EASBizException;
}
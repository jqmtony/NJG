package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import java.util.Iterator;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ContextUtils;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.rpc.RPCException;

public class CurProjProductEntriesInfo extends AbstractCurProjProductEntriesInfo implements Serializable 
{
    public CurProjProductEntriesInfo()
    {
        super();
    }
    protected CurProjProductEntriesInfo(String pkField)
    {
        super(pkField);
    }
    
    /**
     * 保留原来的接口，从指标管理取数, 默认取动态成本
     * @deprecated 批量取指标时请不要使用此接口,可使用ProjectHelper内的相应方法 by sxhong 2008-04-08 09:34:22
     * @return
     */
    public CurProjProEntrApporDataCollection getCurProjProEntrApporData() {
    	return getCurProjProEntrApporData(ProjectStageEnum.DYNCOST);
    }
    /**
     * 保留原来的接口，从指标管理取数
     * @deprecated 批量取指标时请不要使用此接口,可使用ProjectHelper内的相应方法  by sxhong 2008-04-08 09:34:22
     * @return
     */
    public CurProjProEntrApporDataCollection getCurProjProEntrApporData(ProjectStageEnum projectStage) {
    	CurProjProEntrApporDataCollection coll = new CurProjProEntrApporDataCollection();
    	
    	if(getCurProject() == null || getProductType() == null) return coll;
    	
    	String projId = getCurProject().getId().toString();
    	String prodId = getProductType().getId().toString();
    	EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("projOrOrgID", projId));
		filter.getFilterItems().add(new FilterItemInfo("productType", prodId));
		filter.getFilterItems().add(new FilterItemInfo("projectStage", projectStage.getValue()));
		filter.getFilterItems().add(new FilterItemInfo("isLatestVer", Boolean.TRUE));
		view.setFilter(filter);
		view.getSelector().add("entries.apportionType.*");
		view.getSelector().add("entries.indexValue");
		
    	ProjectIndexDataCollection projectIndexDataCollection = null;
		try {
			projectIndexDataCollection = ProjectIndexDataFactory.getRemoteInstance().getProjectIndexDataCollection(view);
		}catch (RPCException ex){	//如果是在服务端调用，则要抛此异常，捕获异常并调用服务器端的方法
			// @AbortException
			Context context = ContextUtils.getContextFromSession();
			try {
				projectIndexDataCollection = ProjectIndexDataFactory.getLocalInstance(context).getProjectIndexDataCollection(view);
			} catch (BOSException e) {
				throw new RuntimeException(e);
			}
		} catch (BOSException e) {
			throw new RuntimeException(e);
		}
    	
    	if(projectIndexDataCollection == null || projectIndexDataCollection.size() == 0) return coll;
    	
    	ProjectIndexDataInfo idxInfo = projectIndexDataCollection.get(0);
    	ProjectIndexDataEntryCollection entries = idxInfo.getEntries();
    	
    	CurProjProEntrApporDataInfo adInfo = null;
    	for (Iterator iter = entries.iterator(); iter.hasNext();) {
    		ProjectIndexDataEntryInfo element = (ProjectIndexDataEntryInfo) iter.next();
    		
    		adInfo = new CurProjProEntrApporDataInfo();
    		
    		adInfo.setApportionType(element.getApportionType());
    		adInfo.setValue(element.getIndexValue());
    		
    		coll.add(adInfo);
		}
    	
    	return coll;
    }
}
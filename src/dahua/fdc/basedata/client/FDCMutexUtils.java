package com.kingdee.eas.fdc.basedata.client;

import java.util.Iterator;
import java.util.List;

import com.kingdee.eas.base.netctrl.IMutexServiceControl;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;

/**
 * ���ز����绥�⹤����
 * @author liupd
 *
 */
public class FDCMutexUtils {

	/**
     * 
     * �������������绥��
     * @param ui ��ǰUI
     * @param id ����ID
     * @author:liupd
     * ����ʱ�䣺2006-4-5 <p>
     */
    public static void requestMutex(CoreUI ui, String id) {
    	setMutex(ui, id);
    }
    
    /**
     * 
     * ���������뻥��
     * @param ui
     * @param ids ����ID����
     * @author:liupd
     * ����ʱ�䣺2006-4-5 <p>
     */
    public static void requestMutex(CoreUI ui, String[] ids) {
    	for (int i = 0; i < ids.length; i++) {
    		setMutex(ui, ids[i]);	
		}
    	
    }
    
    /**
     * 
     * ���������뻥��
     * @param ui
     * @param ids ����ID List
     * @author:liupd
     * ����ʱ�䣺2006-4-5 <p>
     */
    public static void requestMutex(CoreUI ui, List ids) {
    	for (Iterator iter = ids.iterator(); iter.hasNext();) {
			String id = (String) iter.next();
			setMutex(ui, id);
		}
    }
    
   
    /**
     * 
     * �������ͷŻ���
     * @param id ����Id
     * @author:liupd
     * ����ʱ�䣺2006-4-5 <p>
     */
    public static void releaseMutex(String id) {
    	IMutexServiceControl mutexServiceControl = FrameWorkClientUtils.createMutexServiceControl();
    	mutexServiceControl.releaseObjIDForUpdate(id);
    }
    
    /**
     * 
     * �������ͷŻ���
     * @param ids ����Id List
     * @author:liupd
     * ����ʱ�䣺2006-4-5 <p>
     */
    public static void releaseMutex(List ids) {
    	
    	for (Iterator iter = ids.iterator(); iter.hasNext();) {
			String id = (String) iter.next();
			releaseMutex(id);
		}
    }
    
    /**
     * 
     * �������ͷŻ���
     * @param ids ����Id����
     * @author:liupd
     * ����ʱ�䣺2006-4-5 <p>
     */
    public static void releaseMutex(String[] ids) {
    	for (int i = 0; i < ids.length; i++) {
			releaseMutex(ids[i]);
		}
    }
    
	/**
	 * �������ݻ���
	 * @param id
	 */
	public  static void setMutex(CoreUI ui,String id){
		//������󻥳�,����Ƿ����������˶���
		try {
//			String oprt=ui.getOprtState();
//			ui.setOprtState( "REMOVE" ) ;
			ui.pubFireVOChangeListener(id);
//			ui.setOprtState(oprt);
		} catch (Throwable ex) {
			ui.handUIException(ex);
			SysUtil.abort();
		}
	}
}

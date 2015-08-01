package com.kingdee.eas.fdc.material.client;

import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.basedata.master.material.MaterialGroupStandardTypeEnum;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.client.tree.DefaultLNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ILNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ITreeBuilder;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.framework.client.tree.LNTreeBuilder;
import com.kingdee.eas.framework.client.tree.TreeBuilderFactory;

/**
 * 
 * �����������������޹�˾��Ȩ����
 * ������������������
 * @author ������  2010-11-5  <p>
 *
 * �޸��ˣ�<p>
 * �޸�ʱ�䣺<p>
 * �޸�������<p>
 *
 * @version <EAS***>
 * @see <�����>
 */
public class BuilderTreeUtil {
	
	/**�����ڵ�����*/
	private String treeRootName;
	/**˽�й��췽��*/
	private BuilderTreeUtil(){};
	/**����*/
	private static BuilderTreeUtil builderTreeUtil = null;
	
	/**
	 * ����������ģʽ
	 * ����ʱ�䣺2010-11-5<p>
	 * 
	 * �޸��ˣ�<p>
	 * �޸�ʱ�䣺 <p>
	 * �޸������� <p>
	 *
	 * @see  
	*/
	public static synchronized BuilderTreeUtil getTreeUtilInstance(){
		/*
		 * ���δ��ʵ������ʵ����
		 * �����ֱ�ӷ���
		 */
		if (builderTreeUtil  == null) {
			return builderTreeUtil = new BuilderTreeUtil();
		}else
			return builderTreeUtil ;
	}
	
	/**
	 * ����������������Ϣ
	 * ����ʱ�䣺2010-11-5<p>
	 * 
	 * �޸��ˣ�<p>
	 * �޸�ʱ�䣺 <p>
	 * �޸������� <p>
	 *
	 *	@param KDTree tree ���ؼ�
	 *	@param ITreeBase treeBaseFactoryRemoteInstance �����ݵ�Զ�̵��ýӿ�
	 *	@param String treeRootName �����ڵ�����
	 * @see  
	*/
	public void buiderTree(KDTree tree,ITreeBase treeBaseFactoryRemoteInstance,String treeRootName) throws Exception{
		this.setTreeRootName(treeRootName);
		this.initTree(tree,treeBaseFactoryRemoteInstance);
	}
	
	
	/**
	 * ��������ʼ������Ϣ
	 * ����ʱ�䣺2010-11-5<p>
	 * 
	 * �޸��ˣ�<p>
	 * �޸�ʱ�䣺 <p>
	 * �޸������� <p>
	 * 
	 * @see  
	*/
	private void initTree(KDTree tree,ITreeBase treeBaseFactoryRemoteInstance) throws Exception{
		
		TreeSelectionListener treeSelectionListener = null;
    	ITreeBuilder treeBuilder = null;
    	KDTree treeMain = this.getMaterialTree(tree);
    	TreeSelectionListener [] listeners = treeMain.getTreeSelectionListeners();
    	/*
    	 * �������������������0�ͽ�����������Ϊ��1��
    	 * �����Ƴ��������ļ����¼�
    	 */
    	if(listeners.length > 0 ){
    		treeSelectionListener = listeners[0];
    		treeMain.removeTreeSelectionListener(treeSelectionListener);
    	}
    	//�õ���������
    	treeBuilder = TreeBuilderFactory.createTreeBuilder(this.getLNTreeNodeCtrl(treeBaseFactoryRemoteInstance),	this.getTreeInitialLevel(), this.getTreeExpandLevel(), this.getDefaultFilterForTree());
    	
    	/*
    	 * �����������ֲ�Ϊ��
    	 * �͵õ����ڵ��µ��ӽڵ�
    	 */
    	if (null != getRootName() && !"".equals(getRootName())) {
			KDTreeNode rootNode = new KDTreeNode(this.getRootObject());
			((DefaultTreeModel) treeMain.getModel()).setRoot(rootNode);
			
		} else {//������ʾ�κ�����
			((DefaultTreeModel) treeMain.getModel()).setRoot(null);
		}
    	//������
    	treeBuilder.buildTree(treeMain);
		treeMain.addTreeSelectionListener(treeSelectionListener);
		treeMain.setShowPopMenuDefaultItem(false);
	}

	
	
	/**
	 * ��������ø��ڵ�����
	 * ����ʱ�䣺2010-11-8<p>
	 * 
	 * �޸��ˣ�<p>
	 * �޸�ʱ�䣺 <p>
	 * �޸������� <p>
	 *
	 * @see  
	*/
	private String getRootName(){
	    	return this.getTreeRootName();
	 }
	
	 /**
	 * �������õ����ڵ�����
	 * ����ʱ�䣺2010-11-5<p>
	 * 
	 * �޸��ˣ�<p>
	 * �޸�ʱ�䣺 <p>
	 * �޸������� <p>
	 *
	 * @see  
	*/
	 public Object getRootObject(){
	    	return this.getRootName();
	 }
	  
    /**
     * ��������õ�ǰҳ��tree�ؼ�
     * ����ʱ�䣺2010-11-5<p>
     * 
     * �޸��ˣ�<p>
     * �޸�ʱ�䣺 <p>
     * �޸������� <p>
     *
     * @param tree�ؼ�
     * @see  
    */
    private KDTree getMaterialTree(KDTree tree){
    	return tree;
    }
    
    private ITreeBase getTreeInterface(ITreeBase treeBaseFactoryRemoteInstance) throws BOSException{
    	ITreeBase treeBase = null;
		treeBase = treeBaseFactoryRemoteInstance;
		return treeBase;
    }
    
    protected ILNTreeNodeCtrl getLNTreeNodeCtrl(ITreeBase treeBaseFactoryRemoteInstance) throws Exception {
		return new DefaultLNTreeNodeCtrl(this.getTreeInterface(treeBaseFactoryRemoteInstance));
	}
    
    protected int getTreeInitialLevel() {
		return TreeBuilderFactory.DEFAULT_INITIAL_LEVEL;
	}

	protected int getTreeExpandLevel() {
		return TreeBuilderFactory.DEFAULT_EXPAND_LEVEL;
	}
	
	private FilterInfo getDefaultFilterForTree() {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("groupStandard.standardType",new Integer(1)));
		return filter;
	}
	
	private String getTreeRootName() {
		return treeRootName;
	}

	private void setTreeRootName(String treeRootName) {
		this.treeRootName = treeRootName;
	}

	
}

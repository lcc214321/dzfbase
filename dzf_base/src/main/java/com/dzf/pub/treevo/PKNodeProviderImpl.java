package com.dzf.pub.treevo;

import com.dzf.pub.SuperVO;

class PKNodeProviderImpl implements INodeProvider {
	private IPKNodeProvider pkNodeProvider = null;

	public PKNodeProviderImpl(IPKNodeProvider pkNodeProviderImpl) {
		this.pkNodeProvider = pkNodeProviderImpl;
	}

	public SuperVO getTreeNode(Object userObj) {
		return this.pkNodeProvider.createTreeNode(userObj);
	}

	public Object getHandle(Object userObj) {
		return this.pkNodeProvider.getNodeId(userObj);
	}

	public Object getParentHandle(Object userObj) {
		return this.pkNodeProvider.getParentNodeId(userObj);
	}

	public SuperVO getOtherTreeNode() {
		return this.pkNodeProvider.getOtherTreeNode();
	}
}
package com.dzf.pub.treevo;

import com.dzf.pub.SuperVO;

public abstract interface IPKNodeProvider
{
  public abstract SuperVO createTreeNode(Object paramObject);

  public abstract Object getNodeId(Object paramObject);

  public abstract Object getParentNodeId(Object paramObject);

  public abstract SuperVO getOtherTreeNode();
}
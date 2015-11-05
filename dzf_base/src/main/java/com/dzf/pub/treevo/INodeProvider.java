package com.dzf.pub.treevo;


import com.dzf.pub.SuperVO;

public abstract interface INodeProvider
{
  public abstract SuperVO getTreeNode(Object paramObject);

  public abstract Object getHandle(Object paramObject);

  public abstract Object getParentHandle(Object paramObject);

  public abstract SuperVO getOtherTreeNode();
}
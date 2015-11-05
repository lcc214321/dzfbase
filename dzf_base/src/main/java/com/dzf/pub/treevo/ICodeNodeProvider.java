package com.dzf.pub.treevo;

import com.dzf.pub.SuperVO;

public abstract interface ICodeNodeProvider
{
  public abstract SuperVO createTreeNode(Object paramObject);

  public abstract Object getCodeValue(Object paramObject);

  public abstract String getCodeRule();

  public abstract String getCircularRule();

  public abstract SuperVO createDefaultTreeNodeForLoseNode(Object paramObject);

  public abstract SuperVO getOtherTreeNode();
}
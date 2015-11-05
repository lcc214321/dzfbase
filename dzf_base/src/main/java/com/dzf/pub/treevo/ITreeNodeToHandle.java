package com.dzf.pub.treevo;

import javax.swing.tree.DefaultMutableTreeNode;

public abstract interface ITreeNodeToHandle
{
  public abstract Object getHandleFromTreeNode(DefaultMutableTreeNode paramDefaultMutableTreeNode);
}
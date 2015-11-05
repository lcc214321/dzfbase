package com.dzf.dao.jdbc.framework.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache<K, V> extends LinkedHashMap<K, V> {

		private static final long serialVersionUID = 1L;

		private int lruSize = 500;

		public LRUCache(int initSize) {
			super(initSize, 1f, true);
			this.lruSize = initSize;
		}

		@Override
		protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
			if (size() > lruSize)
				return true;
			else
				return false;
		}
	}
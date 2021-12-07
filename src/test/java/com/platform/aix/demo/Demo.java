package com.platform.aix.demo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Demo {

	public static void main(String[] args) {
		int size = 2000000;
		
		List<Map<String, String>> list = new ArrayList<Map<String, String>>(size);
		for(int i=1;i<=size;i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("index", i+"");
			map.put("date", System.currentTimeMillis() + "");
			list.add(map);
		}
		System.out.println("造数据完成！");
		
		//分批
		int limit = 100000;
		
		Iterator<Map<String, String>> iterator = list.iterator();
		int i=0;//数组下标
		int temp = 1;//第一批开始
		int from = i;
		int to = Math.min(size, temp * limit);
		System.out.printf("from %d to %d%n", from, to);
		StringBuilder sb = new StringBuilder();
		while(iterator.hasNext()) {
			if(i==limit * temp) {
				//这批结束了,把StringBuilder里面的数据用掉再清空
				//System.out.println("模拟写入文件：" + sb.toString());
				sb.toString();
				sb = new StringBuilder();//清空
				
				temp++;//下一批
				from = i;
				to = Math.min(size, temp * limit);
				System.out.printf("from %d to %d%n", from, to);
			}
			//sb.append(iterator.next() + ",");
			Map<String, String> map = iterator.next();
			Set<String> set = map.keySet();
			int col = set.size();
			Iterator<String> it2 = set.iterator();
			for(int j=0;j<col;j++) {
				String key = it2.next();
				if("date".equals(key)) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					long mill = Long.valueOf(map.get(key));
					sb.append(sdf.format(new Date(mill)));
				}else {
					sb.append(map.get(key));
				}
				sb.append("|");
			}
			
			i++;
		}
		System.out.println(sb.toString());
	}

}

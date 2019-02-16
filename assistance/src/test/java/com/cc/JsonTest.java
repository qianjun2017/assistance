/**
 * 
 */
package com.cc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cc.common.tools.JsonTools;

/**
 * @author Administrator
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class JsonTest {

	@Test
	public void testJsonList(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roleId", 1);
		List<Long> authList = new ArrayList<Long>();
		authList.add(1l);
		authList.add(2l);
		map.put("authList", authList);
		String json = JsonTools.toJsonString(map);
		System.out.println(json);
	}
}

package com.hk.dream;
import com.hk.dream.onlyDream.autoCreateFile.AutoCreateFileModel;
import com.hk.dream.onlyDream.autoCreateFile.AutoCreateFileUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DreamApplicationTests {
	private MockMvc mvc;
	@Autowired
	private WebApplicationContext wac; // 注入WebApplicationContext
	@Before
	public void setUp() throws Exception {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}
	@Test
	public void contextLoads() throws Exception {
		RequestBuilder request = null;

		MvcResult result = mvc.perform(get("/dream/user/").content(""))
				.andExpect(status().isOk())// 模拟向testRest发送get请求
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
				.andReturn();// 返回执行请求的结果

		System.out.println("测试get:/dream/user/："+result.getResponse().getContentAsString());

		result = mvc.perform(post("/dream/user").param("dreamuUsername","OnlyDream").content(""))
				.andExpect(status().isOk())// 模拟向testRest发送get请求
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
				.andReturn();// 返回执行请求的结果

		System.out.println("测试post:/dream/user/："+result.getResponse().getContentAsString());
	}

	@Test
	public void test(){
		AutoCreateFileUtil autoCreateFileUtil = new AutoCreateFileUtil();
		AutoCreateFileModel autoCreateFileModel = new AutoCreateFileModel();
		autoCreateFileModel.setPathModel("src\\main\\java\\com\\hk\\dream\\model");
		autoCreateFileModel.setTableName("dream_town");
		autoCreateFileUtil.uploadMyBatisCreateFile(autoCreateFileModel);
	}

//


}

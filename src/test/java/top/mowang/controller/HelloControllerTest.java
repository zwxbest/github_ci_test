package top.mowang.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Spring-MVC-Demo
 * HelloController 测试
 * <p>
 * 加载 springMVC.xml，走完整的 DispatcherServlet + Thymeleaf 视图解析流程，
 * 相当于不用启动 Tomcat 就能发请求验证接口。
 *
 * @author : Xuan Li
 * @website : https://mowangblg.top
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:springMVC.xml")
public class HelloControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    /**
     * 访问 "/" 返回 index 视图，页面里带上访问 target 的链接
     */
    @Test
    public void index() throws Exception {
        MvcResult result = mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andReturn();

        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertTrue("首页应渲染出标题", content.contains("首页"));
        assertTrue("首页应包含跳转 target 的链接", content.contains("/target"));
    }

    /**
     * 访问 "/target" 返回 target 视图
     */
    @Test
    public void target() throws Exception {
        MvcResult result = mockMvc.perform(get("/target"))
                .andExpect(status().isOk())
                .andExpect(view().name("target"))
                .andReturn();

        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertTrue("目标页应渲染出标题", content.contains("目标页面"));
    }
}

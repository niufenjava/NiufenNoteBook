package io.niufen.springboot.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.niufen.common.constant.SysConstants;
import io.niufen.springboot.common.response.R;
import io.niufen.common.util.JsonUtil;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;

/**
 * @Description MockMvc API测试基础父类
 * @Author haijun.zhang@luckincoffee.com
 * @Date 2019-06-04 16:48:56
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootCommonApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class Base64ApiTest {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Base64ApiTest.class);
    @Autowired
    protected MockMvc mockMvc;

    public void assertSuccess(MockHttpServletResponse response) throws UnsupportedEncodingException {
        int status = response.getStatus();
        Assert.assertTrue(200 == status);
        String contentAsString = response.getContentAsString();
        R result = JSONObject.parseObject(contentAsString, R.class);
        Assert.assertEquals(R.CODE_SUCCESS,result.getCode());
    }

    /**
     * api 接口 post 请求方法。JSON 入参
     *
     * @param uri 请求路径
     * @param ao 请求 AO 对象
     */
    public void post(String uri, Object ao) {
        String aoJson = JSON.toJSONString(ao);
        LOGGER.error("请求URI：" + uri);
        LOGGER.error("请求内容：" + JsonUtil.format(aoJson));
        try {
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(SysConstants.AUTH_TOKEN,"123456")
                    .content(aoJson)
                    .accept(MediaType.APPLICATION_JSON))
                    .andReturn();
            MockHttpServletResponse response = mvcResult.getResponse();

            int status = response.getStatus();
            String contentAsString = response.getContentAsString();
            R result = JSONObject.parseObject(contentAsString, R.class);
            LOGGER.error("状态码：" + status);
            LOGGER.error("返回结果：" + JsonUtil.format(contentAsString));
            Assert.assertEquals(R.CODE_SUCCESS,result.getCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * api 接口 post 请求方法。JSON 入参
     *
     * @param uri 请求路径
     * @param ao 请求 AO 对象
     */
    public MockHttpServletResponse postTest(String uri, Object ao) {
        MockHttpServletResponse response = null;
        String aoJson = JSON.toJSONString(ao);
        LOGGER.error("请求URI：" + uri);
        LOGGER.error("请求内容：" + JsonUtil.format(aoJson));
        try {
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(SysConstants.AUTH_TOKEN,"123456")
                    .content(aoJson)
                    .accept(MediaType.APPLICATION_JSON))
                    .andReturn();
            response = mvcResult.getResponse();

            int status = response.getStatus();
            String contentAsString = response.getContentAsString();
            LOGGER.error("状态码：" + status);
            LOGGER.error("返回结果：" + JsonUtil.format(contentAsString));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }


    /**
     * api 接口 post 请求方法。PATH 入参
     * @param uri 请求路径，待路径参数
     */
    public void post(String uri) {
        LOGGER.error("请求URI：" + uri);
        try {
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                    .header(SysConstants.AUTH_TOKEN,"123456")
                    .accept(MediaType.APPLICATION_JSON))
                    .andReturn();
            MockHttpServletResponse response = mvcResult.getResponse();

            int status = response.getStatus();
            String contentAsString = response.getContentAsString();
            R result = JSONObject.parseObject(contentAsString, R.class);
            LOGGER.error("状态码：" + status);
            LOGGER.error("返回结果：" + JsonUtil.format(contentAsString));
            Assert.assertEquals(R.CODE_SUCCESS,result.getCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * api 接口 post 请求后下载excel 测试方法
     * @param uri 请求路径
     * @param ao AO对象
     * @param filename 下载本地磁盘后文件名
     */
    public void export(String uri, Object ao, String filename) {
        String aoJson = JSON.toJSONString(ao);
        LOGGER.error("请求URI：" + uri);
        LOGGER.error("请求内容：" + JsonUtil.format(aoJson));
        try {
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .header(SysConstants.AUTH_TOKEN,"123456")
                    .accept(MediaType.MULTIPART_FORM_DATA_VALUE))
                    .andReturn();
            MockHttpServletResponse response = mvcResult.getResponse();
            FileOutputStream file = new FileOutputStream(filename);
            file.write(response.getContentAsByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * API 接口 get 请求方法
     * @param uri
     */
    public void get(String uri) {
        LOGGER.error("请求URI：" + uri);
        try {
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                    .header(SysConstants.AUTH_TOKEN,"123456")
                    .accept(MediaType.APPLICATION_JSON))
                    .andReturn();
            MockHttpServletResponse response = mvcResult.getResponse();

            int status = response.getStatus();
            String contentAsString = response.getContentAsString();
            R result = JSONObject.parseObject(contentAsString, R.class);
            LOGGER.error("状态码：" + status);
            LOGGER.error("返回结果：" + JsonUtil.format(contentAsString));
            Assert.assertEquals(R.CODE_SUCCESS,result.getCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Mock 文件上传方法
     *
     * @param uri  请求路径
     * @param file 上传文件
     */
    public void upload(String uri, File file) {
        LOGGER.error("请求URI：" + uri);
        try {
            MockMultipartFile firstFile = new MockMultipartFile("attachment", file.getName(),
                    MediaType.TEXT_PLAIN_VALUE, new FileInputStream(file));

            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.fileUpload(uri)
                    .file(firstFile).header(SysConstants.AUTH_TOKEN,"123456")).andReturn();
            MockHttpServletResponse response = mvcResult.getResponse();
            int status = response.getStatus();
            String contentAsString = response.getContentAsString();
            R result = JSONObject.parseObject(contentAsString, R.class);
            LOGGER.error("状态码：" + status);
            LOGGER.error("返回结果：" + JsonUtil.format(contentAsString));
            Assert.assertEquals(R.CODE_SUCCESS,result.getCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Mock 文件上传方法
     *
     * @param uri  请求路径
     * @param file 上传文件
     */
    public void uploadSql(String uri, File file) {
        LOGGER.error("请求URI：" + uri);
        try {
            MockMultipartFile firstFile = new MockMultipartFile("file", file.getName(),
                    MediaType.TEXT_PLAIN_VALUE, new FileInputStream(file));

            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.fileUpload(uri)
                    .file(firstFile).header(SysConstants.AUTH_TOKEN,"123456")).andReturn();
            MockHttpServletResponse response = mvcResult.getResponse();
            int status = response.getStatus();
            String contentAsString = response.getContentAsString();
            R result = JSONObject.parseObject(contentAsString, R.class);
            LOGGER.error("状态码：" + status);
            LOGGER.error("返回结果：" + JsonUtil.format(contentAsString));
            Assert.assertEquals(R.CODE_SUCCESS,result.getCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

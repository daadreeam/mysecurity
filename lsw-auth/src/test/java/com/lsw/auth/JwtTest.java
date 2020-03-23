//package com.lsw.auth;
//
//import cn.hutool.json.JSONUtil;
//import com.lsw.auth.pojo.UserInfo;
//import com.lsw.auth.util.JwtUtil;
//import com.lsw.auth.util.RsaUtil;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.security.PrivateKey;
//import java.security.PublicKey;
//import java.util.LinkedHashMap;
//
///**
// * @Author: 98050
// * @Time: 2018-10-23 20:58
// * @Feature: JWT测试
// */
//public class JwtTest {
//
//    private static final String pubKeyPath = "/Users/daadreeam/Desktop/rsa.pub";
//
//    private static final String priKeyPath = "/Users/daadreeam/Desktop/rsa.pri";
//
//    private String token;
//
//    private PublicKey publicKey;
//
//    private PrivateKey privateKey;
//
////    @Test
//    public void testRsa() throws Exception {
//        RsaUtil.generateKey(pubKeyPath, priKeyPath, "234");
//    }
//
//    @Before
//    public void testGetRsa() throws Exception {
//        this.publicKey = RsaUtil.getPublicKey(pubKeyPath);
//        this.privateKey = RsaUtil.getPrivateKey(priKeyPath);
//    }
//
//    @Test
//    public void testGenerateToken() throws Exception {
//        LinkedHashMap<String, Object> jsonObject = new LinkedHashMap();
//        jsonObject.put("role","admin");
//        jsonObject.put("auth","/admin/do,/test/do,/test/do,/test/do,/test/do,/test/do,/test/do,/test/do,/test/do,/test/do,/test/do,/test/do,/test/do," +
//                "/test/do,/test/do,/test/do,/test/do,/test/do,/test/do,/test/do,/test/do,/test/do");
//        // 生成token
//        String token = JwtUtil.generateToken(new UserInfo(20L, "jack", jsonObject), privateKey, 1);
//        this.token = token;
//        System.out.println("token = " + token);
//    }
//
//    @Test
//    public void testParseToken() throws Exception {
//        String token = "eyJhbGciOiJSUzI1NiJ9.eyJhdXRoSnNvbiI6eyJyb2xlIjoiYWRtaW4iLCJhdXRoIjoiL2FkbWluL2RvLC90ZXN0L2RvLC90ZXN0L2RvLC90ZXN0L2RvLC90ZXN0L2RvLC90ZXN0L2RvLC90ZXN0L2RvLC90ZXN0L2RvLC90ZXN0L2RvLC90ZXN0L2RvLC90ZXN0L2RvLC90ZXN0L2RvLC90ZXN0L2RvLC90ZXN0L2RvLC90ZXN0L2RvLC90ZXN0L2RvLC90ZXN0L2RvLC90ZXN0L2RvLC90ZXN0L2RvLC90ZXN0L2RvLC90ZXN0L2RvLC90ZXN0L2RvIn0sImlkIjoyMCwidXNlcm5hbWUiOiJqYWNrIiwiZXhwIjoxNTg0MDI4OTA0fQ.LGNj-zPT9dCmXUAlxOjgnBudnKHa9-7WX3e1KPM4QDT9xG_tq-Y85bjA9T4NTffj-sT7VLT3Du8MW2SYWJKA_X9yV7UzRSz_ijl6Z_x3NNHRzyKOeeRstDiaZeD5tbgF-ncigkPp2Vy4RjOQKn-ralpHwZzfCILPcW2Cbyb4otg";
////        String token = this.token;
//        // 解析token
//        UserInfo user = JwtUtil.getInfoFromToken(token, publicKey);
//        System.out.println(JSONUtil.toJsonPrettyStr(user));
//    }
//}

package com.example;

<<<<<<< HEAD
import com.RabbitmqMain;
=======
>>>>>>> 4acc65a562a1aab4e954a9d027fd2918a7f403f7
import com.example.cachemanager.CacheTest;
import com.example.cipher.RsaDecoder;
import com.example.configuration.RabbitmqConfiguration;
import com.example.constant.MqConstant;
import com.example.constant.RsaConstant;
import com.example.consumer.MqConsumer;
import com.example.exception.DecryptBeyondLengthException;
<<<<<<< HEAD
import com.example.ffmpegUtil.FfmpegUtils;
import com.example.producer.MqProducer;
import com.example.redis.OperationForExchangeRouteKey;
import com.example.utils.ActivitiUtils;
=======
import com.example.producer.MqProducer;
import com.example.redis.OperationForExchangeRouteKey;
>>>>>>> 4acc65a562a1aab4e954a9d027fd2918a7f403f7
import com.example.utils.Formater;
import com.rabbitmq.client.Channel;
import com.service.dao.CrudForConsumer;
import com.service.dao.CrudForMessageQueue;
import com.service.dao.QueryForStudent;
import com.service.model.MessageTemp;
import com.service.model.PaperInformation;
import com.service.model.Student;
<<<<<<< HEAD
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentQuery;
=======
import org.junit.AfterClass;
>>>>>>> 4acc65a562a1aab4e954a9d027fd2918a7f403f7
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.JedisPool;

<<<<<<< HEAD
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
=======
import javax.crypto.*;
import java.io.*;
import java.nio.charset.Charset;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.TimeoutException;
>>>>>>> 4acc65a562a1aab4e954a9d027fd2918a7f403f7

/**
 * @author chenlei
 * @version 2.0
 * @date 2022/4/17 17:50
 */
<<<<<<< HEAD
//@SpringBootTest(classes = RabbitmqMain.class)
//@RunWith(SpringRunner.class)
public class rabbit_test {

=======
@SpringBootTest(classes = RabbitmqMain.class)
@RunWith(SpringRunner.class)
public class rabbit_test {
    @Test
    public void test() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException {

//        KeyPair keyPair = new KeyPair(new PublicKey() {
//            @Override
//            public String getAlgorithm() {
//                return "RSA";
//            }
//
//            @Override
//            public String getFormat() {
//                return "X.509";
//            }
//
//            @Override
//            public byte[] getEncoded() {
//                return Base64.getDecoder().decode(RsaConstant.pubkey);
//            }
//        }, new PrivateKey() {
//            @Override
//            public String getAlgorithm() {
//                return "RSA";
//            }
//
//            @Override
//            public String getFormat() {
//                return "PKCS#8";
//            }
//
//            @Override
//            public byte[] getEncoded() {
//                return Base64.getDecoder().decode(RsaConstant.prikey);
//            }
//        });
//
        byte[] pubkey = Base64.getDecoder().decode(RsaConstant.pubkey);
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(pubkey);
        PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(x509EncodedKeySpec);

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE,publicKey);
        byte[] cypher = cipher.doFinal("guest".getBytes(Charset.defaultCharset()));
        String miwen = new String(Base64.getEncoder().encode(cypher), Charset.defaultCharset());

        System.out.println("密文: " + miwen);

        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(RsaConstant.prikey));
        PrivateKey privateKey = KeyFactory.getInstance("RSA").generatePrivate(pkcs8EncodedKeySpec);
        byte[] bytes = Base64.getDecoder().decode("MaTKEh99H0QxDMHyE061guwSGdt9KplHA1BAbGWNSSDkXi1/MnKtT5e9h/cog1WI5lC6H9qQx5OfuU5+9TQ9cjpvlO+enE8jisDIt//xmX5/m4AI2JR01FhcI9sGgkBvIjGj5QorQSjFLhU4iihGYfMCgbB9YT07KeGUTnj45uA=");

        System.out.println("私钥长度 ; "+privateKey.getEncoded().length);
        Cipher cipher1 = Cipher.getInstance("RSA");
        cipher1.init(Cipher.DECRYPT_MODE,privateKey);
        byte[] minwen = cipher1.doFinal(bytes);
        System.out.println("铭文: "+new String(minwen));
    }
>>>>>>> 4acc65a562a1aab4e954a9d027fd2918a7f403f7

    private RabbitmqConfiguration rabbitmqConfiguration;

    private RsaDecoder rsaDecoder;

    private MqConsumer mqConsumer;

    private MqProducer mqProducer;

    private CacheTest cacheTest;

    private RedisTemplate<String, Object> redisTemplate;

    private JedisPool jedisPool;

    private QueryForStudent queryForStudent;

    private CrudForMessageQueue crudForMessageQueue;

    private CrudForConsumer crudForConsumer;

    private OperationForExchangeRouteKey operationForExchangeRouteKey;

    public OperationForExchangeRouteKey getOperationForExchangeRouteKey() {
        return operationForExchangeRouteKey;
    }

    @Autowired
    public void setOperationForExchangeRouteKey(OperationForExchangeRouteKey operationForExchangeRouteKey) {
        this.operationForExchangeRouteKey = operationForExchangeRouteKey;
    }

    public QueryForStudent getQueryForStudent() {
        return queryForStudent;
    }

    @Autowired(required = false)
    public void setQueryForStudent(QueryForStudent queryForStudent) {
        this.queryForStudent = queryForStudent;
    }

    @Autowired
    public void setRabbitmqConfiguration(RabbitmqConfiguration rabbitmqConfiguration) {
        this.rabbitmqConfiguration = rabbitmqConfiguration;
    }

    @Autowired
    public void setRsaDecoder(RsaDecoder rsaDecoder) {
        this.rsaDecoder = rsaDecoder;
    }

    public RabbitmqConfiguration getRabbitmqConfiguration() {
        return rabbitmqConfiguration;
    }

    public RsaDecoder getRsaDecoder() {
        return rsaDecoder;
    }

    public MqConsumer getMqConsumer() {
        return mqConsumer;
    }

    @Autowired
    public void setMqConsumer(MqConsumer mqConsumer) {
        this.mqConsumer = mqConsumer;
    }

    public MqProducer getMqProducer() {
        return mqProducer;
    }

    @Autowired
    public void setMqProducer(MqProducer mqProducer) {
        this.mqProducer = mqProducer;
    }

    public CacheTest getCacheTest() {
        return cacheTest;
    }

    @Autowired
    public void setCacheTest(CacheTest cacheTest) {
        this.cacheTest = cacheTest;
    }

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    @Autowired
    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public CrudForMessageQueue getCrudForMessageQueue() {
        return crudForMessageQueue;
    }

    @Autowired(required = false)
    public void setCrudForMessageQueue(CrudForMessageQueue crudForMessageQueue) {
        this.crudForMessageQueue = crudForMessageQueue;
    }

    public CrudForConsumer getCrudForConsumer() {
        return crudForConsumer;
    }

    @Autowired(required = false)
    public void setCrudForConsumer(CrudForConsumer crudForConsumer) {
        this.crudForConsumer = crudForConsumer;
    }
<<<<<<< HEAD


    @Test
    public void test() throws InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException {
        byte[] pubkey = Base64.getDecoder().decode(RsaConstant.pubkey);
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(pubkey);
        PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(x509EncodedKeySpec);

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE,publicKey);
        byte[] cypher = cipher.doFinal("guest".getBytes(Charset.defaultCharset()));
        String miwen = new String(Base64.getEncoder().encode(cypher), Charset.defaultCharset());

        System.out.println("密文: " + miwen);

        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(RsaConstant.prikey));
        PrivateKey privateKey = KeyFactory.getInstance("RSA").generatePrivate(pkcs8EncodedKeySpec);
        byte[] bytes = Base64.getDecoder().decode("MaTKEh99H0QxDMHyE061guwSGdt9KplHA1BAbGWNSSDkXi1/MnKtT5e9h/cog1WI5lC6H9qQx5OfuU5+9TQ9cjpvlO+enE8jisDIt//xmX5/m4AI2JR01FhcI9sGgkBvIjGj5QorQSjFLhU4iihGYfMCgbB9YT07KeGUTnj45uA=");

        System.out.println("private key length ; "+privateKey.getEncoded().length);
        Cipher cipher1 = Cipher.getInstance("RSA");
        cipher1.init(Cipher.DECRYPT_MODE,privateKey);
        byte[] minwen = cipher1.doFinal(bytes);
        System.out.println("明文: "+new String(minwen));
    }

    @Test
=======

    @Test
>>>>>>> 4acc65a562a1aab4e954a9d027fd2918a7f403f7
    public void test3() {
        rabbitmqConfiguration.getConnectionFactory();
    }



    @Test
    public void test2() throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, DecryptBeyondLengthException, BadPaddingException, InvalidKeyException, InvalidKeySpecException {
        InputStream inputStream = new FileInputStream(new File("C:\\Users\\Lenovo\\Desktop\\downloadVideo\\victory.mp3"));
        byte[] buff = new byte[RsaDecoder.MAX_ENCRYPT_LENGTH];
        byte[] de;
        int len;
        OutputStream outputStream = new FileOutputStream(new File("C:\\Users\\Lenovo\\Desktop\\downloadVideo\\victoryRsa.mp3"));

        float current = 0;
<<<<<<< HEAD
        System.out.println("???");
=======
        System.out.println("加密中");
>>>>>>> 4acc65a562a1aab4e954a9d027fd2918a7f403f7
        int total = inputStream.available();
        while ((len = inputStream.read(buff)) != -1) {
            de = rsaDecoder.enCodeFiles(buff);
            outputStream.write(de,0,de.length);
            current += len;
            System.out.println(current/total*100+"%");
        }

<<<<<<< HEAD
        System.out.println("????");
        outputStream.close();
        inputStream.close();
        System.out.println("????");
    }


    @Test
    // 解密
=======
        System.out.println("加密完成");
        outputStream.close();
        inputStream.close();
        System.out.println("打包完成");
    }

    // 注意加密后的字节和源字节的长度不一样, rsa算法会有填充
    @Test
>>>>>>> 4acc65a562a1aab4e954a9d027fd2918a7f403f7
    public void test4() throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, DecryptBeyondLengthException, BadPaddingException, InvalidKeyException, InvalidKeySpecException {
        InputStream inputStream = new FileInputStream(new File("C:\\Users\\Lenovo\\Desktop\\downloadVideo\\victoryRsa.mp3"));
        byte[] buff = new byte[RsaDecoder.MAX_DECRYPT_LENGTH];
        int len;
        float current = 0;
        OutputStream outputStream = new FileOutputStream(new File("C:\\Users\\Lenovo\\Desktop\\downloadVideo\\victoryOrigin.mp3"));
        int total = inputStream.available();
        byte[] de;
<<<<<<< HEAD
=======
        System.out.println("解密中");
>>>>>>> 4acc65a562a1aab4e954a9d027fd2918a7f403f7
        while ((len = inputStream.read(buff)) != -1) {
            de = rsaDecoder.deCodeFiles(buff);
            outputStream.write(de,0,de.length);
            current += len;
            System.out.println(current/total*100+"%");
            buff = new byte[RsaDecoder.MAX_DECRYPT_LENGTH];
        }
<<<<<<< HEAD
        outputStream.close();
        inputStream.close();
    }

    // ??
    @Test
    public void test5() throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException, SignatureException {
=======

        System.out.println("解密完成");
        outputStream.close();
        inputStream.close();
        System.out.println("打包完成");
    }

    // 验签
    @Test
    public void test5() throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException {
>>>>>>> 4acc65a562a1aab4e954a9d027fd2918a7f403f7
        Signature signature = Signature.getInstance("SHA1WithRSA");
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(RsaConstant.prikey));
        PrivateKey privateKey = KeyFactory.getInstance("RSA").generatePrivate(pkcs8EncodedKeySpec);

        signature.initSign(privateKey);
        signature.update("chenlei".getBytes(Charset.defaultCharset()));
        byte[] passFlag = signature.sign();
        System.out.println(new String(Base64.getEncoder().encode(passFlag), Charset.defaultCharset()));

        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(RsaConstant.pubkey));
        PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(x509EncodedKeySpec);

        Signature signature1 = Signature.getInstance("SHA1WithRSA");
        signature1.initVerify(publicKey);
        signature1.update("chenlei1".getBytes(Charset.defaultCharset()));
<<<<<<< HEAD
        System.out.println("校验是否通过: "+signature1.verify(passFlag));
=======
        System.out.println("验签是否通过: "+signature1.verify(passFlag));
>>>>>>> 4acc65a562a1aab4e954a9d027fd2918a7f403f7
    }

    @Test
    public void test6() throws Exception {
<<<<<<< HEAD
=======
//        mqProducer.produce("发送消息");
>>>>>>> 4acc65a562a1aab4e954a9d027fd2918a7f403f7
        mqConsumer.consume();
    }

    @Test
    public void test7() {
       Student student = new Student();
       student.setStuId("101");
       student.setSexType("male");
       student.setPassPort("123456");
       redisTemplate.opsForValue().set("hello", student);

    }

<<<<<<< HEAD
    // ??mybatis xml??????crud
    @Test
    public void test8() throws IOException {
        List<PaperInformation> paperInformations = queryForStudent.queryAllInformation();
=======
    // 测试mybatis xml配置文件实现crud
    @Test
    public void test8() throws IOException {
        List<PaperInformation> paperInformations = queryForStudent.queryAllInformation();
        System.out.println("写入图片");
>>>>>>> 4acc65a562a1aab4e954a9d027fd2918a7f403f7
        int len;
        ByteArrayInputStream inputStream = null;
        byte[] buf = new byte[1024];
        for (PaperInformation paperInformation : paperInformations) {
            OutputStream outputStream = new FileOutputStream(new File("C:\\Users\\Lenovo\\Desktop\\photo\\" + UUID.randomUUID() + ".jpg"));
            inputStream = new ByteArrayInputStream(paperInformation.getPaperResource());
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            System.out.println("success!");
            inputStream.close();
            outputStream.close();
        }
    }

    @Test
    public void test9() {
        MessageTemp messageTemp = null;
        for (int i = 0; i<200; i++) {
            messageTemp = new MessageTemp();
            messageTemp.setActiveTime(Formater.transferDateFormat());
            messageTemp.setMessageContent(UUID.randomUUID().toString());
            crudForMessageQueue.insertInformation(messageTemp);
<<<<<<< HEAD
=======
            System.out.println("提交完成");
>>>>>>> 4acc65a562a1aab4e954a9d027fd2918a7f403f7
        }
    }

    @Test
    public void test10() {
        System.out.println(crudForMessageQueue.queryAllInformation());
    }

<<<<<<< HEAD
//    @AfterClass
//    public static void pause() {
//        Scanner scanner = new Scanner(System.in);
//        String s = scanner.nextLine();
//    }
=======
    @AfterClass
    public static void pause() {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
    }
>>>>>>> 4acc65a562a1aab4e954a9d027fd2918a7f403f7

    @Test
    public void test11() throws Exception {
        Channel channel = getRabbitmqConfiguration().getChannel();
        channel.basicPublish(MqConstant.EXCHANGE_ONE, "MqConstant.routekey", true, null, "message".getBytes(Charset.defaultCharset()));
    }

    @Test
    public void test12() {
        int size = getRedisTemplate().opsForList().size(MqConstant.EXCHANGE_ONE).intValue();
        boolean flag = false;
        for (int i = 0; i < size; i++) {
            System.out.println(getRedisTemplate().opsForList().index(MqConstant.EXCHANGE_ONE, i).equals(MqConstant.EXCHANGE_ROUTE_ABANDON));
        }
//        System.out.println(getRedisTemplate().opsForList().index(MqConstant.EXCHANGE_ONE, 10));
<<<<<<< HEAD
    }

    @Test
    public void test13() throws IOException {
        System.out.println(FfmpegUtils.transform("C:\\Users\\Lenovo\\Desktop\\downloadVideo\\1.mp4","C:\\Users\\Lenovo\\Desktop\\downloadVideo\\dest\\test.h264","h264"));
    }

    private ActivitiUtils activitiUtils;

    @Test
    public void test14() {
//        ProcessEngine engine = ActivitiUtils.getProcessEngine();
//        System.out.println("get engine");
//
//        Map<String, Object> map = new HashMap<>();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        String date = simpleDateFormat.format(new Date());
//
//        ActivitiUtils.startProcessInstance(map, "orderOfBuyGoods");
        ActivitiUtils.executeNode("orderOfBuyGoods");
=======
>>>>>>> 4acc65a562a1aab4e954a9d027fd2918a7f403f7
    }
}

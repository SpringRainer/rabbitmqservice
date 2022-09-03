package com.example.ffmpegUtil;

import java.io.IOException;

/**
 * @author 陈磊
 * @version 2.0
 * @date 2022/8/6 20:58
 */
public class FfmpegUtils {

    // 转换视频文件格式
    public static boolean transform(String filepath, String destfilepath, String changedFormat) throws IOException {
        Runtime cmd = Runtime.getRuntime();
        String command = "ffmpeg -i "+filepath+" -c:v "+changedFormat+" -c:a copy -y "+destfilepath;
//        Process result = cmd.exec(command);
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(result.getInputStream()));
//        String s = null;
//        StringBuffer stringBuffer = new StringBuffer();
//
//        while ((s = bufferedReader.readLine()) != null) {
//            stringBuffer.append(s).append('\n');
//        }

        System.out.println("执行命令: " + command);
//        return stringBuffer;
        return false;
    }
}

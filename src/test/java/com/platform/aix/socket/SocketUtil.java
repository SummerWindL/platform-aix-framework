package com.platform.aix.socket;

import com.platform.common.util.DateUtil;
import com.platform.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.UUID;


/**
 * @author lixiang
 * @version 1.0
 * @date 2019/6/26 8:59
 * @description  socket 定长报文头处理工具
 */
public class SocketUtil {
	private static Logger logger = LoggerFactory.getLogger(SocketUtil.class);
    /**
	 * 计算xml报文长度
	 * @param sendMsg xml报文字符串
	 * @param charset 报文编码
	 * @return
	 */
    public static int getMsgHead(String sendMsg,String charset){
    	int plength = 0;
        try {
	        byte[] sour = sendMsg.getBytes(charset);
	        plength = sour.length;
	        
        } catch (Exception e) {
            e.printStackTrace();
            return plength;
        }
        return plength;
    }

	public static String getGlobalSeq(String reqsysId){

		//拼装长度信息 不足4位，右补0
		reqsysId=buzu(reqsysId,"0",4,1);

		String gloSeqNo="G" + reqsysId
				+ DateUtil.format(new Date(), "yyyyMMdd")
				+ getXulie(8,0)
				+"00";

		return gloSeqNo;
	}

	/**
	 * 生成序列
	 * @param size 长度
	 * @param flag 0随机数 1 UUID  9数据库序列数
	 * @return
	 */
	public static String getXulie(int size,int flag){
		if(flag==0){
			return DateUtil.getDateRandom(size);
		}
		else if(flag==1){   //UUID标准格式：xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxx（8-4-4-4-12）
			return UUID.randomUUID().toString();
		}
		else{ //TODO:序列数从数据库中取值完善
			return DateUtil.getDateRandom(size);
		}
	}

	/**
	 * 补足字符数据
	 * @param str 处理字符
	 * @param buzu 填充字符，长度为1，"0" " " "*"
	 * @param totalsize 返回的字符串总长度
	 * @param flag ==0左补 !=0右补
	 * @return
	 */
	public static String buzu(String str,String buzu,int totalsize,int flag){

		if(StringUtil.isEmpty(str)|| StringUtil.isEmpty(buzu)){
			str="";
			buzu="*";//错误提醒
		}
		if(str.length()>totalsize){
			str="";
			buzu="*";//错误提醒
		}
		if(buzu.length()>1){
			buzu=buzu.substring(0,1);
		}

		//拼装长度信息 不足totalsize位，flag补0
		if (str.length() < totalsize) {
			int s = totalsize - str.length();
			for (int i = 0; i < s; i ++) {
				if(flag==0){ //左补
					str = buzu+str;
				}
				else{
					str = str+buzu;
				}
			}
		}

		return str;
	}
    

}

package io.niufen.common.core.text;

import lombok.extern.slf4j.Slf4j;

/**
 * ASCII字符对应的字符串缓存
 * ASCII（American Standard Code for Information Interchange，美国信息交换标准代码）
 * 是基于拉丁字母的一套电脑编码系统，主要用于显示现代英语和其他西欧语言。
 * 它是现今最通用的单字节编码系统，并等同于国际标准ISO/IEC 646。
 *
 * 产生原因：
 * 		在计算机中，所有的数据在存储和运算时都要使用二进制数表示（因为计算机用高电平和低电平分别表示1和0）。
 * 		具体用哪些二进制数字表示哪个符号，当然每个人都可以约定自己的一套（这就叫编码），而大家如果要想互相通信而不造成混乱，
 * 		那么大家就必须使用相同的编码规则，于是美国有关的标准化组织就出台了ASCII编码，统一规定了常用符号用哪些二进制数来表示。
 * https://baike.baidu.com/item/ASCII/309296
 *
 * 表述方式：
 * ASCII 码使用指定的7 位或8 位二进制数组合来表示128 或256 种可能的字符。
 * 标准ASCII 码也叫基础ASCII码，使用7 位二进制数（剩下的1位二进制为0）来表示所有的大写和小写字母，数字0 到9、标点符号， 以及在美式英语中使用的特殊控制字符。
 *
 * 其中：
 *		0～31及127(共33个)是控制字符或通信专用字符（其余为可显示字符），如控制符：LF（换行）、CR（回车）、FF（换页）、DEL（删除）、BS（退格)、BEL（响铃）等；
 *		通信专用字符：SOH（文头）、EOT（文尾）、ACK（确认）等；ASCII值为8、9、10 和13 分别转换为退格、制表、换行和回车字符。
 *		它们并没有特定的图形显示，但会依不同的应用程序，而对文本显示有不同的影响。
 *
 * 		32～126(共95个)是字符(32是空格），其中48～57为0到9十个阿拉伯数字。
 *
 * 		65～90为26个大写英文字母，
 *
 * 		97～122号为26个小写英文字母，其余为一些标点符号、运算符号等。
 *
 * 		同时还要注意，在标准ASCII中，其最高位(b7)用作奇偶校验位。
 * 		所谓奇偶校验，是指在代码传送过程中用来检验是否出现错误的一种方法，一般分奇校验和偶校验两种。
 * 		奇校验规定：正确的代码一个字节中1的个数必须是奇数，若非奇数，则在最高位b7添1；
 * 		偶校验规定：正确的代码一个字节中1的个数必须是偶数，若非偶数，则在最高位b7添1。
 *
 * 后128个称为扩展ASCII码。许多基于x86的系统都支持使用扩展（或“高”）ASCII。扩展ASCII 码允许将每个字符的第8 位用于确定附加的128 个特殊符号字符、外来语字母和图形符号。 [4]
 *
 * @author niufen
 */
@Slf4j
public class ASCIIStrCache {

	/**
	 * 标准ASCII码有 128个，所以设置缓存数组的长度为 128
	 * 并且不能被串改，设置成私有的
	 */
	private static final int ASCII_LENGTH = 128;

	/**
	 * ASCII 字符串缓存数组
	 */
	private static final String[] ASCII_CACHE = new String[ASCII_LENGTH];

	/*
	 * 静态代码块儿，类加载时进行初始化工作；
	 * 程序启动后，不允许在进行修改
	 */
	static {
		for (char c = 0; c < ASCII_LENGTH; c++) {
			ASCII_CACHE[c] = String.valueOf(c);
		}
	}

	/**
	 * 字符转为字符串<br>
	 * 如果为ASCII字符，使用缓存
	 * 如果不是ASCII字符，使用 String.valueOf(c) 进行转换
	 *
	 * @param c 字符
	 * @return 字符串
	 */
	public static String toString(char c) {
		return c < ASCII_LENGTH ? ASCII_CACHE[c] : String.valueOf(c);
	}

	/**
	 * 仅仅为了测试提供的方法
	 */
	public static void print(){
		int i = 0;
		for (String s : ASCII_CACHE) {
			log.debug("ASCII 十进制码:{}, 对应字符：{}",i,s);
			i++;
		}
	}
}

package io.niufen.springboot.mail.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * MQ 发送邮件DTO
 * @author haijun.zhang
 * @date 2020/5/21
 * @time 16:18
 */
@Data
public class SendMailDTO implements Serializable {
    private static final long serialVersionUID = 4296502819577081096L;

    private String toMailAddr;

    private String ccMailAddr;

    private List<String> toMailAddrList;

    private List<String> ccMailAddrList;

    private String title;

    private String content;

    private String attachmentUrl;

    private String imageUrl;

    private List<String> attachmentUrlList;

    private List<String> imageUrlList;
}

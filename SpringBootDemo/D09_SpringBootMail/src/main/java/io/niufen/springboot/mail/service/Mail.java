package io.niufen.springboot.mail.service;

/**
 * Created by summer on 2017/5/4.
 */
public interface Mail {

    /**
     * 发送简单邮件
     * @param to 发给谁
     * @param subject 标题
     * @param content 内容
     */
    void sendSimpleMail(String to, String subject, String content);


    /**
     * 发送HTML邮件
     * @param to 发给谁
     * @param subject 标题
     * @param content 内容
     */
    void sendHtmlMail(String to, String subject, String content);

    /**
     * 发送带附件的邮件
     * @param to 发给谁
     * @param subject 标题
     * @param content 内容
     * @param filePath 附件路径
     */
    void sendAttachmentsMail(String to, String subject, String content, String filePath);

    /**
     * 发送在线链接邮件
     * @param to 发给谁
     * @param subject 标题
     * @param content 内容
     * @param rscPath rsc路径
     * @param rscId rscId
     */
    void sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId);

}

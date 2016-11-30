package com.repository.web.message;

import com.repository.base.BaseController;
import com.repository.model.MessageResponse;
import com.repository.model.SimpleResponse;
import com.repository.service.MessageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import static com.repository.Constants.HTML_MESSAGE_LIST;
import static com.repository.Constants.HTML_MESSAGE_NEWMESSAGE;
import static com.repository.Constants.URL_MESSAGE;
import static com.repository.Constants.URL_MESSAGE_DELETWHITID_AJAX;
import static com.repository.Constants.URL_MESSAGE_FINDMESSAGE_BY_ID_AJAX;
import static com.repository.Constants.URL_MESSAGE_FIND_WRANTYPE_AJAX;
import static com.repository.Constants.URL_MESSAGE_NEW;
import static com.repository.Constants.URL_MESSAGE_READWHITID_AJAX;
import static com.repository.Constants.URL_MESSAGE_SEND_AJAX;
import static com.repository.Constants.URL_MESSAGE_NEW;

@Controller
public class MessageController extends BaseController {


    @RequestMapping(URL_MESSAGE)
    public String message() {
        return HTML_MESSAGE_LIST;
    }

    @RequestMapping(URL_MESSAGE_NEW)
    public String newmessage() {
        return HTML_MESSAGE_NEWMESSAGE;
    }

    @Autowired
    MessageService messageService;

    /**
     * 发送一条消息，参数/message/new中表单中的值
     */
    @RequestMapping(value = URL_MESSAGE_SEND_AJAX, method = RequestMethod.POST)
    @ResponseBody
    public MessageResponse send(MessageForm messageForm, Principal principal) {
        messageForm.setSend_ID(principal.getName());
        if (messageService.send(messageForm)) {
            return MessageResponse.success();
        } else return MessageResponse.error();
    }

    /**
     * 获取d当前用户收到的消息
     */
    @RequestMapping(URL_MESSAGE_FINDMESSAGE_BY_ID_AJAX)
    @ResponseBody
    public SimpleResponse findmsg(Principal principal) {
        SimpleResponse simpleResponse = new SimpleResponse();
        simpleResponse.setContent(messageService.findMessage(principal.getName()));
        return simpleResponse;
    }

    /**
     * 获取提醒消息，库存提醒，返回为message的list
     */
    @RequestMapping(URL_MESSAGE_FIND_WRANTYPE_AJAX)
    @ResponseBody
    public SimpleResponse wran() {
        return SimpleResponse.withObject(messageService.findWranMessage());
    }

    /**
     * 把信息设为已读，参数为消息的id
     */
    @RequestMapping(URL_MESSAGE_READWHITID_AJAX)
    @ResponseBody
    public MessageResponse read(
            @RequestParam(name = "messageID", required = false, defaultValue = "") String msgId) {
        if (msgId != null && !msgId.trim().equals("")) {
            messageService.read(msgId);
            return MessageResponse.success();
        } else {
            return MessageResponse.error("消息id为空");
        }

    }

    /**
     * 删除一条信息，参数为消息的id
     */
    @RequestMapping(URL_MESSAGE_DELETWHITID_AJAX)
    @ResponseBody
    public MessageResponse delete(@RequestParam(name = "messageID", required = false, defaultValue = "") String msgId) {
        if (msgId != null && !msgId.trim().equals("")) {
            messageService.read(msgId);
            return MessageResponse.success();
        } else {
            return MessageResponse.error("消息id为空");
        }
    }
}

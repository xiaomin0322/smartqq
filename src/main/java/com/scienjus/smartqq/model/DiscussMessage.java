package com.scienjus.smartqq.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * 讨论组消息
 * @author ScienJus
 * @date 15/12/19.
 */
@Data
public class DiscussMessage {

    public long getDiscussId() {
		return discussId;
	}

	public void setDiscussId(long discussId) {
		this.discussId = discussId;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	private long discussId;

    private long time;

    private String content;

    private long userId;

    private Font font;

    public DiscussMessage(JSONObject json) {
        JSONArray content = json.getJSONArray("content");
        this.font = content.getJSONArray(0).getObject(1, Font.class);
        this.content = content.getString(1);
        this.time = json.getLongValue("time");
        this.discussId = json.getLongValue("did");
        this.userId = json.getLongValue("send_uin");
    }

}

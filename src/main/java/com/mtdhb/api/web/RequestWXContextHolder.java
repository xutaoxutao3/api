package com.mtdhb.api.web;

import org.springframework.core.NamedThreadLocal;

import com.mtdhb.api.dto.UserWXDTO;

/**
 * @author i@huangdenghe.com
 * @date 2018/04/22
 */
public abstract class RequestWXContextHolder {

    private static final ThreadLocal<UserWXDTO> holder = new NamedThreadLocal<UserWXDTO>("UserWXDTO");

    public static void reset() {
        holder.remove();
    }

    public static void set(UserWXDTO value) {
        holder.set(value);
    }

    public static UserWXDTO get() {
        return holder.get();
    }

}

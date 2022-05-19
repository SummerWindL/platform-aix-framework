package com.platform.aix.common.datacommon.db.domain;

import com.platform.aix.common.datacommon.db.KeyMethodInterface;
import com.platform.aix.service.user.account.UserInterface;
import com.platform.core.base.BaseEntity;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author Advance
 * @date 2022年03月26日 11:01
 * @since V1.0.0
 */
public class User extends BaseEntity implements Serializable, UserInterface, Cloneable, KeyMethodInterface<String>{

    @NotNull
    @Size(max = 32)
    @Id
    private String id;

    @NotNull
    @Size(max = 255)
    private String username;

    /**
     *  数量
     */
    private Integer count;

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}

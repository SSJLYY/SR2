package com.bp.alps.core.service;

import com.bp.alps.facades.data.user.PasswordResetRequest;

public interface AlpsPasswordResetService {

    /**
     * 修改密码shaun
     * @param passwordResetRequest
     */
    Boolean resetPassword(PasswordResetRequest passwordResetRequest);



}

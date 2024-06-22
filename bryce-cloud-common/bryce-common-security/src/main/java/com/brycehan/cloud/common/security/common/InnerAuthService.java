package com.brycehan.cloud.common.security.common;

import com.brycehan.cloud.common.core.constant.DataConstants;
import com.brycehan.cloud.common.core.util.ServletUtils;
import org.springframework.stereotype.Service;

/**
 * 内部认证服务
 *
 * @author Bryce Han
 * @since 2024/6/22
 */
@SuppressWarnings("unused")
@Service("innerAuth")
public class InnerAuthService {

    public boolean hasAuthority() {
        String inner = ServletUtils.getRequest().getHeader(DataConstants.INNER_CALL);
        return DataConstants.YES.equalsIgnoreCase(inner);
    }

}

package com.brycehan.cloud.common.security.common;

import com.brycehan.cloud.common.core.constant.DataConstants;
import com.brycehan.cloud.common.core.enums.YesNoType;
import com.brycehan.cloud.common.core.util.ServletUtils;
import org.springframework.stereotype.Service;

/**
 * 内部认证服务
 *
 * @author Bryce Han
 * @since 2024/6/22
 */
@Service("innerAuth")
@SuppressWarnings("unused")
public class InnerAuthService {

    public boolean hasAuthority() {
        String inner = ServletUtils.getRequest().getHeader(DataConstants.INNER_CALL);
        return YesNoType.YES.getValue().equalsIgnoreCase(inner);
    }

}

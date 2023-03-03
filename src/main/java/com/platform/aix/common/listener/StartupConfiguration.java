package com.platform.aix.common.listener;

import com.platform.aix.cmd.bean.SysPara;
import com.platform.aix.config.CacheLoadService;
import com.platform.common.enumeration.EnumCountflagType;
import com.platform.core.constant.GlobalConstant;
import com.platform.core.interf.DataDict;
import com.platform.core.interf.Menu;
import com.platform.core.interf.Page;
import com.platform.repo.pg.model.base.BasePlpgsqlModel;
import com.platform.repo.pg.repo.sys.para.SysParaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

import static com.platform.aix.common.util.BeanUtils.basePlpgsqlModel2Clz;

/**
 * @author Advance
 * @date 2021年11月19日 15:45
 * @since V1.0.0
 */
@Component
@Slf4j
public class StartupConfiguration extends AppStartupAdapter {

    @Autowired
    private SysParaRepository sysParaRepository;
    /**
     * CatchLoadService
     */
    @Autowired
    private CacheLoadService catchLoadService;


    @Override
    public void afterStartup(ApplicationContext applicationContext) {
        log.info("系统成功启动..");

    }

    @Override
    protected Map<String, Page> pageCache() {
        return catchLoadService.pageCache();
    }

    @Override
    protected Map<String, Menu> menuCache() {
        return catchLoadService.menuCache();
    }

    @Override
    protected Map<String, DataDict> dictCodeCache() {
        return catchLoadService.dictCodeCache();
    }

    @Override
    public long getCacheRefreshTime() {
        BasePlpgsqlModel basePlpgsqlModel = sysParaRepository.queryAllSysPara(GlobalConstant.SYS, GlobalConstant.DATADICT_REFRESH_PERIOD, "",
                EnumCountflagType.LIST.getCountflag(), 0, Integer.MAX_VALUE);
        SysPara[] param = basePlpgsqlModel2Clz(basePlpgsqlModel, SysPara[].class);
        if(null != param) {
            List<SysPara> params = (List<SysPara>) CollectionUtils.arrayToList(param);
            return new Long(params.get(0).getParavalue()).longValue();
        }
        return 3600000L;
    }
}

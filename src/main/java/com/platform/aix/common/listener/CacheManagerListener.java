package com.platform.aix.common.listener;

import com.google.common.base.Stopwatch;
import com.platform.aix.cmd.bean.SysPara;
import com.platform.aix.common.exception.BIZException;
import com.platform.aix.common.response.enums.ResponseResult;
import com.platform.aix.common.util.CacheUtil;
import com.platform.aix.module.cache.EntrustIdManager;
import com.platform.common.enumeration.EnumCountflagType;
import com.platform.common.util.JsonAdaptor;
import com.platform.core.constant.GlobalConstant;
import com.platform.repo.pg.model.base.BasePlpgsqlModel;
import com.platform.repo.pg.repo.sys.para.SysParaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.platform.aix.common.util.BeanUtils.basePlpgsqlModel2Clz;

/**
 * @author Advance
 * @date 2022年05月29日 12:49
 * @since V1.0.0
 */
@Component
public class CacheManagerListener extends StartupConfiguration{
    private static Logger logger = LoggerFactory.getLogger(CacheManagerListener.class);
    @Autowired
    private SysParaRepository sysParaRepository;
    @Autowired
    private JsonAdaptor jsonAdaptor;
    @Autowired
    private EntrustIdManager entrustIdManager;
    String SYS_CODE_ = "SYS_CODE_";

    @Override
    public void afterStartup(ApplicationContext applicationContext) {
        start();
    }

    public void start() {
        // 加载缓存
        Stopwatch stopwatch = Stopwatch.createStarted();
        logger.info("==================开始加载sys_para系统参数缓存=================");
        putCache();
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("sys_para系统参数缓存更新结束，执行了：" + stopwatch.stop().elapsed(TimeUnit.MILLISECONDS) + "毫秒 \n");
        }
        //清除rockdb的缓存文件 每次重启清除缓存
        entrustIdManager.clearCache();
    }

    private void putCache() {
        CacheUtil.putAppCache(GlobalConstant.SYS_PARA_KEY, findAllSysParam());
        logger.info("==================成功加载sys_para系统参数缓存=================");
    }

    /**
     * 获取所有的系统参数
     * @return
     */
    private Map<String, Object> findAllSysParam() {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        BasePlpgsqlModel basePlpgsqlModel = sysParaRepository.queryAllSysPara("", "", "",
                EnumCountflagType.LIST.getCountflag(), 0, Integer.MAX_VALUE);
        SysPara[] sysParas = basePlpgsqlModel2Clz(basePlpgsqlModel, SysPara[].class);
        if (!ObjectUtils.isEmpty(sysParas)) {
            List<SysPara> sysParasList = (List<SysPara>) CollectionUtils.arrayToList(sysParas);
            for (SysPara sysPara : sysParasList) {
                resultMap.put(sysPara.getParacode(), sysPara.getParavalue());
                // sysPara以SYS_CODE维度的缓存
                if (resultMap.get(SYS_CODE_ + sysPara.getSystemcode()) == null) {
                    Map<String, String> map = new HashMap<>();
                    map.put(sysPara.getParacode(), sysPara.getParavalue());
                    resultMap.put(SYS_CODE_ + sysPara.getSystemcode(), map);
                }
                else {
                    ((Map<String, String>)resultMap.get(SYS_CODE_ + sysPara.getSystemcode())).put(sysPara.getParacode(),
                            sysPara.getParavalue());
                }
            }
        }
        return resultMap;
    }
}

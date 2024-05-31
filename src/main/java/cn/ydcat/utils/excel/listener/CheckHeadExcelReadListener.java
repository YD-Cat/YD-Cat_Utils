package cn.ydcat.utils.excel.listener;

import com.alibaba.excel.context.AnalysisContext;

import java.util.Map;

/**
 * 表头校验接口
 */
public interface CheckHeadExcelReadListener {

    /**
     * 校验表头方法
     * @param headsMap 表头数据
     * @param context
     * @return
     */
    boolean checkHead(Map<Integer, String> headsMap, AnalysisContext context);
}

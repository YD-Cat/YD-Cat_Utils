package cn.ydcat.utils.excel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.read.metadata.holder.ReadRowHolder;
import com.alibaba.excel.util.ConverterUtils;
import lombok.extern.slf4j.Slf4j;
import cn.ydcat.utils.excel.po.BaseExcelData;

import java.util.Map;

/**
 * Excel监听器抽象类
 * @param <T>
 */
@Slf4j
public abstract class AbstractExcelReadListener<T extends BaseExcelData> implements ReadListener<T>, CheckHeadExcelReadListener {

    /**
     * 单挑数据处理方法
     * @param baseData
     * @param analysisContext
     */
    @Override
    public void invoke(T baseData, AnalysisContext analysisContext) {
        this.setRowIndex(baseData, analysisContext);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
    }

    /**
     * 读取表头数据
     * @param headMap
     * @param context
     */
    @Override
    public void invokeHead(Map<Integer, ReadCellData<?>> headMap, AnalysisContext context) {
        this.checkHead(ConverterUtils.convertToStringMap(headMap, context), context);
    }

    /**
     * 设置行数
     * @param baseData
     * @param analysisContext
     */
    protected void setRowIndex(T baseData, AnalysisContext analysisContext){
        ReadRowHolder readRowHolder = analysisContext.readRowHolder();
        Integer rowIndex = readRowHolder.getRowIndex();
        baseData.setRowIndex(Long.valueOf(rowIndex));
    }
}

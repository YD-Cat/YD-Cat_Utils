package cn.ydcat.utils.excel.listener;

import com.alibaba.excel.context.AnalysisContext;
import lombok.Data;
import cn.ydcat.utils.excel.po.BaseExcelData;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Excel简单监听器
 * @param <T>
 */
@Slf4j
@Data
public class SimpleExcelReadListener<T extends BaseExcelData> extends AbstractExcelReadListener<T> {

    /**
     * 表头
     */
    private List<String> heads;

    /**
     * 文件表头
     */
    private Map<Integer, String> headsMap;

    /**
     * 文件数据
     */
    private List<T> datas = new ArrayList<>();

    public SimpleExcelReadListener(List<String> heads) {
        this.heads = heads;
    }

    public SimpleExcelReadListener() {
    }

    /**
     * 单挑数据处理方法
     * @param data 数据
     * @param analysisContext
     */
    @Override
    public void invoke(T data, AnalysisContext analysisContext) {
        this.setRowIndex(data, analysisContext);
        datas.add(data);
    }

    @Override
    public boolean checkHead(Map<Integer, String> headsMap, AnalysisContext context) {
        this.headsMap = headsMap;
        if (heads != null && heads.size() > 0) {
            Collection<String> headsInExcel = this.headsMap.values();
            boolean b = headsInExcel.containsAll(heads);
            if (!b) {
                throw new RuntimeException("表头校验不通过");
            }
        }
        return true;
    }

    @Override
    public void onException(Exception exception, AnalysisContext context) {
        log.error(exception.getMessage());
    }
}

package cn.ydcat.utils.excel.po;

import com.alibaba.excel.annotation.ExcelIgnore;
import lombok.Data;

@Data
public class BaseExcelData {
    @ExcelIgnore
    private Long rowIndex;
}

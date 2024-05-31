package cn.ydcat.utils.excel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.util.DateUtils;
import cn.ydcat.utils.excel.annotation.ExcelDateTimeFormat;

import java.text.ParseException;
import java.util.Date;

/**
 * Excel日期格式转换器<br/>
 * 以String读取单元格，根据{@link ExcelDateTimeFormat}注解的格式进行转换
 */
public class ExcelDateConverter implements Converter<Date> {
    public ExcelDateConverter() {
    }

    public Class<?> supportJavaTypeKey() {
        return Date.class;
    }

    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    public Date convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws ParseException {
        if (cellData == null) {
            return null;
        }
        String dateTimeStr = cellData.getStringValue();
        ExcelDateTimeFormat[] dateTimeFormats = contentProperty.getField().getAnnotationsByType(ExcelDateTimeFormat.class);
        if(dateTimeFormats!=null && dateTimeFormats.length>0){
            for (ExcelDateTimeFormat dateTimeFormat : dateTimeFormats) {
                String[] formats = dateTimeFormat.value();
                try {
                    for (String format : formats) {
                        Date date = DateUtils.parseDate(dateTimeStr, format);
                        if (date != null) {
                            return date;
                        }
                    }
                } catch (ParseException e) {
                    throw new RuntimeException("日期转换异常");
                }
            }
        }
        return DateUtils.parseDate(dateTimeStr, null);
    }
}

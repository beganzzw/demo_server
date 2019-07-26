package org.gwhere.excel;

import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Created by jiangtao on 16/5/5.
 */
public abstract class AbstractExcelExporter extends AbstractExcelView {

    private final static String CONTENT_TYPE_XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    public void export(Map<String, Object> model) throws Exception {
        export(model, null);
    }

    public void export(Map<String, Object> model, String fileName) throws Exception {
        ServletRequestAttributes requestAttributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
        HttpServletRequest request = requestAttributes.getRequest();
        HttpServletResponse response = requestAttributes.getResponse();
        if (StringUtils.hasText(fileName)) {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xls");
            response.setContentType(CONTENT_TYPE_XLSX);
        }
        render(model, request, response);
    }

    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        buildExcelDocument(model, workbook);
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("export", "UTF-8") + ".xls");
        response.setContentType(CONTENT_TYPE_XLSX);
    }

    protected Row createRowHeader(Sheet sheet, String... titles) {
        return createRowHeader(sheet, 0, titles);
    }

    Row createRowHeader(Sheet sheet, int index, String... titles) {
        HSSFPalette palette = ((HSSFWorkbook) sheet.getWorkbook()).getCustomPalette();
        palette.setColorAtIndex(HSSFColor.RED.index,
                (byte) 158,
                (byte) 158,
                (byte) 158
        );
        CellStyle style = ((HSSFWorkbook) sheet.getWorkbook()).createCellStyle();
        style.setFillForegroundColor(HSSFColor.RED.index);
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);

        return createRowHeader(sheet, index, style, titles);
    }

    Row createRowHeader(Sheet sheet, int rowIndex, CellStyle style, String... titles) {
        Row header = sheet.createRow(rowIndex);
        int index = 0;
        for (String title : titles) {
            header.createCell(index).setCellValue(title);
            header.getCell(index).setCellStyle(style);
            index++;
        }
        return header;
    }

    protected abstract void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook);

    protected RowBuilder createRow(Sheet sheet, int rowIndex) {
        return new RowBuilder(sheet.createRow(rowIndex));
    }

    protected class RowBuilder {
        Row row;

        int cellIndex;

        RowBuilder(Row row) {
            this.row = row;
        }

        public RowBuilder addCell(double value) {
            row.createCell(cellIndex++).setCellValue(value);
            return this;
        }

        public RowBuilder addCell(Date value) {
            row.createCell(cellIndex++).setCellValue(value);
            return this;
        }

        public RowBuilder addCell(Calendar value) {
            row.createCell(cellIndex++).setCellValue(value);
            return this;
        }

        public RowBuilder addCell(RichTextString value) {
            row.createCell(cellIndex++).setCellValue(value);
            return this;
        }

        public RowBuilder addCell(String value) {
            row.createCell(cellIndex++).setCellValue(value);
            return this;
        }

        public Row getRow() {
            return row;
        }
    }
}

package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import model.JasperModel;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

@RestController
@RequestMapping("/jasperReportTest")
public class JasperReportTest {
	
	@GetMapping("/downloadPdf")
	@CrossOrigin
	public @ResponseBody byte[] downloadPdf() throws JRException, FileNotFoundException {
		System.out.println("開始產出JasperReport文件");
		
		// 設定parameter參數
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("title", "第一份 JasperReport 檔案");
		
		// 設定field參數
		List<JasperModel> jasperModelList = new ArrayList<JasperModel>();
		
		JasperModel jasperModel = new JasperModel();
		jasperModel.setName("小明");
		jasperModel.setScore("77");
		jasperModelList.add(jasperModel);
		
		jasperModel = new JasperModel();
		jasperModel.setName("小華");
		jasperModel.setScore("100");
		jasperModelList.add(jasperModel);
		
		// jasper文件
		InputStream input = new FileInputStream(
				new File("C:\\Users\\hyt\\Desktop\\JAVA_CODE\\test\\src\\main\\resources\\pdf\\firstJasperReport.jrxml")); 
		JasperDesign design = JRXmlLoader.load(input); 
		JasperReport jasperReport = JasperCompileManager.compileReport(design);
		
		// 集合轉資料
		JRDataSource dataSource = new JRBeanCollectionDataSource(jasperModelList, true);
		
		// print文件
		JasperPrint print = JasperFillManager.fillReport(jasperReport, params, dataSource);
		
		// PDF
		byte[] data = JasperExportManager.exportReportToPdf(print);
		
		System.out.println("JasperReport:"+data);
		
		return data;
	}

}

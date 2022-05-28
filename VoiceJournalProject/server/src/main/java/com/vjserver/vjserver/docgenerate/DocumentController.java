package com.vjserver.vjserver.docgenerate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletContext;

import com.vjserver.vjserver.repository.ClassRecordRepository;
import com.vjserver.vjserver.entity.ClassRecord;
import com.vjserver.vjserver.entity.Discipline;
import com.vjserver.vjserver.entity.Schedule;
import com.vjserver.vjserver.entity.Student;
import com.vjserver.vjserver.entity.StudentGroup;
import com.vjserver.vjserver.entity.StudentWork;
import com.vjserver.vjserver.entity.User;
import com.vjserver.vjserver.repository.DisciplineRepository;
import com.vjserver.vjserver.repository.ScheduleRepository;
import com.vjserver.vjserver.repository.StudentGroupRepository;
import com.vjserver.vjserver.repository.StudentRepository;
import com.vjserver.vjserver.repository.StudentWorkRepository;
import com.vjserver.vjserver.repository.UserRepository;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.TableWidthType;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFStyles;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTFonts;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/document")
public class DocumentController  {
    DisciplineRepository disciplineRepository;
    ScheduleRepository scheduleRepository;
    ClassRecordRepository classRecordRepository;
    StudentRepository studentRepository;
    StudentWorkRepository studentWorkRepository;
    StudentGroupRepository studentGroupRepository;
    UserRepository userRepository;

    DocumentController(DisciplineRepository disciplineRepository,
    ScheduleRepository scheduleRepository,
    StudentRepository studentRepository,
    StudentWorkRepository studentWorkRepository,
    ClassRecordRepository classRecordRepository,
    StudentGroupRepository studentGroupRepository,
    UserRepository userRepository) {
        this.disciplineRepository = disciplineRepository;
        this.scheduleRepository = scheduleRepository;
        this.studentRepository = studentRepository;
        this.studentWorkRepository = studentWorkRepository;
        this.classRecordRepository = classRecordRepository;
        this.studentGroupRepository = studentGroupRepository;
        this.userRepository = userRepository;
    }


    @RequestMapping(value = "/grade_sheet/{teacherId}/{disciplineId}/{groupId}", method = RequestMethod.GET,
    produces = "application/json; charset=utf-8"
    )
    ResponseEntity<InputStreamResource> getGradeSheet(
        @PathVariable Integer disciplineId,
        @PathVariable Integer groupId,
        @PathVariable Integer teacherId) throws IOException {
        
        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(today);
        System.out.println(formattedDate);

        Discipline discipline = disciplineRepository.select(disciplineId);
        StringBuilder shortDisName = new StringBuilder();
        String[] discSplit = discipline.getName().split(" ");
        for (String s: discSplit) {
            if (s.length() > 4) shortDisName.append(s.substring(0, 4));
        }

        StudentGroup group = studentGroupRepository.select(groupId);
        
        Schedule[] scheduleRows = scheduleRepository.selectByGroupDisciplineTeacher(groupId, disciplineId, teacherId);
        ClassRecord[] classRecords = null;
        Student[] students = studentRepository.selectByGroupId(groupId);

        User teacher = userRepository.select(teacherId);

        //массив студентов и полных баллов за семестр
        ArrayList<FullGrade> grades = new ArrayList<>();
        for (Student s: students) {
            grades.add(new FullGrade(s.getId(), s.getLastName() + " " + s.getFirstName() + " " + s.getPatronymic(), 0));
        }

        for (Schedule s: scheduleRows) {
            //для каждой пары в расписании достаем записи о проведенных парах
            classRecords = classRecordRepository.selectByScheduleId(s.getId());

            //для каждой записи о паре достаем все полученные баллы студентами
            for (ClassRecord cr: classRecords) {
                StudentWork[] studentWorks = studentWorkRepository.selectByClassRecordId(cr.getId());
                
                for (int i = 0; i < grades.size(); i++) {
                    //для каждого студента находим все его отметки
                    ArrayList<StudentWork> works = findByStudentId(studentWorks, grades.get(i).getStudentId());
                    //для каждой записи суммируем полученные баллы в финальном массиве
                    for (StudentWork sw: works) {
                        grades.get(i).setGrade(grades.get(i).getGrade() + sw.getGrade());
                    }
                }
            }
        }
        
        for (FullGrade f: grades) {
            System.out.println(f.getStudentId() + " " + f.getStudentFullName() + " " + f.getGrade());
        }

        
        ///////////////////////ГЕНЕРАЦИЯ ДОКУМЕНТА/////////////////////
        XWPFDocument document = new XWPFDocument();

        ////НАЗВАНИЕ
        XWPFParagraph title = document.createParagraph();
        title.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun titleRun = title.createRun();
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd.MM.yyyy");
        titleRun.setText("ВЕДОМОСТЬ");
        titleRun.setFontFamily("Times New Roman");
        titleRun.setFontSize(14);

        ////ДАТА СОЗДАНИЯ ДОКУМЕНТА
        XWPFRun dateRun = document.createParagraph().createRun();
        dateRun.setText("от " + sdf2.format(today));
        dateRun.setFontFamily("Times New Roman");
        dateRun.setFontSize(14);
        dateRun.getParagraph().setAlignment(ParagraphAlignment.CENTER);

        ////ИНФОРМАЦИЯ О ПРЕДМЕТЕ И ПРЕПОДАВАТЕЛЕ
        XWPFParagraph disciplineInfo = document.createParagraph();
        XWPFRun disRun = disciplineInfo.createRun();
        disRun.setText("Дисциплина: " + discipline.getName());
        disRun.setFontFamily("Times New Roman");
        disRun.setFontSize(14);

        XWPFRun disRun2 = document.createParagraph().createRun();
        disRun2.setText("Преподаватель: " + teacher.getLastName() + " " +
        teacher.getFirstName() + " " + teacher.getPatronymic());
        disRun2.setFontFamily("Times New Roman");
        disRun2.setFontSize(14);

        /////table
        XWPFTable table = document.createTable();
        table.setWidth("100%");

        table.getRow(0).getCell(0).removeParagraph(0);
        XWPFRun cellRun = table.getRow(0).getCell(0).addParagraph().createRun();
        cellRun.setText("№");
        cellRun.setFontFamily("Times New Roman");
        cellRun.setFontSize(14);

        cellRun = table.getRow(0).addNewTableCell().addParagraph().createRun();
        table.getRow(0).getCell(1).removeParagraph(0);
        cellRun.setText("ФИО студента");
        cellRun.setFontFamily("Times New Roman");
        cellRun.setFontSize(14);

        cellRun = table.getRow(0).addNewTableCell().addParagraph().createRun();
        table.getRow(0).getCell(2).removeParagraph(0);
        cellRun.setText("Баллы");
        cellRun.setFontFamily("Times New Roman");
        cellRun.setFontSize(14);

        table.getRow(0).getCell(0).setWidth("5%");
        table.getRow(0).getCell(1).setWidth("75%");
        table.getRow(0).getCell(2).setWidth("20%");

        for (int i = 0; i <= grades.size()-1; i++) {
            XWPFTableRow row = table.createRow();

            row.getCell(0).removeParagraph(0);
            row.getCell(1).removeParagraph(0);
            row.getCell(2).removeParagraph(0);

            cellRun = row.getCell(0).addParagraph().createRun();
            cellRun.setText(Integer.toString(i+1));
            cellRun.setFontFamily("Times New Roman");
            cellRun.setFontSize(14);

            cellRun = row.getCell(1).addParagraph().createRun();
            cellRun.setText(grades.get(i).getStudentFullName());
            cellRun.setFontFamily("Times New Roman");
            cellRun.setFontSize(14);

            cellRun = row.getCell(2).addParagraph().createRun();
            cellRun.setText(Integer.toString(grades.get(i).getGrade()));
            cellRun.setFontFamily("Times New Roman");
            cellRun.setFontSize(14);
        }

        String desiredName = "ведомость_от_" + formattedDate + "_" + shortDisName.toString() + "_" + group.getGroupNumber() + ".docx";

        File output = new File("vedomost_" + formattedDate + "_" + group.getGroupNumber() + ".docx");
        FileOutputStream out = new FileOutputStream(output);
        document.write(out);
        out.close();
        document.close();

        System.out.println(output.getName());

        //////TRANSLITERATION
        // var CYRILLIC_TO_LATIN = "Latin-Russian/BGN";
        // Transliterator toLatinTrans = Transliterator.getInstance(CYRILLIC_TO_LATIN);
        // String result = toLatinTrans.transliterate(output.getName());
        // System.out.println(result);

        // return null;
        // ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(Paths.get(output.getAbsolutePath())));
        // return resource;
        
        InputStreamResource resource = new InputStreamResource(new FileInputStream(output));

        return ResponseEntity.ok()
                // Content-Disposition
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + output.getName())
                // Content-Type
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                // Contet-Length
                .contentLength(output.length()) //
                .body(resource);
    
    }

    public static ArrayList<StudentWork> findByStudentId(StudentWork[] array, Integer id) {
        ArrayList<StudentWork> result = new ArrayList<>();
        for (StudentWork sw: array) {
            if (sw.getStudentId() == id) 
            result.add(sw);
        }
        return result;
    }

    public static MediaType getMediaTypeForFileName(ServletContext servletContext, String fileName) {
        // application/pdf
        // application/xml
        // image/gif, ...
        String mineType = servletContext.getMimeType(fileName);
        try {
            MediaType mediaType = MediaType.parseMediaType(mineType);
            return mediaType;
        } catch (Exception e) {
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
}

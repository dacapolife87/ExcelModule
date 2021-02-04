package me.hjjang.excelmodule;

import me.hjjang.excelmodule.domain.ObjectForTest;
import me.hjjang.excelmodule.domain.Person;
import me.hjjang.excelmodule.domain.Team;
import me.hjjang.excelmodule.excelmaker.ExcelHandler;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class ExcelHandlerTest {

    @Test
    void createExcel() throws NoSuchFieldException, IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, ClassNotFoundException {
        ExcelHandler excelHandler = new ExcelHandler();
        String fileName = "TestExcel";
        List<Person> personList = new ArrayList<>();
        personList.add(new Person("hjjang1", 15, "seoul", ""));
        personList.add(new Person("hjjang2", 23, "gyunggi", ""));
        personList.add(new Person("hjjang3", 57, "busan", ""));
        personList.add(new Person("hjjang4", 25, "daegu", ""));
        personList.add(new Person("hjjang2", 15, "seoul", ""));

        excelHandler.excelMaker(fileName,personList, Person.class);


        List<Team> teams = new ArrayList<>();
        teams.add(new Team(100L,"Team1", 15, "최연소"));
        teams.add(new Team(101L,"Team2", 23, "젊은이"));
        teams.add(new Team(102L,"Team3", 57, "가장어른"));
        teams.add(new Team(103L,"Team4", 25, "군대제대함"));
        teams.add(new Team(104L,"Team2", 15, "다른팀"));

        excelHandler.excelMaker("TeamInfo",teams, Team.class);

        List<ObjectForTest> objectForTestList = new ArrayList<>();
        objectForTestList.add(new ObjectForTest(Long.valueOf(100L), 100L, "String0","문자열0", Integer.valueOf(100),100, LocalDateTime.now(),LocalDateTime.now()));
        objectForTestList.add(new ObjectForTest(Long.valueOf(101L), 101L, "String1","문자열1", Integer.valueOf(101),101, LocalDateTime.now(),LocalDateTime.now()));
        objectForTestList.add(new ObjectForTest(Long.valueOf(102L), 102L, "String2","문자열2", Integer.valueOf(102),102, LocalDateTime.now(),LocalDateTime.now()));
        objectForTestList.add(new ObjectForTest(Long.valueOf(103L), 103L, "String3","문자열3", Integer.valueOf(103),103, LocalDateTime.now(),LocalDateTime.now()));
        objectForTestList.add(new ObjectForTest(Long.valueOf(104L), 104L, "String4","문자열4", Integer.valueOf(104),104, LocalDateTime.now(),LocalDateTime.now()));

        excelHandler.excelMaker("ExcelFileTest",objectForTestList, ObjectForTest.class);

    }

    @Test
    void cellStyleFormat() {
        XSSFWorkbook newWorkBook = new XSSFWorkbook();
        XSSFCellStyle cellStyle = newWorkBook.createCellStyle();
        for(int i = 0; i < 50; i++) {
            cellStyle.setDataFormat(i);
            System.out.println(cellStyle.getDataFormat() + " : "+cellStyle.getDataFormatString());
        }

    }
}
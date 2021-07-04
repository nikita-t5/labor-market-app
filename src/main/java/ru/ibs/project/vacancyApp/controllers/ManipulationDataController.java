package ru.ibs.project.vacancyApp.controllers;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.ibs.project.vacancyApp.dto.hhDTO.respAllVacanciesDTOs.ItemDTO;
import ru.ibs.project.vacancyApp.entities.VacancyArea;
import ru.ibs.project.vacancyApp.services.interfaces.VacancyAreaService;
import ru.ibs.project.vacancyApp.dto.frontDTO.DownloadRequestDTO;
import ru.ibs.project.vacancyApp.services.interfaces.DataManipulationService;
import ru.ibs.project.vacancyApp.services.interfaces.DownloadFromHHService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = "/data-manipulation", consumes = {MediaType.ALL_VALUE},
        produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class ManipulationDataController {

    @Autowired
    private DownloadFromHHService downloadFromHHService;

    @Autowired
    private DataManipulationService dataManipulationService;

    @Autowired
    private VacancyAreaService vacancyAreaService;


    @PostMapping("create")
    public DownloadRequestDTO create(@RequestBody DownloadRequestDTO requestDTO) throws ExecutionException, InterruptedException {
        dataManipulationService.deleteVacancyArea();
        dataManipulationService.deleteAreaAndVacancy();
        Set<ItemDTO> vacanciesDTO = downloadFromHHService.downloadAllItemDTOsByDownloadRequestDTO(requestDTO);
        dataManipulationService.createAll(vacanciesDTO);
        vacanciesDTO.clear();

        return requestDTO;
    }


    @PostMapping("create-and-delete")
    public DownloadRequestDTO createAndDelete(@RequestBody DownloadRequestDTO requestDTO) throws ExecutionException, InterruptedException {
        dataManipulationService.deleteVacancyArea();
        dataManipulationService.deleteAreaAndVacancy();
        Set<ItemDTO> vacanciesDTO = downloadFromHHService.downloadAllItemDTOsByDownloadRequestDTO(requestDTO);
        dataManipulationService.createAll(vacanciesDTO);
        vacanciesDTO.clear();
        return requestDTO;
    }

    @GetMapping("delete")
    public void delete() {
        dataManipulationService.deleteVacancyArea();
        dataManipulationService.deleteAreaAndVacancy();
    }

    @GetMapping("export-vacancies-CSV")
    public void exportCSV(HttpServletResponse response) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        String filename = "vacancies.csv";
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\"");
        //create a csv writer
        StatefulBeanToCsv<VacancyArea> writer = new StatefulBeanToCsvBuilder<VacancyArea>(response
                .getWriter())
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .withSeparator('|')
                .withOrderedResults(false)
                .build();
        writer.write(vacancyAreaService.createAllVacancyAreaList());
    }
}
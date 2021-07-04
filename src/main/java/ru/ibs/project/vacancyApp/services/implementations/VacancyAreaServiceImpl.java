package ru.ibs.project.vacancyApp.services.implementations;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ibs.project.vacancyApp.entities.VacancyArea;
import ru.ibs.project.vacancyApp.repositories.VacancyAreaRepository;
import ru.ibs.project.vacancyApp.services.interfaces.VacancyAreaService;

import java.util.List;

@Service
@Slf4j
public class VacancyAreaServiceImpl implements VacancyAreaService {

    @Autowired
    VacancyAreaRepository vacancyAreaRepository;

    @Override
    public List<VacancyArea> createAllVacancyAreaList() {   //массив всх вакансий
        List<VacancyArea> allVacancies = (List<VacancyArea>) vacancyAreaRepository.findAll();
        log.info("size allVacancies:" + allVacancies.size());
        return allVacancies;
    }
}
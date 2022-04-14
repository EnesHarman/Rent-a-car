package com.webproje.arackiralama.Business.concretes;

import com.webproje.arackiralama.Core.utilities.emailSender.EmailSenderService;
import com.webproje.arackiralama.Entity.concretes.Company;
import com.webproje.arackiralama.Entity.dto.companyDtos.CompanyAddDto;
import com.webproje.arackiralama.Repository.CompanyRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompanyManagerTest {

    @Mock
    CompanyRepository companyRepository;

    @Mock
    EmailSenderService emailSenderService;

    @InjectMocks
    CompanyManager underTest;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testAddCompany() {
        //GIVEN
        CompanyAddDto companyAddDto = new CompanyAddDto();
        companyAddDto.setManagerEmail("enes@gmail.com");

        Company company = new Company();
        company.setCompanyName("Deneme");

        when(companyRepository.save(any())).thenReturn(company);

        //WHEN
        this.underTest.addCompany(companyAddDto);

        //THEN
        verify(companyRepository).save(any());
        verify(emailSenderService).sendEmail(any(),any(),any());

    }

    @Test
    void testDeleteCompany() {
        //GIVEN
        int companyId =1;

        //WHEN
        this.underTest.deleteCompany(companyId);

        //THEN
        verify(companyRepository).deleteById(companyId);
    }

    @Test
    void testListCompany() {
        //GIVEN
        Optional<Integer> companyId = Optional.of(1);
        Optional<Integer> pageSize = Optional.of(1);
        Optional<Integer> pageNum = Optional.of(1);

        //WHEN
        this.underTest.listCompany(Optional.empty(),pageSize,pageNum);
        this.underTest.listCompany(companyId,pageSize,pageNum);

        //THEN
        verify(companyRepository).listAllCompanies(any());
        verify(companyRepository).listSingCompany(companyId.get());
    }

    @Test
    void testGetById() {
        //GIVEN
        int companyId = 1;

        //WHEN
        this.underTest.getById(companyId);

        //THEN
        verify(companyRepository).findById(companyId);
    }

    @Test
    void testSave() {
        //GIVEN
        Company company = new Company();

        //WHEN
        this.underTest.save(company);

        //THEN
        verify(companyRepository).save(company);
    }
}
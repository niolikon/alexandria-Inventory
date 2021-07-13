package org.niolikon.alexandria.inventory.catalog.commons;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.niolikon.alexandria.inventory.catalog.commons.converter.CompanyToCompanyViewConverter;
import org.niolikon.alexandria.inventory.catalog.commons.dto.CompanyRequest;
import org.niolikon.alexandria.inventory.catalog.commons.dto.CompanyView;
import org.niolikon.alexandria.inventory.catalog.commons.entities.Company;
import org.niolikon.alexandria.inventory.system.MessageProvider;
import org.niolikon.alexandria.inventory.system.exceptions.EntityDuplicationException;
import org.niolikon.alexandria.inventory.system.exceptions.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {
    
    private final CompanyRepository companyRepo;
    private final CompanyToCompanyViewConverter companyConverter;
    private final MessageProvider messageProvider;
    
    public CompanyService(CompanyRepository companyRepo,
            CompanyToCompanyViewConverter companyConverter,
            MessageProvider messageUtil) {
        this.companyRepo = companyRepo;
        this.companyConverter = companyConverter;
        this.messageProvider = messageUtil;
    }
    
    public Company findCompanyOrThrow(Long id) {
        return companyRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messageProvider.getMessage("company.NotFound", id)));
    }
    
    public CompanyView getCompany(Long id) {
        Company company = findCompanyOrThrow(id);
        return companyConverter.convert(company);
    }
    
    public Page<CompanyView> findAllcompanies(Pageable pageable) {
        return this.findAllcompaniesMatching(Optional.empty(), pageable);
    }
    
    public Page<CompanyView> findAllcompaniesMatching(Optional<String> search, Pageable pageable) {
        Page<Company> companies;
    	if (search.isPresent()) {
    		companies = companyRepo.findByNameContainingIgnoreCase(search.get(), pageable);
    	} 
    	else {
    		companies = companyRepo.findAll(pageable);
    	}
        
        List<CompanyView> companyViews = companies.stream()
        		.map( company -> companyConverter.convert(company))
        		.collect(Collectors.toList());
        
        return new PageImpl<>(companyViews, pageable, companies.getTotalElements());
    }
    
    public CompanyView create(CompanyRequest req) {
        Company company = new Company();
        company = this.fetchFromRequest(company, req);
        
        try {
            Company companysaved = companyRepo.save(company);
            return companyConverter.convert(companysaved);
        }  catch (DataIntegrityViolationException e) {
            throw new EntityDuplicationException(messageProvider.getMessage("company.Duplication", company.getName()));
        }
    }
    
    @Transactional
    public void delete(Long id) {
        try {
            companyRepo.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(messageProvider.getMessage("company.NotFound", id));
        }
    }
    
    public CompanyView update(Company company, CompanyRequest req) {
        Company companyUpdated = this.fetchFromRequest(company, req);
        Company companysaved = companyRepo.save(companyUpdated);
        return companyConverter.convert(companysaved);
    }
    
    private Company fetchFromRequest(Company company, CompanyRequest req) {
        company.setName(req.getName());
        return company;
    }
}

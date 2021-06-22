package org.niolikon.alexandria.inventory.catalog.commons;

import javax.validation.Valid;

import org.niolikon.alexandria.inventory.catalog.commons.dto.CompanyRequest;
import org.niolikon.alexandria.inventory.catalog.commons.dto.CompanyView;
import org.niolikon.alexandria.inventory.catalog.commons.entities.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/companies")
@Api(tags="Management of Company entities")
public class CompanyController {
    
    private final CompanyService service;

    public CompanyController(CompanyService service) {
        this.service = service;
    }
    
    @GetMapping("/{id}")
    @ResponseBody
    @ApiOperation(
            value = "Read company by ID", notes = "Returns Company data in JSON", response = CompanyView.class, produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The Company has been fetched"),
            @ApiResponse(code = 404, message = "Could not find the specified Company"),
            @ApiResponse(code = 403, message = "You are not authorized to access this resource"),
            @ApiResponse(code = 401, message = "You are not logged in") })
    public CompanyView getCompany(@ApiParam("The ID of the Company") @PathVariable Long id) {
        return service.getCompany(id);
    }

    @GetMapping
    @ResponseBody
    @ApiOperation(
            value = "Read all companies", notes = "Returns Company data in JSON", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The Companys have been fetched"),
            @ApiResponse(code = 404, message = "No Companys are present in the repository"),
            @ApiResponse(code = 403, message = "You are not authorized to access this resource"),
            @ApiResponse(code = 401, message = "You are not logged in") })
    public Page<CompanyView> getAllCompanies(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return service.findAllcompanies(pageable);
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @ApiOperation(
            value = "Create a company", notes = "Stores the input JSON Company data", response = CompanyView.class, produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The Company has been stored"),
            @ApiResponse(code = 409, message = "Could not complete the storage, the input Company data would cause duplication"),
            @ApiResponse(code = 403, message = "You are not authorized to access this resource"),
            @ApiResponse(code = 401, message = "You are not logged in") })
    public CompanyView create(@ApiParam("The input Company data") @RequestBody @Valid CompanyRequest req) {
        return service.create(req);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(
            value = "Delete a company", notes = "Deletes the specified Company data", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "The Company has been deleted"),
            @ApiResponse(code = 404, message = "Could not find the specified Company"),
            @ApiResponse(code = 403, message = "You are not authorized to access this resource"),
            @ApiResponse(code = 401, message = "You are not logged in") })
    public void deleteCompany(@ApiParam("The ID of the Company") @PathVariable Long id) {
        service.delete(id);
    }
    
    @PutMapping("/{id}")
    @ApiOperation(
            value = "Update a company", notes = "Modifies the specified Company with the input JSON Company data", response = CompanyView.class, produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The Company has been modified"),
            @ApiResponse(code = 404, message = "Could not find the specified Company"),
            @ApiResponse(code = 409, message = "Could not complete the modification, the input Company data would cause duplication"),
            @ApiResponse(code = 403, message = "You are not authorized to access this resource"),
            @ApiResponse(code = 401, message = "You are not logged in") })
    public CompanyView updateCompany(@ApiParam("The ID of the Company") @PathVariable Long id,
            @ApiParam("The input Company data") @RequestBody @Valid CompanyRequest req) {
        Company company = service.findCompanyOrThrow(id);
        return service.update(company, req);
    }
    
}

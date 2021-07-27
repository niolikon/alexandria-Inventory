package org.niolikon.alexandria.inventory.catalog.commons;

import java.util.Optional;

import javax.validation.Valid;

import org.niolikon.alexandria.inventory.catalog.commons.dto.PersonRequest;
import org.niolikon.alexandria.inventory.catalog.commons.dto.PersonView;
import org.niolikon.alexandria.inventory.catalog.commons.entities.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/persons")
@Api(tags="Management of Person entities")
public class PersonController {
    
    private final PersonService service;
    
    public PersonController(PersonService service) {
        this.service = service;
    }
    
    @GetMapping("/{id}")
    @ResponseBody
    @ApiOperation(
            value = "Read Person by ID", notes = "Returns Person data in JSON", response = PersonView.class, produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The Person has been fetched"),
            @ApiResponse(code = 404, message = "Could not find the specified Person")})
    @CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200", "http://localhost:3100", "http://localhost:3200"})
    public PersonView getPerson(@ApiParam("The ID of the Person") @PathVariable Long id) {
        return service.getPerson(id);
    }

    @GetMapping
    @ResponseBody
    @ApiOperation(
            value = "Search Persons", notes = "Returns Person data in JSON", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The Persons have been fetched"),
            @ApiResponse(code = 404, message = "No Persons are present in the repository")})
    @CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200", "http://localhost:3100", "http://localhost:3200"})
    public Page<PersonView> getAllPersons(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
    		@RequestParam Optional<String> search) {
        return service.findAllPersonsMatching(search, pageable);
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @ApiOperation(
            value = "Create a Person", notes = "Stores the input JSON Person data", response = PersonView.class, produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The Person has been stored"),
            @ApiResponse(code = 409, message = "Could not complete the storage, the input Person data would cause duplication"),
            @ApiResponse(code = 403, message = "You are not authorized to access this resource"),
            @ApiResponse(code = 401, message = "You are not logged in") })
    @CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200", "http://localhost:3100"})
    public PersonView create(@ApiParam("The input Person data") @RequestBody @Valid PersonRequest req) {
        return service.create(req);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(
            value = "Delete a Person", notes = "Deletes the specified Person data", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "The Person has been deleted"),
            @ApiResponse(code = 404, message = "Could not find the specified Person"),
            @ApiResponse(code = 403, message = "You are not authorized to access this resource"),
            @ApiResponse(code = 401, message = "You are not logged in") })
    @CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200", "http://localhost:3100"})
    public void deletePerson(@ApiParam("The ID of the Person") @PathVariable Long id) {
        service.delete(id);
    }
    
    @PutMapping("/{id}")
    @ApiOperation(
            value = "Update a Person", notes = "Modifies the specified Person with the input JSON Person data", response = PersonView.class, produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The Person has been modified"),
            @ApiResponse(code = 404, message = "Could not find the specified Person"),
            @ApiResponse(code = 409, message = "Could not complete the modification, the input Person data would cause duplication"),
            @ApiResponse(code = 403, message = "You are not authorized to access this resource"),
            @ApiResponse(code = 401, message = "You are not logged in") })
    @CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200", "http://localhost:3100"})
    public PersonView updatePerson(@ApiParam("The ID of the Person") @PathVariable Long id,
            @ApiParam("The input Person data") @RequestBody @Valid PersonRequest req) {
        Person Person = service.findPersonOrThrow(id);
        return service.update(Person, req);
    }
}

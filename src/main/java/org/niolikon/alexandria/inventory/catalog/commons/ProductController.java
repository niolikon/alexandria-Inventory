package org.niolikon.alexandria.inventory.catalog.commons;

import java.util.Optional;

import javax.validation.Valid;

import org.niolikon.alexandria.inventory.catalog.commons.dto.ProductRequest;
import org.niolikon.alexandria.inventory.catalog.commons.dto.ProductView;
import org.niolikon.alexandria.inventory.catalog.commons.entities.Product;
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
@RequestMapping("/products")
@Api(tags="Management of Product entities")
public class ProductController {
    
    private final ProductService service;
    
    public ProductController(ProductService service) {
        this.service = service;
    }
    
    @GetMapping("/{id}")
    @ResponseBody
    @ApiOperation(
            value = "Read product by ID", notes = "Returns Product data in JSON", response = ProductView.class, produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The Product has been fetched"),
            @ApiResponse(code = 404, message = "Could not find the specified Product")})
    @CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200", "http://localhost:3100", "http://localhost:3200"})
    public ProductView getProduct(@ApiParam("The ID of the Product") @PathVariable Long id) {
        return service.getProduct(id);
    }

    @GetMapping
    @ResponseBody
    @ApiOperation(
            value = "Search products", notes = "Returns Product data in JSON", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The Products have been fetched"),
            @ApiResponse(code = 404, message = "No Products are present in the repository")})
    @CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200", "http://localhost:3100", "http://localhost:3200"})
    public Page<ProductView> getAllProducts(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
    		@RequestParam Optional<String> search) {
        return service.findAllProductsMatching(search, pageable);
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @ApiOperation(
            value = "Create an product", notes = "Stores the input JSON Product data", response = ProductView.class, produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The Product has been stored"),
            @ApiResponse(code = 409, message = "Could not complete the storage, the input Product data would cause duplication"),
            @ApiResponse(code = 403, message = "You are not authorized to access this resource"),
            @ApiResponse(code = 401, message = "You are not logged in") })
    @CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200", "http://localhost:3100"})
    public ProductView create(@ApiParam("The input Product data") @RequestBody @Valid ProductRequest req) {
        return service.create(req);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(
            value = "Delete an product", notes = "Deletes the specified Product data", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "The Product has been deleted"),
            @ApiResponse(code = 404, message = "Could not find the specified Product"),
            @ApiResponse(code = 403, message = "You are not authorized to access this resource"),
            @ApiResponse(code = 401, message = "You are not logged in") })
    @CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200", "http://localhost:3100"})
    public void deleteProduct(@ApiParam("The ID of the Product") @PathVariable Long id) {
        service.delete(id);
    }
    
    @PutMapping("/{id}")
    @ApiOperation(
            value = "Update an product", notes = "Modifies the specified Product with the input JSON Product data", response = ProductView.class, produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The Product has been modified"),
            @ApiResponse(code = 404, message = "Could not find the specified Product"),
            @ApiResponse(code = 409, message = "Could not complete the modification, the input Product data would cause duplication"),
            @ApiResponse(code = 403, message = "You are not authorized to access this resource"),
            @ApiResponse(code = 401, message = "You are not logged in") })
    @CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200", "http://localhost:3100"})
    public ProductView updateProduct(@ApiParam("The ID of the Product") @PathVariable Long id,
            @ApiParam("The input Product data") @RequestBody @Valid ProductRequest req) {
        Product product = service.findProductOrThrow(id);
        return service.update(product, req);
    }
}

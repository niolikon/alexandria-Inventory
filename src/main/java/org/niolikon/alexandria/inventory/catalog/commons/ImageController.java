package org.niolikon.alexandria.inventory.catalog.commons;

import java.io.ByteArrayInputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.niolikon.alexandria.inventory.catalog.commons.dto.ImageView;
import org.niolikon.alexandria.inventory.catalog.commons.entities.Image;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.java.Log;

@Log
@RestController
@RequestMapping("/images")
@Api(tags="Management of Image entities")
public class ImageController {

    private final ImageService service;

    public ImageController(ImageService service, 
    		ProductService productService) {
        this.service = service;
    }
    
    @GetMapping("/{id}")
    @ApiOperation(
            value = "Read image by ID", notes = "Returns Image data")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The Image has been fetched"),
            @ApiResponse(code = 404, message = "Could not find the specified Image") })
    @CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200", "http://localhost:3100", "http://localhost:3200"})
    public void getImage(@ApiParam("The ID of the Image") @PathVariable Long id, HttpServletResponse res) {
        Image image = service.getDownloadableImage(id);
        
        try {
            res.setStatus(HttpServletResponse.SC_OK);
            res.setContentType(image.getMimetype());
			res.setContentLength((int) image.getData().length);
			IOUtils.copy(new ByteArrayInputStream(image.getData()), res.getOutputStream());
		} catch (Exception e) {
			log.warning("Error during stream of image: " + id);
		}
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @ApiOperation(
            value = "Create a image", notes = "Stores the input JSON Image data", response = ImageView.class, produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The Image has been stored"),
            @ApiResponse(code = 409, message = "Could not complete the storage, the input Image data would cause duplication"),
            @ApiResponse(code = 403, message = "You are not authorized to access this resource"),
            @ApiResponse(code = 401, message = "You are not logged in") })
    @CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200", "http://localhost:3100"})
    public ImageView create(@ApiParam("The input Image data") @RequestParam("image") MultipartFile imgFile, 
    		@RequestParam("productId") Long productId) {
        return service.create(imgFile, productId);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(
            value = "Delete a image", notes = "Deletes the specified Image data", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "The Image has been deleted"),
            @ApiResponse(code = 404, message = "Could not find the specified Image"),
            @ApiResponse(code = 403, message = "You are not authorized to access this resource"),
            @ApiResponse(code = 401, message = "You are not logged in") })
    @CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200", "http://localhost:3100"})
    public void deleteImage(@ApiParam("The ID of the Image") @PathVariable Long id) {
        service.delete(id);
    }
    
}

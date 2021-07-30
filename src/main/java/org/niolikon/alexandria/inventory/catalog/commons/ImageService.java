package org.niolikon.alexandria.inventory.catalog.commons;

import javax.sql.rowset.serial.SerialBlob;
import javax.transaction.Transactional;

import org.niolikon.alexandria.inventory.catalog.commons.converter.ImageToImageViewConverter;
import org.niolikon.alexandria.inventory.catalog.commons.dto.ImageView;
import org.niolikon.alexandria.inventory.catalog.commons.entities.Image;
import org.niolikon.alexandria.inventory.catalog.commons.entities.Product;
import org.niolikon.alexandria.inventory.system.MessageProvider;
import org.niolikon.alexandria.inventory.system.exceptions.EntityDuplicationException;
import org.niolikon.alexandria.inventory.system.exceptions.EntityNotFoundException;
import org.niolikon.alexandria.inventory.system.exceptions.EntityNotProcessableException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {

    private final ImageRepository imageRepo;
    private final ProductRepository productRepo;
    private final ImageToImageViewConverter imageConverter;
    private final MessageProvider messageProvider;
    
    public ImageService(ImageRepository imageRepo,
    		ProductRepository productRepo,
            ImageToImageViewConverter imageConverter,
            MessageProvider messageUtil) {
        this.imageRepo = imageRepo;
        this.productRepo = productRepo;
        this.imageConverter = imageConverter;
        this.messageProvider = messageUtil;
    }
    
    public Image findImageOrThrow(Long id) {
        return imageRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messageProvider.getMessage("image.NotFound", id)));
    }
    
    public Product findProductOrThrow(Long id) {
    	return productRepo.findById(id)
    			.orElseThrow( () -> new EntityNotFoundException(messageProvider.getMessage("product.NotFound", id)));
        
    }
    
    public ImageView getImage(Long id) {
        Image image = findImageOrThrow(id);
        return imageConverter.convert(image);
    }
    
    public Image getDownloadableImage(Long id) {
    	return findImageOrThrow(id);
    }

    @Transactional
    public ImageView create(MultipartFile file, Long productId) {
        Image image = new Image();
        image.setMimetype(file.getContentType());
        image.setProduct(this.findProductOrThrow(productId));
    	
    	try {
    		image.setData(file.getBytes()); 
    	}
    	catch (Exception _se) {
    		throw new EntityNotProcessableException(messageProvider.getMessage("image.SerializationFail", file.getName()));
    	}
    	
        try {
            Image imagesaved = imageRepo.save(image);
            return imageConverter.convert(imagesaved);
        }  catch (DataIntegrityViolationException e) {
            throw new EntityDuplicationException(messageProvider.getMessage("image.NotProcessable", file.getName()));
        }
    }
    
    @Transactional
    public void delete(Long id) {
        try {
            imageRepo.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(messageProvider.getMessage("image.NotFound", id));
        }
    }
}

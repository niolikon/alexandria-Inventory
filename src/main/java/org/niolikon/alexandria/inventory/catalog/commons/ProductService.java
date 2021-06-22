package org.niolikon.alexandria.inventory.catalog.commons;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.niolikon.alexandria.inventory.catalog.commons.converter.ProductToProductViewConverter;
import org.niolikon.alexandria.inventory.catalog.commons.dto.ProductRequest;
import org.niolikon.alexandria.inventory.catalog.commons.dto.ProductView;
import org.niolikon.alexandria.inventory.catalog.commons.entities.Product;
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
public class ProductService {
    
    private final ProductRepository productRepo;
    private final ProductToProductViewConverter productConverter;
    private final MessageProvider messageProvider;
    
    public ProductService(ProductRepository productRepo,
            ProductToProductViewConverter productConverter,
            MessageProvider messageUtil) {
        this.productRepo = productRepo;
        this.productConverter = productConverter;
        this.messageProvider = messageUtil;
    }
    
    public Product findProductOrThrow(Long id) {
        return productRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messageProvider.getMessage("product.NotFound", id)));
    }
    
    public ProductView getProduct(Long id) {
        Product product = findProductOrThrow(id);
        return productConverter.convert(product);
    }

    public Page<ProductView> findAllProducts(Pageable pageable) {
        Page<Product> products = productRepo.findAll(pageable);
        List<ProductView> productViews = new ArrayList<>();
        products.forEach(product -> {
            ProductView productView = productConverter.convert(product);
            productViews.add(productView);
        });
        return new PageImpl<>(productViews, pageable, products.getTotalElements());
    }
    
    public ProductView create(ProductRequest req) {
        Product product = new Product();
        this.fetchFromRequest(product, req);

        try {
            Product productSaved = productRepo.save(product);
            return productConverter.convert(productSaved);
        } catch (DataIntegrityViolationException e) {
            throw new EntityDuplicationException(messageProvider.getMessage("product.Duplication", product.getName()));
        }
    }
    
    @Transactional
    public void delete(Long id) {
        try {
            productRepo.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(messageProvider.getMessage("product.NotFound", id));
        }
    }
    
    public ProductView update(Product product, ProductRequest req) {
        Product productUpdated = this.fetchFromRequest(product, req);
        Product productSaved = productRepo.save(productUpdated);
        return productConverter.convert(productSaved);
    }
    
    private Product fetchFromRequest(Product product, ProductRequest req) {
        product.setName(req.getName());
        product.setDescription(req.getDescription());
        product.setLabel(req.getLabel());
        product.setImage(req.getImage());
        product.setPrice(req.getPrice());
        return product;
    }
}

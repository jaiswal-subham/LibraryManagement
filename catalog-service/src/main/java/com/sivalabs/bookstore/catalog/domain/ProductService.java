package com.sivalabs.bookstore.catalog.domain;

import com.sivalabs.bookstore.catalog.ApplicationProperties;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final ApplicationProperties applicationProperties;

    ProductService(ProductRepository productRepository, ApplicationProperties applicationProperties) {
        this.productRepository = productRepository;
        this.applicationProperties = applicationProperties;
    }

    public PagedResult<Product> getProducts(int pageNo) {
        pageNo = pageNo <= 1 ? 0 : pageNo - 1;
        Pageable pageable = PageRequest.of(
                pageNo, applicationProperties.pageSize(), Sort.by("name").ascending());
        Page<ProductEntity> p = this.productRepository.findAll(pageable);
        List<Product> products = p.getContent().stream()
                .map(new ProductMapper()::convertProductEntityToProduct)
                .collect(Collectors.toList());
        return new PagedResult<>(
                products,
                p.getTotalElements(),
                p.getNumber() + 1,
                p.getTotalPages(),
                p.isFirst(),
                p.isLast(),
                p.hasNext(),
                p.hasPrevious());
    }

    public Optional<Product> getProductByCode(String code) {
        return this.productRepository.findByCode(code).map(new ProductMapper()::convertProductEntityToProduct);
    }
}

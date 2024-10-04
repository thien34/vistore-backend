package com.example.back_end.service.product.impl;

import com.example.back_end.core.admin.product.mapper.ProductAttributeCombinationMapper;
import com.example.back_end.core.admin.product.payload.request.ProductAttributeCombinationRequest;
import com.example.back_end.core.admin.product.payload.response.ProductAttributeCombinationResponse;
import com.example.back_end.service.product.ProductAttributeCombinationService;
import com.example.back_end.entity.Picture;
import com.example.back_end.entity.ProductAttributeCombination;
import com.example.back_end.entity.ProductAttributeCombinationPicture;
import com.example.back_end.infrastructure.exception.ExistsByNameException;
import com.example.back_end.infrastructure.exception.ResourceNotFoundException;
import com.example.back_end.repository.PictureRepository;
import com.example.back_end.repository.ProductAttributeCombinationPictureRepository;
import com.example.back_end.repository.ProductAttributeCombinationRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductAttributeCombinationServiceImpl implements ProductAttributeCombinationService {

    private final ProductAttributeCombinationRepository productAttributeCombinationRepository;
    private final ProductAttributeCombinationMapper productAttributeCombinationMapper;
    private final PictureRepository pictureRepository;
    private final ProductAttributeCombinationPictureRepository combinationPictureRepository;

    @Override
    @Transactional
    public void saveOrUpdateProductAttributeCombination(ProductAttributeCombinationRequest request) {
        ProductAttributeCombination existingCombination = null;
        if (request.getId() != null) {
            existingCombination = productAttributeCombinationRepository.findById(request.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Combination not found with id: " + request.getId()));
        }

        List<ProductAttributeCombination> productAttributeCombinations = productAttributeCombinationRepository
                .findByProductId(request.getProductId());

        if (productAttributeCombinations != null) {
            for (ProductAttributeCombination combination : productAttributeCombinations) {
                if (existingCombination == null || !combination.getId().equals(existingCombination.getId())) {
                    if (combination.getSku().equals(request.getSku())) {
                        throw new ExistsByNameException(Messages.SKU_ALREADY_EXISTS.getMessage());
                    }

                    if (combination.getAttributesXml().equals(request.getAttributesXml())) {
                        throw new ExistsByNameException(Messages.COMBINATION_ALREADY_EXISTS.getMessage());
                    }
                }
            }
        }

        ProductAttributeCombination entity;
        if (existingCombination != null) {
            productAttributeCombinationMapper.updateEntityFromRequest(existingCombination, request);
            entity = existingCombination;
        } else {
            entity = productAttributeCombinationMapper.toEntity(request);
        }

        ProductAttributeCombination entitySave = productAttributeCombinationRepository.save(entity);

        List<Picture> pictures = pictureRepository.findAllById(request.getPictureIds());

        if (pictures.size() != request.getPictureIds().size()) {
            List<Long> foundIds = pictures.stream().map(Picture::getId).toList();
            List<Long> notFoundIds = request.getPictureIds().stream()
                    .filter(pId -> !foundIds.contains(pId))
                    .toList();
            throw new ResourceNotFoundException("Pictures not found with ids: " + notFoundIds);
        }

        if (existingCombination != null) {
            combinationPictureRepository.deleteByProductAttributeCombinationId(request.getId());
        }

        List<ProductAttributeCombinationPicture> attributeCombinationPictures = new ArrayList<>();
        for (Picture picture : pictures) {
            ProductAttributeCombinationPicture combinationPicture = new ProductAttributeCombinationPicture();
            combinationPicture.setPicture(picture);
            combinationPicture.setProductAttributeCombination(entitySave);
            attributeCombinationPictures.add(combinationPicture);
        }

        combinationPictureRepository.saveAll(attributeCombinationPictures);
    }


    @Override
    public List<ProductAttributeCombinationResponse> getByProductId(Long productId) {

        List<ProductAttributeCombination> combinations = productAttributeCombinationRepository
                .findByProductId(productId);

        return combinations
                .stream()
                .map(productAttributeCombinationMapper::toDto)
                .sorted(Comparator.comparing(ProductAttributeCombinationResponse::getId))
                .toList();
    }

    @Override
    public void delete(Long id) {

        ProductAttributeCombination combination = productAttributeCombinationRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Messages.COMBINATION_IS_NOT_EXISTS.getMessage()));

        productAttributeCombinationRepository.delete(combination);
    }


    @Getter
    @AllArgsConstructor
    private enum Messages {
        SKU_ALREADY_EXISTS("SKU already exists"),
        COMBINATION_ALREADY_EXISTS("The same combination already exists"),
        COMBINATION_IS_NOT_EXISTS("The same combination is not exists"),

        INVALID_ID_LIST("Invalid ID list provided");
        private final String message;
    }

}

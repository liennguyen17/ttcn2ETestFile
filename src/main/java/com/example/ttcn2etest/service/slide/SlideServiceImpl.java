package com.example.ttcn2etest.service.slide;

import com.example.ttcn2etest.model.dto.SlideDTO;
import com.example.ttcn2etest.model.etity.Slide;
import com.example.ttcn2etest.repository.slide.CustomSlideRepository;
import com.example.ttcn2etest.repository.slide.SlideRepository;
import com.example.ttcn2etest.request.slide.CreateSlideRequest;
import com.example.ttcn2etest.request.slide.FilterSlideRequest;
import com.example.ttcn2etest.request.slide.UpdateSlideRequest;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SlideServiceImpl implements SlideService{
    private final SlideRepository slideRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    public SlideServiceImpl(SlideRepository slideRepository) {
        this.slideRepository = slideRepository;
    }

    @Override
    public List<SlideDTO> getAll() {
        return slideRepository.findAll().stream().map(
                slide -> modelMapper.map(slide, SlideDTO.class)
        ).collect(Collectors.toList());
    }

    @Override
    public SlideDTO getById(Long id) {
        Optional<Slide> slideOptional = slideRepository.findById(id);
        if(slideOptional.isPresent()){
            return modelMapper.map(slideOptional.get(), SlideDTO.class);
        }else {
            throw new RuntimeException("Id slide không tồn tại trong hệ thống!");
        }
    }

    @Override
    @Transactional
    public SlideDTO createSlide(CreateSlideRequest request) {
        try{
            Slide slide = Slide.builder()
                    .location(request.getLocation())
                    .image(request.getImage())
                    .createdDate(new Timestamp(System.currentTimeMillis()))
                    .updateDate(new Timestamp(System.currentTimeMillis()))
                    .build();
            slide = slideRepository.saveAndFlush(slide);
            return modelMapper.map(slide, SlideDTO.class);
        }catch (Exception ex){
            throw new RuntimeException("Có lỗi xảy ra trong quá trình thêm slide mới!");
        }
    }

    @Override
    public SlideDTO update(UpdateSlideRequest request, Long id) {
        Optional<Slide> slideOptional = slideRepository.findById(id);
        if(slideOptional.isPresent()){
            Slide slide = slideOptional.get();
            slide.setImage(request.getImage());
            slide.setLocation(request.getLocation());
            slide.setUpdateDate(new Timestamp(System.currentTimeMillis()));
            return modelMapper.map(slideRepository.saveAndFlush(slide), SlideDTO.class);
        }
        throw new RuntimeException("Có lỗi xảy ra trong quá trình cập nhật Slide!");
    }

    @Override
    @Transactional
    public SlideDTO deleteByIdService(Long id) {
        if(!slideRepository.existsById(id)){
            throw new RuntimeException("Slide có id:"+id+" cần xóa không tồn tại trong hệ thống!");
        }
        Optional<Slide> slideOptional = slideRepository.findById(id);
        if(slideOptional.isPresent()){
            slideRepository.deleteById(id);
            return modelMapper.map(slideOptional, SlideDTO.class);
        }
        throw new RuntimeException("Có lỗi xảy ra trong quá trình xóa Slide!");
    }

    @Override
    public List<SlideDTO> deleteAllId(List<Long> ids) {
        List<SlideDTO> slideDTOS = new ArrayList<>();
        for(Slide slide: slideRepository.findAllById(ids)){
            slideDTOS.add(modelMapper.map(slide, SlideDTO.class));
        }
        slideRepository.deleteAllByIdInBatch(ids);
        return slideDTOS;
    }


    @Transactional
    public List<SlideDTO> deleteAllById(List<Long> ids) {
        List<SlideDTO> deletedSlides = new ArrayList<>();
        for (Long id : ids) {
            Optional<Slide> optionalSlide = slideRepository.findById(id);
            if (optionalSlide.isPresent()) {
                Slide slide = optionalSlide.get();
                deletedSlides.add(modelMapper.map(slide, SlideDTO.class));
                slideRepository.delete(slide);
            } else {
                throw new RuntimeException("Có lỗi xảy ra trong quá trình xóa danh sách slide!");
            }
        }
        return deletedSlides;
    }

    @Override
    public Page<Slide> filterService(FilterSlideRequest request, Date dateFrom, Date dateTo) {
        Specification<Slide> specification = CustomSlideRepository.filterSpecification(dateFrom, dateTo, request);
        Page<Slide> slidePage = slideRepository.findAll(specification, PageRequest.of(request.getStart(), request.getLimit()));
        return slidePage;
    }
}

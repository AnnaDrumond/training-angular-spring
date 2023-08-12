package pt.airc.training.services;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;
import pt.airc.training.dtos.BrandDTO;
import pt.airc.training.mappers.BrandMapper;
import pt.airc.training.models.Brand;
import pt.airc.training.repository.BrandRepository;
import pt.airc.training.services.BrandService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@Slf4j
@RunWith(SpringRunner.class)
public class BrandServiceTest {
    @InjectMocks
    BrandService brandService;
    @Mock
    BrandRepository brandRepository;
    @Mock
    BrandMapper brandMapper;
    List<Brand> brandList;

    @Before
    public void setup() {
        brandList = new ArrayList<>();
        Brand brand = new Brand();
        brand.setName("Aaaa");
        brandList.add(brand);

    }

    @Test
    public void listAllIsDeletedAtNull() {
        when(brandRepository.findAllByDeletedAtNull()).thenReturn(brandList);
        List<BrandDTO> brandDTOList = brandService.listAllIsDeletedAtNull();
        log.info(brandList.get(0).getName());
        assertEquals(1, brandDTOList.size());
    }


    @Test
    public void create() {
        brandService.create(new BrandDTO());
        verify(brandRepository, times(1)).save(any());
    }

    @Test
    public void listAllIsDeletedAtNullSpec() {

        when(brandRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(brandList));

        Page<BrandDTO> brands = brandService.listAllIsDeletedAtNull(PageRequest.of(0, 5), "");
        assertEquals(1, brands.getContent().size());
    }

}
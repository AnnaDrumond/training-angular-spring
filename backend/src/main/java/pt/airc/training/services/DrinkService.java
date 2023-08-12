package pt.airc.training.services;

import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pt.airc.training.dtos.DrinkDTO;

import pt.airc.training.exceptions.AircTrainingException;
import pt.airc.training.mappers.DrinkMapper;

import pt.airc.training.models.Drink;
import pt.airc.training.enums.TypeDrink;
import pt.airc.training.repository.DrinkRepository;

import pt.airc.training.repository.StorageDrinkRepository;
import pt.airc.training.specifications.DrinkSpecs;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class DrinkService {
    private final DrinkRepository drinkRepository;
    private final DrinkMapper drinkMapper;
    private final EntityManager em;

    private final StorageDrinkRepository storageDrinkRepository;


    public Page<DrinkDTO> listAll(Pageable pageable, String filter) {
        System.out.println("service");
        Specification<Drink> filtersAux = DrinkSpecs.filters(filter);
        System.out.println("voltei do specs e vou ao repository com filtersAux " + filtersAux);
        return this.drinkRepository.findAll(filtersAux, pageable).map(this.drinkMapper::toDTO);
    }

    public Page<DrinkDTO> listAllIsDeletedAtNull(Pageable pageable, String filter) {

        Specification<Drink> filtersAux = DrinkSpecs.filters(filter);
        
        Sort sort = pageable.getSort();
        Sort.Direction direction = null;
        String property = null;
        for (Sort.Order order : sort) {
            direction = order.getDirection();
            property = order.getProperty();
        }
        if (!property.contains("type")) {
            //Neste caso não queremos ordenar por TypeDrink
            return this.drinkRepository.findAll(filtersAux, pageable).map(this.drinkMapper::toDTO);

        }
        //Seria para ordenar por TypeDrink
        return sortBeveragePagesByTypeV2(pageable, direction);
    }

    public PageImpl<DrinkDTO> sortBeveragePagesByTypeV2(Pageable pageable, Sort.Direction direction) {
        List<DrinkDTO> listDrinksToReturn = getListOfNotDeletedOrderedbyType(direction);
        int firstFromTheListToReturn = pageable.getPageNumber() * pageable.getPageSize();
        int lastFromTheListToReturn = Math.min(firstFromTheListToReturn + pageable.getPageSize(), listDrinksToReturn.size());
        return new PageImpl<>(listDrinksToReturn.subList(firstFromTheListToReturn, lastFromTheListToReturn), pageable, listDrinksToReturn.size());
    }

    public List<DrinkDTO> getListOfNotDeletedOrderedbyType(Sort.Direction direction) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Drink> criteriaQuery = criteriaBuilder.createQuery(Drink.class);
        Root<Drink> rootBeverage = criteriaQuery.from(Drink.class);
        Expression<Object> expressionConvertType = criteriaBuilder.selectCase()
                .when(criteriaBuilder.equal(rootBeverage.get("type"), criteriaBuilder.literal(TypeDrink.AGUA)), "Água")
                .when(criteriaBuilder.equal(rootBeverage.get("type"), criteriaBuilder.literal(TypeDrink.SUMOS)), "Sumo")
                .when(criteriaBuilder.equal(rootBeverage.get("type"), criteriaBuilder.literal(TypeDrink.NECTARES)), "Néctar")
                .when(criteriaBuilder.equal(rootBeverage.get("type"), criteriaBuilder.literal(TypeDrink.ALCOOOLICAS)), "Álcool")
                .otherwise("Outros");
        Order orderType;
        if (direction == Sort.Direction.DESC) {
            orderType = criteriaBuilder.desc(expressionConvertType);
        } else {
            orderType = criteriaBuilder.asc(expressionConvertType);
        }
        criteriaQuery.select(rootBeverage)
                .where(em.getCriteriaBuilder().and(em.getCriteriaBuilder().isNull(rootBeverage.get("deletedAt")))).orderBy(orderType);
        return em.createQuery(criteriaQuery).getResultList()
                .stream().map(this.drinkMapper::toDTO).collect(Collectors.toList());
    }


    public List<TypeDrink> findAllEnums() {
        return new ArrayList<>(EnumSet.allOf(TypeDrink.class));
    }

    public DrinkDTO create(DrinkDTO drinkDTO) {
        Drink drink = this.drinkMapper.ToModel(drinkDTO);
        this.drinkRepository.save(drink);
        return this.drinkMapper.toDTO(drink);
    }

    public DrinkDTO getById(Long id) {
        return this.drinkMapper.toDTO(this.drinkRepository.findById(id).orElseThrow(() -> {
            throw new AircTrainingException(HttpStatus.NOT_FOUND, "Não encontrada entidada para chave primária " + id);
        }));
    }

    public DrinkDTO edit(DrinkDTO drinkDTO, Long id) {

        Drink drink = this.drinkMapper.updateToEntityFromDto(drinkDTO,
                this.drinkRepository.findById(id).orElseThrow(() -> {
                    throw new AircTrainingException(HttpStatus.NOT_FOUND, "Não encontrada entidada para chave primária " + id);
                }));
        this.drinkRepository.save(drink);
        return this.drinkMapper.toDTO(drink);
    }

    public void doSoftdelete(Long id) {
        Drink drink = this.drinkRepository.findById(id).orElseThrow(() -> {
            throw new AircTrainingException(HttpStatus.NOT_FOUND, "Não encontrada entidada para chave primária " + id);
        });

        if (this.storageDrinkRepository.existsByDrinkId(id)) {
            throw new AircTrainingException(HttpStatus.CONFLICT, "Operação não permitida. Existem bebidas disponiveis em armazens.");
        }

        drink.setDeletedAt(Timestamp.from(ZonedDateTime.now().toInstant()));
        this.drinkRepository.save(drink);
    }

    public List<DrinkDTO> getDrinksByBrandId(Long id) {
        return this.drinkRepository.findAllByBrandIdAndDeletedAtIsNull(id).stream()
                .map(this.drinkMapper::toDTO).collect(Collectors.toList());
    }


    public List<DrinkDTO> listAllIsDeletedAtNullSimple() {
        return this.drinkRepository.findAllByDeletedAtNull().stream()
                .map(this.drinkMapper::toDTO).collect(Collectors.toList());
    }
}

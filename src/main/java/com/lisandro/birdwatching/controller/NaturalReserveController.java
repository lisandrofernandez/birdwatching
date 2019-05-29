package com.lisandro.birdwatching.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import java.net.URI;
import java.util.List;

import com.lisandro.birdwatching.core.ApiError;
import com.lisandro.birdwatching.core.BusinessException;
import com.lisandro.birdwatching.dto.NaturalReserveDTO;
import com.lisandro.birdwatching.dto.NaturalReserve_TupleDTO;
import com.lisandro.birdwatching.service.NaturalReserveNotFoundException;
import com.lisandro.birdwatching.service.NaturalReserveService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(NaturalReserveController.BASE_URL)
public class NaturalReserveController {

    public static final String BASE_URL = "/api/v1/reserves";

    @Autowired
    private NaturalReserveService reserveService;

    @GetMapping
    public List<NaturalReserve_TupleDTO> allTuples() {
        return reserveService.findAllTuples();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        NaturalReserveDTO reserveDTO = reserveService.findByIdDTO(id);
        if (reserveDTO == null) {
            ApiError apiError = new ApiError(
                    HttpStatus.NOT_FOUND,
                    "Natural reserve not found",
                    "There is no natural reserve with ID = " + id
            );
            return ResponseEntity.status(apiError.getStatus()).body(apiError);
        }
        return ResponseEntity.ok(reserveDTO);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody NaturalReserveDTO reserveDTO) {
        return createOrUpdate(reserveDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestBody NaturalReserveDTO reserveDTO
    ) {
        reserveDTO.setId(id); // use ID in URL
        return createOrUpdate(reserveDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        ApiError apiError = null;
        try {
            reserveService.deleteById(id);
        } catch (NaturalReserveNotFoundException e) {
            apiError = new ApiError(
                    HttpStatus.NOT_FOUND,
                    "Natural reserve not found",
                    "There is no natural reserve with ID = " + id
            );
        } catch (Exception e) {
            apiError = new ApiError(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Internal server error",
                    "There was an error while processing the request"
            );
        }
        if (apiError != null) {
            return ResponseEntity.status(apiError.getStatus()).body(apiError);
        }
        return ResponseEntity.noContent().build();
    }

    private ResponseEntity<?> createOrUpdate(NaturalReserveDTO reserveDTO) {
        ApiError apiError = null;
        try {
            reserveDTO = reserveService.createorUpdateDTO(reserveDTO);
        } catch (BusinessException e) {
            apiError = new ApiError(HttpStatus.BAD_REQUEST, "Bad Request", e.getMessage());
        } catch (Exception e) {
            apiError = new ApiError(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Internal server error",
                    "There was an error while processing the request"
            );
        }
        if (apiError != null) {
            return ResponseEntity.status(apiError.getStatus()).body(apiError);
        }
        URI location = linkTo(methodOn(NaturalReserveController.class).findById(reserveDTO.getId()))
                .toUri();
        return ResponseEntity.created(location).body(reserveDTO);
    }

}

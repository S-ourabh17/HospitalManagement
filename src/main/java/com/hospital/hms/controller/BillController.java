package com.hospital.hms.controller;


import com.hospital.hms.dtos.Dtos.BillResponse;
import com.hospital.hms.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/bills")
public class BillController {
    private BillService billService;


    @PostMapping("appointment/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public BillResponse generate(@PathVariable(name = "id") Long appointmentId){
        return billService.generateForAppointment(appointmentId);
    }

    @GetMapping("{id}")
    public BillResponse getById(@PathVariable Long id){
        return billService.getById(id);
    }

    @PatchMapping("/{id}/pay")
    public BillResponse pay(@PathVariable Long id) {
        return billService.pay(id);
    }


    @PatchMapping("/{id}/cancel")
    public BillResponse cancel(@PathVariable Long id) {
        return billService.cancel(id);
    }

}

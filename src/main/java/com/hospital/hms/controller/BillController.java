package com.hospital.hms.controller;


import com.hospital.hms.dtos.Dtos.BillResponse;
import com.hospital.hms.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bills")
@RequiredArgsConstructor
public class BillController {
    private final BillService bills;

    @PostMapping("/appointment/{appointmentId}")
    @ResponseStatus(HttpStatus.CREATED)
    public BillResponse generateBill(@PathVariable Long appointmentId){
        return bills.generateForAppointment(appointmentId);

    }

    @PatchMapping("/{id}/pay")
    public BillResponse payBill(@PathVariable Long id){
        return bills.pay(id);
    }

    @GetMapping("/{id}")
    public BillResponse findBillById(@PathVariable Long id){
        return bills.getById(id);
    }


    @PatchMapping("/id")
    public BillResponse cancel(@PathVariable Long id){
        return bills.cancel(id);
    }

}

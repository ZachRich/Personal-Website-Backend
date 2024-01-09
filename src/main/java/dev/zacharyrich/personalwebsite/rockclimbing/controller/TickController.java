package dev.zacharyrich.personalwebsite.rockclimbing.controller;

import dev.zacharyrich.personalwebsite.rockclimbing.model.Tick;
import dev.zacharyrich.personalwebsite.rockclimbing.service.TickService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/tick")
public class TickController {

    Logger logger = Logger.getLogger(TickController.class.getName());

    private final TickService tickService;


    public TickController(TickService tickService) {
        this.tickService = tickService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Tick>> getAllTicks() {
        List<Tick> tickList = tickService.findAllTicks();
        logger.info("Getting all Ticks.");
        return new ResponseEntity<>(tickList, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Tick> addTick(@RequestBody Tick tick) {
        Tick newTick = tickService.addTick(tick);
        return new ResponseEntity<>(newTick, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Tick> updateTick(@RequestBody Tick tick) {
        Tick updatedTick = tickService.updateTick(tick);
        return new ResponseEntity<>(updatedTick, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTickById(@PathVariable("id") Long id) {
        tickService.deleteTick(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/find/{id}")
    public ResponseEntity<Tick> getTickById(@PathVariable("id") Long id) {
        Tick tick = tickService.findTickById(id);
        return new ResponseEntity<>(tick, HttpStatus.OK);
    }



}

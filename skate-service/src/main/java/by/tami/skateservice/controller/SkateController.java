package by.tami.skateservice.controller;

import by.tami.skateservice.dto.*;
import by.tami.skateservice.dto.GetSkateResponse;
import by.tami.skateservice.service.SkateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/skates")
public class SkateController {

    private final SkateService skateService;

    @GetMapping
    public ResponseEntity<CursorResponse<SkateDto>> getSkates(
            @RequestParam(name = "cursor", required = false) String cursor,
            @RequestParam(name = "size", defaultValue = "10", required = false) Integer size
    ) {
        var args = GetSkatesArgs.builder()
                .cursor(cursor)
                .size(size)
                .build();

        return ResponseEntity.ok(skateService.getSkates(args));
    }

    @PostMapping
    public ResponseEntity<CreateSkateResponse> createSkate(@RequestBody CreateSkateArgs args) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(skateService.createSkate(args));
    }


    @PutMapping("/{id}")
    public ResponseEntity<UpdateSkateResponse> updateSkate(
            @RequestBody UpdateSkateArgs args,
            @PathVariable Long id
    ) {
        args.setId(id);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(skateService.updateSkate(args));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetSkateResponse> getSkateById(@PathVariable Long id) {
        return ResponseEntity.ok(skateService.getSkateById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSkate(@PathVariable Long id) {
        skateService.deleteSkate(id);
        return ResponseEntity.noContent().build();
    }
}

package by.tami.skateservice.service;

import by.tami.skateservice.dto.*;
import by.tami.skateservice.exception.BadRequestException;
import by.tami.skateservice.exception.NotFoundException;
import by.tami.skateservice.mapper.SkateMapper;
import by.tami.skateservice.model.Skate;
import by.tami.skateservice.repository.SkateRepository;
import by.tami.skateservice.util.CursorEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SkateService {

    private final SkateRepository skateRepository;

    public CursorResponse<SkateDto> getSkates(GetSkatesArgs args) {
        Long lastId = CursorEncoder.decode(args.getCursor());

        /* adding extra size to check if it has more for the next fetching
         * the result list will be sliced afterward
         * */
        var pageable = PageRequest.of(0, args.getSize() + 1);
        List<Skate> skates = skateRepository.fetchNextPage(lastId, pageable);

        boolean hasNext = skates.size() > args.getSize();
        if (hasNext) {
            /* removing the last element */
            skates = skates.subList(0, args.getSize());
        }

        String nextCursor = hasNext
                ? CursorEncoder.encode(skates.getLast().getId())
                : null;

        return CursorResponse.<SkateDto>builder()
                .content(skates.stream().map(SkateMapper::toDto).toList())
                .hasNext(hasNext)
                .size(args.getSize())
                .nextCursor(nextCursor)
                .build();
    }

    public CreateSkateResponse createSkate(CreateSkateArgs args) {
        if (skateRepository.existsBySkateIdentityNumber(args.getSkateIdentityNumber())) {
            throw new BadRequestException("Коньки с таким идентификатором " + args.getSkateIdentityNumber() + " уже существует");
        }

        Skate skate = Skate.builder()
                .skateIdentityNumber(args.getSkateIdentityNumber())
                .size(args.getSize())
                .sex(args.getSex())
                .isAvailable(args.getIsAvailable())
                .build();

        skate = skateRepository.save(skate);
        return CreateSkateResponse.builder()
                .data(SkateMapper.toDto(skate))
                .build();
    }

    public UpdateSkateResponse updateSkate(UpdateSkateArgs args) {
        var skate = skateRepository.findById(args.getId())
                .orElseThrow(() -> new NotFoundException("Коньки с таким идентификатором " + args.getId() + " не найдены"));

        if (args.getIsAvailable() != null && skate.getIsAvailable() != args.getIsAvailable()) {
            skate.setIsAvailable(args.getIsAvailable());
        }

        if (args.getSize() != null && !Objects.equals(skate.getSize(), args.getSize())) {
            skate.setSize(args.getSize());
        }

        if (args.getSex() != null && !skate.getSex().equals(args.getSex())) {
            skate.setSex(args.getSex());
        }

        skate = skateRepository.save(skate);

        return UpdateSkateResponse.builder()
                .data(SkateMapper.toDto(skate))
                .build();
    }

    public GetSkateResponse getSkateById(Long id) {
        var skate = skateRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Коньки с таким идентификатором " + id + " не найдены"));
        return GetSkateResponse.builder()
                .data(SkateMapper.toDto(skate))
                .build();
    }

    public void deleteSkate(Long id) {
        skateRepository.deleteById(id);
    }

}

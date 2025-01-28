package edu.java.scrapper.controllers.dto;

import edu.java.scrapper.repositories.jpa.entity.Link;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Component
@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy =  ReportingPolicy.ERROR,
    unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public abstract class LinkMapper {

    public abstract LinkDtoOut linkToLinkDtoOut(Link link);

}
